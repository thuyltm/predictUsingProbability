<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
  <head>
      <title>Simple Map</title>
      <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
      <meta charset="utf-8">
      <style>
        html, body, #map_canvas {
          margin: 0;
          padding: 0;
          height: 100%;
        }
      </style>
  </head>
  <script src="<c:url value="/resources/core/jquery.1.10.2.min.js" />"></script>
  <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBqHfK_4j6EcoN7J6dL0Z_ZdEBc1SJES5Q"></script>
  <script src="<c:url value="resources/core/geolocation-marker.js" />"></script>
  <body>
   <div id="map_canvas"></div>
   <script>
      var map, GeoMarker;

        function initialize() {
          var mapOptions = {
            zoom: 17,
            center: new google.maps.LatLng(-34.397, 150.644),
            mapTypeId: google.maps.MapTypeId.ROADMAP
          };
          map = new google.maps.Map(document.getElementById('map_canvas'),
              mapOptions);

          GeoMarker = new GeolocationMarker();
         // GeoMarker.setCircleOptions({fillColor: '#808080'});

          google.maps.event.addListenerOnce(GeoMarker, 'position_changed', function() {
            map.setCenter(this.getPosition());
            map.fitBounds(this.getBounds());
          });

          google.maps.event.addListener(GeoMarker, 'geolocation_error', function(e) {
            alert('There was an error obtaining your position. Message: ' + e.message);
          });

          GeoMarker.setMap(map);
        }

        google.maps.event.addDomListener(window, 'load', initialize);

        if(!navigator.geolocation) {
          alert('Your browser does not support geolocation');
        }
      </script>
  </body>
</html>
