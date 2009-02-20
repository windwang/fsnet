function removeInterestNode(node) {
	document.getElementById('interests').removeChild(node.parentNode);
}

function addInterest() {
	var divNode = document.createElement('div');
	var fileNode = document.createElement('input');
	var removeNode = document.createElement('input');

	fileNode.setAttribute('type', 'text');
	fileNode.setAttribute('name', 'interets[]');

	removeNode.setAttribute('type', 'button');
	removeNode.setAttribute('value', 'supprimer');

	divNode.appendChild(fileNode);
	divNode.appendChild(removeNode);

	removeNode.onclick = function(event) {
		removeInterestNode(this);
	}

	document.getElementById('interests').appendChild(divNode);
}

function affCache(idDiv) {
	var div = document.getElementById(idDiv);
	if (div.style.display == "")
	div.style.display = "none";
	else
	div.style.display = "";
}

function show(id) {
	var d = document.getElementById(id);
		for (var i = 1; i<=10; i++) {
			if (document.getElementById('smenu'+i)) {document.getElementById('smenu'+i).style.display='none';}
		}
	if (d) {d.style.display='block';}
}


