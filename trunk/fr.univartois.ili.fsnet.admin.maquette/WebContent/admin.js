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