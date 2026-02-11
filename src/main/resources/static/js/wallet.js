
function confirmApplyWallet(btn) {

    const orgId = btn.dataset.orgId;
    const adminApproval = btn.dataset.direct === "true";
    const status = btn.dataset.status; 

    const isRenew = status === "EXPIRED";

    Swal.fire({
        title: isRenew
            ? "Renew Wallet Certificate?"
            : "Apply Wallet Certificate?",
        text: isRenew
            ? "Do you want to renew the wallet certificate?"
            : "Do you want to apply for wallet certificate?",
        icon: "question",
        showCancelButton: true,
        confirmButtonColor: "#986f2f",
        confirmButtonText: isRenew ? "Yes, Renew" : "Yes, Apply",
        cancelButtonText: "Cancel"
    }).then(result => {

        if (result.isConfirmed) {
            showLoader();

            const url = adminApproval
                ? portalUrl + "/api/wallet/save/wallet/req"
                : portalUrl + "/api/wallet/save/wallet";

            fetch(url, {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: new URLSearchParams({
                    orgDetailsId: orgId
                })
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

