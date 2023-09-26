let url = new URL('http://localhost:8080/api/calculate');
//-----------------------------------TODO: change--------------------------------------
//-------------------------------------------------------------------------------------

let inputForm = document.forms.calculationForm;

inputForm.addEventListener('submit', function (event) {
    let inputFormElements = inputForm.getElementsByClassName('container__input--data');
    let signOnDate = inputForm.elements.signOnDate;
    let signOffDate = inputForm.elements.signOffDate;
    let headerText = document.getElementById('headerText');
    let vesselName = inputFormElements.length === 2 ? 'anon' : inputForm.elements.vesselName.value;
    let isAllDataValid = true;

    for (let input of inputFormElements) {
        if (input.value === '') {
            isAllDataValid = false;
            input.classList.add('error');
            headerText.textContent = 'Input not to be blank';
        }
        input.addEventListener('focus', function (event) {
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
        (async function () {
            let dataToSend = JSON.stringify({
                "signOnDate": signOnDate.value,
                "signOffDate": signOffDate.value,
                "shipName": vesselName
            })
            let dataReceived = await fetch(url, {
                credentials: "same-origin",
                mode: "same-origin",
                method: "post",
                headers: {"Content-Type": "application/json"},
                body: dataToSend
            })
                .then(response => response.text())
                .catch(error => console.log(error));
            dataReceived != null ? headerText.textContent = dataReceived : 'Error happened :(';
        })();
    }
    event.preventDefault();
});


