<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
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
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="${pageContext.request.contextPath}/CSS/commonStyles.css" type="text/css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/CSS/admin.css" type="text/css" rel="stylesheet" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="./JS/sorttable.js"></script>

        <title>Admin</title>
    </head>

    <body>
        <div class="header">
            <a href="/"><img src="./Images/WOM_fullLogo_with_bg.png"></a>
            <h2>Admin Page</h2>
            <nav>
                <a class="logoutBtn firstNav" href="/">Home</a>
                <a class="logoutBtn" href="login?logout">Logout</a>
            </nav>

        </div>
        <div class="container">


            <h1>Welcome back, ${user.userFname} ${user.userLname}</h1>            
            <div class="notification__form create_form">
                <h2 class="create_form__header" id="createNotificationHeader"><span>Create a New Notification</span> <img id="createNotificationHeaderImage" src="./Images/angle_bracket_down.png" /> </h2>
                <div class="notification_inactive">
                    <form action="admin" method="post" class="form__select">
                        <div class="form__select__option">
                            <span>Notification type:</span>
                            <select name="notificationType" id="createNotifType">
                                <c:forEach items="${notifTypes}" var="type">
                                    <option value="${type.typeId}">${type.typeDesc}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form__select__option" >
                            <span>Cause: </span>
                            <select name="notifCause" id="createNotifCause">
                                <c:forEach items="${causes}" var="cause">
                                    <option value="${cause.causeId}">${cause.codeDescription}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form__select__option">
                            <span>Damage: </span>
                            <select name="notifDamage" id="createNotifDamage">
                                <c:forEach items="${damages}" var="damage">
                                    <option value="${damage.damageId}">${damage.codeDescription}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form__select__option" >
                            <span>Plant:</span>
                            <select name="notifPlant" id="createNotifPlant">
                                <c:forEach items="${plants}" var="plant">
                                    <option value="${plant.plantId}">${plant.plantName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form__select__option">
                            <span>Technical Object:</span>
                            <select name="notifObject" id="createNotifTechObj">
                                <c:forEach items="${techObjects}" var="techObj">
                                    <option value="${techObj.techObjId}">${techObj.techObjName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form__select__option">
                            <span>Assign to:</span>
                            <select name="notifWorkCenter" id="createNotifWorkCenter">
                                <c:forEach items="${workCenters}" var="center">
                                    <option value="${center.workCenterId}">${center.workCenterName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <input type="hidden" id="createNotifAssignedBy" value="${user.userId}">
                        <input type="hidden" name="action" value="createNotification">
                        <input class="submitBtn" id="createNotificationBtn" type="submit" value="Create">
                    </form>
                </div>
                <p class="message">${notificationMessage}</p>
            </div>

            <!--                create a new user-->
            <div class="user__form create_form">
                <h2 class="create_form__header" id="createUserHeader"><span>Create a New User</span> <img id="createUserHeaderImage" src="./Images/angle_bracket_down.png" /></h2>
                <div class="user_inactive">
                    <form action="admin" method="post" class="form__select">
                        <div class="form__select__option">
                            <span>First Name: </span>
                            <input type="text" id="firstName" name="firstName">
                        </div>
                        <div class="form__select__option">
                            <span>Last Name: </span>
                            <input type="text" id="lastName" name="lastName">
                        </div>
                        <div class="form__select__option">
                            <span>Password: </span>
                            <input type="password" id="password" name="password">
                        </div>
                        <div class="form__select__option">
                            <span>Role: </span>
                            <select name="roles" id="userRole">
                                <c:forEach items="${roless}" var="role">
                                    <option value="${role.roleId}">${role.roleDesc}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form__select__option">
                            <span>Assign to: </span>
                            <select name="wc_Id" id="workCenter">
                                <option value=null></option>
                                <c:forEach items="${workCenters}" var="center">
                                    <option value="${center.workCenterId}">${center.workCenterName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form__select__option"></div>

                        <input type="hidden" name="action" value="createUser">
                        <input class="submitBtn" id="createUserBtn" type="submit" value="Create">

                    </form>
                </div>
                <p class="message">${message}</p>
            </div>
            <!--                    display notification table-->
            <form action="admin" method="POST">
                <div class="table">

                    <div style="overflow-x: auto;">
                        <div class="table__size">
                            <div class="table__header">
                                <h2>View All Notifications (${notificationsLength})</h2>
                                <div class="table__header__options">
                                    <input type="hidden" name="action" value="deleteNotification">
                                    <input class="submitBtn" type="submit" value="Delete">
                                </div>
                            </div>
                                <div class="searchOption">
                            <b>Search / Filter Table: 
                                <input id="searchNo" type="text" 
                                       placeholder="Search For...">
                            </b>
                                    </div>
                            <table id="mynos">
                                <tr>
                                    <th onclick="sortTable4(0)" class="square">□</th>
                                    <th onclick="sortTable4(1)"><b>Notification ID</b></th>
                                    <th onclick="sortTable4(2)"><b>Notification Type</b></th>
                                    <th onclick="sortTable4(3)"><b>Cause</b></th>
                                    <th onclick="sortTable4(4)"><b>Damage</b></th>
                                    <th onclick="sortTable4(5)"><b>Plant</b></th>
                                    <th onclick="sortTable4(6)"><b>Technical Object</b></th>
                                    <th onclick="sortTable4(7)"><b>Created By</b></th>
                                    <th onclick="sortTable4(8)"><b>Current Status</b></th>
                                    <th onclick="sortTable4(9)"><b>Assigned to</b></th>
                                </tr>
                                <c:forEach items="${notifications}" var="notification">
                                    <tbody id ="nos">
                                        <tr>
                                            <td class="td__square">
                                                <input type="checkbox" name="notificationId" value=${notification.notificationId}>
                                            </td>
                                            <td>${notification.notificationId}</td>
                                            <td>${notification.notificationType.typeDesc}</td>
                                            <td>${notification.causeId.codeDescription}</td>
                                            <td>${notification.damageId.codeDescription}</td>
                                            <td>${notification.plantId.plantName}</td>
                                            <td>${notification.techObjId.techObjName}</td>
                                            <td>${notification.createdBy.userId}</td>
                                            <td>${notification.status.statusDesc}</td>
                                            <td>${notification.workCenterId.workCenterName}</td>
                                        </tr>

                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </form>


            <!--                    display work order table-->
            <form action="admin" method="POST">
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
                                <div class="searchOption">
                            <b>Search / Filter Table: 
                                <input id="searchWo" type="text" 
                                       placeholder="Search For...">
                            </b>
                                    </div>
                            <table id="mywos">
                                <tr>
                                    <th onclick="sortTable3(0)" class="square">□</th>
                                    <th onclick="sortTable3(1)"><b>Work Order ID</b></th>
                                    <th onclick="sortTable3(2)"><b>Order Type</b></th>
                                    <th onclick="sortTable3(3)"><b>Order Description</b></th>
                                    <th onclick="sortTable3(4)"><b>Required Start Date</b></th>
                                    <th onclick="sortTable3(5)"><b>Required End Date</b></th>
                                    <th onclick="sortTable3(6)"><b>Notification ID</b></th>
                                    <th onclick="sortTable3(7)"><b>Work Center</b></th>
                                    <th onclick="sortTable3(8)"><b>Current Status</b></th>
                                    <th>Order Detail</th>

                                </tr>
                                <c:forEach items="${orders}" var="order">
                                    <tbody id ="wos">
                                        <%--<fmt:formatDate var="formatRegDate" value="${order.requiredStartDate}"pattern="yyyy.MM.dd"/>--%>
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
                                                <!--<button class="submitBtn__ind__orderDetail">-->
                                                <a class="detail submitBtn__ind__orderDetail" href="/workorder?workOrderId=${order.workOrderId}">Detail</a>
                                                <!--</button>-->
                                            </td>
                                        </tr>

                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </form>

            <!--                    display operations table-->

            <form action="admin" method="POST">
                <div class="table">
                    <div style="overflow-x: auto;">
                        <div class="table__size">
                            <div class="table__header">
                                <h2>View All Operations (${operationsLength})</h2>
                                <div class="table__header__options">
                                    <input type="hidden" name="action" value="deleteOperation">
                                    <input type="submit" class="submitBtn" value="Delete">
                                </div>
                            </div>
                                <div class="searchOption">     
                            <b>Search / Filter Table: 
                                <input id="searchOp" type="text" 
                                       placeholder="Search For...">
                            </b>
                                    </div>
                            <table id="myops">
                                <tr>
                                    <th onclick="sortTable2(0)" class="square">□</th>
                                    <th onclick="sortTable2(1)"><b>Operation ID</b></th>
                                    <th onclick="sortTable2(2)"><b>Description</b></th>
                                    <th onclick="sortTable2(3)"><b>Long Text</b></th>
                                    <th onclick="sortTable2(4)"><b>Work Order ID</b></th>
                                    <th onclick="sortTable2(5)"><b>Status</b></th>
                                    <th onclick="sortTable2(6)"><b>Responsible Person</b></th>
                                    <th onclick="sortTable2(7)"><b>Work Center</b></th>
                                </tr>
                                <tbody id ="ops">
                                <c:forEach items="${operations}" var="operation">
                                    <tr>
                                        <td class="td__square">
                                            <input type="checkbox" name="operationId" value=${operation.operationId}>
                                        </td>
                                        <td>${operation.operationId}</td>
                                        <td>${operation.description}</td>
                                        <td>${operation.longText}</td>
                                        <td>${operation.workOrderId.workOrderId}</td>
                                        <td>${operation.status.statusDesc}</td>
                                        <td>${operation.personResponsible.userId}: ${operation.personResponsible.userFname},
                                            ${operation.personResponsible.userLname}</td>
                                        <td>${operation.personResponsible.workCenterId.workCenterName}</td>
                                    </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </form>

            <!--                    display users table-->

            <form action="admin" method="POST">
                <div class="table">

                    <div style="overflow-x: auto;">
                        <div class="table__size">
                            <div class="table__header">
                                <h2>View All Users (${usersLength})</h2>

                                <%-- commented out delete for the user table --%>
<!--                                <div class="table__header__options">
                                    <input type="hidden" name="action" value="deleteUser">
                                    <input type="submit" class="submitBtn" value="Delete">
                                </div>-->
                            </div>
                            <div class="searchOption">     
                                <b>Search / Filter Table: 
                                    <input id="search" type="text" 
                                           placeholder="Search For...">
                                </b>
                            </div>  

                            <table id="myusers">
                                <tr>
                                    <th onclick="sortTable(0)" class="square">□</th>
                                    <th onclick="sortTable(1)"><b>User ID</b></th>
                                    <th onclick="sortTable(2)"><b>First Name</b></th>
                                    <th onclick="sortTable(3)"><b>Last Name</b></th>
                                    <th onclick="sortTable(4)"><b>Role</b></th>
                                    <th onclick="sortTable(5)"><b>Role Desc</b></th>
                                    <th onclick="sortTable(6)"><b>Role Type</b></th>
                                    <th onclick="sortTable(7)"><b>Work Center ID</b></th>
                                    <th onclick="sortTable(8)"><b>Work Center Desc</b></th>
                                    <th><b>Password Reset</b></th>
                                    <th><b>Account Lock</b></th>

                                </tr>
                                <%-- This form tag is here because if it isnt, the first form for password reset does not show up.
                                If you are in netbeans and click the form under this, you will see it's red. I dont understand why this happens.--%>
                                </form>
                                <c:forEach items="${users}" var="user">
                                    <tbody id="users">
                                        <tr>
                                            <td class="td__square"><input type="checkbox" name="userId" value=${user.userId}></td>
                                            <td>${user.userId}</td>
                                            <td>${user.userFname}</td>
                                            <td>${user.userLname}</td>
                                            <td>${user.userRole}</td>
                                            <td>${roles.get(user.userRole).roleDesc}</td>
                                            <td>${roles.get(user.userRole).roleType}</td>
                                            <td>${user.workCenterId.workCenterId}</td>
                                            <td>${user.workCenterId.workCenterName}</td>
                                            <td><form action="admin" method="POST">
                                                    <input type="hidden" name="action" value="forceUser">
                                                    <input type="hidden" name="changedUser" value="${user.userId}">
                                                    <c:if test="${user.userForcedReset}">
                                                        <span style="font-size: 0.9rem; color:red; ">Forced Password Reset Active</span>
                                                    </c:if>
                                                    <c:if test="${not user.userForcedReset}">
                                                        <input type="submit" class="submitBtn__ind" value="Reset Password">
                                                    </c:if>
                                                </form></td>
                                            <td><form action="admin" method="POST">
                                                    <input type="hidden" name="action" value="lockUser">
                                                    <input type="hidden" name="changedUser" value="${user.userId}">
                                                    <c:if test="${user.userLocked}">
                                                        <input type="submit" class="submitBtn__ind" value="Unlock User Account">
                                                    </c:if>
                                                    <c:if test="${not user.userLocked}">
                                                        <input type="submit" class="submitBtn__ind" value="Lock User Account">
                                                    </c:if>
                                                </form></td>

                                        </tr>
                                    </tbody>
                                    </c:forEach>
                                
                            </table>
                        </div>
                    </div>
                </div>
            </form>
        </div>


        <script src="${pageContext.request.contextPath}/JS/admin.js"></script>
        <script type="text/JavaScript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js" ></script>
        <script src="${pageContext.request.contextPath}/JS/webstorage.js"></script>
    </body>

</html>
