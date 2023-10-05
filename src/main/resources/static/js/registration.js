import {
    burgerMenu,
    particles
} from "/static/js/staticElements.js";

burgerMenu();
particles();

let regForm = document.querySelector('.form');
let email = document.querySelector('.email');
let isErrorsPresent = false;
regForm.addEventListener('submit', function (event) {
    let password = document.querySelector('.password')
    let passwordConfirm = document.querySelector('.passwordConfirm')
    for (let input of regForm.getElementsByClassName('form__input')) {
        if (input.value === '') {
            input.classList.add('error');
            input.placeholder = 'Please fill up';
            isErrorsPresent = true;
        }
        input.addEventListener('focus', function () {
            input.classList.remove('error');
        });
    }

    if (password.value !== passwordConfirm.value) {
        password.classList.add('error');
        passwordConfirm.classList.add('error');
        password.placeholder = 'Passwords are not same';
        passwordConfirm.placeholder = 'Passwords are not same';
        password.value = '';
        passwordConfirm.value = '';
        isErrorsPresent = true;
    }
    if (isErrorsPresent) {
        event.preventDefault();
    }
});


email.addEventListener('change', function () {
    (async function () {
        let validationURL = new URL(document.querySelector(".emailValidationLink").href);
        let dataToSend = email.value;
        let isEmailExists = await fetch(validationURL, {
            credentials: "same-origin",
            mode: "same-origin",
            method: "post",
            headers: {"Content-Type": "application/json"},
            body: dataToSend
        })
            .then(response => response.json())
            .catch(error => console.log(error));
        if (isEmailExists) {
            isErrorsPresent = true;
            email.classList.add('error');
            email.value = '';
            email.placeholder = 'Email is taken';
            if (document.querySelector('.forgot_password_link') === null) {
                let emailLabel = document.querySelector('.form__label--email');
                let a = document.createElement('a');
                a.classList.add('forgot_password_link');
                a.href = '/forgot_password';
                a.innerHTML = 'Forgot password';
                emailLabel.after(a);
            }
        } else {
            isErrorsPresent = false;
            document.querySelector('.forgot_password_link').remove();
        }
        email.addEventListener('focus', function () {
            email.classList.remove('error');
        })
    })();
});