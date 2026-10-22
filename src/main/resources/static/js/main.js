/**
 * Main JavaScript for Lavanderia Laviky Website
 * Handles form masking, Google Reviews integration and UI effects.
 */

document.addEventListener('DOMContentLoaded', () => {
    initMasks();
    initGoogleReviews();
});

/**
 * Initializes input masks for CNPJ and Phone
 */
function initMasks() {
    const cnpjInput = document.getElementById('cnpj');
    const phoneInput = document.getElementById('telefone');

    if (cnpjInput) {
        cnpjInput.addEventListener('input', (e) => {
            let value = e.target.value.replace(/\D/g, '');
            if (value.length > 14) value = value.slice(0, 14);

            // Apply mask: 00.000.000/0000-00
            if (value.length > 0) {
                value = value.replace(/^(\d{2})(\d)/, '$1.$2');
                value = value.replace(/^(\d{2})\.(\d{3})(\d)/, '$1.$2.$3');
                value = value.replace(/\.(\d{3})(\d)/, '.$1.$2');
                value = value.replace(/(\d{8})\.?(\d{4})(\d)/, '$1/$2$3');
                value = value.replace(/(\d{12})\.?(\d{4})(\d{2})/, '$1/$2-$3');
                value = value.replace(/(\d{12})\.?(\d{4})/, '$1/$2');
            }
            e.target.value = value;
        });
    }

    if (phoneInput) {
        phoneInput.addEventListener('input', (e) => {
            let value = e.target.value.replace(/\D/g, '');
            if (value.length > 11) value = value.slice(0, 11);

            // Apply mask: (XX) XXXXX-XXXX
            if (value.length > 0) {
                value = value.replace(/^(\d{2})(\d)/, '($1) $2');
                value = value.replace(/(\d{5})(\d)/, '$1-$2');
            }
            e.target.value = value;
        });
    }
}

/**
 * Handles Google Reviews integration
 */
async function initGoogleReviews() {
    const carouselContainer = document.getElementById('google-reviews-container');
    const modalList = document.getElementById('modal-reviews-list');

    if (!carouselContainer || !modalList) return;

    try {
        // Call backend endpoint to get filtered reviews from Google API
        const response = await fetch('/api/reviews');
        if (!response.ok) throw new Error('Failed to fetch reviews');

        const data = await response.json();
        const reviews = data.reviews || [];

        if (reviews.length === 0) {
            const emptyMsg = '<div class="text-center p-5 text-muted">Nenhuma avaliação encontrada no momento.</div>';
            carouselContainer.innerHTML = `<div class="carousel-item active">${emptyMsg}</div>`;
            modalList.innerHTML = emptyMsg;
            return;
        }

        // Populate Carousel
        carouselContainer.innerHTML = reviews.map((rev, idx) => `
            <div class="carousel-item ${idx === 0 ? 'active' : ''}">
                <div class="review-card mx-auto">
                    <div class="star-rating">
                        ${'★'.repeat(rev.rating)}${'☆'.repeat(5 - rev.rating)}
                    </div>
                    <p class="fst-italic mb-4">"${rev.text}"</p>
                    <div class="d-flex align-items-center justify-content-center">
                        <img src="${rev.authorPhoto || '/images/default-avatar.png'}"
                             alt="${rev.authorName}"
                             class="rounded-circle me-2"
                             style="width: 40px; height: 40px; object-fit: cover;">
                        <span class="fw-bold">${rev.authorName}</span>
                    </div>
                </div>
            </div>
        `).join('');

        // Populate Modal
        modalList.innerHTML = reviews.map(rev => `
            <div class="list-group-item p-3">
                <div class="d-flex justify-content-between align-items-center mb-2">
                    <div class="d-flex align-items-center">
                        <img src="${rev.authorPhoto || '/images/default-avatar.png'}"
                             class="rounded-circle me-2"
                             style="width: 32px; height: 32px; object-fit: cover;">
                        <span class="fw-bold">${rev.authorName}</span>
                    </div>
                    <div class="star-rating mb-0" style="font-size: 0.9rem;">
                        ${'★'.repeat(rev.rating)}${'☆'.repeat(5 - rev.rating)}
                    </div>
                </div>
                <p class="mb-1 text-muted small">${rev.text}</p>
                <div class="text-end">
                    <small class="text-secondary">${rev.relativeTime || ''}</small>
                </div>
            </div>
        `).join('');

    } catch (error) {
        console.error('Google Reviews Error:', error);
        const errorMsg = '<div class="text-center p-5 text-danger">Não foi possível carregar as avaliações no momento.</div>';
        carouselContainer.innerHTML = `<div class="carousel-item active">${errorMsg}</div>`;
        modalList.innerHTML = errorMsg;
    }
}
