
let currentStep = 1;
let selectedType = '';
let navigationHistory = ['module-selection'];

let apiData = { deptCode: '', estabNo: '', userEmiratesId: '' };

let storedEstabDetails = null;
let storedOwnerDetails = null;

const progressBar = document.getElementById('progress-bar');
const stepCount = document.getElementById('step-count');
const backBtn = document.getElementById('btn-back');

const MAX_PDF_SIZE_KB = document.getElementById('MAX_PDF_SIZE_KB').value;
const portalUrl = document.getElementById('portalUrl').value;

updateProgress(1);

function selectType(type) {
    selectedType = type;
    let nextModuleId = type === 'dept' ? 'module-dept-form' : 'module-est-form';
    nextStep(nextModuleId);
}

function nextStep(moduleId) {
    const currentModuleId = navigationHistory[navigationHistory.length - 1];
    document.getElementById(currentModuleId).classList.remove('active');
    document.getElementById(moduleId).classList.add('active');
    navigationHistory.push(moduleId);
    currentStep++;
    updateProgress(currentStep);
    if (navigationHistory.length > 1) backBtn.style.display = 'flex';
}

function goBack() {
    if (navigationHistory.length <= 1) return;
    const currentModuleId = navigationHistory.pop();
    document.getElementById(currentModuleId).classList.remove('active');
    const prevModuleId = navigationHistory[navigationHistory.length - 1];
    document.getElementById(prevModuleId).classList.add('active');
    currentStep--;
    updateProgress(currentStep);
    if (navigationHistory.length === 1) backBtn.style.display = 'none';
}

function updateProgress(step) {
    const totalSteps = 5;
    const percent = (step / totalSteps) * 100;
    progressBar.style.width = percent + '%';
    stepCount.innerText = `Step ${step} of ${totalSteps}`;
}


function validateAndNext(containerId) {
    const container = document.getElementById(containerId);
    const inputs = container.querySelectorAll('input');
    let isValid = true;
    inputs.forEach(input => {
        if (!input.value.trim()) { input.classList.add('error'); input.nextElementSibling.style.display = 'block'; isValid = false; }
        else { input.classList.remove('error'); input.nextElementSibling.style.display = 'none'; }
    });

    if (isValid) {
        if (selectedType === 'dept') {
            apiData.userEmiratesId = document.getElementById('dept-emirates').value.trim();
        } else {
            apiData.userEmiratesId = document.getElementById('est-emirates').value.trim();
        }
        nextStep('module-verification');
        startVerificationSequence();
    }
}


async function startVerificationSequence() {
    resetVerificationUI();
    updateVerifyStatus('v-step-director', 'loading');
    setTimeout(async () => {
        updateVerifyStatus('v-step-director', 'success');

        updateVerifyStatus('v-step-estab', 'loading');
        const estabSuccess = await callEstabDetailsApi();

        if (estabSuccess) {
            updateVerifyStatus('v-step-estab', 'success');

            updateVerifyStatus('v-step-board', 'loading');
            const ownerSuccess = await callEstabOwnersApi();

            if (ownerSuccess) {
                updateVerifyStatus('v-step-board', 'success');
                showSuccessState();
            } else {
                updateVerifyStatus('v-step-board', 'error');
            }
        } else {
            updateVerifyStatus('v-step-estab', 'error');
        }
    }, 1000);
}

async function callEstabDetailsApi() {

    const url = portalUrl + '/api/public/estabDetails';
    let requestBody = { "transactionRefNo": "TX123" };
    if (selectedType === 'dept') {
        requestBody.deptCode = document.getElementById('dept-code').value.trim();
        requestBody.estabNo = document.getElementById('dept-number').value.trim();
    } else {
        requestBody.licenseNo = document.getElementById('est-num').value.trim();
    }

    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestBody)
        });
        const data = await response.json();

        if (data.success && data.result && data.result.estabDetails) {
            apiData.deptCode = data.result.estabDetails.deptCode;
            apiData.estabNo = data.result.estabDetails.estabNo;

          
            storedEstabDetails = data.result.estabDetails;
           
            return true;
        } else {
            showFailureState("Establishment details not found");
            return false;
        }
    } catch (error) {
        console.error(error);
        showFailureState("Network error while connecting to Establishment API.");
        return false;
    }
}

