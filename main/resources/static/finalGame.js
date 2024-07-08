window.onload = function() {
    fetch('/api/finalGameData')
        .then(response => response.json())
        .then(data => {
            document.getElementById('finalGameBoard').innerHTML = data.htmlBoard;
            const scoreElement = document.getElementById('finalScore');
            scoreElement.innerText = "Your score: " + data.score;

            const hiddenScoreInput = document.getElementById('hiddenScore');
            hiddenScoreInput.value = data.score;  // Прямое присвоение счета
        })
        .catch(error => console.error('Error loading final game data: ', error));
};