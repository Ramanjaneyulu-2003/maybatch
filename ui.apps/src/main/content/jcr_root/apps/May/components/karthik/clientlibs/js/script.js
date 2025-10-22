console.log("hi1");
const playBtn = document.getElementById('playBtn');
    const video = document.getElementById('myVideo');

    // Play video and hide button when clicked
    playBtn.addEventListener('click', () => {
      video.play();
      playBtn.style.display = 'none';
    });
console.log("hi");
    // Hide play button if video is played manually
    video.addEventListener('play', () => {
      playBtn.style.display = 'none';
    });

    // Show play button if video is paused
    video.addEventListener('pause', () => {
      playBtn.style.display = 'flex';
    });