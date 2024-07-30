window.addEventListener("scroll", function () {
    const navbar = document.getElementById("nav-bar-scroll");
    if (window.scrollY > 1) {
        navbar.classList.add("scrolled");
    } else {
        navbar.classList.remove("scrolled");
    }
});
