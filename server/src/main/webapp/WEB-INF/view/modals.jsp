<div class="modal fade" id="pickFirstLocationModal" tabindex="-1" role="dialog"
    aria-labelledby="pickFirstLocationModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="pickFirstLocationModalLabel">Pick a location</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                Let's find a safe path to where you want to go!
                Pick the starting point of your journey.
                </br>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal"
                    onclick="pathFindingProcess()">Let's go!</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="chooseFirstLocationModal" tabindex="-1" role="dialog"
    aria-labelledby="chooseFirstLocationModal" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="chooseFirstLocationModal">Pick a location</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                These are the locations we know around this area, select where you want to start.
                </br>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">OK</button>
            </div>
        </div>
    </div>
</div>