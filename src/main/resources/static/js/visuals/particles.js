export {particles};

function particles() {
    if (document.body.animate) {
        pop()
        setInterval(pop, 5000)
    }

    function pop(e) {
        for (let i = 0; i < 75; i++) {
            createParticle(Math.random() * window.screen.width, Math.random() * window.screen.height);
        }
    }

    function createParticle(x, y) {
        const particle = document.createElement('particle');
        document.body.appendChild(particle);
        const size = Math.floor(Math.random() * 3);
        particle.style.width = `${size}px`;
        particle.style.height = `${size}px`;
        particle.style.background = `rgba(255, 255, 255, ${Math.random() * 10})`;
        particle.style.boxShadow = ` ${Math.random()}px ${Math.random()}px ${Math.random() * 5}px ${Math.random()}px rgba(255,255,255)`;
        const destinationX = x + (Math.random() - 0.5) * 2 * 150;
        const destinationY = y + (Math.random() - 0.5) * 2 * 150;

        const animation = particle.animate([
            {
                transform: `translate(${x - (size)}px, ${y - (size)}px)`,
                opacity: 0
            },
            {
                opacity: 1
            },
            {
                transform: `translate(${destinationX}px, ${destinationY}px)`,
                opacity: 0
            }
        ], {
            duration: 12000,
            easing: 'linear',
            delay: Math.random() * 100
        });
    }
}

