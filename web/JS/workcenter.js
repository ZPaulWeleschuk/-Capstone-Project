/*
 * @author Jaehan Kim
*/

const createWorkOrder = document.getElementById("createWorkOrderHeader");
//const createWorkOrderFormActive = document.querySelector(".workOrder_inactive");
const createWorkOrderFormActive = document.getElementById("workOrder_inactive");

const createWorkOrderHeaderImage = document.getElementById("createWorkOrderHeaderImage");

const createAndSeeBtn = document.getElementById("createAndSeeBtn");
let workOrderActive = false;

const bracket_up = "../Images/angle_bracket_up.png";
const bracket_down = "../Images/angle_bracket_down.png";
let bracketChangedIsDown=false;

createWorkOrder.addEventListener("click", () => {
//  console.log("click");
  createWorkOrderFormActive.classList.toggle("workOrder_inactive");
   if(bracketChangedIsDown == true){
        createWorkOrderHeaderImage.src = bracket_up;

      bracketChangedIsDown = false;
      console.log("bracket is down")
  } else {
            createWorkOrderHeaderImage.src = bracket_down;
        bracketChangedIsDown = true;
        console.log("bracket is up")
  }
});

//createAndSeeBtn.addEventListener("click", (event) => {
//    workOrderActive = true;
////    event.preventDefault();
//    createWorkOrderFormActive.classList.remove("workOrder_inactive");
//    
////  workOrderInactive();
//  console.log("click");
//  
//});


function init(){
        console.log(workOrderActive)
    if (workOrderActive){
        console.log("active")
      createWorkOrderFormActive.classList.remove("workOrder_inactive");
    }
    
}

init();
