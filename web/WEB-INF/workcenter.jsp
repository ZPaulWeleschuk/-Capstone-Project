<%-- 
    Document   : workcenter
    Created on : Jun 14, 2021, 10:01:42 AM
    Author     : 807930
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
        <link href="${pageContext.request.contextPath}/CSS/workcenter.css" type="text/css" rel="stylesheet" />
        <title>Work Center</title>
    </head>
    <body>
        <!-- Header -->
        <div class="header">
            <a href="/"><img src="./Images/WOM_fullLogo_with_bg.png"></a>
            <h2>Work Center</h2>
            <nav>
                <a class="logoutBtn firstNav" href="/">Home</a>
                <a class="logoutBtn" href="login?logout">Logout</a>
            </nav>
        </div>
        <div class="container">
            <c:if test="${user.workCenterId.workCenterName != null}">
                <h1>${user.workCenterId.workCenterName}</h1>
            </c:if>
            <c:if test="${user.workCenterId.workCenterName == null}">
                <h1 style="color:red">Error: No Work center is assigned to ${user.userFname} ${user.userLname}</h1>
            </c:if>
            <!-- display notification table-->
            <div class="table">
                <div class="table__header">
                    <h2>View All Notifications for Work Center </h2>
                </div>
                <div style="overflow-x: auto;">
                    <table>
                        <tr>
                            <th><b>Notification ID</b></th>
                            <th><b>Notification Type</b></th>
                            <th><b>Cause</b></th>
                            <th><b>Damage</b></th>
                            <th><b>Plant</b></th>
                            <th><b>Technical Object</b></th>
                            <th><b>Created By</b></th>
                            <th><b>Current Status</b></th>
                            <th class="form__action"></th>
                        </tr>
                        <c:forEach items="${notifications}" var="notification">
                            <tr>
                                <td>${notification.notificationId}</td>
                                <td>${notification.notificationType.typeDesc}</td>
                                <td>${notification.causeId.codeDescription}</td>
                                <td>${notification.damageId.codeDescription}</td>
                                <td>${notification.plantId.plantName}</td>
                                <td>${notification.techObjId.techObjName}</td>
                                <td>${notification.createdBy.userId}</td>
                                <td>${notification.status.statusDesc}</td>
                                <td class="form__action">
                                    <form   action="workcenter" method="post" id="createAndSeeBtn" >
                                        <input  type="submit" class="submitBtn submitBtn__ind " name="orderOptions"
                                               value="Create/See WorkOrder">
                                        <input type="hidden" name="action" value="notifOptions" >
                                        <input type="hidden" name="notifId" value="${notification.notificationId}">
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>

            <!-- create a new notification-->
            <div class="create_form workOrder__form">
                <h2 class="create_form__header" id="createWorkOrderHeader">
                    <span>Create Work Order from Notification</span>
                    <img id="createWorkOrderHeaderImage" src="./Images/angle_bracket_up.png" />
                </h2>
                <div id="workOrder_inactive">
                    <form action="workcenter" method="post" class="form__select">
                        <div class="form__select__option">
                            Original Notification:
                            <input type="text" name="newOrderNotifId" id="newOrderNotifId" value="${newOrderNotifId}">
                        </div>
                        <div class="form__select__option">
                            <span> Notification Type: </span>
                            ${newOrderNotifType}
                        </div>
                        <div class="form__select__option">
                            <span> Cause: </span>
                            ${newOrderNotifCause}
                        </div>
                        <div class="form__select__option">
                            <span> Damage: </span>
                            ${newOrderNotifDamage}
                        </div>
                        <div class="form__select__option">
                            <span> Plant: </span>
                            ${newOrderNotifPlant}
                        </div>
                        <div class="form__select__option">
                            <span> Technical Object: </span>
                            ${newOrderNotifObj}
                        </div>
                        <div class="form__select__option">
                            <span> Select Order Type: </span>
                            <select name="newOrderType" id="newOrderType">
                                <c:forEach items="${orderTypes}" var="type">
                                    <option value="${type.typeId}">${type.typeDesc}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form__select__option">
                            <span> Enter Order Description: </span>
                            <input type="text" name="newOrderDesc" id="newOrderDesc">
                        </div>
                        <div class="form__select__option">
                            <span> Enter Required Start Date: </span>
                            <input type="datetime" name="newOrderStart" placeholder="MM/DD/YYYY" id="newOrderStart">
                        </div>
                        <div class="form__select__option">
                            <span> Enter Required End Date: </span>
                            <input type="datetime" name="newOrderEnd" placeholder="MM/DD/YYYY" id="newOrderEnd">
                        </div>
                        <div class="form__select__option"></div>
                        <div class="form__select__option"></div>
                        <input type="hidden" name="action" value="create">
                        <input class="submitBtn" type="submit" id="createWorkOrderBtn" value="Create">
                    </form>
                </div>
                 <p class="message">${notificationMessage}</p>           
            </div>

            <form action="workcenter" method="POST">
                <div class="table">

                    <div style="overflow-x: auto;">
                        <div class="table__size">
                            <div class="table__header">
                                <h2>View All Work Orders (${workOrdersLength})</h2>

                                <div class="table__header__options">
                                    <input type="hidden" name="action" value="deleteWorkOrder">
                                    <input type="submit" class="submitBtn" value="Delete">
                                </div>
                            </div>
                            <table>
                                <tr>
                                    <th class="square">□</th>
                                    <th><b>Work Order ID</b></th>
                                    <th><b>Order Type</b></th>
                                    <th><b>Order Description</b></th>
                                    <th><b>Required Start Date</b></th>
                                    <th><b>Required End Date</b></th>
                                    <th><b>Notification ID</b></th>
                                    <th><b>Work Center</b></th>
                                    <th><b>Current Status</b></th>
                                    <th>Order Detail</th>

                                </tr>
                                <c:forEach items="${orders}" var="order">
                                     <fmt:formatDate var="startDate" value="${order.requiredStartDate}" pattern="yyyy-MM-dd"/>
                                     <fmt:formatDate var="endDate" value="${order.requiredEndDate}" pattern="yyyy-MM-dd"/>
                                    <tr>
                                        <td class="td__square">
                                            <input type="checkbox" name="workOrderId" value=${order.workOrderId}>
                                        </td>
                                        <td>${order.workOrderId}</td>
                                        <td>${order.orderType.typeDesc}</td>
                                        <td>${order.description}</td>
                                        <td>${startDate}</td>
                                        <td>${endDate}</td>
                                        <td>${order.notificationId.notificationId}</td>
                                        <td>${order.notificationId.workCenterId.workCenterName}</td>
                                        <td>${order.status.statusDesc}</td>
                                        <td class="td__orderDetail">
                                   
                                            <a class="detail submitBtn__ind__orderDetail" href="/workorder?workOrderId=${order.workOrderId}">Detail</a>
                                  
                                            </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                </div>
            </form>


        </div>
        <script src="${pageContext.request.contextPath}/JS/workcenter.js"></script>
        <script type="text/JavaScript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js" ></script>
        <script src="${pageContext.request.contextPath}/JS/webstorage.js"></script>
    </body>
</html>
