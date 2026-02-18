
let currentStep = 1;
let metaDocuments = [];
const fileStore = {};
const portalUrl = document.getElementById('portalUrl')?.value || '';

document.addEventListener("DOMContentLoaded", function () {

    fetchCategories();
    fetchMetaDocuments();

    addLiveEmailValidation("orgEmail");
    addLiveEmailValidation("auditor-email");
    addLiveAlphaNumericValidation("orgName", "Only letters, numbers, and spaces are allowed");
    addLiveAlphaNumericValidation("taxNumber", "Only letters, numbers, and spaces are allowed");
    addLiveAlphaNumericValidation("regNo", "Only letters, numbers, and spaces are allowed");
    addLiveAlphaNumericValidation("spoc-doc", "Only letters, numbers, and spaces are allowed");
    addLiveAlphaNumericValidation("auditor-name", "Only letters, numbers, and spaces are allowed");
    addLiveAlphaNumericValidation("auditor-doc", "Only letters, numbers, and spaces are allowed");
    setupLiveStepValidation();

});
function addLiveEmailValidation(inputId) {
    const input = document.getElementById(inputId);
    if (!input) return;

    input.addEventListener("input", function () {
        const email = input.value.trim();
        const errorEl = input.closest(".form-group").querySelector(".error-msg");

        if (!email) {
            input.classList.remove("error");
            errorEl.style.display = "none";
            return;
        }

        if (!isValidEmail(email)) {
            input.classList.add("error");
            errorEl.textContent = "Invalid email format";
            errorEl.style.display = "block";
        } else {
            input.classList.remove("error");
            errorEl.style.display = "none";
        }
    });
}

function addLiveAlphaNumericValidation(inputId, errorMessage) {
    const input = document.getElementById(inputId);
    if (!input) return;

    input.addEventListener("input", function () {
        const value = input.value;
        const errorEl = input.closest(".form-group")?.querySelector(".error-msg");

        if (!value.trim()) {
            input.classList.remove("error");
            if (errorEl) errorEl.style.display = "none";
            return;
        }

        if (!isAlphaNumericSpace(value)) {
            input.classList.add("error");
            if (errorEl) {
                errorEl.textContent = errorMessage;
                errorEl.style.display = "block";
            }
        } else {
            input.classList.remove("error");
            if (errorEl) errorEl.style.display = "none";
        }
    });
}


function isValidEmail(email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}
function isAlphaNumericSpace(value) {
    return /^[A-Za-z0-9 ]+$/.test(value);
}

function setupLiveStepValidation() {
    document.querySelectorAll(".form-input, input[type='checkbox'], select")
        .forEach(el => {
            el.addEventListener("input", updateNavigationButtons);
            el.addEventListener("change", updateNavigationButtons);
        });

    updateNavigationButtons();
}
function updateNavigationButtons() {
    const nextBtn = document.getElementById("nextBtn");
    const submitBtn = document.getElementById("submitBtn");

    const isValid = validateCurrentStep();

    if (currentStep < 3) {
        nextBtn.disabled = !isValid;
        nextBtn.classList.toggle("disabled", !isValid);
    } else {
        submitBtn.disabled = !isValid;
        submitBtn.classList.toggle("disabled", !isValid);
    }
}
function changeStep(n) {
    if (n === 1 && !validateCurrentStep()) return;

    const steps = document.querySelectorAll(".form-step");
    const indicators = document.querySelectorAll(".stepper-item");

    steps[currentStep - 1].classList.remove("active");
    currentStep += n;
    steps[currentStep - 1].classList.add("active");


    indicators.forEach((ind, idx) => {
        ind.classList.remove("active");
        if (idx + 1 === currentStep) ind.classList.add("active");
        if (idx + 1 < currentStep) ind.classList.add("completed");
        else ind.classList.remove("completed");
    });

    document.getElementById("prevBtn").style.display = currentStep === 1 ? "none" : "inline-flex";
    document.getElementById("nextBtn").style.display = currentStep === 3 ? "none" : "inline-flex";
    document.getElementById("submitBtn").style.display = currentStep === 3 ? "inline-flex" : "none";

    window.scrollTo({ top: 0, behavior: 'smooth' });
}


async function fetchMetaDocuments() {
    try {

        const response = await fetch(portalUrl + "/api/meta-documents");
        const data = await response.json();
        if (data.success) {
            metaDocuments = data.result;
            renderDocumentFields();
        }
    } catch (err) {
        console.error("Failed to fetch meta documents", err);
    }
}

