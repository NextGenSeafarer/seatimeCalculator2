function createLoader(element) {
    let loader = document.createElement('span');
    loader.style.alignSelf = 'start';
    if (document.querySelector('.loading_status') == null) {
        if (document.querySelector('.message__exist') != null) {
            document.querySelector('.message__exist').remove();
        }
        loader.innerHTML = `<div class="boxLoading">Im working on it</div>`
        loader.classList.add('loading_status');
        loader.classList.add('shine');
        element.prepend(loader);
    }
}


function deleteLoader(loader) {
    document.querySelector('.loading_status').remove();
}

function disableElementsOfInput(form) {
    for (let element of form) {
        element.disabled = true;
    }
}

function enableElementsOfInput(form) {
    for (let element of form) {
        element.disabled = false;
    }
}

export {createLoader, deleteLoader, disableElementsOfInput, enableElementsOfInput};