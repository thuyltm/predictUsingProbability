<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Custom map projections</title>
    <style>
     html, body {
        height: 100%;
        margin: 0;
        padding: 0;
     }

      #right-panel {
        font-family: 'Roboto','sans-serif';
        line-height: 30px;
        padding-left: 10px;
      }

      #right-panel select, #right-panel input {
        font-size: 15px;
      }

      #right-panel select {
        width: 100%;
      }

      #right-panel i {
        font-size: 12px;
      }
      #right-panel {
        height: 100%;
        float: right;
        width: 390px;
        overflow: auto;
      }
     #map {
        height: 100%;
        margin-right: 400px;
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
      #drop-container {
       display: none;
       height: 100%;
       width: 100%;
       position: absolute;
       z-index: 1;
       top: 0px;
       left: 0px;
       padding: 20px;
       background-color: rgba(100, 100, 100, 0.5);
     }
     #drop-silhouette {
       color: white;
       border: white dashed 8px;
       height: calc(100% - 56px);
       width: calc(100% - 56px);
       background-image: url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAGQAAABkCAYAAABw4pVUAAAAAXNSR0IArs4c6QAAAAZiS0dEAGQAZABkkPCsTwAAAAlwSFlzAAALEwAACxMBAJqcGAAAAAd0SU1FB90LHAIvICWdsKwAAAAZdEVYdENvbW1lbnQAQ3JlYXRlZCB3aXRoIEdJTVBXgQ4XAAACdklEQVR42u3csU7icBzA8Xp3GBMSeRITH8JHMY7cRMvmVmXoE9TAcJubhjD4ApoiopgqDMWAKAgIcSAiCfxuwhwROVJbkPD9rP23ob8vpZCQKgoAAAAAAAAAAPDYyiK/eNM05bNtr6+vSjgcXiHxDMkE1WpVFvGcfpCVICAIQUAQgoAgBAFBCAKCgCAEAUEIAoIQBAQhCAgCghAEBCEICEIQEIQgIAgIQhAQhCAgCEFAEIKAICAIQUAQgoAgBAFBCDIzhmFINBo9/K6D0XVddnd3ZaneDY7jSCqVcn3SfjyeKRKJbJ2dnYllWbKUl2i5XJaXlxdJJBIy7yDHx8fy9vYm6XR6OWMM3d/fi4hIqVSSWCwmsw5ycHAgrVZLRETOz8+XO8ZQpVJ5H2Y6nRZN0/b9DqLruhSLxfd9MpkMMT6L0uv1JJlMih9BhveJwWDwvv7i4oIY4zw8PIwMtt1uSzweF6+CHB0dSbfbHVmbzWaJMcnj4+OHAd/d3cne3p64DWKapjw/P39Yd3l5SYxpVKvVsYO2LEtUVd2ZNoiu6+I4ztg1V1dXxPAiSq/Xk5OTk0k9pNVqyenp6ch94l+5XI4YbtRqNfHa9fX1t43xcwGa/Nnc3PwdDAY9OZht28rGxgZPvP6KSCSy9fT09OUrw7ZtPqa8jFKv113HuLm5IYbXVFXdcRPl9vaWGH5GaTQaU8fI5/PE8JumafvNZvO/MQqFAjFmJRqNHk6Ksqgx5vr1zzAM2d7edr3/6uqqsra2NnZbp9NR+v2+62OHQqG5zObXPIMEAgFlfX3dl2N79btl1viTA0FAEIKAIAQBAAAAAAAAsMz+Ai1bUgo6ebm8AAAAAElFTkSuQmCC');
       background-repeat: no-repeat;
       background-position: center;
     }
     .context-menu {
        position: absolute;
        background: white;
        padding: 3px;
        color: #666;
        font-weight: bold;
        border-style:none;
        border-width:0px;
        font-family: sans-serif;
        font-size: 12px;
        box-shadow: 1px 3px 3px rgba(0, 0, 0, .3);
        margin-top: -10px;
        margin-left: 10px;
        cursor: pointer;
        width: 100px;
      }

    .options-marker {
        padding:3px 0px 3px 2px;
        cursor:pointer;
    }

    .options-marker:hover {
        background:#ccc;
    }
    </style>
  </head>
  <body>
  <script src="<c:url value="/resources/core/jquery.1.10.2.min.js" />"></script>
  <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBqHfK_4j6EcoN7J6dL0Z_ZdEBc1SJES5Q&libraries=drawing"></script>
  <script src="<c:url value="resources/core/maplabel-compiled.js" />"></script>
  <script src="<c:url value="resources/scripts/dragAndDropGeoJson.js" />"></script>
  <script src="<c:url value="resources/scripts/direction.js" />"></script>
  <script src="<c:url value="resources/scripts/util.js" />"></script>
  <script src="<c:url value="resources/scripts/contextmenu.js" />"></script>
  <div id="floating-panel">
      <div id="color-palette"></div>
      <div style="clear:both;"></div>
      <div>
        <button id="delete-button">Delete Selected Shape</button>
        <button id="delete-all-button">Delete All Shapes</button>
      </div>
      <div>
        <button id="start-draw-button">Start Draw</button>
        <button id="cancel-draw-button">Cancel Draw</button>
      </div>
   </div>
   <div id="right-panel"></div>
   <div id="map"></div>
   <div id="drop-container"><div id="drop-silhouette"></div></div>
   <div id="warnings-panel"></div>
   <script>
   var all_overlays = [];
   var all_mapLabel = [];
   var colors = ['#1E90FF', '#FF1493', '#32CD32', '#FF8C00', '#4B0082'];
   var selectedColor;
   var colorButtons = {};
   var selectedShape = null;
   var map;
   var inputValue = 'startLatLng';
   var selectedPath = [];
   var timeTravel = [];
   var startDraw = false;
   var strokeColor = '#1E90FF';
   var geocoder = new google.maps.Geocoder;
   var drawingManager = new google.maps.drawing.DrawingManager({
       drawingMode: null,
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
   var directionsDisplay = new google.maps.DirectionsRenderer({map: map, suppressMarkers : true});
   var directionsService = new google.maps.DirectionsService;
   var markerArray = [];
   var contextMenu = new ContextMenu();
   var stepDisplay = new google.maps.InfoWindow;

   function initialize() {
      map = new google.maps.Map(document.getElementById('map'), {
        zoom: 15,
        center: {lat: 10.772144622506568, lng: 106.65773570537567}
     });
     initEvents();
     drawingManager.setMap(map);

     //map.data.loadGeoJson('<c:url value="resources/data/output.json" />');

     map.data.setStyle(function(feature) {
         return {icon:feature.getProperty('icon')};
     });
     map.data.addListener('rightclick', function(event) {
         if (event.feature.getGeometry().getType() === 'Point') {
           contextMenu.open(map, event.latLng, event);
         }
     });

     map.data.addListener('click', function(event) {
          if (event.feature.getGeometry().getType() === 'Point') {
            var myLatLng = event.latLng;
            var lat = myLatLng.lat();
            var lng = myLatLng.lng();
            var uluru = {lat: lat, lng: lng};
            var content = 'LatLng: ' + myLatLng + '<br/>' + event.feature.getProperty('date');
            showWindowInfo(content, myLatLng);
            if (startDraw===false) {
              return;
            }
            if (inputValue==='endLatLng') {
              inputValue = 'startLatLng';
              startDraw=false;
              selectedPath.push(uluru);
              timeTravel.push(event.feature.getProperty('date'));
              var dateDiff = dateMeasure(new Date(timeTravel[1]), new Date(timeTravel[0]));;
              var newShape = new google.maps.Polyline({
                path: selectedPath,
                geodesic: true,
                strokeColor: strokeColor,
                strokeOpacity: 1.0,
                strokeWeight: 2,
                editable: true
              });

              newShape.setMap(map);
              all_overlays.push(newShape);
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
                     return calculateDistanceAndDuration(startLatLng, endLatLng, event, map, dateDiff);
                  }
              });
            } else {
              inputValue = 'endLatLng';
              selectedPath = [];
              selectedPath.push(uluru);
              timeTravel = [];
              timeTravel.push(event.feature.getProperty('date'));
            }
          }
      });
      google.maps.event.addListener(map, 'click', clearSelection);
      google.maps.event.addDomListener(document.getElementById('delete-button'), 'click', deleteSelectedShape);
      google.maps.event.addDomListener(document.getElementById('delete-all-button'), 'click', deleteAllShape);
      google.maps.event.addDomListener(document.getElementById('start-draw-button'), 'click', function(){startDraw=true;initDraw();});
      google.maps.event.addDomListener(document.getElementById('cancel-draw-button'), 'click', function(){startDraw=false;initDraw();});
      google.maps.event.addListener(map, 'rightclick', function(event) {
        showWindowInfo("Map click at LatLng"+ event.latLng, event.latLng);
      })
      directionsDisplay.setMap(map);
      directionsDisplay.setPanel(document.getElementById('right-panel'));

      // Instantiate an info window to hold step text.
      var control = document.getElementById('floating-panel');
      control.style.display = 'block';
      map.controls[google.maps.ControlPosition.TOP_CENTER].push(control);
  };
  google.maps.event.addDomListener(window, 'load', initialize);
  buildColorPalette();
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
              && getDistanceFromLatLonInKm(event.latLng.lat(),event.latLng.lng(),startLatLng.lat(),startLatLng.lng())>0
              && getDistanceFromLatLonInKm(event.latLng.lat(),event.latLng.lng(),endLatLng.lat(),endLatLng.lng())>0) {
                calculateDistance(startLatLng, endLatLng, event, map);
                return;
             }
           }
         });
        setSelection(newShape);
        all_overlays.push(newShape);
      }
    });

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
    selectColor(shape.get('strokeColor'));
  }

  function deleteSelectedShape() {
    if (selectedShape) {
      selectedShape.setMap(null);
    }
  }

  function deleteAllShape() {
    for (var i = 0; i < all_overlays.length; i++) {
      all_overlays[i].setMap(null);
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
    strokeColor = color;
  }
  function initDraw() {
    inputValue = 'startLatLng';
    selectedPath = [];
    timeTravel = [];
  }
  function displayRoute(start, end, waypts) {
    calculateAndDisplayRoute(start, end, waypts,
              directionsDisplay, directionsService, markerArray, stepDisplay, map);
  }
  </script>
  </body>
</html>