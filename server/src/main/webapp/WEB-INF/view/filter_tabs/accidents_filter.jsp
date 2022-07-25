<div class="filter-tab-parent" id="accidents-filter-tab">
	<div class="card bottom-tab accidents-filter">
	    <div id="body-accidents-filter" class="collapse hide" aria-labelledby="footing-accidents-filter">
	        <div class="card-body">
				<button type="button" class="btn btn-outline-danger remove-filter-button" onclick="removeAccidentFilter()">Remove filter</button>
				<div class="form-group accident-filters-list-parent">
					<form id="accident-filters-list">
						<div id="accidents-selected-filters">	
							<div class= "form-row" id="accident-filter-id-0">
								<div class="col">
									No filters active.
								</div>						
							</div>
						</div>

						<!-- Add a filter row -->
						<div id="accidents-add-a-filter-row" class="form-row accident-filter-row">
						    <div class="col">
						        <select class="form-control form-select" aria-label="Filter attribute selection" type="selection" id="default-accident-filter-attribute-selector" onchange="updateAccidentFilterAdditionOptions()">
						          <option selected value="accidents-id-filter">Identifier</option>
						          <option value="accidents-death-filter">Deaths</option>
						          <option value="accidents-light-inj-filter">Light Injuries</option>
						          <option value="accidents-serious-inj-filter">Serious Injuries</option>
						          <option value="accidents-type-filter">Type</option>
						          <option value="accidents-vehicle-filter">Vehicles</option>
						        </select>
						    </div>
						    <div class="col"> <!-- Can change depending on filter-->
						        <select class="form-control form-select" aria-label="Filter type selection" type="selection" id="default-filter-operator-selector">
								  <option value="IS">is</option>
								  <option value="IS NOT">is not</option>
						        </select>
						    </div>
						    <div class="col">
						        <input class="form-control form-input" type="text" id="accidents-id-filter">
						    </div>
						    <div class="col-lg-2">
						        <button type="button" class="btn btn-outline-success remove-filter-button" onclick="addAccidentFilterRow()">+</button>
						    </div>
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