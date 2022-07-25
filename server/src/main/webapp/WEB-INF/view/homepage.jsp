<!DOCTYPE html>
<%@ include file="include/headers.jsp" %>
    <html lang="en">

    <head>
        <title>Accident Viewer</title>
        <link rel="icon" type="image/ico" href="images/favicon.ico" />
        <script src="/js/homepage.js"></script>
    </head>

    <body>
        <div class="main">
            <%@ include file="topnav.jsp" %>
            <%@ include file="filters.jsp" %>
            <div id="specific-data-overlay">
                <%@ include file="specific-data.jsp" %>
            </div>
            <div class="map-area">
                <%@ include file="map.jsp" %>
            </div>
            <%@ include file="about.jsp" %>
        </div>
    </body>
    <script src="js/requests.js"></script>
    </html>