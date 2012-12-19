function responseHandler(userArray) {
	userArray = eval("(" + userArray + ")");
	updateUserLocationArray(userArray);
}

function updateUserLocationArray(userArray) {
	deleteOverlays();

	for (index in userArray) {
		addMarker(userArray[index]);
	}
}