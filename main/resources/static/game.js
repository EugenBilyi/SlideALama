let randomButtonClicked = false;

window.onload = function() {
    loadInitialGameData();
    setupRandomButton();
};

function loadInitialGameData() {
    fetch('/slidealama/field')
        .then(response => response.text())
        .then(html => {
            document.getElementById('gameBoard').innerHTML = html;
            setupButtons();
        })
        .catch(error => console.error('Error loading the game board: ', error));

    fetch('/api/symbols')
        .then(response => response.json())
        .then(data => {
            document.getElementById('currentSymbol').innerHTML = "Current:" + getImageTag(data.currentSymbol);
            document.getElementById('nextSymbol').innerHTML = "Next: " + getImageTag(data.nextSymbol);
            document.getElementById('score').innerText = "Score: " + data.score;
        })
        .catch(error => console.error('Error loading symbols: ', error));
}

function setupButtons() {
    for (let i = 1; i <= 15; i++) {
        let leftButton = document.querySelector('.subButtonLeft' + i);
        if (leftButton) {
            leftButton.addEventListener('click', () => makeMove(6 - i)); // Соответствие номера кнопки позиции
        }
        let topButton = document.querySelector('.subButtonTop' + i);
        if (topButton) {
            topButton.addEventListener('click', () => makeMove(5 + i)); // Соответствие номера кнопки позиции
        }
        let rightButton = document.querySelector('.subButtonRight' + i);
        if (rightButton) {
            rightButton.addEventListener('click', () => makeMove(10 + i)); // Соответствие номера кнопки позиции
        }
    }
}

function makeMove(position) {
    fetch(`/api/makeMove?position=${position}`, { method: 'POST' })
        .then(response => response.json())  // Изменено для обработки JSON ответа
        .then(data => {
            if (data.gameOver) {
                window.location.href = '/gameWon.html';  // Перенаправление
            } else {
                loadInitialGameData();  // Обновление данных игры
            }
        })
        .catch(error => console.error('Error making move: ', error));
}

// Функция для получения пути к изображению по символу
function getImagePath(symbol) {
    switch (symbol) {
        case 'A': return "/images/bell(A).png";
        case 'B': return "/images/banana(B).png";
        case 'C': return "/images/plum(C).png";
        case 'D': return "/images/pear(D).png";
        case 'E': return "/images/cherry(E).png";
        case 'F': return "/images/watermelon(F).png";
        case 'G': return "/images/7(G).png";
        default:  return "/images/default.png"; // Изображение по умолчанию, если символ не распознан
    }
}

// Функция для создания тега img для изображения символа
function getImageTag(symbol) {
    const imagePath = getImagePath(symbol);
    return `<img src="${imagePath}" alt="${symbol}" style="width: 50px; height: 50px;">`;
}

function setupRandomButton() {
    const randomButton = document.getElementById('randomButton');
    randomButton.addEventListener('click', function() {
        if (!randomButtonClicked) {
            randomButtonClicked = true;
            removeRandomSymbol();
            randomButton.classList.add('hidden'); // Скрыть кнопку
        } else {
            alert('You can only use the random button once per game!');
        }
    });
}

function removeRandomSymbol() {
    fetch('/api/removeRandomSymbol', { method: 'POST' })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                loadInitialGameData();
            } else {
                console.error('Error removing random symbol:', data.message);
            }
        })
        .catch(error => console.error('Error removing random symbol:', error));
}