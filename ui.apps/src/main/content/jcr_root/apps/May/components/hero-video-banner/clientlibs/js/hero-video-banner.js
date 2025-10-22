(function (document) {
  "use strict";

  function init(cmpEl) {
    const video = cmpEl.querySelector("video");
    const playBtn = cmpEl.querySelector("[data-cmp-hook-hero-video-banner='playpause']");
    const transcriptToggle = cmpEl.querySelector("[data-cmp-hook-hero-video-banner='transcriptToggle']");
    const transcript = cmpEl.querySelector("[data-cmp-hook-hero-video-banner='transcript']");

    let playedOnce = false;

    if (!video) return;

    // Auto play once (muted)
    video.play().catch(() => {
      // Autoplay blocked
      video.pause();
    });

    video.addEventListener("ended", () => {
      playedOnce = true;
      playBtn.innerText = "▶"; // change icon to Play
    });

    playBtn.addEventListener("click", () => {
      if (video.paused) {
        video.play();
        playBtn.innerText = "❚❚";
      } else {
        video.pause();
        playBtn.innerText = "▶";
      }
    });

    transcriptToggle.addEventListener("click", () => {
      const open = transcript.classList.toggle("is-open");
      transcriptToggle.setAttribute("aria-expanded", open);
    });
  }

  document.querySelectorAll("[data-cmp-is='hero-video-banner']").forEach(init);
})(document);
