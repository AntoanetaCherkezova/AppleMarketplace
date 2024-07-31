const map = L.map('map').setView([42.70021805650745, 23.292227983474735], 16);

L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
}).addTo(map);

const pointerIcon = L.icon({
    iconUrl: '/images/pointer.png',
    iconSize: [40, 40],
    iconAnchor: [16, 32],
});

L.marker([42.70021805650745, 23.292227983474735], { icon: pointerIcon }).addTo(map);