import {burgerMenu, particles} from "/static/js/staticElements.js";

burgerMenu();
particles();
let buttons = document.querySelectorAll('.button-trips');
let isLoading = false;

buttons.forEach(btn => {
    btn.addEventListener('click', () => {
        let buttonID = btn.id;
        let formID = buttonID.replace(/\D/g, '');
        let form = document.getElementById(formID);
        form.addEventListener('submit', event => {
            event.preventDefault();
        })
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
                            dataReceived === 'error' ?
                                form.querySelector('.form__input--contractLength').textContent = 'Oops..error' :
                                form.querySelector('.form__input--contractLength').textContent = dataReceived;
                            deleteLoader(form);
                        })();
                        isLoading = false;
                    }
                } else {
                    btn.classList.toggle('active');
                }
            }
        } else {
            confirmDeletingCreate(form);
            let deleteButton = document.getElementById("delete" + form.id);
            deleteButton.addEventListener('click', () => {
                (async function deleteEntry() {
                    let tripsLink = new URL(document.querySelector(".tripsLink").href);
                    let dataReceived = await fetch(tripsLink, {
                        credentials: "same-origin",
                        mode: "same-origin",
                        method: "DELETE",
                        headers: {"Content-Type": "application/json"},
                        body: formID
                    })
                        .then(response => response.json())
                        .catch(error => {
                            console.log(error)
                            dataReceived = false;
                        });
                    if (dataReceived) {
                        form.style.opacity = '0';
                        form.style.rotate = '30deg';
                        form.style.scale = '0.3';
                        setTimeout(() => {
                            form.remove();
                        }, 1000);
                    }
                })();
            })
        }
    })
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
    let isOk = true;
    let signOnDate = form.elements.signOnDate;
    let signOffDate = form.elements.signOffDate;
    for (let element of form) {
        if (element.value === '' && !element.classList.contains('button-trips')) {
            element.classList.add('error');
            isOk = false;
        } else {
            element.classList.remove('error');
        }
    }
    if (signOnDate.value > signOffDate.value ||
        new Date(signOnDate.value).getFullYear() < 1990 ||
        new Date(signOnDate.value).getFullYear() > 2050 ||
        new Date(signOffDate.value).getFullYear() < 1990 ||
        new Date(signOffDate.value).getFullYear() > 2050) {
        signOnDate.classList.add('error');
        signOffDate.classList.add('error');
        isOk = false;
    } else {
        signOnDate.classList.remove('error');
        signOffDate.classList.remove('error');
    }
    return isOk;
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

function confirmDeletingCreate(form) {
    let confirmDelete = document.createElement('span');
    confirmDelete.innerHTML = `<div class="delete__container">Delete entry?
<div class="buttons_row buttons_row--delete">
                        <input
                               id = "delete${form.id}"
                               class="button-trips--confirmDelete button--delete"
                               type="button"
                               value="Delete"
                               
                        />
                        <input 
                               id = "cancel${form.id}"
                               class="button-trips--cancel button--delete"
                               type="button"
                               value="Cancel"     
                        />
                    </div>
</div>`
    confirmDelete.classList.add('delete');
    confirmDelete.addEventListener('click', () => {
        confirmDelete.style.opacity = '0';
        setTimeout(() => {
            confirmDelete.remove();
        }, 400);
    });
    form.append(confirmDelete);
}

