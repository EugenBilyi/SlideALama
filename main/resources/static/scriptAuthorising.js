document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');
    form.addEventListener('submit', function(event) {
        event.preventDefault();

        const username = document.querySelector('.input-box input[type=text]').value;
        const password = document.querySelector('.input-box input[type=password]').value;

        const storedPassword = localStorage.getItem(username);

        // Проверяем, существует ли пользователь и правильный ли пароль
        if (storedPassword && storedPassword === password) {
            alert('Login successful!');
            window.location.href = '/slidealama'; // Переход на главную страницу игры
        } else {
            alert('Invalid username or password.');
        }
    });
});
