let regForm = document.forms.registrationForm;
let email = regForm.elements.email;
let isErrorsPresent = false;
//------------------------------------------------------------
//------------------------------------------------------------

let validationURL = new URL('http://localhost:8080/api/emailValidation');
let forgotPasswordLink = 'http://localhost:8080/forgot_password';
//-----------------------------------TODO: change--------------------------------------
//-------------------------------------------------------------------------------------


regForm.addEventListener('submit', function (event) {
    let password = regForm.elements.password;
    let passwordConfirm = regForm.elements.password_confirm;
    for (let input of regForm.getElementsByClassName('container__form-input')) {
        if (input.value === '') {
            input.classList.add('error');
            input.placeholder = 'Please fill up';
            isErrorsPresent = true;
        }
        input.addEventListener('focus', function (event) {
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


email.addEventListener('change', function (event) {
    (async function () {
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
            if (document.getElementById('forgot_link') === null) {
                const a = document.createElement('a');
                let forgotPassword = document.createTextNode("Forgot password?");
                a.appendChild(forgotPassword);
                a.href = forgotPasswordLink;
                a.classList.add('forgot_password_link');
                a.id = 'forgot_link'
                email.after(a);
            }
        } else {
            isErrorsPresent = false;
            email.nextSibling.remove();
        }
        email.addEventListener('focus', function (event) {
            email.classList.remove('error');
        })
    })();
});