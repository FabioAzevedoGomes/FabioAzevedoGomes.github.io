<div class="filter-tab-parent" id="climate-filter-tab">
	<div class="card bottom-tab climate-filter">
	    <div id="body-climate-filter" class="collapse hide" aria-labelledby="footing-climate-filter">
	        <div class="card-body">
				<button type="button" class="btn btn-outline-danger remove-filter-button" onclick="removeClimateFilter()">Remove filter</button>
				<div class="form-group climate-filters-list-parent">
					<form id="climate-filters-list">
						<div id="climate-selected-filters">	
							<div class= "form-row" id="climate-filter-id-0">
								<div class="col">
									No filters active.
								</div>						
							</div>
						</div>

						<!-- Add a filter row -->
						<div id="climate-add-a-filter-row" class="form-row climate-filter-row">
						    <div class="col">
						        <select class="form-control form-select" aria-label="Filter attribute selection" type="selection" id="default-climate-filter-attribute-selector" onchange="updateClimateFilterAdditionOptions()">
						          <option selected value="climate-visibility-filter">Visibility</option>
						          <option value="climate-relative-humidity-percent-filter">Relative Humidity</option>
						          <option value="climate-precipitation-filter">Precipitation</option>
						          <option value="climate-wind-speed-filter">Wind Speed</option>
						          <option value="climate-air-temp-filter">Air Temperature</option>
						        </select>
						    </div>
						    <div class="col"> <!-- Can change depending on filter-->
						        <select class="form-control form-select" aria-label="Filter type selection" type="selection" id="default-climate-filter-operator-selector">
					              <option value="EQUALS">=</option>
					              <option value="GREATER_OR_EQUAL">>=</option>
					              <option value="LESS_OR_EQUAL"><=</option>
						        </select>
						    </div>
						    <div class="col">
						        <input class="form-control form-input" type="number" step="0.01" id="climate-visibility-filter">
						    </div>
						    <div class="col-lg-2">
						        <button type="button" class="btn btn-outline-success remove-filter-button" onclick="addClimateFilterRow()">+</button>
						    </div>
						</div>

					</form>
		        </div>
	        </div>
	    </div>
	    <div class="card-footer">
	        <span class="filter-card-footer-text">
		        <a data-toggle="collapse" href="#body-climate-filter" aria-expanded="true" aria-controls="body-climate-filter" id="footing-climate-filter" class="d-block">
		        	Climate Filters
		        </a>
		    </span>
	    </div>
	</div>
</div>