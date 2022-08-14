
var showHelp = true;

class PathFinder {

    constructor() {
        this.startPoint = null;
        this.endPoint = null;
        this.currentPoint = 'start';
    }

    setLocation(point) {
        if (this.currentPoint == 'start') {
            this.currentPoint = 'end';
            this.startPoint = point;
        } else {
            this.currentPoint = 'start';
            this.endPoint = point;
            globalMap.disableLayer(LayerNames.LocationSuggestion);
            globalMap.reloadLayer(LayerNames.PathViewing);
        }
    }

    isComplete() {
        return this.startPoint != null && this.endPoint != null;
    }

    showStartPickModal() {
        //document.getElementById("pickFirstLocationModal").modal("show");
        //$("#pickFirstLocationModal")[0].modal('show'); Doesnt work whatever
    }

    getPoints() {
        return [this.startPoint, this.endPoint];
    }
};

globalPathfinder = new PathFinder();

function pathFindingProcess() {
    globalMap.disableLayer(LayerNames.LocationSuggestion);
    if (!globalPathfinder.isComplete()) {
        globalMap.enableLayer(LayerNames.LocationSuggestion);
        globalMap.centerOnLayer(LayerNames.LocationSuggestion);
    }
}
