const message = document.getElementById("message").value;
var logoutUrl = document.getElementById("logoutUrl").value;



if (message === "SUSPENDED") {


    Swal.fire({
        title: 'You are not authorised',
        text: "Please Contact Admin",
        confirmButtonColor: "#986f2f",
        icon: 'info'
    })

        .then(function () {
            window.location = logoutUrl;
        });

} else if (message === "Invitation Link Expired") {


    Swal.fire({
        title: 'Invitation link expired',
        text: "Please Contact Admin ",
        confirmButtonColor: "#986f2f",
        icon: 'info'
    })

        .then(function () {
            window.location = logoutUrl;
        });

} else if (message === "Not a Trusted User") {


    Swal.fire({
        title: 'Info',
        text: "You are not authorised ",
        confirmButtonColor: "#986f2f",
        icon: 'info'
    })

        .then(function () {
            window.location = logoutUrl;
        });

}
