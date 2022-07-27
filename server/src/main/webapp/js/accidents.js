let displayedAccidentId = '';

function updateAccidentIdLabel(new_label) {

	accidentIdSlot = document.getElementById('accident-card-id-slot');
	if (accidentIdSlot) {
		accidentIdSlot.innerHTML = new_label;
	}
}

function toggleAccident() {
    var accidentCard = document.getElementById('accident-card-wrapper'); 
    if (accidentCard) {
        if (accidentCard.style.display == 'flex') {
            accidentCard.style.display = 'none';
            displayedAccidentId = ''
        } else if (accidentCard.style.display == 'none') {
            accidentCard.style.display = 'flex';
        }
    }
}

function getAccidentDataHTML(accident) {
	return JSON.stringify(accident);
}


function populateAccidentContentToCard(accident) {
    cardBody = document.getElementById('accident-card-body');
    if (cardBody){ 
        cardBody.innerHTML = getAccidentDataHTML(accident);
    }
}

function displayAccidentDetails(accident) {
    let accidentId = accident['externalId'];

    if (accidentId != displayedAccidentId) {
        populateAccidentContentToCard(accident);
        updateAccidentIdLabel(accidentId);
    }

    if (!displayedAccidentId || accidentId == displayedAccidentId) {
        $('#accident-card')[0].click();
    }

    displayedAccidentId = accidentId;
}
