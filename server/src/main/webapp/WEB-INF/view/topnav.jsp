<nav class="navbar navbar-expand-lg navbar-light bg-light" style="border-bottom: 1px solid black">
  <a class=" navbar-brand" href="#">TCC</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" data-toggle="collapse" href="#body-about" aria-expanded="true" aria-controls="body-about" onclick="toggleAbout()">
          About<span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item active">
        <a class="nav-link" href="#">Credits <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item active">
        <a class="nav-link" href="#">Statistics</a>
      </li>
      <li class="nav-item active dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="fliterDropdown" role="button" data-toggle="dropdown">Filters</a>
        <div class="dropdown-menu" aria-labelledby="fliterDropdown">
          <a class="dropdown-item" href="#" onclick="addAccidentFilter()">Accidents</a>
          <a class="dropdown-item" href="#" onclick="addTimeFilter()">Time</a>
          <a class="dropdown-item" href="#" onclick="addLocationFilter()">Location</a>
          <a class="dropdown-item" href="#" onclick="addClimateFilter()">Climate</a>
        </div>
      </li>
      <li class="nav-item active">
        <a class="nav-link" href="#">Suggest a path</a>
      </li>
    </ul>

    <div class="nav-item inactive">
      <div class="nav-link matching-accidents-tooltip" id="matching-accidents-tooltip">
        <span style ="display: inline-block;" id="match-quantity">?</span> Matching accidents</div>
    </div>

    <!-- Show/Hide -->
    <form class="nav-item">
      <div class="form-inline">
        <div class="custom-control custom-switch my-lg-2">
          <input type="checkbox" class="custom-control-input" id="showHideSwitch" checked="false"
            onchange="updateShowOrHideAccidents()">
          <label id="showHideText" class="custom-control-label" for="showHideSwitch">Hide accidents</label>
        </div>
      </div>
    </form>

    <!-- Search bar-->
    <form class="form-inline my-3 my-lg-0">
      <div class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown"
          aria-haspopup="true" aria-expanded="false">
          I'm looking for <span id="lookingForText">...</span>
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="#" onclick="updateSearchText('a street')">A street</a>
          <div class="dropdown-divider"></div>
          <a class="dropdown-item" href="#" onclick="updateSearchText('an accident with id')">An accident by id</a>
          <a class="dropdown-item" href="#" onclick="updateSearchText('accidents with severity')">An accident by
            severity</a>
        </div>
      </div>
      <input class="form-control mr-sm-3" type="search" placeholder="Search" aria-label="Search">
      <button class="btn btn-outline-success my-3 my-sm-0" type="submit" id="search-button">Search</button>
    </form>

  </div>
</nav>
<script src="js/topnav.js"></script>