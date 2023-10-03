export {burgerMenu}

function burgerMenu() {
    let openMenu = document.querySelector('.header__inner');

    document.querySelector('.burger').addEventListener('click', function () {
        openMenu.classList.toggle('header__inner--open');
    })
    openMenu.addEventListener('click', function () {
        openMenu.classList.toggle('header__inner--open');
    })
    document.querySelector('.header-nav-link').addEventListener('click', function () {
        openMenu.classList.toggle('header__inner--open');
    })
}