async function callEstabOwnersApi() {
    const url = portalUrl + '/api/public/estabOwners';
    const requestBody = {
        "transactionRefNo": "TX123",
        "deptCode": apiData.deptCode,
        "estabNo": apiData.estabNo
    };

    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestBody)
        });
        const data = await response.json();

        if (data.success && data.result && data.result.estabOwners) {
            let owners = data.result.estabOwners.estabOwner;
            if (!Array.isArray(owners)) owners = [owners];

           
            storedOwnerDetails = owners;

            const userInput = apiData.userEmiratesId;
            let isMatchFound = false;
            for (let owner of owners) {
                if (owner.idn === userInput || owner.passportNo === userInput) {
                    isMatchFound = true;
                    document.getElementById('director-name').value = owner.fullEnName || '';
                    break;
                }
            }
            if (isMatchFound) return true;
            else {
                showFailureState("Verification Failed: Emirates Number not found in Board of Directors.");
                return false;
            }
        } else {
            showFailureState("No owners found.");
            return false;
        }
    } catch (error) {
        console.error(error);
        showFailureState("Network error while connecting to Owners API.");
        return false;
    }
}

function updateVerifyStatus(elementId, status) {
    const el = document.getElementById(elementId);
    el.classList.remove('loading', 'success');
    if (status === 'loading') el.classList.add('loading');
    if (status === 'success') el.classList.add('success');
}

function resetVerificationUI() {
    const heading = document.getElementById('verify-heading');
    const subtext = document.getElementById('verify-subtext');
    heading.innerText = "Verifying Details";
    heading.style.color = "var(--text-dark)";
    subtext.innerText = "Your information is being verified. Please wait.";
    document.querySelectorAll('.v-item').forEach(el => el.classList.remove('loading', 'success'));
    document.getElementById('api-error-msg').style.display = 'none';
    document.getElementById('btn-continue-upload').style.display = 'none';
    document.getElementById('btn-back-login').style.display = 'none';
}

function showSuccessState() {
    const heading = document.getElementById('verify-heading');
    heading.innerText = "Verified";
    heading.style.color = "var(--success)";
    document.getElementById('verify-subtext').innerText = "Verified successfully. Please continue.";
    document.getElementById('btn-continue-upload').style.display = 'block';
}

function showFailureState(msg) {
    const heading = document.getElementById('verify-heading');
    heading.innerText = "Verification Failed";
    heading.style.color = "var(--error)";
    document.getElementById('verify-subtext').innerText = "We could not verify your details.";
    const errDiv = document.getElementById('api-error-msg');
    errDiv.innerText = msg;
    errDiv.style.display = 'block';
    document.getElementById('btn-back-login').style.display = 'block';
}


function updateFileName(input, spanId) {
    if (input.files && input.files.length > 0) {
        document.getElementById(spanId).innerText = input.files[0].name;
        document.getElementById(spanId).style.color = '#111827';
    }
}
function validateDocs() {

    const fileInputs = [
        { input: document.getElementById('file-spoc'), errorId: 'err-file-spoc' },
        { input: document.getElementById('file-est'),  errorId: 'err-file-est' }
    ];

    for (const { input, errorId } of fileInputs) {

        const errorEl = document.getElementById(errorId);

     
        if (!input.files || input.files.length === 0) {
            errorEl.innerText = "Document required (PDF only)";
            errorEl.style.display = 'block';
            return;
        }

        const file = input.files[0];
        const fileSizeKb = file.size / 1024;

        
        const isPdf =
            file.type === "application/pdf" ||
            file.name.toLowerCase().endsWith(".pdf");

        if (!isPdf) {
            errorEl.innerText = "Only PDF files are allowed";
            errorEl.style.display = 'block';
            input.value = ""; 
            return;
        }

      
        if (fileSizeKb > MAX_PDF_SIZE_KB) {
            errorEl.innerText = `File exceeds ${MAX_PDF_SIZE_KB} KB limit`;
            errorEl.style.display = 'block';
            input.value = "";
            return;
        }

     
        errorEl.style.display = 'none';
    }

  
    nextStep('module-final');
}



function validateAndComplete() {
    const container = document.getElementById('module-final');
    const mandatoryInputs = container.querySelectorAll('.mandatory');
    let isValid = true;

    mandatoryInputs.forEach(input => {
        if (!input.value.trim()) {
            input.classList.add('error');
            input.nextElementSibling.style.display = 'block';
            isValid = false;
        } else {
            input.classList.remove('error');
            input.nextElementSibling.style.display = 'none';
        }
    });

    
    const emailFields = [
        document.getElementById('spoc-email'),
        document.getElementById('director-email')
    ];

    emailFields.forEach(input => {
        if (input.value.trim() && !isValidEmail(input.value.trim())) {
            input.classList.add('error');
            input.nextElementSibling.textContent = 'Invalid email format';
            input.nextElementSibling.style.display = 'block';
            isValid = false;
        }
    });

    
    const auditorEmail = document.getElementById('auditor-email');
    if (auditorEmail.value.trim() && !isValidEmail(auditorEmail.value.trim())) {
        auditorEmail.classList.add('error');
        auditorEmail.nextElementSibling.textContent = 'Invalid email format';
        auditorEmail.nextElementSibling.style.display = 'block';
        isValid = false;
    }

   
    if (isValid) {
        submitRegistration();
    }
}


