function showLoader() {
    document.getElementById("global-loader").classList.remove("hidden");
}

function hideLoader() {
    document.getElementById("global-loader").classList.add("hidden");
}

window.addEventListener("load", () => {
    hideLoader();
});

function toggleProfileMenu(event) {
    event.stopPropagation();
    const menu = document.getElementById('profileMenu');
    menu.classList.toggle('show');
}

document.addEventListener('click', function () {
    const menu = document.getElementById('profileMenu');
    if (menu) {
        menu.classList.remove('show');
    }
});