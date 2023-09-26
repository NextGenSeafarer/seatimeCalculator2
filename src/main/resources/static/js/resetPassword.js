
let form = document.querySelector('#reset_password');

form.addEventListener('submit', function (event) {
    let password = document.querySelector('#password')
    let passwordConfirm = document.querySelector('#password_confirm')
    if (password.value !== passwordConfirm.value) {
        if (document.querySelector('.message__exist') == null) {
            let message = document.createElement('span');
            message.classList.add('message__exist');
            message.innerHTML = '<div class="message_fail message">\n' +
                '                <div class="message_text">Passwords are not same</div>\n' +
                '            </div>'
            form.prepend(message);
        }
        event.preventDefault();
        password.classList.add('error');
        passwordConfirm.classList.add('error');
    }
    password.addEventListener('focus', function (event) {
        password.classList.remove('error');
    });
    passwordConfirm.addEventListener('focus', function (event) {
        passwordConfirm.classList.remove('error');
    });
})