import {
    burgerMenu,
    createLoader,
    deleteLoader,
    disableElementsOfInput,
    enableElementsOfInput,
    particles
} from "/static/js/staticElements.js";

burgerMenu();
particles();

let form = document.querySelector('.form')
let isLoading = false;
form.addEventListener('submit', function (event) {
    event.preventDefault();
    let mailValue = document.querySelector('.email').value;

    if (!isLoading) {
        let resetPasswordURL = new URL(document.querySelector(".resetLink").href);
        createLoader(form);
        (async function send() {
                isLoading = true;
                disableElementsOfInput(form);
                let response = await fetch(resetPasswordURL, {
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
                        '                <div class="message_text">Link delivered to: ' + mailValue +
                        ' </div>\n' +
                        '            </div>'
                    form.addEventListener('transitionend', function () {
                    });
                } else {
                    enableElementsOfInput(form);
                    deleteLoader();
                    if (document.querySelector('.message__exist') == null) {
                        let message = document.createElement('span');
                        message.classList.add('message__exist');
                        message.innerHTML = '<div class="message_fail message">\n' +
                            '                <div class="message_text">Error happened</div>\n' +
                            '            </div>';
                        form.prepend(message);
                    }
                }
            }
        )();

        isLoading = false;

    }
})
