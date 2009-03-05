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

function showHide(idDiv) {
	var div = document.getElementById(idDiv);
	if (div.style.display == "")
	div.style.display = "none";
	else
	div.style.display = "";
}

function test(idButton,idElt){
	var d = document.getElementById(idButton);
	if (showTest(idElt)){
		d.style.display = "block";
	} else {
		d.style.display = "none";
	}
}

function showTest(id){
	var d = document.getElementById(id);
	var td = d.getElementsByTagName('td');
	var tdcheckbox = td.item(0);
	var checkbox = tdcheckbox.childNodes;
	var length = checkbox.length;
	alert(length);
	for (var i = 0; i<length; i++){
		//alert(checkbox.item(i).nodeValue);
		if (checkbox.item(i).checked == true){
			return true;
		}
	}
	return false;
}

function show(id){
	var d = document.getElementById(id);
	d.style.display = "block";
}

function hide(id){
	var d = document.getElementById(id);
	d.style.display = "none";
}

function showMenu(id) {
	var d = document.getElementById(id);
		for (var i = 1; i<=10; i++) {
			if (document.getElementById('smenu'+i)) {document.getElementById('smenu'+i).style.display='none';}
		}
	if (d) {d.style.display='block';}
}

function setFocus(id){
	document.id.focus();
}

function confirmRemove(){
	if (!confirm("Etes-vous sûr de vouloir supprimer?")) 
		return false;
	else return true;
}
