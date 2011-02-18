/* variables globales */
var directionDisplay;
var directionsService;
var map;

/* */
function getDestinationAddresse() {
	var destinationAddresse = null;
	if (document.getElementById("address") != null) {
		destinationAddresse = document.getElementById("address").getAttribute(
				"value");
	}
	return destinationAddresse;
}

/* */
function initializeGeolocalisation() {
	initializeMap();

	var destinationAddresse = getDestinationAddresse();

	if (destinationAddresse != null) {
		putOnMapEvent(destinationAddresse);

	}
}

/* */
function putOnMapEvent(addr) {
	var geocoder = new google.maps.Geocoder();

	geocoder.geocode({
		'address' : addr
	}, function(results, status) {
		if (status == google.maps.GeocoderStatus.OK) {
			var coordinates = new google.maps.LatLng(
					results[0].geometry.location.lat(),
					results[0].geometry.location.lng());
			var marker = new google.maps.Marker({
				position : coordinates
			});
			marker.setMap(map);
			map.setCenter(coordinates);

			if (navigator.geolocation) {
				navigator.geolocation.getCurrentPosition(leadVisitorToEvent);
			}
		
			document.getElementById("mapCanvas").style.visibility = "visible"; 
		} else {
	
			document.getElementById("mapCanvas").style.visibility = "hidden"; 
		}
	});
}

/* */
function leadVisitorToEvent(position) {

	if (position == null) {
		return;
	}

	var start = new google.maps.LatLng(position.coords.latitude,
			position.coords.longitude);
	var end = getDestinationAddresse();
	directionsDisplay.setMap(map);
	var requeteItineraire = {
		origin : start,
		destination : end,
		travelMode : google.maps.DirectionsTravelMode.DRIVING
	};
	directionsService.route(requeteItineraire, function(response, status) {
		if (status == google.maps.DirectionsStatus.OK) {
			directionsDisplay.setDirections(response);
		}
	});
}

/* */
function initializeMap() {
	directionsDisplay = new google.maps.DirectionsRenderer();
	directionsService = new google.maps.DirectionsService();

	var latlng = new google.maps.LatLng(48.8566667, 2.3509871);
	var myOptions = {
		zoom : 12,
		center : latlng,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	}
	map = new google.maps.Map(document.getElementById("mapCanvas"), myOptions);
}
