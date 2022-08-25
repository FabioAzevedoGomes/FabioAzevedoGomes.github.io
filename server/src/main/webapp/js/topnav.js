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
        globalMap.reloadLayer(LayerNames.Accident);
    } else {
        document.getElementById('matching-accidents-tooltip').style.display = 'none'
        document.getElementById('match-quantity').innerHTML = 'Looking for';
        globalMap.disableLayer(LayerNames.Accident);
    }
}

function updateShowOrHideRiskHeatmap() {
    shouldShow = document.getElementById("showHideRiskSwitch").checked

    if (shouldShow) {
        globalMap.reloadLayer(LayerNames.RiskHeatmap);
    } else {
        globalMap.disableLayer(LayerNames.RiskHeatmap);
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