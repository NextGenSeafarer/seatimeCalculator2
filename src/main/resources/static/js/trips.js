import {
    burgerMenu,
    particles
} from "/static/js/staticElements.js";

burgerMenu();
particles();
let buttons = document.querySelectorAll('.button-trips');
let isLoading = false;

buttons.forEach(btn => {
    btn.addEventListener('click', () => {
            let buttonID = btn.id;
            let formID = buttonID.replace(/\D/g, '');
            let form = document.getElementById(formID);
            let dates = form.getElementsByClassName('form__input-date');

            if (buttonID.endsWith('e')) {
                enableForm(form);
                changeTextToDateType(dates);
                btn.classList.toggle('active');
                if (!btn.classList.contains('active')) {
                    if (validateForm(form)) {
                        disableForm(form);
                        changeDateToTextType(dates);
                        createLoader(form);
                        if (!isLoading) {
                            (async function updateEntry() {
                                isLoading = true;
                                let tripsLink = new URL(document.querySelector(".tripsLink").href);
                                let dataToSend = JSON.stringify({
                                    "signOnDate": form.querySelector('.form__input--signOn').value,
                                    "signOffDate": form.querySelector('.form__input--signOff').value,
                                    "shipName": form.querySelector('.form__input--name').value,
                                    "id": form.id,
                                    "contractLength": form.querySelector('.form__input--contractLength').textContent
                                });
                                let dataReceived = await fetch(tripsLink, {
                                    credentials: "same-origin",
                                    mode: "same-origin",
                                    method: "PATCH",
                                    headers: {"Content-Type": "application/json"},
                                    body: dataToSend
                                })
                                    .then(response => response.text())
                                    .catch(error => {
                                        console.log(error)
                                    });
                                dataReceived === 'error' ? form.querySelector('.form__input--contractLength').textContent = 'Oops..error' :
                                    form.querySelector('.form__input--contractLength').textContent = dataReceived;
                                deleteLoader(form);
                            })();
                            isLoading = false;
                        } else {
                            btn.classList.toggle('active');
                        }
                    }
                }
            } else {
                alert('delete?')
            }
        }
    )
})

function enableForm(form) {
    for (let element of form) {
        if (!element.classList.contains('button-trips')) {
            element.disabled = false;
        }
    }
}

function disableForm(form) {
    for (let element of form) {
        if (!element.classList.contains('button-trips')) {
            element.disabled = true;
        }
    }
}

function changeTextToDateType(datesInput) {
    for (let element of datesInput) {
        element.type = 'date';
    }
}

function changeDateToTextType(datesInput) {
    for (let element of datesInput) {
        element.type = 'text';
    }
}

function validateForm(form) {
    let signOnDate = form.elements.signOnDate;
    let signOffDate = form.elements.signOffDate;
    for (let element of form) {
        if (element.value === '' && !element.classList.contains('button-trips')) {
            element.classList.add('error');
            return false;
        } else {
            element.classList.remove('error');
        }
        if (signOnDate.value > signOffDate.value) {
            signOffDate.classList.add('error');
            return false;
        } else {
            signOffDate.classList.remove('error');
        }
    }
    return true;
}

function createLoader(form) {
    let loader = document.createElement('span');
    loader.innerHTML = `<div class="boxLoading">Updating..</div>`
    loader.setAttribute('id', 'loader' + form.id);
    loader.classList.add('updating');
    form.append(loader);
}

function deleteLoader(form) {
    let id = 'loader' + form.id;
    let loader = document.getElementById(id);
    loader.style.opacity = '0';
    setTimeout(() => {
        loader.remove();
    }, 1000);
}



