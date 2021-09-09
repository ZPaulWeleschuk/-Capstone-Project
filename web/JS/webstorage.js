
window.localStorage;
var clicks = 0;

function initiateClicks() {
    var clickStr = localStorage.getItem("clicks");
    if(clickStr == undefined){
        localStorage.setItem("clicks", 0);
        clicks = 0;
    }else{
        clicks = parseInt(clickStr);   
    }
    
}

function doClick() {
    clicks += 1;
    localStorage.setItem("clicks", clicks);
}

initiateClicks();

$("#createNotificationBtn").click(function(){
    doClick();
    if (localStorage) {
        var newNotification = {};
        newNotification.type = $("#createNotifType").val();
        newNotification.cause = $("#createNotifCause").val();
        newNotification.damage = $("#createNotifDamage").val();
        newNotification.plant = $("#createNotifPlant").val();
        newNotification.techObj = $("#createNotifTechObj").val();
        newNotification.workCenter = $("#createNotifWorkCenter").val();
        newNotification.createdBy = $("#createNotifAssignedBy").val();
        newNotification.status = 1;
        localStorage.setItem("NotificationID"+clicks, JSON.stringify(newNotification));
    } else {
        alert("Your browser does not support local storage.");
    }
    
});

$("#createUserBtn").click(function(){
    doClick();
   if (localStorage) {
       var newUser = {};
       newUser.firstName = $("#firstName").val();
       newUser.lastName = $("#lastName").val();
       newUser.password = $("#password").val();
       newUser.role = $("#userRole").val();
       newUser.workCenter = $("#workCenter").val();
       localStorage.setItem("UserID"+clicks, JSON.stringify(newUser));
   } else {
        alert("Your browser does not support local storage.");
    }
});

$("#createWorkOrderBtn").click(function(){
    doClick();
    if (localStorage) {
        var newWorkOrder = {};
        newWorkOrder.type = $("#newOrderType").val();
        newWorkOrder.description = $("#newOrderDesc").val();
        newWorkOrder.startDate = $("#newOrderStart").val();
        newWorkOrder.endDate = $("#newOrderEnd").val();
        newWorkOrder.notificationId = $("#newOrderNotifId").val();
        newWorkOrder.status = 1;
        localStorage.setItem("WorkOrderID"+clicks, JSON.stringify(newWorkOrder));
    } else {
        alert("Your browser does not support local storage.");
    }
});

$("#createOperationBtn").click(function(){
    doClick();
    if (localStorage) {
        var newOperation = {};
        newOperation.id = $("#newOperationId").val();
        newOperation.personResponsible = $("#newOperationAssignTo").val();
        newOperation.description = $("#newOperationDesc").val();
        newOperation.status = 1;
        newOperation.longText = $("#newOperationLongText").val();
        localStorage.setItem("OperationID"+clicks, JSON.stringify(newOperation));
    } else {
        alert("Your browser does not support local storage.");
    }
});



 
 

