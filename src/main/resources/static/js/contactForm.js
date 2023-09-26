let form = document.querySelector('.contact__form');
//-------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------
let contactUrl = new URL('http://localhost:8080/api/contact');
//-----------------------------------TODO: change--------------------------------------
//-------------------------------------------------------------------------------------
let isLoading = false;
form.addEventListener('submit', function (event) {
    event.preventDefault();
    if (!isLoading) {
        createLoader();
        (async function () {
            isLoading = true;
            if (isLoading) {
                for (let element of form.elements) {
                    element.disabled = true;
                }
            }
            let dataToSend = JSON.stringify({
                "name": form.querySelector('#contactForm_name').value,
                "email": form.querySelector('#contactForm_email').value,
                "message": form.querySelector('#contactForm_text').value
            })
            let dataReceived = await fetch(contactUrl, {
                credentials: "same-origin",
                mode: "same-origin",
                method: "post",
                headers: {"Content-Type": "application/json"},
                body: dataToSend
            })
                .then(response => response.text())
                .catch(error => console.log(error));
            if (dataReceived === 'success') {
                if (document.querySelector('.message__exist') == null) {
                    form.classList.add('fade_away');
                    form.innerHTML = '';
                    form.classList.remove('fade_away');
                    form.innerHTML = '<div class="message_success message">\n' +
                        '                <div class="message_text">I\'m probably at sea :) But will reply soon!' +
                        ' </div>\n' +
                        '            </div>'
                }
                isLoading = false;
            } else {
                document.querySelector('.loading_status').remove();
                for (let element of form.elements) {
                    element.disabled = false;
                }
                if (document.querySelector('.message__exist') == null) {
                    let message = document.createElement('span');
                    message.classList.add('message__exist');
                    message.innerHTML = '<div class="message_fail message">\n' +
                        '                <div class="message_text">Something went wrong :(</div>\n' +
                        '            </div>';
                    form.prepend(message);
                }
                isLoading = false;
            }
        })();
    }

})

function createLoader() {
    let loader = document.createElement('span');
    if (document.querySelector('.loading_status') == null) {
        if (document.querySelector('.message__exist') != null) {
            document.querySelector('.message__exist').remove();
        }
        loader.innerHTML = `<div class="boxLoading">Im working on it..</div>`;
        loader.classList.add('loading_status');
        form.prepend(loader);
    }
}