function isValidEmail(email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}



async function submitRegistration() {
    const btn = document.querySelector('#module-final .btn-primary');
    btn.innerText = "Processing...";
    btn.disabled = true;

    try {

        const spocFile = document.getElementById('file-spoc').files[0];
        const estFile = document.getElementById('file-est').files[0];


        const directorEmail = document.getElementById('director-email').value.trim();
        let boardOfDirectorsList = [];

        if (storedOwnerDetails) {
            storedOwnerDetails.forEach(owner => {
                const isAuthorized = (owner.idn === apiData.userEmiratesId || owner.passportNo === apiData.userEmiratesId);
                const ownerEmail = isAuthorized ? directorEmail : "";

                boardOfDirectorsList.push({
                    fullName: owner.fullEnName,
                    country: owner.country,
                    passportNo: owner.passportNo,
                    ownerClass: owner.ownerClass,
                    ownerStatus: owner.ownerStatus,
                    email: ownerEmail,
                    bodEmail: "",
                    isSpocAuthorizedByBoard: isAuthorized
                });
            });
        }


        let auditorObj = null;
        const audName = document.getElementById('auditor-name').value.trim();
        const audEmail = document.getElementById('auditor-email').value.trim();
        const audDoc = document.getElementById('auditor-doc').value.trim();

        if (audName || audEmail || audDoc) {
            auditorObj = {
                auditorName: audName,
                auditorDocumentNumber: audDoc,
                auditorOfficalEmail: audEmail
            };
        }


        let rawEmiratesCode = storedEstabDetails.emirateCode || storedEstabDetails.emiratesCode;

        let validEmiratesCode = rawEmiratesCode ? parseInt(rawEmiratesCode) : 999;




        const dto = {
            organization: {
                orgName: storedEstabDetails.estabEnName,
                orgNo: storedEstabDetails.estabNo,
                deptCode: storedEstabDetails.deptCode,
                orgType: storedEstabDetails.estabType,
                licenseNo: storedEstabDetails.licenseNo,

                licenseIssueDate: storedEstabDetails.licenseIssueDate ? storedEstabDetails.licenseIssueDate.split('T')[0] : "2024-01-01",
                licenseExpiryDate: storedEstabDetails.licenseExpiryDate ? storedEstabDetails.licenseExpiryDate.split('T')[0] : "2025-01-01",
                emiratesCode: validEmiratesCode,

                spocAuthorizationLetter: spocFile.name,
                organizationLetter: estFile.name
            },
            boardOfDirectors: boardOfDirectorsList,
            spocDetails: {
                spocName: document.getElementById('spoc-name').value.trim(),
                spocOfficalEmail: document.getElementById('spoc-email').value.trim(),
                spocDocumentNumber: document.getElementById('spoc-doc').value.trim()
            },
            auditorDetails: auditorObj
        };

        console.log("Payload:", dto);


        const formData = new FormData();
        formData.append('data', new Blob([JSON.stringify(dto)], { type: 'application/json' }));
        formData.append('spocAuthLetter', spocFile);
        formData.append('organizationLetter', estFile);
        showLoader();

        const response = await fetch(portalUrl + '/api/public/save', {
            method: 'POST',
            body: formData
        });

        if (response.ok) {
            hideLoader();
            const result = await response.json();

            if (result.success) {

                Swal.fire({
                    icon: 'success',
                    title: 'Registration Successful',
                    text: result.message

                }).then(() => {
                     window.location.href = portalUrl;
                });

            } else {
                Swal.fire({
                    icon: 'info',
                    title: 'Registration Info',
                    text: result.message

                }).then(() => {
                    window.location.href = portalUrl;
                });

            }

        } else {
            hideLoader()

            const errorText = await response.json();
    

            Swal.fire({
                icon: 'error',
                title: 'Registration Failed',
                text: errorText.message || 'Server error occurred. Please try again later.'
            });
        }

    } catch (error) {
        hideLoader();
        console.error("Submission Error:", error);
        alert("An error occurred during submission.");
    } finally {
        hideLoader();
        btn.innerText = "Complete Registration";
        btn.disabled = false;
    }
}
