<div class="card accident-card" id="accident-card-wrapper" style="display: none;">
    <div id="body-accident" class="collapse hide" aria-labelledby="accident-card">
        <div class="card-body accident-card-body" id="accident-card-body">
			<h2>Accident #<b id="accident-card-id">A659002</b></h2>
			<h5 id="accident-card-description">Collision on Monday, July 7th 2021, at 15:00</h5>
        	<div class="row">
	        	<div class="col">
					<div class="row">
			  			<div class="col">
			    			<h6>Vehicles</h6>
			    			<div class="row">
				    			<div class="col-lg-2">
				    				<i class="fa-solid fa-car"></i><br>
				    				<i class="fa-solid fa-motorcycle"></i><br>
			    					<i class="fa-solid fa-truck"></i><br>
		    						<i class="fa-solid fa-bus-simple"></i><br>
									<i class="fa-solid fa-bicycle"></i><br>
				    			</div>
				    			<div class="col-lg-10">
				    				<span id="accident-card-num-cars">0</span> Cars<br>
				    				<span id="accident-card-num-motorcycles">0</span> Motorcycles<br>
				    				<span id="accident-card-num-trucks">0</span> Trucks<br>
				    				<span id="accident-card-num-buses">0</span> Buses<br>
				    				<span id="accident-card-num-other">0</span> Other<br>
				    			</div>
							</div>
						</div>
						<div class="col">
							<h6>Seriousness</h6>
							<div class="row">
								<div class="col-lg-2">
									<i class="fa-solid fa-crutch"></i><br>
									<i class="fa-solid fa-user-injured"></i><br>
									<i class="fa-solid fa-skull"></i><br>
								</div>
								<div class="col-lg-10">
									<span id="accident-card-num-light-injuries">0</span> Light Injuries<br>
									<span id="accident-card-num-serious-injuries">0</span> Serious Injuries<br>
									<span id="accident-card-num-deaths">0</span> Deaths<br>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col">
					<h6><i class="fa-solid fa-cloud-rain"></i> Weather</h6>
					<div class="row">
						<div class="col-lg-1">
							<i class="fa-solid fa-droplet"></i><br>
							<i class="fa-solid fa-wind"></i><br>
							<i class="fa-solid fa-smog"></i><br>
							<i class="fa-solid fa-eye"></i><br>
							<i class="fa-solid fa-temperature-high"></i><br>
						</div>
						<div class="col-lg-11">
							Pecipitation of <span id="accident-card-precipitation">2</span> Millimeters<br>
							Wind speed of <span id="accident-card-wind-speed">1.5</span> m/s<br>
							Relative Humidity of <span id="accident-card-relative-humidity">60</span>%<br>
							Visibility at an estimated <span id="accident-card-visibility">60</span>%<br>
							Air temperature of <span id="accident-card-air-temperature">99</span>&#176;C<br>
						</div>
					</div>
				</div>
			</div>
			<div style="margin-top:2%"></div>
			<div class="row">
				<div class="col">
					<h6><i class="fa-solid fa-location-dot"></i> Location</h6>
					<div class="row">
						<div class="col-lg-1">
							<i class="fa-solid fa-road"></i><br>
							<i class="fa-solid fa-road"></i><br>
							<i class="fa-solid fa-map"></i><br>
							<i class="fa-solid fa-compass"></i><br>
						</div>
						<div class="col-lg-11">
							<span id="accident-card-street1">Rua XYZ</span><br>
							<span id="accident-card-street2">Travessa ABC</span><br>
							<span id="accident-card-region">North</span> region<br>
							<span id="accident-card-lat-long">-51.231932W, -29.1278321S</span><br>
						</div>
					</div>
				</div>
				<div class="col">
				</div>
			</div>
		</div>
    </div>
    <div class="card-footer">
        <span class="">
	        <a data-toggle="collapse" href="#body-accident" aria-expanded="true" aria-controls="body-accidnet" id="accident-card" class="d-block" onclick="toggleAccident()">
	            Accident #<b><span id="accident-card-id-slot">?</span></b>
	        </a>
	    </span>
	</div>
</div>
