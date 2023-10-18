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

function particles() {
    let particlesContainer = document.createElement('particlesContainer');
    document.body.appendChild(particlesContainer);
    if (document.body.animate) {
        pop()
    }

    function pop() {
        for (let i = 0; i < 75; i++) {
            createParticle(Math.random() * window.screen.width, Math.random() * window.screen.height);

        }
    }

    function createParticle(x, y) {
        const particle = document.createElement('particle');
        particlesContainer.append(particle);
        const size = Math.floor(Math.random() * 3);
        particle.style.width = `${size}px`;
        particle.style.height = `${size}px`;
        particle.style.background = `rgba(255, 255, 255, ${Math.random() * 10})`;
        particle.style.boxShadow = ` ${Math.random()}px ${Math.random()}px ${Math.random() * 5}px ${Math.random()}px rgba(255,255,255)`;
        const destinationX = x + (Math.random() - 0.5) * 5 * 150;
        const destinationY = y + (Math.random() - 0.5) * 5 * 150;
        particle.animate([
            {
                transform: `translate(${x - (size)}px, ${y - (size)}px)`,
                opacity: 0.1
            },
            {opacity: 1},
            {},
            {},
            {},
            {},
            {},
            {},
            {
                transform: `translate(${destinationX}px, ${destinationY}px)`,
                opacity: 0
            }
        ], {
            duration: 120000,
            easing: 'linear',
            delay: Math.random() * 100
        });
    }
}

function createLoader(element) {
    let loader = document.createElement('span');
    loader.style.alignSelf = 'start';
    if (document.querySelector('.loading_status') == null) {
        if (document.querySelector('.message__exist') != null) {
            document.querySelector('.message__exist').remove();
        }
        loader.innerHTML = `<div class="boxLoading">Im working on it</div>`
        loader.classList.add('loading_status');
        loader.classList.add('shine');
        element.prepend(loader);
    }
}


function deleteLoader() {
    document.querySelector('.loading_status').remove();
}

function disableElementsOfInput(form) {
    for (let element of form) {
        element.disabled = true;
    }
}

function enableElementsOfInput(form) {
    for (let element of form) {
        element.disabled = false;
    }
}

export {burgerMenu, particles, createLoader, deleteLoader, enableElementsOfInput, disableElementsOfInput};