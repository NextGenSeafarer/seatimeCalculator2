let form = document.querySelector('.container__forgotPassword_form')
let forgotPasswordUrl = new URL('http://localhost:8080/api/resetPassword');
let isLoading = false;

form.addEventListener('submit', function (event) {
    event.preventDefault();
    let input = document.querySelector('.container__form-input');
    let mailValue = input.value;
    if (!isLoading) {
        (async function send() {
                isLoading = true;
                let response = await fetch(forgotPasswordUrl, {
                    method: "post",
                    headers: {"Content-Type": "application/json"},
                    body: mailValue
                })
                    .then(response => response.json())
                    .catch(error => console.log(error));
                if (response) {
                    form.classList.add('fade_away');
                    form.innerHTML = '';
                    form.classList.remove('fade_away');
                    form.innerHTML = '<div class="message_success message">\n' +
                        '                <div class="message_text">Your link successfully send to ' + mailValue +
                        ' </div>\n' +
                        '            </div>'
                    form.addEventListener('transitionend', function () {
                        isLoading = false;
                    });
                } else {
                    if (document.querySelector('.message__exist') == null) {
                        let message = document.createElement('span');
                        message.classList.add('message__exist');
                        message.innerHTML = '<div class="message_fail message">\n' +
                            '                <div class="message_text">Email is not exist</div>\n' +
                            '            </div>'
                        form.prepend(message);
                    }
                    isLoading = false;
                }
            }
        )();
    }
})