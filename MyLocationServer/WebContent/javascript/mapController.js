var map;
var markersArray = [];

function initialize(divMap) {
	var position = new google.maps.LatLng(-8.494105, -55.195312);
	var myOptions = {
		zoom : 4,
		center : position,
		mapTypeId : google.maps.MapTypeId.TERRAIN
	};
	new google.maps.Map(document.getElementById(divMap), myOptions);
	
	requestUserLocation();
}

function addMarker(user) {
	marker = new google.maps.Marker({
		position : new google.maps.LatLng(user.latitude, user.longitude),
		map : map,
		title : user.name
	});
	addClickEvent(user, marker);
	markersArray.push(marker);
}

function deleteOverlays() {
	if (markersArray) {
		for (i in markersArray) {
			markersArray[i].setMap(null);
		}
		markersArray.length = 0;
	}
}

function addClickEvent(user, marker) {
	var contentString = "<div id='content'><h1>" + user.name + "</h1></div>";

	var infoWindow = new google.maps.InfoWindow({
		content : contentString
	});

	google.maps.event.addListener(marker, 'click', function() {
		infoWindow.open(map, marker);
	});
}