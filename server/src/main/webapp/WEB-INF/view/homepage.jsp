<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Accident Viewer</title>
        <link rel="icon" type="image/ico" href="images/favicon.ico" />
        <%@ include file="include/external_scripts.jsp" %>
        <%@ include file="include/local_scripts.jsp" %>
        <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@page contentType="text/html;charset=UTF-8" language="java" %>
    </head>

    <body>
        <div class="main">
            <%@ include file="topnav.jsp" %>
            <%@ include file="modals.jsp" %>
            <div>
                <div class="loader-container" style="display: none;" id="loading-spinner">
                    <div class="spinner"></div>
                </div>
                <div id="map" class="map"></div>
            </div>
            <%@ include file="filters.jsp" %>
            <%@ include file="accident-card.jsp" %>
            <%@ include file="about-card.jsp" %>
        </div>
    </body>
</html>