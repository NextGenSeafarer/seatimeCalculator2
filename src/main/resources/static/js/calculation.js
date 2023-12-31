import {
    disableElementsOfInput,
    enableElementsOfInput,
} from "/static/js/staticElements.js";

let inputForm = document.querySelector('.form');
let isLoading = false;
inputForm.addEventListener('submit', function (event) {
    event.preventDefault();
    let inputFormElements = inputForm.getElementsByClassName('form__input');
    let signOnDate = inputForm.elements.signOnDate;
    let signOffDate = inputForm.elements.signOffDate;
    let headerText = document.querySelector('.header_text-calculated');
    let vesselName = inputFormElements.length === 2 ? 'anon' : inputForm.elements.vesselName.value;
    let isAllDataValid = true;

    for (let input of inputFormElements) {
        if (input.value === '') {
            isAllDataValid = false;
            input.classList.add('error');
            headerText.textContent = 'Input not to be blank';
        }
        input.addEventListener('focus', function () {
            input.classList.remove('error');
        });
    }
    if (signOnDate.value > signOffDate.value) {
        isAllDataValid = false;
        signOffDate.classList.add('error');
        headerText.textContent = 'Check dates';
    }

    if (isAllDataValid) {
        for (let input of inputFormElements) {
            input.classList.remove('error');
        }
        if (!isLoading) {
            let calculationURL = new URL(document.querySelector(".calculationLink").href);
            (async function () {
                isLoading = true;
                disableElementsOfInput(inputForm);
                let dataToSend = JSON.stringify({
                    "signOnDate": signOnDate.value,
                    "signOffDate": signOffDate.value,
                    "shipName": vesselName
                })
                let dataReceived = await fetch(calculationURL, {
                    credentials: "same-origin",
                    mode: "same-origin",
                    method: "post",
                    headers: {"Content-Type": "application/json"},
                    body: dataToSend
                })
                    .then(response => response.text())
                    .catch(error => {
                        console.log(error)
                        return 'error'
                    });
                enableElementsOfInput(inputForm);
                headerText.style.opacity = '0';
                setTimeout(function () {
                    headerText.style.opacity = '1';
                    dataReceived === 'error' ? headerText.textContent = 'Oops..error' : headerText.textContent = dataReceived;
                }, 1000);

            })();
            isLoading = false;
        }
    }
});



