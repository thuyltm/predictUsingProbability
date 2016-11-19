<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <title>Map Label Utility Library Example</title>
    <style>
      body {
        font-family: sans-serif;
      }
    </style>
  </head>
  <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBqHfK_4j6EcoN7J6dL0Z_ZdEBc1SJES5Q"></script>
  <script src="<c:url value="resources/core/maplabel-compiled.js" />"></script>
  <body>
    <h1>Map Label Utility Library</h1>
    <div id="map" style="width: 500px; height: 500px; float: left"></div>
    <div id="map2" style="width: 500px; height: 500px; float: left; margin-left: 20px;"></div>
    <div style="clear: both; padding-top: 10px;">
      <label>Map:</label>
      <button id="move">Toggle Maps</button>
    </div>
    <div>
      <label>Font Size:</label>
      <input type="text" id="font" value="15">px
      <button id="change-font">Change Size</button>
    </div>
    <div>
      <label>Font Color:</label>
      #<input type="text" id="font-color" value="ff0000">
      <button id="change-color">Change Color</button>
    </div>
    <div>
      <label>Stroke Weight:</label>
      <input type="text" id="stroke-weight" value="3">px
      <button id="change-strokeweight">Change Weight</button>
    </div>
    <div>
      <label>Stroke Color:</label>
      #<input type="text" id="stroke-color" value="8ada55">
      <button id="change-strokecolor">Change Color</button>
    <div>
      <label>Text:</label>
      <input type="text" id="text" value="foo">
      <button id="change-text">Change Text</button>
    </div>
    <div>
      <label>Alignment:</label>
      <select id="align">
        <option value="left">Left</option>
        <option value="center">Center</option>
        <option value="right">Right</option>
      </select>
      <button id="change-align">Align</button>
    </div>
  </body>
    <script>
      function init() {
        var myLatlng = new google.maps.LatLng(34.04, -118.24);
        var myOptions = {
          zoom: 13,
          center: myLatlng,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        };

        var map = new google.maps.Map(document.getElementById('map'), myOptions);

        var mapLabel = new MapLabel({
          text: 'Test',
          position: new google.maps.LatLng(34.03, -118.235),
          map: map,
          fontSize: 35,
          align: 'right'
        });
        mapLabel.set('position', new google.maps.LatLng(34.03, -118.235));

        var marker = new google.maps.Marker();
        marker.bindTo('map', mapLabel);
        marker.bindTo('position', mapLabel);
        marker.setDraggable(true);

        var map2 = new google.maps.Map(document.getElementById('map2'), myOptions);

        var changeAlign = document.getElementById('change-align');
        google.maps.event.addDomListener(changeAlign, 'click', function() {
          mapLabel.set('align', document.getElementById('align').value);
        });

        var changeFont = document.getElementById('change-font');
        google.maps.event.addDomListener(changeFont, 'click', function() {
          mapLabel.set('fontSize', document.getElementById('font').value);
        });

        var changeColor = document.getElementById('change-color');
        google.maps.event.addDomListener(changeColor, 'click', function() {
          mapLabel.set('fontColor', document.getElementById('font-color').value);
        });

        var changeText = document.getElementById('change-text');
        google.maps.event.addDomListener(changeText, 'click', function() {
          mapLabel.set('text', document.getElementById('text').value);
        });

        var changeStrokeWeight = document.getElementById('change-strokeweight');
        google.maps.event.addDomListener(changeStrokeWeight, 'click', function() {
          mapLabel.set('strokeWeight',
            document.getElementById('stroke-weight').value);
        });

        var changeStrokeColor = document.getElementById('change-strokecolor');
        google.maps.event.addDomListener(changeStrokeColor, 'click', function() {
          mapLabel.set('strokeColor',
            document.getElementById('stroke-color').value);
        });

        var move = document.getElementById('move');
        google.maps.event.addDomListener(move, 'click', function() {
          mapLabel.setMap(mapLabel.getMap() === map ? map2 : map);
        });
      }

      google.maps.event.addDomListener(window, 'load', init);
    </script>
    <script>
      var _gaq = _gaq || [];
      _gaq.push(['_setAccount', 'UA-12846745-20']);
      _gaq.push(['_trackPageview']);

      (function() {
        var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
        ga.src = ('https:' === document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
        var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
      })();
    </script>
</html>
