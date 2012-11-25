<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>My Location</title>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<style type="text/css">
html {
	height: 100%
}

body {
	height: 100%;
	margin: 0px;
	padding: 0px
}

#map_canvas {
	height: 100%
}
</style>
<script type="text/javascript"
	src="https://maps.google.com/maps/api/js?sensor=false">
	
</script>
<script type="text/javascript">
	function initialize() {
		var latlng = new google.maps.LatLng(-8.494105, -55.195312);
		var myOptions = {
			zoom : 4,
			center : latlng,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		};
		var map = new google.maps.Map(document.getElementById("map_canvas"),
				myOptions);
	}

	function loadPoints() {
		var request = new XMLHttpRequest();
		request.onreadystatechange(handlerRequest);
		request.open("get", "UserLocationManager.do", true);
		request.send(null);
	}

	function handlerRequest() {
		//var request = new 
	}
</script>
</head>
<body onload="initialize()">
	<br />
	<form action="" method="get">
		<table>
			<tr>
				<td>User code:</td>
				<td><input id="user_code" name="user_code" type="text" /></td>
				<td><input id="send_button" type="submit" value="Show location"
					onclick="loadPoints()" /></td>
				<td>${user_info['name']}</td>
			</tr>
		</table>
	</form>
	<br />
	<div id="map_canvas" style="width: 100%; height: 85%"></div>
</body>
</html>