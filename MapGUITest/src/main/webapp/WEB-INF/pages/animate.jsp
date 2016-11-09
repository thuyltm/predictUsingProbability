<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Distance Matrix service</title>
    <style>
      #map_canvas {
        height: 100%;
        width: 100%;
        margin: 0px;
        padding: 0px
      }
      html, body {
        height: 100%;
        margin: 0;
        padding: 0;
      }
    </style>
  </head>
  <body>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBqHfK_4j6EcoN7J6dL0Z_ZdEBc1SJES5Q&libraries=geometry"></script>
    <script src="<c:url value="resources/core/v3_epoly.js" />"></script>
    <input type="button" value="restart animation" onclick="startAnimation()" />
    <div id="map_canvas"></div>
    <script>
      var map;
      var position;
      var marker = null;
      var polyline = null;
      var poly2 = null;
      var speed = 0.000005,
        wait = 1;
      var infowindow = null;

      var myPano;
      var panoClient;
      var nextPanoId;
      var timerHandle = null;

      var polyline;

      function initialize() {
        map = new google.maps.Map(
              document.getElementById("map_canvas"), {
                center: new google.maps.LatLng(10.94943,106.52087),
                zoom: 13,
                mapTypeId: google.maps.MapTypeId.ROADMAP
              });
            var bounds = new google.maps.LatLngBounds();
            map.data.addListener('addfeature', function(e) {
              processPoints(e.feature.getGeometry(), bounds.extend, bounds);
              if (e.feature.getGeometry().getType() == "LineString") {

                polyline = new google.maps.Polyline({
                  map: map,
                  path: e.feature.getGeometry().getArray(),
                  strokeWeight: 5,
                  strokeOpacity: 0.4,
                  strokeColor: 'red'
                });
                marker = createMarker(polyline.getPath().getAt(0), "here", "marker text");
                setTimeout(startAnimation, 2000);

              }
              map.fitBounds(bounds);
            });
            // var line = google.maps.Data;
           // map.data.addGeoJson(geoJson);
           map.data.loadGeoJson('<c:url value="/resources/data/outputLine.json" />');
            // styling features aliran
            map.data.setStyle(function(feature) {
              return {
                strokeColor: '#0000FF',
                strokeWeight: 1,
                zIndex: 1
              }
            });
            // set map;
            map.data.setMap(map);
      }

      var step = 50; // metres
      var tick = 100; // milliseconds
      var eol;
      var k = 0;
      var stepnum = 0;
      var speed = "";
      var lastVertex = 1;
    //=============== animation functions ======================
      function updatePoly(d) {
        // Spawn a new polyline every 20 vertices, because updating a 100-vertex poly is too slow
        if (poly2.getPath().getLength() > 20) {
          poly2 = new google.maps.Polyline([polyline.getPath().getAt(lastVertex - 1)]);
        }

        if (polyline.GetIndexAtDistance(d) < lastVertex + 2) {
          if (poly2.getPath().getLength() > 1) {
            poly2.getPath().removeAt(poly2.getPath().getLength() - 1)
          }
          poly2.getPath().insertAt(poly2.getPath().getLength(), polyline.GetPointAtDistance(d));
        } else {
          poly2.getPath().insertAt(poly2.getPath().getLength(), polyline.getPath().getAt(polyline.getPath().getLength() - 1));
        }
      }


      function animate(d) {
        if (d > eol) {
          map.panTo(endLocation.latlng);
          marker.setPosition(endLocation.latlng);
          return;
        }
        var p = polyline.GetPointAtDistance(d);
        map.panTo(p);
        marker.setPosition(p);
        updatePoly(d);
        timerHandle = setTimeout("animate(" + (d + step) + ")", tick);
      }


      function startAnimation() {
        if (timerHandle) clearTimeout(timerHandle);
        eol = google.maps.geometry.spherical.computeLength(polyline.getPath());
        map.setCenter(polyline.getPath().getAt(0));
        poly2 = new google.maps.Polyline({
          path: [polyline.getPath().getAt(0)],
          strokeColor: "#0000FF",
          strokeWeight: 10
        });
        setTimeout("animate(0)", tick); // Allow time for the initial map display
      }




      function processPoints(geometry, callback, thisArg) {
        if (geometry instanceof google.maps.LatLng) {
          callback.call(thisArg, geometry);
        } else if (geometry instanceof google.maps.Data.Point) {
          callback.call(thisArg, geometry.get());
        } else {
          geometry.getArray().forEach(function(g) {
            processPoints(g, callback, thisArg);
          });
        }
      }

      // ==================================================
      function createMarker(latlng, label, html) {
        var contentString = '<b>' + label + '</b><br>' + html;
        var marker = new google.maps.Marker({
          position: latlng,
          map: map,
          title: label,
          icon: {
            path: google.maps.SymbolPath.CIRCLE,
            scale: 8,
            strokeColor: '#393'
          },
          zIndex: Math.round(latlng.lat() * -100000) << 5
        });

        marker.myname = label;
        // gmarkers.push(marker);

        google.maps.event.addListener(marker, 'click', function() {
          infowindow.setContent(contentString);
          infowindow.open(map, marker);
        });
        return marker;
      }

      google.maps.event.addDomListener(window, "load", initialize);

      var geoJson = {
        "type": "FeatureCollection",
        "crs": {
          "type": "name",
          "properties": {
            "name": "urn:ogc:def:crs:OGC:1.3:CRS84"
          }
        },

        "features": [{
          "type": "Feature",
          "properties": {
            "Name": "Saluran II",
            "descriptio": null,
            "timestamp": null,
            "begin": null,
            "end": null,
            "altitudeMo": null,
            "tessellate": 1,
            "extrude": -1,
            "visibility": -1,
            "drawOrder": null,
            "icon": null
          },
          "geometry": {
            "type": "LineString",
            "coordinates": [
              [115.1647420393289, -8.691263798416188],
              [115.16480885306601, -8.691910749059817],
              [115.1648021389716, -8.692020041570267],
              [115.16476460026691, -8.692197857370241],
              [115.16467300953239, -8.692311184386924],
              [115.1645832229062, -8.692570845169653],
              [115.164590403574, -8.69280000074686],
              [115.1642184661912, -8.692878192437396],
              [115.16418831658579, -8.693471791565786],
              [115.16411080877791, -8.69417746825723],
              [115.1639500244154, -8.694469415766308],
              [115.1638498474275, -8.69450385891758],
              [115.1637726241196, -8.6949183073729],
              [115.1637482310352, -8.695106949243888],
              [115.16369576938609, -8.695516868583109],
              [115.1633930487843, -8.695552277605064],
              [115.1628619559151, -8.695616245099258],
              [115.1628453449146, -8.695861398016726],
              [115.1625531407406, -8.695981675836846],
              [115.1619167160671, -8.696110249672243],
              [115.1621001879372, -8.697348692504496],
              [115.1619454016469, -8.697429501488445]
            ]
          }
        }]
      };
    </script>
  </body>
</html>