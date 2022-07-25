<div class="filter-tab-parent" id="time-filter-tab">
	<div class="card bottom-tab time-filter">
	    <div id="body-time-filter" class="collapse hide" aria-labelledby="footing-time-filter">
	        <div class="card-body">
				<button type="button" class="btn btn-outline-danger remove-filter-button" onclick="removeTimeFilter()">Remove filter</button>
				<div class="form-group time-filters-list-parent">
					<form id="time-filters-list">
						<div id="time-selected-filters">	
							<div class= "form-row" id="time-filter-id-0">
								<div class="col">
									No filters active.
								</div>						
							</div>
						</div>

						<!-- Add a filter row -->
						<div id="time-add-a-filter-row" class="form-row time-filter-row">
						    <div class="col">
						        <select class="form-control form-select" aria-label="Filter attribute selection" type="selection" id="default-time-filter-attribute-selector" onchange="updateTimeFilterAdditionOptions()">
						          <option selected value="time-date-filter">Date</option>
						          <option value="time-time-filter">Time</option>
						        </select>
						    </div>
						    <div class="col"> <!-- Can change depending on filter-->
						        <select class="form-control form-select" aria-label="Filter type selection" type="selection" id="default-time-filter-operator-selector">
						          <option value="IS">is</option>
						          <option value="BEFORE">is before</option>
						          <option value="AFTER">is after</option>
						        </select>
						    </div>
						    <div class="col date">
						        <div class="input-group date" id="time-date-filter" data-target-input="nearest">
						            <input type="text" class="form-control datetimepicker-input" data-target="#time-date-filter"/>
						            <div class="input-group-append" data-target="#time-date-filter" data-toggle="datetimepicker">
						                <div class="input-group-text"><i class="fa fa-clock-o"></i></div>
						            </div>
						        </div>
						    </div>
						    <div class="col-lg-2">
						        <button type="button" class="btn btn-outline-success remove-filter-button" onclick="addTimeFilterRow()">+</button>
						    </div>
						</div>

					</form>
		        </div>
	        </div>
	    </div>
	    <div class="card-footer">
	        <span class="filter-card-footer-text">
		        <a data-toggle="collapse" href="#body-time-filter" aria-expanded="true" aria-controls="body-time-filter" id="footing-time-filter" class="d-block">
		            Time Filters
		        </a>
		    </span>
		</div>
	</div>
</div>