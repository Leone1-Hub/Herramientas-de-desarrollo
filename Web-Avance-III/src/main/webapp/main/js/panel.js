function openPanel(title, imgSrc, description, brandName, brandLogoUrl, price) {
    document.getElementById("panel-title").innerText = title;
    document.getElementById("panel-img").src = imgSrc;
    document.getElementById("panel-description").innerText = description;
    document.getElementById("panel-brand-name").innerText = `Marca: ${brandName}`;
    document.getElementById("panel-brand-logo").src = brandLogoUrl;
    document.getElementById("panel-price").innerText = price;

    document.getElementById("product-panel").classList.add("open");
}

function closePanel() {
    document.getElementById("product-panel").classList.remove("open");
}


