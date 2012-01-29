function getElementsByClass(tag, clazz) {
	var elements = document.getElementsByTagName(tag);
	var results = new Array();
	for ( var i = 0; i < elements.length; i++) {
		if (elements[i].className == clazz) {
			results[results.length] = elements[i];
		}
	}
	return results;
}

function getXhr() {
	var xhr;
	if (window.XMLHttpRequest)
		xhr = new XMLHttpRequest();
	else if (window.ActiveXObject) {
		try {
			xhr = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			xhr = new ActiveXObject("Microsoft.XMLHTTP");
		}
	} else
		xhr = null;
	return xhr;
}

function switchFavorite(id) {
	var xhr = getXhr();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			isFavorite[id] = !isFavorite[id];
			var favoriteImages = getElementsByClass('img', 'favorite' + id);
			for ( var i = 0; i < favoriteImages.length; i++) {
				favoriteImages[i].src = "images/"
						+ (isFavorite[id] ? "favorite.png" : "non-favorite.png");
			}
		}
	}
	if (isFavorite[id]) {
		xhr.open("POST", 'RemoveFavorite.do', true);
		xhr.setRequestHeader('Content-Type',
				'application/x-www-form-urlencoded');
		xhr.send("interactionId=" + id);
	} else {
		xhr.open("POST", 'AddFavorite.do', true);
		xhr.setRequestHeader('Content-Type',
				'application/x-www-form-urlencoded');
		xhr.send("interactionId=" + id);
	}
}
