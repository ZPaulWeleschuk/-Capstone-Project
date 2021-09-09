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
        <link href="${pageContext.request.contextPath}/CSS/workorder.css" type="text/css" rel="stylesheet" />
        <title>Work Order</title>
    </head>
    <body>
        <div class="header">
            <a href="/"><img src="./Images/WOM_fullLogo_with_bg.png"></a>
            <h2>Work Order</h2>
            <nav>
                <a class="logoutBtn firstNav" href="/">Home</a>
                <a class="logoutBtn" href="login?logout">Logout</a>
            </nav>
        </div>

        <div class="container">
            <div class="table">
                <div class="table__header">
                    <h2>Work Order </h2>
                </div>
                <div style="overflow-x: auto;">
                    <table>
                        <tr>
                            <th><b>Work Order ID</b></th>
                            <th><b>Order Type</b></th>
                            <th><b>Order Description</b></th>
                            <th><b>Required Start Date</b></th>
                            <th><b>Required End Date</b></th>
                            <th><b>Work Center</b></th>
                            <th><b>Status</b></th>
                        </tr>

                        <tr>
                            <fmt:formatDate var="startDate" value="${workOrder.requiredStartDate}" pattern="yyyy-MM-dd"/>
                            <fmt:formatDate var="endDate" value="${workOrder.requiredEndDate}" pattern="yyyy-MM-dd"/>
                            <td>${workOrder.workOrderId}<input type="hidden" name="workOrderId"
                                                               value="${workOrder.workOrderId}">
                            </td>
                            <td>${workOrder.orderType.typeDesc}</td>
                            <td>${workOrder.description}</td>
                            <td>${startDate}</td>
                            <td>${endDate}</td>
                             <td>${workOrder.notificationId.workCenterId.workCenterName}</td>
                              <td>${workOrder.status.statusDesc}</td>
                        </tr>
                    </table>
                </div>

            </div>
            <div class="table">
                <div class="table__header">
                    <h2>Notifications </h2>
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
                            <th><b>Status</b></th>
                            <th><b>Navigation</b></th>
                        </tr>
                        <tr>
                            <td>${workOrder.notificationId.notificationId}<input type="hidden" name="notificationId"
                                                                                 value="${workOrder.notificationId.notificationId}"></td>
                            <td>${workOrder.notificationId.notificationType.typeDesc}</td>                            
                            <td>${workOrder.notificationId.causeId.codeDescription}</td>
                            <td>${workOrder.notificationId.damageId.codeDescription}</td>
                            <td>${workOrder.notificationId.plantId.plantName}  </td>
                           
                            <td>${workOrder.notificationId.techObjId.techObjName}</td>
                            <td>${workOrder.notificationId.status.statusDesc}<input type="hidden" name="notificationStatus"
                                                                                    value="${workOrder.notificationId.status.statusId}"></td>
                             <td class="td__orderDetail">
                             <a class="detail submitBtn__ind__orderDetail" href="/location?workOrderId=${workOrder.workOrderId}">Map</a>   
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <br>
            <div class="table">
                <div class="table__header">
                    <h2>Operations </h2>
                </div>
                <div style="overflow-x: auto;">
                    <c:if test="${operationError==true}">
                        <span><b>${operationErrorMessage}</b></span><br><br>
                    </c:if>
                    <table class="form__operations">
                        <tr>
                            <th><b>Operation ID</b></th>
                            <th><b>Assigned To</b></th>
                            <th><b>Description</b></th>
                            <th><b>Status</b></th>
                            <th><b>Comments</b></th>
                            <th class="form__action"></th>
                        </tr>
                        <c:forEach items="${workOrder.operationsList}" var="operation">
                            <tr>
                            <form action="workorder" method="POST" >
                                <td>${operation.operationId}</td>
                                <td>${operation.personResponsible.userFname}
                                    ${operation.personResponsible.userLname}<input type="hidden" name="personId"
                                           value="${operation.personResponsible.userId}"></td>
                                <td>${operation.description}<input type="hidden" name="operationDescription"
                                                                   value="${operation.description}"></td>
                                <td>
                                    <select name="updateStatus">
                                        <c:forEach items="${allStatus}" var="status">
                                            <option value="${status.statusId}" <c:if
                                                        test="${status.statusId eq operation.status.statusId}"> selected </c:if>
                                                    >${status.statusDesc}</option>
                                        </c:forEach>

                                    </select>
                                </td>
                                <td><input name="longText" type="text" value="${operation.longText}"></td>
                                <td class="form__action"><input class="submitBtn submitBtn__ind" type="submit" name="operationToUpdate"
                                                                value="Update"><input type="hidden" class="submitBtn"  name="operationIdToEdit"
                                                                value="${operation.operationId}"><input type="hidden" name="action"
                                                                value="updateOperation"></td>
                            </form>
                            </tr>
                        </c:forEach>
                        <c:if test="${(roleId==30001) || (roleId==30002) || (roleId==30003)}">
                        <tr>
                            <form action="workorder" method="POST">
                                <td><input type="text" name="newOperationId" id="newOperationId"></td>
                                <td><select name="newOperationAssignTo" id="newOperationAssignTo">
                                        <c:forEach items="${allUsers}" var="user">
                                            <option value="${user.userId}">${user.userFname} ${user.userLname}</option>
                                        </c:forEach>
                                    </select></td>
                                <td><input type="text" name="newOperationDesc" id="newOperationDesc" value="${operation.description}"></td>
                                <td><select name="newOperationStatus" id="newOperationStatus">
                                        <c:forEach items="${allStatus}" var="status">
                                            <option value="${status.statusId}">${status.statusDesc}</option>
                                        </c:forEach>
                                    </select></td>
                                <td><input name="newOperationLongText" id="newOperationLongText" type="text" value="${operation.longText}"></td>
                                <td class="form__action">
                                    <input class="submitBtn submitBtn__ind" type="submit" name="addOperation" value="Add Operation" id="createOperationBtn">
                                    <input type="hidden" name="action" value="createOperation">
                                </td>
                            </form>
                        </tr>
                        </c:if>
                    </table>
                </div>
                <p class="message">${operationMessage}</p>
                <br>
                <c:if test="${showUpdateWorkOrderButton==false}">
                    <span><b>Complete all operations to unlock closing notes</b></span>
                </c:if>
                <c:if test="${showUpdateWorkOrderButton==true}">
                    <span><b>Update closing notes to close the work order</b></span>
                </c:if>                
            </div>
            <c:if test="${showUpdateWorkOrderButton==true}">
                <div class="table closingNotes">
                    <div class="table__header">
                        <h2>Closing Notes </h2>
                    </div>


                    <table class="closingNotesTable">
                        <form action="workorder" method="POST">
                            <tr>                                
                                <td> <textarea cols="50" style="font-family:'72';" class="closingNotesTable__textArea" name="closingNotes" placeholder="Enter closing notes" rows="10">${workOrder.notes}</textarea></td>
                            </tr>
                            <tr>
                                <td>
                                    <input class="submitBtn" type="submit" name="update" value="Update Work Order"><input type="hidden"
                                                                                                                          name="action" value="updateWorkOrder">
                                </td>
                            </tr>
                        </form>

                    </table>
                <p class="message">${closingMessage}</p>    
                </div>
            </c:if>
        </div>
        <script type="text/JavaScript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js" ></script>
        <script src="${pageContext.request.contextPath}/JS/webstorage.js"></script>
    </body>
</html>
