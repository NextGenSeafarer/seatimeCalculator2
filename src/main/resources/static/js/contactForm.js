import {contactUrl} from "/static/js/staticLinks.js";
import {createLoader, deleteLoader, disableElementsOfInput, enableElementsOfInput} from "/static/js/blocks/loader.js";

let form = document.querySelector('.contact');
let isLoading = false;

form.addEventListener('submit', function (event) {
    event.preventDefault();
    if (!isLoading) {
        createLoader(form);
        (async function () {
            isLoading = true;
            disableElementsOfInput(form);
            let dataToSend = JSON.stringify({
                "name": form.querySelector('.contact_name').value,
                "email": form.querySelector('.contact_email').value,
                "message": form.querySelector('.contact_text').value
            })
            let dataReceived = await fetch(contactUrl, {
                credentials: "same-origin",
                mode: "same-origin",
                method: "post",
                headers: {"Content-Type": "application/json"},
                body: dataToSend
            })
                .then(response => response.json())
                .catch(error => console.log(error));
            if (dataReceived === false) {
                deleteLoader(form);
                enableElementsOfInput(form);
                if (document.querySelector('.message__exist') == null) {
                    let message = document.createElement('span');
                    message.classList.add('message__exist');
                    message.innerHTML = '<div class="message_fail message">\n' +
                        '                <div class="message_text">Something went wrong :(</div>\n' +
                        '            </div>';
                    form.prepend(message);
                }
            } else {
                if (document.querySelector('.message__exist') == null) {
                    form.classList.add('fade_away');
                    form.innerHTML = '';
                    form.classList.remove('fade_away');
                    form.innerHTML = '<div class="message_success message">\n' +
                        '                <div class="message_text">I\'ll look into it right away' +
                        ' </div>\n' +
                        '            </div>';
                }
            }
        })();
        isLoading = false;
    }

})

