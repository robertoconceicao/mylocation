function initializeBlankMap(divMap) {
	var latlng = new google.maps.LatLng(-8.494105, -55.195312);
	var myOptions = {
		zoom : 4,
		center : latlng,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};
	new google.maps.Map(document.getElementById(divMap), myOptions);
}

function getGenericXMLHttpRequest() {
	var request = null;

	if (window.XMLHttpRequest) {
		try {
			// Firefox e Chrome
			request = new XMLHttpRequest();
		} catch (e) {
			request = null;
		}
	} else if (window.ActiveXObject) {
		try {
			// new IE
			request = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				// old IE
				request = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {
				request = null;
			}
		}
	}
	return request;
}

function sendRequest(ajaxRequest, url){
	ajaxRequest.open("get", url, true);
	ajaxRequest.send();
}

function requestUserLocation(divMap) {
	ajaxRequest = getGenericXMLHttpRequest();

	if (ajaxRequest == null) {
		alert("Invalid XMLHttpRequest.");
		return;
	}

	var userCode = document.getElementById("user_code").value;
	var url = "UserLocationManager?user_code=" + userCode;

	ajaxRequest.onreadystatechange = function() {
		if (ajaxRequest.readyState == 4 && ajaxRequest.status == 200) {
			responseHandler(ajaxRequest.responseText, divMap);
		}
	};
	sendRequest(ajaxRequest, url);
	setInterval(function(){sendRequest(ajaxRequest, url);}, (5 * 1000));
}

function responseHandler(jsonUserInfo, divMap) {
	var user = eval("(" + jsonUserInfo + ")");

	if (user instanceof Array) {
		updateUserLocationList(user, divMap);
	} else {
		updateUserLocation(jsonUserInfo, divMap);
	}

}

function updateUserLocationList(users, divMap) {
	var myLatlng = new google.maps.LatLng(-27.68441337986734,
			-48.75920301560907);
	var myOptions = {
		zoom : 10,
		center : myLatlng,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};

	var map = new google.maps.Map(document.getElementById(divMap), myOptions);

	var marker;
	for (index in users) {
		marker = new google.maps.Marker({
			position : new google.maps.LatLng(users[index].latitude,
					users[index].longitude),
			map : map,
			title : users[index].name
		});
	}
}

function updateUserLocation(user, divMap) {

	// switch (user.status) {
	// case -1:
	// document.getElementById("user_info").innerHTML = "Internal error!";
	// return;
	// case 0:
	// document.getElementById("user_info").innerHTML = "User not found!";
	// return;
	// case 1:
	// document.getElementById("user_info").innerHTML = "User: " + user.name;
	// break;
	// default:
	// document.getElementById("user_info").innerHTML = "x-(";
	// return;
	// }

	user = eval("(" + user + ")");

	if (user.status != 1) {
		return;
	}

	var myLatlng = new google.maps.LatLng(user.latitude, user.longitude);
	var myOptions = {
		zoom : 10,
		center : myLatlng,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};

	var map = new google.maps.Map(document.getElementById(divMap), myOptions);

	// var contentString = "<div id='content'>" + "<h1>" + user.name + ":<br/>"
	// + user.extra + "</h1></div>";

	// var infowindow = new google.maps.InfoWindow({
	// content : contentString
	// });

	var marker = new google.maps.Marker({
		position : myLatlng,
		map : map,
		title : user.name
	});

	// google.maps.event.addListener(marker, 'click', function() {
	// infowindow.open(map, marker);
	// });

	marker.setMap(map);
}