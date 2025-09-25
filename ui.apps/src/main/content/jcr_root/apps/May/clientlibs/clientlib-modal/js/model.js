document.addEventListener("DOMContentLoaded", function () {
    const buttons = document.querySelectorAll(".open-modal-button");

    buttons.forEach(button => {
        button.addEventListener("click", function () {
            const overlayId = this.getAttribute("data-overlay-id");
            const modal = document.querySelector(#${overlayId} .modal-content);
            if (modal) {
                modal.style.display = "block";
            }
        });
    });

    // Close modal
    const closeButtons = document.querySelectorAll(".close-modal");
    closeButtons.forEach(btn => {
        btn.addEventListener("click", function () {
            this.closest(".modal-content").style.display = "none";
        });
    });
});