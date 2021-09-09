/*
 * @author Jaehan Kim
*/

const createNotification = document.getElementById("createNotificationHeader");
const createNotificationHeaderImage = document.getElementById("createNotificationHeaderImage");
const createUserHeaderImage = document.getElementById("createUserHeaderImage");
const createUser = document.getElementById("createUserHeader");
const createNotiFormActive = document.querySelector(".notification_inactive");
const createUserFormActive = document.querySelector(".user_inactive");
//console.log(createNotiFormActive);
const bracket_up = "../Images/angle_bracket_up.png";
const bracket_down = "../Images/angle_bracket_down.png";
let bracketChangedIsDown=true;


// Animation create form for each click event
createNotification.addEventListener("click", () => {
  createNotiFormActive.classList.toggle("notification_inactive");
//  if(createNotificationHeaderImage.src == bracket_up){
//      createNotificationHeaderImage.src = bracket_down;
//      console.log("bracket up")
//  } else {
//      createNotificationHeaderImage.src = bracket_up;
//      console.log("bracket down")
//  }
    if(bracketChangedIsDown == true){
        createNotificationHeaderImage.src = bracket_up;

      bracketChangedIsDown = false;
      console.log("bracket is down")
  } else {
            createNotificationHeaderImage.src = bracket_down;
        bracketChangedIsDown = true;
        console.log("bracket is up")
  }
  
});

createUser.addEventListener("click", ()=>{
    console.log('clicked')
    createUserFormActive.classList.toggle("user_inactive");
    
     if(bracketChangedIsDown == true){
        createUserHeaderImage.src = bracket_up;

      bracketChangedIsDown = false;
      console.log("bracket is down")
  } else {
            createUserHeaderImage.src = bracket_down;
        bracketChangedIsDown = true;
        console.log("bracket is up")
  }
})