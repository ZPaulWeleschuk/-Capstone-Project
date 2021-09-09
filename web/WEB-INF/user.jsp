<%-- 
    Document   : user
    Created on : Jun 16, 2021, 6:22:37 PM
    Author     : Zennon Weleschuk
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.request.contextPath}/CSS/commonStyles.css" type="text/css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/CSS/user.css" type="text/css" rel="stylesheet" /> 
        <title>User Page</title>
    </head>
    <body>
        <div class="header">
            <a href="/"><img src="./Images/WOM_fullLogo_with_bg.png"></a>
        <h2>Worker Page</h2>
        <nav>
            <a class="logoutBtn firstNav" href="/">Home</a>
            <a class="logoutBtn" href="login?logout">Logout</a>
        </nav>

    </div>
    <div class="container">
        <h1>Jobs for ${userFName} ${userLName} </h1>
        <h2>Work Center: ${userWorkCenter}</h2>
        <br>
        <div class="table" style="overflow-x: auto;">
            <table>
                <tr>
                    <th><b>Work Order ID</b></th>
                    <th><b>Order Type</b></th>
                    <th><b>Order Description</b></th>
                    <th><b>Required Start Date</b></th>
                    <th><b>Required End Date</b></th>
                    <th><b>Order Detail</b></th>
                </tr>

                <c:forEach var="workOrder" items="${workOrders}">
                      <fmt:formatDate var="startDate" value="${workOrder.requiredStartDate}" pattern="yyyy-MM-dd"/>
                        <fmt:formatDate var="endDate" value="${workOrder.requiredEndDate}" pattern="yyyy-MM-dd"/>
                    <tr>
                        <td>${workOrder.workOrderId}</td>
                        <td>${workOrder.orderType.typeDesc}</td>
                        <td>${workOrder.description}</td>
                        <td>${startDate}</td>
                        <td>${endDate}</td>
                        <td><a class="detail detail submitBtn__ind__orderDetail" href="/workorder?workOrderId=${workOrder.workOrderId}">Order Details</a></td>
                    </tr>

                </c:forEach>
            </table>
        </div>
    </div>
        
    </body>
</html>
