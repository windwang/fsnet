function removeInterestNode(node) {
	document.getElementById('interests').removeChild(node.parentNode);
}

function addInterest() {
	var divNode = document.createElement('div');
	var fileNode = document.createElement('input');
	var removeNode = document.createElement('input');

	fileNode.setAttribute('type', 'text');
	fileNode.setAttribute('name', 'interets[]');
	fileNode.setAttribute('class', 'moreInteret');

	removeNode.setAttribute('type', 'button');
	removeNode.setAttribute('value', 'supprimer');
	removeNode.setAttribute('class', 'removeBut');

	divNode.appendChild(fileNode);
	divNode.appendChild(removeNode);

	removeNode.onclick = function(event) {
		removeInterestNode(this);
	}

	document.getElementById('interests').appendChild(divNode);
}

function showHide(idDiv) {
	var div = document.getElementById(idDiv);
	if (div.style.display == "")
		div.style.display = "none";
	else
		div.style.display = "";
}

function deploy(idButton, idList, attrNameCheckBoxes, attrNameCheckBoxAll) {
	var d = document.getElementById(idButton);
	c = d.firstChild;
	var value = c.nodeValue;
	if (value == "[+]") {
		c.nodeValue = "[-]";
		d.title = "Réduire la liste";
	} else {
		c.nodeValue = "[+]";
		d.title = "Déployer la liste";
		hide('removeButton');
		unchecked(attrNameCheckBoxes);
		unchecked(attrNameCheckBoxAll);
	}
	showHide(idList);
}

function showHideButton(idButton, attributeName) {
	var d = document.getElementById(idButton);
	if (hasAnCheckedInput(attributeName)) {
		d.style.display = "block";
	} else {
		d.style.display = "none";
	}
}

function hasAnCheckedInput(attributeName) {
	var d = document.getElementsByTagName('input');
	for ( var i = 0; i < d.length; i++) {
		if ((d.item(i).getAttribute('name') == attributeName)
				&& (d.item(i).checked == true))
			return true;
	}
	return false;
}

function unchecked(attributeName) {
	var d = document.getElementsByTagName('input');
	for ( var i = 0; i < d.length; i++) {
		if (d.item(i).getAttribute('name') == attributeName)
			d.item(i).checked = false;
	}
}

function show(id) {
	var d = document.getElementById(id);
	d.style.display = "";
}

function hide(id) {
	var d = document.getElementById(id);
	d.style.display = "none";
}

function showMenu(id) {
	var d = document.getElementById(id);
	for ( var i = 1; i <= 10; i++) {
		if (document.getElementById('smenu' + i)) {
			document.getElementById('smenu' + i).style.display = 'none';
		}
	}
	if (d) {
		d.style.display = 'block';
	}
}

function setFocus(id) {
	document.id.focus();
}

function confirmRemove() {
	if (!confirm("Etes-vous sûr de vouloir supprimer?"))
		return false;
	else
		return true;
}

function selectAll(idcheckbox, attributeName) {
	var d = document.getElementById(idcheckbox);
	var checkboxes = document.getElementsByTagName('input');
	var selected = d.checked;
	for ( var i = 0; i < checkboxes.length; i++) {
		if (checkboxes.item(i).getAttribute('name') == attributeName)
			checkboxes.item(i).checked = selected;
	}

}

function recupXHR() {
	var xhr = false;
	if (window.XMLHttpRequest) { // Mozilla, Safari,...
		xhr = new XMLHttpRequest();
	} else if (window.ActiveXObject) { // IE
		try {
			xhr = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				xhr = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {
			}
		}
	}
	return xhr;
}
function recupPage(page, param, valeur, idDiv) {
	var req = recupXHR();
	req.onreadystatechange = function() {
		recupResponse(req, idDiv);
	};
	req.open("GET", page + "?" + param + "=" + valeur, true);
	req.send(null);
}

function recupResponse(req, idDiv) {
	if (req.readyState == 4) {
		if (req.status == 200) {
			document.getElementById(idDiv).innerHTML = req.responseText;
		}
	}
}