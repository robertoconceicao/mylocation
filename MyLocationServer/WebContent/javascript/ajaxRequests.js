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

function requestUserLocation(divMap) {
	if (this.task != null && typeof this.task !== "undefined") {
		stopTask(this.task);
		this.task = null;
	}

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

	ajaxRequest.open("get", url, true);
	ajaxRequest.send();

	this.task = startTask(ajaxRequest, url);
}

function startTask(ajaxRequest, url) {
	return setInterval(function() {
		ajaxRequest.open("get", url, true);
		ajaxRequest.send();
	}, (5 * 1000));
}

function stopTask(task) {
	clearInterval(task);
}