function responseHandler(jsonUserInfo, divMap) {
	var infoUser = eval("(" + jsonUserInfo + ")");

	if (infoUser instanceof Array) {
		updateUserLocationArray(infoUser, divMap);
	} else {
		updateUserLocation(infoUser, divMap);
	}
}

function initializeBlankMap(divMap) {
	var position = new google.maps.LatLng(-8.494105, -55.195312);
	var myOptions = {
		zoom : 4,
		center : position,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};
	new google.maps.Map(document.getElementById(divMap), myOptions);
}

function calcCenterPosition(userArray) {
	var biggerLatitude = -90.0;
	var smallerLatitude = 90.0;
	var biggerLongitude = -180.0;
	var smallerLongitude = 180.0;

	for (index in userArray) {
		if (userArray[index].latitude > biggerLatitude) {
			biggerLatitude = userArray[index].latitude;
		}
		if (userArray[index].latitude < smallerLatitude) {
			smallerLatitude = userArray[index].latitude;
		}
		if (userArray[index].longitude > biggerLongitude) {
			biggerLongitude = userArray[index].longitude;
		}
		if (userArray[index].longitude < smallerLongitude) {
			smallerLongitude = userArray[index].longitude;
		}
	}

	var centerLat = (biggerLatitude + smallerLatitude) / 2;
	var centerLong = (biggerLongitude + smallerLongitude) / 2;

	return {
		latitude : centerLat,
		longitude : centerLong
	};
}

function updateUserLocationArray(userArray, divMap) {
	var center = calcCenterPosition(userArray);

	var position = new google.maps.LatLng(center.latitude, center.longitude);
	var myOptions = {
		zoom : 8,
		center : position,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};

	var map = new google.maps.Map(document.getElementById(divMap), myOptions);

	var marker;
	for (index in userArray) {
		marker = new google.maps.Marker({
			position : new google.maps.LatLng(userArray[index].latitude,
					userArray[index].longitude),
			map : map,
			title : userArray[index].name
		});

		addClickEvent(userArray[index], marker, map);
	}
}

function updateUserLocation(user, divMap) {

	switch (user.status) {
	case -1:
		document.getElementById("user_info").innerHTML = "Internal error!";
		return;
	case 0:
		document.getElementById("user_info").innerHTML = "User not found!";
		return;
	case 1:
		document.getElementById("user_info").innerHTML = "User: " + user.name;
		break;
	default:
		document.getElementById("user_info").innerHTML = "x-(";
		return;
	}

	var position = new google.maps.LatLng(user.latitude, user.longitude);
	var myOptions = {
		zoom : 10,
		center : position,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};

	var map = new google.maps.Map(document.getElementById(divMap), myOptions);

	var marker = new google.maps.Marker({
		position : position,
		map : map,
		title : user.name
	});

	addClickEvent(user, marker, map);
}

function addClickEvent(user, marker, map) {
	var contentString = "<div id='content'>" + "<h1>" + user.name + ":<br/>"
			+ user.extra + "</h1></div>";

	var infoWindow = new google.maps.InfoWindow({
		content : contentString
	});

	google.maps.event.addListener(marker, 'click', function() {
		infoWindow.open(map, marker);
	});
}