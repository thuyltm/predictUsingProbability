  /**
   * A menu that lets a user delete a selected vertex of a path.
   * @constructor
   */
  var geocoder = new google.maps.Geocoder;
  var startPoint = null;
  var endPoint = null;
  var waypts = [];
  function ContextMenu() {
    var divOuter = document.createElement('div');
    divOuter.className = 'context-menu';

    var divGetLocation = document.createElement('div');
    divGetLocation.className = 'options-marker';
    divGetLocation.id = 'get-location';
    divGetLocation.innerHTML = 'Get Location';

    var divClose = document.createElement('div');
    divClose.className = 'options-marker';
    divClose.id = 'close';
    divClose.innerHTML = 'Close';

    var divStartPoint = document.createElement('div');
    divStartPoint.className = 'options-marker';
    divStartPoint.id = 'start-point';
    divStartPoint.innerHTML = 'Add Start Point';

    var divEndPoint = document.createElement('div');
    divEndPoint.className = 'options-marker';
    divEndPoint.id = 'end-point';
    divEndPoint.innerHTML = 'Add End Point';

    var divWayPoint = document.createElement('div');
    divWayPoint.className = 'options-marker';
    divWayPoint.id = 'way-point';
    divWayPoint.innerHTML = 'Add To WayPoint';

    var calWayPoint = document.createElement('div');
    calWayPoint.className = 'options-marker';
    calWayPoint.id = 'cal-WayPoint';
    calWayPoint.innerHTML = 'Caculate Way Point';

    divOuter.appendChild(divGetLocation);
    divOuter.appendChild(divClose);
    divOuter.appendChild(divStartPoint);
    divOuter.appendChild(divEndPoint);
    divOuter.appendChild(divWayPoint);
    divOuter.appendChild(calWayPoint);
    this.div_ = divOuter;
    var menu = this;
  }
  ContextMenu.prototype = new google.maps.OverlayView();

  ContextMenu.prototype.onAdd = function() {
    var menu = this;
    var map = this.getMap();
    this.getPanes().floatPane.appendChild(this.div_);

    // mousedown anywhere on the map except on the menu div will close the
    // menu.
    this.divListener_ = google.maps.event.addDomListener(map.getDiv(), 'mousedown', function(e) {
        if (e.target.id === 'get-location') {
          menu.getLocation();
        } else if (e.target.id === 'start-point') {
          startPoint=menu.get('object');
          endPoint = null;
          waypts = [];
         /* var startMarker = new google.maps.Marker({
              position: startPoint,
              map: map,
              label: "S"
          });
          markerArray.push(startMarker);*/
        } else if (e.target.id === 'end-point') {
          endPoint = menu.get('object');
         /* var endMarker = new google.maps.Marker({
              position: endPoint,
              map: map,
              label: "F"
          });
          markerArray.push(endMarker);*/
          displayRoute(startPoint, endPoint, waypts);
        } else if (e.target.id === 'way-point') {
          var wayPoint = menu.get('object');
          waypts.push({
              location: wayPoint,
              stopover: true
            });
         /* var wayMarker = new google.maps.Marker({
              position: wayPoint,
              map: map,
              label: "W"
          });
          markerArray.push(wayMarker);*/
        } else if (e.target.id === 'cal-WayPoint') {
          displayRoute(startPoint, endPoint, waypts);
        } else if (e.target != menu.div_ || e.target.id === 'close') {
          menu.close();
        }
    }, true);
  };

  ContextMenu.prototype.onRemove = function() {
    google.maps.event.removeListener(this.divListener_);
    this.div_.parentNode.removeChild(this.div_);
    // clean up
    this.set('position');
  };

  ContextMenu.prototype.close = function() {
    this.setMap(null);
  };

  ContextMenu.prototype.draw = function() {
    var position = this.get('position');
    var projection = this.getProjection();

    if (!position || !projection) {
      return;
    }

    var point = projection.fromLatLngToDivPixel(position);
    this.div_.style.top = point.y + 'px';
    this.div_.style.left = point.x + 'px';
  };

  /**
   * Opens the menu at a vertex of a given path.
   */
  ContextMenu.prototype.open = function(map, vertex, object) {
    this.set('position', vertex);
    this.set('object', object.latLng);
    this.setMap(map);
    this.draw();
  };

  /**
   * Get Location from the vertex.
   */
  ContextMenu.prototype.getLocation = function() {
    var menu = this;
    geocoder.geocode({'location': this.get('position')}, function(results, status) {
       if (status === google.maps.GeocoderStatus.OK) {
          if (results[1]) {
            menu.showWindowInfo(results[1].formatted_address);
          } else {
            window.alert('No results found');
          }
       } else {
          window.alert('Geocoder failed due to: ' + status);
       }
    });
  };
  /**
   * Get Location from the vertex.
   */
  ContextMenu.prototype.showWindowInfo = function(context) {
      var infowindow = new google.maps.InfoWindow({
          content: context,
          position: this.get('position')
      });
      infowindow.open(this.getMap());
  };