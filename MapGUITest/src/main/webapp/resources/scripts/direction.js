  function calculateAndDisplayRoute(start, end, waypts, directionsDisplay, directionsService, markerArray, stepDisplay, map) {
      // First, remove any existing markers from the map.
      for (var i = 0; i < markerArray.length; i++) {
         markerArray[i].setMap(null);
      }
      markerArray = [];
      directionsService.route({
        origin: start,
        destination: end,
        waypoints: waypts,
        travelMode: google.maps.TravelMode.DRIVING
      }, function(response, status) {
        if (status === google.maps.DirectionsStatus.OK) {
          console.log(response);
          directionsDisplay.setDirections(response);
          showSteps(response, markerArray, stepDisplay, map);
        } else {
          window.alert('Directions request failed due to ' + status);
        }
      });
  }
  function showSteps(directionResult, markerArray, stepDisplay, map) {
    // For each step, place a marker, and add the text to the marker's infowindow.
    // Also attach the marker to an array so we can keep track of it and remove it
    // when calculating new routes.
    var total = 0;
    for (var k = 0; k < directionResult.routes[0].legs.length; k++) {
        var myRoute = directionResult.routes[0].legs[k];
        for (var i = 0; i < myRoute.steps.length; i++) {
          total = total + getNumberInKm(myRoute.steps[i].distance.text);
          if (total >= 0.1) {
            var mapLabel = new MapLabel({
              text: total.toFixed(2)+'km',
              position: myRoute.steps[i].end_location,
              map: map,
              fontSize: 20,
              align: 'center'
            });
          }
          var marker = new google.maps.Marker;
          marker.setIcon("/SpringMvcExample/resources/images/bus.png");
          marker.setMap(map);
          marker.setPosition(myRoute.steps[i].start_location);
          attachInstructionText(
              stepDisplay, marker, myRoute.steps[i].instructions
              + "<br/>" + myRoute.steps[i].distance.text, map);
          markerArray.push(marker);
       }
    }
    var routePath = new google.maps.Polyline({
       path: directionResult.routes[0].overview_path,
       geodesic: true,
       strokeColor: '#FF0000',
       strokeOpacity: 1.0,
       strokeWeight: 2
     });

    routePath.setMap(map);
  }

  function attachInstructionText(stepDisplay, marker, text, map) {
    google.maps.event.addListener(marker, 'click', function() {
      // Open an info window when the marker is clicked on, containing the text
      // of the step.
      stepDisplay.setContent(text);
      stepDisplay.open(map, marker);
    });
  }