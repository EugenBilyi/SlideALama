document.addEventListener('DOMContentLoaded', function() {
    // Находим все формы на странице, которые могут отправлять комментарии или рейтинги
    const forms = document.querySelectorAll('form');

    forms.forEach(form => {
        form.addEventListener('submit', function(event) {
            // Проверяем, есть ли в форме элемент с классом 'input-style' и name='player'
            const playerInput = form.querySelector('.input-style[name="player"]');

            if (playerInput && playerInput.value.trim() === '') {
                // Если поле ввода пустое, предотвращаем отправку формы
                event.preventDefault();
                alert('Please enter your name before submitting!');
            }
        });
    });
});