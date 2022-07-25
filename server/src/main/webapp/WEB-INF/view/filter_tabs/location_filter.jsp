<div class="filter-tab-parent" id="location-filter-tab">
	<div class="card bottom-tab location-filter">
	    <div id="body-location-filter" class="collapse hide" aria-labelledby="footing-location-filter">
	        <div class="card-body">
				<button type="button" class="btn btn-outline-danger remove-filter-button" onclick="removeLocationFilter()">Remove filter</button>
				<div class="form-group location-filters-list-parent">
					<form id="location-filters-list">
						<div id="location-selected-filters">	
							<div class= "form-row" id="location-filter-id-0">
								<div class="col">
									No filters active.
								</div>						
							</div>
						</div>

						<!-- Add a filter row -->
						<div id="location-add-a-filter-row" class="form-row location-filter-row">
						    <div class="col">
						        <select class="form-control form-select" aria-label="Filter attribute selection" type="selection" id="default-location-filter-attribute-selector" onchange="updateLocationFilterAdditionOptions()">
						          <option selected value="location-street-filter">Street</option>
						          <option value="location-latitude-filter">Latitude</option>
						          <option value="location-longitude-filter">Longitude</option>
						          <option value="location-region-filter">Region</option>
						        </select>
						    </div>
						    <div class="col"> <!-- Can change depending on filter-->
						        <select class="form-control form-select" aria-label="Filter type selection" type="selection" id="default-location-filter-operator-selector" disabled>
					              <option value="IS">is</option>
						        </select>
						    </div>
						    <div class="col">
						        <input class="form-control form-input" type="text" id="location-street-filter">
						    </div>
						    <div class="col-lg-2">
						        <button type="button" class="btn btn-outline-success remove-filter-button" onclick="addLocationFilterRow()">+</button>
						    </div>
						</div>

					</form>
		        </div>
	        </div>
	    </div>
	    <div class="card-footer">
	        <span class="filter-card-footer-text">
		        <a data-toggle="collapse" href="#body-location-filter" aria-expanded="true" aria-controls="body-location-filter" id="footing-location-filter" class="d-block">
		        	Location Filters
		        </a>
		    </span>
	    </div>
	</div>
</div>