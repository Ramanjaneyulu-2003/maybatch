console.log("hi");
document.addEventListener("DOMContentLoaded", function () {
    console.log("hlo");
    // Open modal
    document.querySelectorAll('.open-modal').forEach(button => {
        button.addEventListener('click', function () {
            const modalId = this.getAttribute('data-modal-id');
            const modal = document.getElementById(modalId);
            if (modal) {
                modal.setAttribute('aria-hidden', 'false');
                modal.classList.add('show');
            }
        });
    });

    // Close modal
    document.querySelectorAll('.close-btn').forEach(button => {
        button.addEventListener('click', function () {
            const modal = this.closest('.modal-overlay-container');
            if (modal) {
                modal.setAttribute('aria-hidden', 'true');
                modal.classList.remove('show');
            }
        });
    });
});
