function disp(form) {
    if (form.style.display === "none") {
        form.style.display = "block";
    } else {
        form.style.display = "none";
    }
}
let request;

function logOut() {
    document.getElementById('logout').submit();
}