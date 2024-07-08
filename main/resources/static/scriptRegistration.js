document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');
    form.addEventListener('submit', function(event) {
        event.preventDefault();

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        // Проверяем, существует ли уже пользователь
        if (localStorage.getItem(username)) {
            alert('This username is already taken.');
        } else {
            // Сохраняем данные пользователя
            localStorage.setItem(username, password);
            alert('Registration successful!');
            window.location.href = 'index.html'; // Переадресация на страницу входа
        }
    });
});
