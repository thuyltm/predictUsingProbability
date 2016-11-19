<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Draw Matrix service</title>
    <style>
      html, body {
        height: 100%;
        margin: 0;
        padding: 0;
      }
      #map {
        height: 100%;
      }
      #floating-panel {
        position: absolute;
        top: 0px;
        left: 20%;
        z-index: 5;
        background-color: #fff;
        padding: 5px;
        border: 1px solid #999;
        text-align: center;
        font-family: 'Roboto','sans-serif';
        line-height: 30px;
        padding-left: 10px;
      }
      .color-button {
         width: 14px;
         height: 14px;
         font-size: 0;
         margin: 2px;
         float: left;
         cursor: pointer;
      }
    </style>
  </head>
  <body>
   <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBqHfK_4j6EcoN7J6dL0Z_ZdEBc1SJES5Q&libraries=drawing"></script>
   <script src="<c:url value="resources/core/maplabel-compiled.js" />"></script>
    <div id="floating-panel">
      <div id="color-palette"></div>
      <div style="clear:both;"></div>
      <div>
        <button id="delete-button">Delete Selected Shape</button>
        <button id="delete-all-button">Delete All Shapes</button>
      </div>
    </div>
   <div id="map"></div>
   <script>
   var all_overlays = [];
   var all_mapLabel = [];
   var colors = ['#1E90FF', '#FF1493', '#32CD32', '#FF8C00', '#4B0082'];
   var selectedColor;
   var colorButtons = {};
   var drawingManager ;
   var selectedShape = null;
   function initMap() {
      var map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: -34.397, lng: 150.644},
        zoom: 13,
        mapTypeId: google.maps.MapTypeId.ROADMAP
      });

      drawingManager = new google.maps.drawing.DrawingManager({
        drawingMode: google.maps.drawing.OverlayType.POLYLINE,
        drawingControl: true,
        drawingControlOptions: {
          position: google.maps.ControlPosition.TOP_CENTER,
          drawingModes: [
            google.maps.drawing.OverlayType.POLYLINE
          ]
        },
        polylineOptions: {
            editable: true
         }
      });
      drawingManager.setMap(map);
      google.maps.event.addListener(drawingManager, 'overlaycomplete', function(e) {
        if (e.type == google.maps.drawing.OverlayType.POLYLINE) {
           // Switch back to non-drawing mode after drawing a shape.
           drawingManager.setDrawingMode(null);
           // Add an event listener that selects the newly-drawn shape when the user
           // mouses down on it.
           var newShape = e.overlay;
           newShape.type = e.type;
           google.maps.event.addListener(newShape, 'click', function(event) {
             setSelection(newShape);
             var contentString ="content for infowindow";
             var coordinateList = [];
             var pointCount = newShape.getPath().length;
             for(var i = 0 ; i < pointCount; i++)
             {
               var path = newShape.getPath().b[i];
               coordinateList.push(new google.maps.LatLng(path.lat(),path.lng()));
             }
             for (var i = 0 ; i <= coordinateList.length - 2; i++) {
               var startLatLng = coordinateList[i];
               var endLatLng = coordinateList[i+1];
               var lineBound = new google.maps.Polygon({
                  paths: [startLatLng,endLatLng]
                });
               if(google.maps.geometry.poly.containsLocation(event.latLng, lineBound)
                && getDistanceFromLatLonInKm(event.latLng,startLatLng)>0
                && getDistanceFromLatLonInKm(event.latLng,endLatLng)>0) {
                  calculateDistance(startLatLng, endLatLng, event, map);
                  return;
               }
             }
           });
          setSelection(newShape);
          all_overlays.push(e);
        }
      });
      google.maps.event.addListener(drawingManager, 'drawingmode_changed', clearSelection);
      google.maps.event.addListener(map, 'click', clearSelection);
      google.maps.event.addDomListener(document.getElementById('delete-button'), 'click', deleteSelectedShape);
      google.maps.event.addDomListener(document.getElementById('delete-all-button'), 'click', deleteAllShape);
      buildColorPalette();
   }
   google.maps.event.addDomListener(window, 'load', initMap);
   function calculateDistance(origin, destination, event, map) {
     var service = new google.maps.DistanceMatrixService;
       service.getDistanceMatrix({
         origins: [origin],
         destinations: [destination],
         travelMode: google.maps.DirectionsTravelMode.DRIVING
       }, function(response, status) {
         if (status !== google.maps.DistanceMatrixStatus.OK) {
           alert('Error was: ' + status);
         } else {
           var originList = response.originAddresses;
           var destinationList = response.destinationAddresses;
           for (var i = 0; i < originList.length; i++) {
             var results = response.rows[i].elements;
             for (var j = 0; j < results.length; j++) {
               var contentString = (originList[i] + ' <br/> to <br/> ' + destinationList[j] +
                         ': ' + results[j].distance.text + ' <br/> in ' +
                         results[j].duration.text)
               var mapLabel = new MapLabel({
                   text: results[j].distance.text,
                   position: event.latLng,
                   map: map,
                   fontSize: 20,
                   align: 'right'
                 });
               var marker = new google.maps.Marker();
               marker.setIcon("<c:url value="resources/images/bus.png" />");
               marker.bindTo('map', mapLabel);
               marker.bindTo('position', mapLabel);
               marker.setDraggable(true);
               google.maps.event.addListener(marker, "rightclick", function () {
                   marker.setMap(null);
               });
               google.maps.event.addListener(marker, "click", function (event) {
                 var infowindow = new google.maps.InfoWindow();
                   infowindow.setContent(contentString);
                   infowindow.setPosition(event.latLng);
                   infowindow.open(map);
               });
               all_mapLabel.push(mapLabel);
             }
           }
         }
       });
   }

   function clearSelection() {
     if (selectedShape) {
       selectedShape.setEditable(false);
       selectedShape = null;
     }
   }

   function setSelection(shape) {
     clearSelection();
     selectedShape = shape;
     shape.setEditable(true);
     selectColor(shape.get('fillColor') || shape.get('strokeColor'));
   }

   function deleteSelectedShape() {
     if (selectedShape) {
       selectedShape.setMap(null);
     }
   }

   function deleteAllShape() {
     for (var i = 0; i < all_overlays.length; i++) {
       all_overlays[i].overlay.setMap(null);
     }
     all_overlays = [];
     deleteAllMapLabel();
   }

   function deleteAllMapLabel() {
       for (var i = 0; i < all_mapLabel.length; i++) {
         all_mapLabel[i].setMap(null);
       }
       all_mapLabel = [];
     }

   function selectColor(color) {
     selectedColor = color;
     for (var i = 0; i < colors.length; ++i) {
       var currColor = colors[i];
       colorButtons[currColor].style.border = currColor == color ? '2px solid #789' : '2px solid #fff';
     }

     // Retrieves the current options from the drawing manager and replaces the
     // stroke or fill color as appropriate.
     var polylineOptions = drawingManager.get('polylineOptions');
     polylineOptions.strokeColor = color;
     drawingManager.set('polylineOptions', polylineOptions);
   }

   function makeColorButton(color) {
     var button = document.createElement('span');
     button.className = 'color-button';
     button.style.backgroundColor = color;
     google.maps.event.addDomListener(button, 'click', function() {
       selectColor(color);
     });
     return button;
   }

   function buildColorPalette() {
     var colorPalette = document.getElementById('color-palette');
     for (var i = 0; i < colors.length; ++i) {
       var currColor = colors[i];
       var colorButton = makeColorButton(currColor);
       colorPalette.appendChild(colorButton);
       colorButtons[currColor] = colorButton;
     }
     selectColor(colors[0]);
   }

   function getDistanceFromLatLonInKm(point1,point2) {
     var lat1 = point1.lat();
     var lon1 = point1.lng();
     var lat2 = point2.lat();
     var lon2 = point2.lng();
     var R = 6371; // Radius of the earth in km
     var dLat = deg2rad(lat2-lat1);  // deg2rad below
     var dLon = deg2rad(lon2-lon1);
     var a =
       Math.sin(dLat/2) * Math.sin(dLat/2) +
       Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
       Math.sin(dLon/2) * Math.sin(dLon/2)
       ;
     var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
     var d = R * c; // Distance in km
     return d;
   }

   function deg2rad(deg) {
     return deg * (Math.PI/180)
   }
    </script>
  </body>
</html>