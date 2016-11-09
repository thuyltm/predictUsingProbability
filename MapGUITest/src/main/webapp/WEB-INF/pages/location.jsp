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
      #map {
        height: 100%;
      }
      #coords {
        background-color: black;
        color: white;
        padding: 5px;
      }
    </style>
  </head>
  <body>
   <div id="map"></div>
    <script>
      function initMap() {
        var myLatlng = {lat: -25.363, lng: 131.044};

        var map = new google.maps.Map(document.getElementById('map'), {
          zoom: 4,
          center: myLatlng
        });

        var marker = new google.maps.Marker({
          position: myLatlng,
          map: map,
          title: 'Click to zoom'
        });
        google.maps.event.addDomListener(map, 'click', function(event) {
            var myLatLng = event.latLng;
            var lat = myLatLng.lat();
            var lng = myLatLng.lng();
            var uluru = {lat: lat, lng: lng};
            var marker = new google.maps.Marker({
              position: uluru,
              map: map
            });
            var infowindow = new google.maps.InfoWindow({
                content: 'LatLng: ' + myLatLng,
            });

            marker.addListener('click', function() {
                infowindow.open(map, marker);
            });
        })

      }
      function createInfoWindowContent(latLng, zoom) {
          var scale = 1 << zoom;

          var worldCoordinate = project(latLng);

          var pixelCoordinate = new google.maps.Point(
              Math.floor(worldCoordinate.x * scale),
              Math.floor(worldCoordinate.y * scale));

          var tileCoordinate = new google.maps.Point(
              Math.floor(worldCoordinate.x * scale / TILE_SIZE),
              Math.floor(worldCoordinate.y * scale / TILE_SIZE));

          return [
            'Chicago, IL',
            'LatLng: ' + latLng,
            'Zoom level: ' + zoom,
            'World Coordinate: ' + worldCoordinate,
            'Pixel Coordinate: ' + pixelCoordinate,
            'Tile Coordinate: ' + tileCoordinate
          ].join('<br>');
    }
    </script>
    <script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBqHfK_4j6EcoN7J6dL0Z_ZdEBc1SJES5Q&callback=initMap"></script>
  </body>
</html>