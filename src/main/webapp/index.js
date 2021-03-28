function validateInput() {
    let els = document.getElementsByTagName('input');
    let rslString = '';
    for (let i = 0; i < els.length; i++) {
        let element = els[i];
        if (element.getAttribute('type') === 'text') {
            if (element.value !== '' && !isNumeric(element.value)) {
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
    if (request.errorValue === '') {
        alert('Введите погрешность')
        return false;
    }
    if (request.orderData.length < 3) {
        alert('Недостаточно измерений')
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
    return element.value;
}
function getOrderData(){
    let rslArray = [];
    let list = document.querySelectorAll('.data-field');
    console.log(list)
    for (let el of list) {
        if (el.value !== '') {
            rslArray.push(el.value);
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
    element.innerText = `Результат: ${data.average.toFixed(1)} ± ${data.uncertaintyDoubleSideExpanded.toFixed(1)}`
}

document.querySelector('#addButton')
    .addEventListener('click', addInputField);
document.querySelector('#calcButton')
    .addEventListener('click', sendData);