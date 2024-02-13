function displayDescription(hoverElementId, descriptionId, text) {
    document.getElementById(hoverElementId).addEventListener('mouseenter', function() {
        document.getElementById(descriptionId).style.opacity = '1';
        document.getElementById(descriptionId).textContent = text;
        flag = false;
    });

    document.getElementById(hoverElementId).addEventListener('mouseleave', function() {
        document.getElementById(descriptionId).style.opacity = '0';
    });
}

displayDescription('new-training', 'new-training-desc', 'Dodaj nowy trening.');
displayDescription('exercise-base', 'exercise-base-desc', 'Dodaj nowe ćwiczenie do istniejącej bazy ćwiczeń.');
displayDescription('previous-trainings', 'previous-trainings-desc', 'Obejrzyj swoje poprzednie treningi.');
displayDescription('progress', 'progress-desc', 'Śledź swoje postępy na bieżąco.');
