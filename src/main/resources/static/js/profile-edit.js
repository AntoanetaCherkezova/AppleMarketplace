function editProfile(field) {
    const spanElement = document.getElementById(`${field}-span`);
    const inputElement = document.getElementById(`${field}-input`);

    if (inputElement.style.display === 'none' || inputElement.style.display === '') {
        inputElement.value = spanElement.textContent === 'N/A' ? '' : spanElement.textContent;
        spanElement.style.display = 'none';
        inputElement.style.display = 'inline';
        inputElement.focus();
        inputElement.setSelectionRange(inputElement.value.length, inputElement.value.length);
    } else {
        spanElement.textContent = inputElement.value === '' ? 'N/A' : inputElement.value;
        spanElement.style.display = 'inline';
        inputElement.style.display = 'none';
    }
}

document.addEventListener('click', function (event) {
    const inputElements = document.querySelectorAll('.edit-input');

    inputElements.forEach(inputElement => {
        const spanElement = inputElement.previousElementSibling;

        if (inputElement.style.display === 'inline' && !inputElement.contains(event.target)) {
            spanElement.textContent = inputElement.value === '' ? 'N/A' : inputElement.value;
            spanElement.style.display = 'inline';
            inputElement.style.display = 'none';
        }
    });
});

document.querySelectorAll('.edit-icon').forEach(icon => {
    icon.addEventListener('click', function (event) {
        event.stopPropagation();
        const field = this.getAttribute('data-field');
        editProfile(field);
    });
});

document.querySelectorAll('.edit-input').forEach(input => {
    input.addEventListener('click', function (event) {
        event.stopPropagation();
    });
});