function renderDocumentFields() {
    const container = document.getElementById('dynamicDocumentsContainer');
    if (!container) return;
    container.innerHTML = '';

    metaDocuments.forEach(doc => {
        const cardHtml = `
            <div class="doc-card" id="card-${doc.id}">
                <div class="doc-header">
                    <div class="doc-info">
                        <h3>${doc.documentLabel.replace(/_/g, ' ')} ${doc.mandatory ? '<span class="required">*</span>' : ''}</h3>
                        <p>${doc.documentType.toUpperCase()} (Max ${doc.documentSizeKb}KB)</p>
                    </div>
                    <div class="doc-status-icon" id="status-icon-${doc.id}">
                        <!-- Use standard src and the window.iconPaths object -->
                        <img src="${window.iconPaths.checkCircle}" style="width:20px; display:none;" id="check-${doc.id}">
                        <img src="${window.iconPaths.files}" style="width:20px;" id="file-icon-${doc.id}">
                    </div>
                </div>

                <div class="upload-success-banner" id="banner-${doc.id}" style="display:none; background:#f0fdf4; border:1px solid #bbf7d0; color:#166534; padding:10px; border-radius:8px; margin-bottom:15px; font-size:13px; align-items:center; gap:8px;">
                    <span style="font-weight:bold;">✓</span> Document uploaded successfully
                </div>

                <div class="upload-zone" id="zone-${doc.id}" onclick="document.getElementById('input-${doc.id}').click()"
                     style="border:1px dashed #d1d5db; border-radius:10px; padding:30px; text-align:center; cursor:pointer; background:#fff;">
                    <div class="upload-zone-content" style="display:flex; flex-direction:column; align-items:center; gap:10px;">
                        <img src="${window.iconPaths.uploadCloud}" style="width:24px;">
                        <span id="text-${doc.id}" style="font-size:14px; color:#374151; font-weight:500;">Click to upload</span>
                    </div>
                    <input type="file"
                           id="input-${doc.id}"
                           style="display:none"
                           accept="${doc.documentType.split(',').map(t => '.' + t.trim()).join(',')}"
                           onchange="handleFileSelect(event, ${doc.id})">
                </div>
                <div class="error-msg" id="error-${doc.id}" style="color:red; font-size:12px; margin-top:8px; display:none;"></div>
            </div>
        `;
        container.insertAdjacentHTML('beforeend', cardHtml);
    });
}

function handleFileSelect(event, id) {
    const file = event.target.files[0];
    const meta = metaDocuments.find(m => m.id === id);
    const card = document.getElementById(`card-${id}`);
    const banner = document.getElementById(`banner-${id}`);
    const check = document.getElementById(`check-${id}`);
    const fileIcon = document.getElementById(`file-icon-${id}`);
    const errorEl = document.getElementById(`error-${id}`);
    const textEl = document.getElementById(`text-${id}`);
    const zone = document.getElementById(`zone-${id}`);

    if (!file) return;


    const ext = file.name.split('.').pop().toLowerCase();
    const allowed = meta.documentType.toLowerCase().split(',').map(s => s.trim());

    if (!allowed.includes(ext)) {
        showError(id, `Invalid type. Allowed: ${meta.documentType}`);
        resetFileState(id);
        return;
    }

    if (file.size > meta.documentSizeKb * 1024) {
        showError(id, `File exceeds ${meta.documentSizeKb}KB`);
        resetFileState(id);
        return;
    }

    errorEl.style.display = 'none';
    banner.style.display = 'flex';
    check.style.display = 'block';
    fileIcon.style.display = 'none';
    textEl.textContent = "Click to replace";
    zone.style.borderColor = "#10b981";
    zone.style.backgroundColor = "#f0fdf4";


    fileStore[meta.documentLabel] = file;
}

function showError(id, msg) {
    const err = document.getElementById(`error-${id}`);
    err.textContent = msg;
    err.style.display = 'block';
}

function resetFileState(id) {
    const meta = metaDocuments.find(m => m.id === id);
    delete fileStore[meta.documentLabel];
    document.getElementById(`banner-${id}`).style.display = 'none';
    document.getElementById(`check-${id}`).style.display = 'none';
    document.getElementById(`file-icon-${id}`).style.display = 'block';
    document.getElementById(`text-${id}`).textContent = "Click to upload";
}


