<div class="filter-tab-parent" id="accident-filter-tab">
	<div class="card bottom-tab accidents-filter">
	    <div id="body-accidents-filter" class="collapse hide" aria-labelledby="footing-accidents-filter">
	        <div class="card-body">
				<button type="button" class="btn btn-outline-danger remove-filter-button" onclick="removeFilter(FilterClasses.Accident)">Remove filter</button>
				<div class="form-group accident-filters-list-parent">
					<form id="accident-filters-list">
						<div id="accident-selected-filters">
						</div>
						<div id="accident-add-a-filter-row" class="form-row accident-filter-row">
						</div>
					</form>
		        </div>
		    </div>
	    </div>
	    <div class="card-footer">
	        <span class="filter-card-footer-text">
		        <a data-toggle="collapse" href="#body-accidents-filter" aria-expanded="true" aria-controls="body-accidents-filter" id="footing-accidents-filter" class="d-block">
		        	Accident Filters
		        </a>
		    </span>
	    </div>
	</div>
</div>