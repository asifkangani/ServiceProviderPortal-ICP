
function confirmApplyLicense(btn) {

    const softwareId = btn.dataset.softwareId;
    const orgId = btn.dataset.orgId;

    Swal.fire({
        title: "Apply for License?",
        text: "Are you sure you want to apply for this license?",
        icon: "question",
        showCancelButton: true,
        confirmButtonColor: "#986f2f",
        confirmButtonText: "Yes, Apply",
        cancelButtonText: "Cancel"
    }).then(result => {
        if (result.isConfirmed) {
            showLoader();
            fetch(portalUrl + `/license/apply?softwareId=${softwareId}&orgId=${orgId}`, {
                method: "POST"
            })
                .then(res => res.json())
            
                .then(data => {
                    hideLoader();
                    if (data.success) {
                        Swal.fire("Success", data.message, "success")
                            .then(() => location.reload());
                    } else {
                        Swal.fire("Error", data.message, "error");
                    }
                });
        }
    });
}


function confirmRenewLicense(btn) {

    const licenseId = btn.dataset.licenseId;

    Swal.fire({
        title: "Renew License?",
        text: "Are you sure you want to renew this license?",
        icon: "question",
        showCancelButton: true,
        confirmButtonColor: "#986f2f",
        confirmButtonText: "Yes, Renew",
        cancelButtonText: "Cancel"
    }).then(result => {
        if (result.isConfirmed) {
            showLoader();
            fetch(portalUrl + `/license/renew?licenseId=${licenseId}`, {
                method: "POST"
            })
                .then(res => res.json())
                .then(data => {
                    hideLoader();
                    if (data.success) {
                        Swal.fire("Success", data.message, "success")
                            .then(() => location.reload());
                    } else {
                        Swal.fire("Error", data.message, "error");
                    }
                })
                .catch(() => {
                    hideLoader();
                    Swal.fire("Error", "Something went wrong", "error");
                });
        }
    });
}
