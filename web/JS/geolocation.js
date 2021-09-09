/*
 * @author Laura Diaz
 */
var plantName = document.getElementById("plantName").innerHTML;

var mapCenter;
switch (plantName) {
    case "Edmonton Gas Plant":
        mapCenter = ol.proj.fromLonLat([-113.354,53.502]);
        break;
    case "Fort McMurray Gas Plant":
        mapCenter = ol.proj.fromLonLat([-111.468, 56.732]);
        break;
    case "Calgary Gas Plant":
        mapCenter = ol.proj.fromLonLat([-113.916, 51.082]);
        break;
    case "Grande Prairie Gas Plant":
        mapCenter = ol.proj.fromLonLat([-118.706, 55.170]);
        break;
    case "Red Deer Gas Plant":
        mapCenter = ol.proj.fromLonLat([-113.747, 52.289]);
        break;
    case "Lethbridge Gas Plant":
        mapCenter = ol.proj.fromLonLat([-112.777, 49.724]);
        break;
}

var map = new ol.Map({
    target: 'map',
    layers: [
      new ol.layer.Tile({
        source: new ol.source.OSM()
      })
    ],
    view: new ol.View({
      center: mapCenter,
      zoom: 15
    })
  });
  
 var layer = new ol.layer.Vector({
     source: new ol.source.Vector({
         features: [
             new ol.Feature({
                 geometry: new ol.geom.Point(mapCenter)
             })
         ]
     })
 });
 map.addLayer(layer);  
  
const showDirection = document.getElementById("showDirectionHeader");
const showDirectionHeaderImage = document.getElementById("showDirectionHeaderImage");
const locationActive = document.querySelector(".location_inactive");
const bracket_up = "../Images/angle_bracket_up.png";
const bracket_down = "../Images/angle_bracket_down.png";
let bracketChangedIsDown=true;

showDirection.addEventListener("click", ()=> {
   locationActive.classList.toggle("location_inactive");
   
   if(bracketChangedIsDown == true){
        showDirectionHeaderImage.src = bracket_up;
        bracketChangedIsDown = false;
        console.log("bracket is down")
  } else {
        showDirectionHeaderImage.src = bracket_down;
        bracketChangedIsDown = true;
        console.log("bracket is up")
  }
   
});