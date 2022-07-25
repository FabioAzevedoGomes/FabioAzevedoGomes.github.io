function updateSearchText(newText) {
    document.getElementById("lookingForText").textContent = newText
}

function updateShowHideText(shouldShow) {
    const hideText = "Hide accidents"
    const showText = "Show accidents"
    let newText = ""
    if (shouldShow) {
        newText = showText
    } else {
        newText = hideText
    }
    document.getElementById("showHideText").textContent = newText
}

function updateShowOrHideAccidents() {
    shouldShow = document.getElementById("showHideSwitch").checked
    updateShowHideText(shouldShow)
    if (shouldShow) {
        document.getElementById('matching-accidents-tooltip').style.display = 'flex'
        getAccidents();
    } else {
        document.getElementById('matching-accidents-tooltip').style.display = 'none'
        clearMap();
    }
}

function toggleAbout() {
    var aboutCard = document.getElementById('about-card-wrapper'); 
    if (aboutCard) {
        if (aboutCard.style.display == 'flex') {
            aboutCard.style.display = 'none';
        } else if (aboutCard.style.display == 'none') {
            aboutCard.style.display = 'flex';
        }
    }
}

document.getElementById('search-button').addEventListener("click", function(event){
    event.preventDefault()
});
