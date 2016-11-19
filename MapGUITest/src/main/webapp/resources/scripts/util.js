  function dateMeasure(date1, date2) {
    var h, m, s;
    if (date2.getTime() > date1.getTime()) {
      ms = date2 - date1;
    } else {
      ms = date1 - date2;
    }
    s = Math.floor(ms / 1000);
    m = Math.floor(s / 60);
    s = s % 60;
    h = Math.floor(m / 60);
    m = m % 60;
    var result = "";
    if (h>0) {
      result += h + " hours ";
    }
    if (m>0) {
      result += m + " minutes ";
    }
    if (s>0) {
      result += s + " seconds ";
    }
    return result;
  }
  function calculateDistanceAndDuration(origin, destination, event, map, dateDiff) {
    var service = new google.maps.DistanceMatrixService;
      service.getDistanceMatrix({
        origins: [origin],
        destinations: [destination],
        travelMode: google.maps.TravelMode.DRIVING
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
                        ':<br/> ' + results[j].distance.text + ' in ' + results[j].duration.text
                        + '  <br/> while in the real <br/>' +
                        getDistanceFromLatLonInKm(selectedPath[0].lat,selectedPath[0].lng,
                            selectedPath[1].lat,selectedPath[1].lng)
                        + " km in " +  dateDiff);

              var mapLabel = new MapLabel({
                  text: results[j].distance.text,
                  position: event.latLng,
                  map: map,
                  fontSize: 20,
                  align: 'center'
                });
              var marker = new google.maps.Marker();
              marker.setIcon("/SpringMvcExample/resources/images/bus.png");
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
                 marker.setIcon("/SpringMvcExample/resources/images/bus.png");
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

  function getDistanceFromLatLonInKm(lat1, lon1, lat2, lon2) {
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
    return d.toFixed(2);
  }

  function deg2rad(deg) {
    return deg * (Math.PI/180)
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
  function showWindowInfo(content, latLng) {
      var infowindow = new google.maps.InfoWindow({
          content: content,
          position: latLng
      });
      infowindow.open(map);
  }
  function getNumberInKm(text) {
    var result = text.split(" ");
    if (result[1] === 'm') {
      return Number(result[0]/1000);
    }
    return Number(result[0]);
  }