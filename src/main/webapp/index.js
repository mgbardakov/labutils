function validateInput() {
    let els = document.getElementsByTagName('input');
    let rslString = '';
    for (let i = 0; i < els.length; i++) {
        let element = els[i];
        if (element.getAttribute('type') === 'text') {
            let fixedElement = element.value.replaceAll(',', '.')
            if (fixedElement !== '' && !isNumeric(fixedElement)) {
                rslString = 'Проверьте корректность ввода'
            }
        }
    }
    if (rslString !== '') {
        alert(rslString);
        return false;
    }
    return true;
}

function addInputField() {
    let list = document.getElementById('dataInput');
    list.insertAdjacentHTML('beforeend', ' <li>\n' +
        '                        <div class="form-group">\n' +
        '                            <input type="text" class="form-control data-field">\n' +
        '                        </div>\n' +
        '                    </li>')
    mapDataListeners();
}

function isNumeric(str) {
    if (typeof str != "string") return false
    return !isNaN(str) &&
        !isNaN(parseFloat(str))
}

function createRequestObject() {
    return {
        isAbsolute: isAbsolute(),
        errorValue: getErrorValue(),
        orderData: getOrderData()
    };
}

function isRequestValid(request) {
    if (request.orderData.length === 2) {
        return false;
    }
    return true;
}

function isAbsolute(){
    let element = document.querySelector('#errorType');
    return element.value;
}
function getErrorValue(){
    let element = document.querySelector('#errorValue');
    return element.value.replaceAll(',', '.');
}
function getOrderData(){
    let rslArray = [];
    let list = document.querySelectorAll('.data-field');
    console.log(list)
    for (let el of list) {
        if (el.value !== '') {
            let fixedElement = el.value.replaceAll(',', '.')
            rslArray.push(fixedElement);
        }
    }
    return rslArray;
}

function sendData() {
    let requestObject = createRequestObject();
    if (!validateInput() || !isRequestValid(requestObject)) {
        return;
    }
    let message = JSON.stringify(requestObject);
    console.log(message)
    fetch('measurement.do', {
        method : 'POST',
        body : message
    }).then(response => {
        return response.json();
    })
        .then(data => {
            setResult(data)
        })
}

function setResult(data) {
    let element = document.getElementById('result');
    element.innerText = `Результат: ${Math.round(data.average * 10) / 10} ± ${Math.round(data.uncertaintyDoubleSideExpanded * 10) / 10}`
    let grubbs = document.querySelector('#grubbs');
    grubbs.innerHTML = `Минимум: ${data.minMiss}<br>
                        Максимум: ${data.maxMiss}`
}

function hideTable(){
    let element = document.querySelector('#table-div');
    if (element.classList.contains('d-none')) {
        element.classList.remove('d-none');
        element.classList.add('d-block')
    } else if (element.classList.contains('d-block')) {
        element.classList.remove('d-block');
        element.classList.add('d-none')
    }
}

async function addNewJournalMessage() {
    let result = document.querySelector('#result').innerText.substr(11);
    let grubbs = document.querySelector('#grubbs').innerHTML;
    setResultEmpty();
    if (result !== '') {
        let dataArr = getOrderData();
        if (dataArr.length < 1) {
            return
        }
        let table  = document.querySelector('#journal-table')
        let row = table.insertRow(-1);
        let number = row.insertCell(-1);
        number.innerText = (table.rows.length - 1).toString();
        let orderData = row.insertCell(-1)
        for (let element of dataArr) {
            orderData.innerText = orderData.innerText + element + ';'
        }
        let resultCell = row.insertCell(-1);
        resultCell.innerText = result;
        let grubbsCell = row.insertCell(-1);
        grubbsCell.innerHTML = grubbs;
    }
}

function mapDataListeners() {
    let dataArr = document.querySelectorAll('.data-field')
    for (let element of dataArr) {
        element.addEventListener('keyup', event => {
            if (event.key === 'Enter') {
                sendData();
            }
        })
        element.addEventListener('blur', sendData)
        element.addEventListener('focus', isErrorHasValue)
    }
}

function isErrorHasValue() {
    return document.querySelector('#errorValue').value !== '';
}

function setResultEmpty() {
    document.querySelector('#result').innerText = '';
    document.querySelector('#grubbs').innerText = '';
}

document.querySelector('#addButton')
    .addEventListener('click', addInputField);
document.querySelector('#journalButton')
    .addEventListener('click', hideTable)
document.querySelector('#save-btn')
    .addEventListener('click', addNewJournalMessage)
document.querySelector('#clearInput')
    .addEventListener('click', setResultEmpty)
mapDataListeners();