function validateCurrentStep() {
    let isValid = true;
    const container = document.getElementById(`step-${currentStep}`);

    container.querySelectorAll('.error-msg').forEach(e => e.style.display = 'none');
    container.querySelectorAll('.form-input').forEach(i => i.classList.remove('error'));

    if (currentStep === 1) {

        container.querySelectorAll('.mandatory').forEach(input => {
            if (!input.value.trim()) {
                setInputError(input, "Required");
                isValid = false;
            }

        });
        const orgEmail = document.getElementById("orgEmail");
        if (orgEmail.value && !isValidEmail(orgEmail.value)) {
            setInputError(orgEmail, "Invalid email format");
            isValid = false;
        }
        const orgName = document.getElementById("orgName");
        if (orgName.value && !isAlphaNumericSpace(orgName.value)) {
            setInputError(orgName, "Only letters, numbers, and spaces are allowed");
            isValid = false;
        }

        if (document.getElementById('taxToggle').checked) {
            const tax = document.getElementById("taxNumber");
            if (tax.value && !isAlphaNumericSpace(tax.value)) {
                setInputError(tax, "Only letters, numbers, and spaces are allowed");
                isValid = false;
            }
        }

        if (document.getElementById('regToggle').checked) {
            const reg = document.getElementById("regNo");
            if (reg.value && !isAlphaNumericSpace(reg.value)) {
                setInputError(reg, "Only letters, numbers, and spaces are allowed");
                isValid = false;
            }
        }


        if (document.getElementById('taxToggle').checked && !document.getElementById('taxNumber').value.trim()) {
            setInputError(document.getElementById('taxNumber'), "Required");
            isValid = false;
        }
        if (document.getElementById('regToggle').checked && !document.getElementById('regNo').value.trim()) {
            setInputError(document.getElementById('regNo'), "Required");
            isValid = false;
        }
    }
    else if (currentStep === 2) {

        metaDocuments.forEach(doc => {
            if (doc.mandatory && !fileStore[doc.documentLabel]) {
                showError(doc.id, "This document is mandatory");
                isValid = false;
            }
        });
    }
    else if (currentStep === 3) {


        const spocDoc = document.getElementById("spoc-doc");
        const value = spocDoc.value.trim();

        if (!value) {
            setInputError(spocDoc, "Required");
            isValid = false;
        }
        else if (!isAlphaNumericSpace(value)) {
            setInputError(spocDoc, "Only letters, numbers, and spaces are allowed");
            isValid = false;
        }


        const audName = document.getElementById("auditor-name");
        const audEmail = document.getElementById("auditor-email");
        const audDoc = document.getElementById("auditor-doc");

        if (audName.value && !isAlphaNumericSpace(audName.value)) {
            setInputError(audName, "Only letters, numbers, and spaces are allowed");
            isValid = false;
        }

        if (audEmail.value && !isValidEmail(audEmail.value)) {
            setInputError(audEmail, "Invalid email format");
            isValid = false;
        }

        if (audDoc.value && !isAlphaNumericSpace(audDoc.value)) {
            setInputError(audDoc, "Only letters, numbers, and spaces are allowed");
            isValid = false;
        }

    }

    return isValid;
}
function setInputError(input, msg) {
    input.classList.add('error');
    const group = input.closest('.form-group');
    const err = group.querySelector('.error-msg');
    if (err) { err.textContent = msg; err.style.display = 'block'; }
}


async function validateAndComplete(event) {
    event.preventDefault();
    if (validateCurrentStep()) {
        submitRegistration();
    }
}

async function submitRegistration() {
    try {
        if (typeof showLoader === "function") showLoader();

        const formData = new FormData();

  
        const fullAddress = `${document.getElementById('addr-building').value.trim()}; ${document.getElementById('addr-area').value.trim()}; ${document.getElementById('addr-country').value}; ${document.getElementById('addr-zip').value.trim()}`;

      
        let auditorObj = null;
        const audName = document.getElementById('auditor-name').value.trim();
        const audEmail = document.getElementById('auditor-email').value.trim();
        const audDoc = document.getElementById('auditor-doc').value.trim();
        if (audName || audEmail || audDoc) {
            auditorObj = { auditorName: audName, auditorDocumentNumber: audDoc, auditorOfficalEmail: audEmail };
        }


        const dto = {
            organization: {
                orgName: document.getElementById('orgName').value.trim(),
                orgEmail: document.getElementById('orgEmail').value.trim(),
                orgType: document.getElementById('orgType').value,
                taxNumber: document.getElementById('taxToggle').checked ? document.getElementById('taxNumber').value.trim() : null,
                regNo: document.getElementById('regToggle').checked ? document.getElementById('regNo').value.trim() : null,
                address: fullAddress
            },
            spocDetails: {
                spocName: document.getElementById('spoc-name').value.trim(),
                spocOfficalEmail: document.getElementById('spoc-email').value.trim(),
                spocDocumentNumber: document.getElementById('spoc-doc').value.trim()
            },
            auditorDetails: auditorObj
        };

   
        formData.append('data', new Blob([JSON.stringify(dto)], { type: 'application/json' }));

        Object.keys(fileStore).forEach(label => {
            formData.append(label, fileStore[label]);
        });

        const response = await fetch(portalUrl + '/api/save', {
            method: 'POST',
            body: formData
        });


        if (typeof hideLoader === "function") hideLoader();

        const result = await response.json();
        if (response.ok && result.success) {
            Swal.fire({ icon: 'success', title: 'Successful', text: result.message })
                .then(() => window.location.href = portalUrl + '/organizations');
        } else {
            Swal.fire({ icon: 'error', title: 'Failed', text: result.message || 'Server error' });
        }

    } catch (error) {
        if (typeof hideLoader === "function") hideLoader();
        console.error(error);
        Swal.fire('Error', 'Something went wrong.', 'error');
    }
}


function fetchCategories() {
    const select = document.getElementById("orgType");
    if (!select) return;
    fetch(portalUrl + "/api/public/get/all/categories")
        .then(res => res.json())
        .then(data => {
            if (data.success && Array.isArray(data.result)) {
                data.result.forEach(cat => {
                    const option = new Option(cat.labelName, cat.categoryName);
                    select.add(option);
                });
            }
        });
}

function toggleField(containerId, checkbox) {
    const container = document.getElementById(containerId);
    if (container) container.style.display = checkbox.checked ? 'block' : 'none';
}




