window.addEventListener("scroll", function () {
    const navbar = document.getElementById("nav-bar-scroll");
    if (window.scrollY > 1) {
        navbar.classList.add("scrolled");
    } else {
        navbar.classList.remove("scrolled");
    }
});
document.querySelector('.menu-button').addEventListener('click', function (event) {
    event.preventDefault();
    const nav = document.querySelector('nav .menu');
    const favorite = document.querySelector('.my-ads-and-favorite');
    const flags = document.querySelector('nav .flags');

    nav.classList.toggle('menu-open');
    favorite.classList.toggle('menu-open');
    flags.classList.toggle('menu-open');
});