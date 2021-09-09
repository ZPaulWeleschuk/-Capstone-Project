<%-- 
    Document   : location
    Created on : Jul 26, 2021, 10:49:41 AM
    Author     : 807930
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="apple-touch-icon" sizes="180x180" href="/apple-touch-icon.png">
        <link rel="icon" type="image/png" sizes="32x32" href="/favicon-32x32.png">
        <link rel="icon" type="image/png" sizes="16x16" href="/favicon-16x16.png">
        <link rel="manifest" href="/site.webmanifest">
        <link rel="mask-icon" href="/safari-pinned-tab.svg" color="#5bbad5">
        <meta name="msapplication-TileColor" content="#603cba">
        <meta name="theme-color" content="#ffffff">
        <script src="https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.6.1/build/ol.js"></script>
        <link type="text/css" rel="stylesheet" href="https://cdn.jsdelivr.net/gh/openlayers/openlayers.github.io@master/en/v6.6.1/css/ol.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.request.contextPath}/CSS/commonStyles.css" type="text/css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/CSS/location.css" type="text/css" rel="stylesheet" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <title>Work Order</title>
    </head>
    <body>
        <div class="header">
            <a href="/"><img src="./Images/WOM_fullLogo_with_bg.png"></a>
            <h2>Work Order Location</h2>
            <nav>
                <a class="logoutBtn firstNav" href="/">Home</a>
                <a class="logoutBtn" href="login?logout">Logout</a>
            </nav>
        </div>

        <div class="container">
            <div class="table">
                <div class="table__header">
                    <h2>Work Order Location</h2>
                </div>
                <a class="detail submitBtn__ind__orderDetail" href="/workorder?workOrderId=${workOrder.workOrderId}">Go back to order details.</a>

                <p id="plantName" value="${workOrder.notificationId.plantId.plantName}" >${workOrder.notificationId.plantId.plantName}</p>
                
                <div id="map" class="map" style="width:auto; height:400px"></div>
            </div>
                
            <div class="show_direction">
                <h2 class="showDirectionHeader" id="showDirectionHeader">
                    <span>Directions</span>
                    <img id="showDirectionHeaderImage" src="./Images/angle_bracket_down.png" /> 
                </h2>
                <div class="location_inactive">
                    <span>${workOrder.notificationId.plantId.location}</span>                                      
                </div>
            </div>
        </div>
            <script type="module" src="${pageContext.request.contextPath}/JS/geolocation.js"></script>
    </body>
</html>
