/**
 * timer.js - Quiz countdown timer
 * Reads durationMinutes from a data attribute and counts down.
 * Applies warning/danger CSS classes and auto-submits on expiry.
 */

(function () {
    'use strict';

    const timerEl = document.getElementById('timer');
    const form    = document.getElementById('quiz-form');

    if (!timerEl || !form) return;

    const durationMinutes = parseInt(timerEl.dataset.duration, 10);
    if (isNaN(durationMinutes) || durationMinutes <= 0) return;

    let remaining = durationMinutes * 60; // seconds

    function formatTime(seconds) {
        const m = Math.floor(seconds / 60);
        const s = seconds % 60;
        return String(m).padStart(2, '0') + ':' + String(s).padStart(2, '0');
    }

    function updateTimer() {
        timerEl.textContent = formatTime(remaining);

        // Apply visual urgency classes
        timerEl.classList.remove('warning', 'danger');
        if (remaining <= 60) {
            timerEl.classList.add('danger');
        } else if (remaining <= 300) {
            timerEl.classList.add('warning');
        }

        if (remaining <= 0) {
            clearInterval(intervalId);
            timerEl.textContent = '00:00';
            autoSubmit();
            return;
        }

        remaining--;
    }

    function autoSubmit() {
        // Add a hidden field so the server knows this was a timed-out submission
        const hiddenField = document.createElement('input');
        hiddenField.type  = 'hidden';
        hiddenField.name  = 'timedOut';
        hiddenField.value = 'true';
        form.appendChild(hiddenField);
        form.submit();
    }

    // Tick immediately then every second
    updateTimer();
    const intervalId = setInterval(updateTimer, 1000);

    // Warn user before leaving mid-quiz
    window.addEventListener('beforeunload', function (e) {
        if (remaining > 0) {
            e.preventDefault();
            e.returnValue = '';
        }
    });

    // Remove beforeunload warning when the form is actually submitted
    form.addEventListener('submit', function () {
        window.onbeforeunload = null;
    });
})();
