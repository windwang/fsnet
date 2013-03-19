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

function changeSimpleOrMultiple() {
	if(document.getElementsByName("chooseSimpleOrMultiple")[0].checked)
	{
		document.getElementById("divChooseSimpleMember").style.display='block';
		document.getElementById("divChooseMultipleMember").style.display='none';
		document.getElementById("divChooseMultipleFileMember").style.display='none';
	}
	if(document.getElementsByName("chooseSimpleOrMultiple")[1].checked)
	{
		document.getElementById("divChooseMultipleMember").style.display='block';
		document.getElementById("divChooseSimpleMember").style.display='none';
		document.getElementById("divChooseMultipleFileMember").style.display='none';
	}
	if(document.getElementsByName("chooseSimpleOrMultiple")[2].checked)
	{
		document.getElementById("divChooseMultipleFileMember").style.display='block';
		document.getElementById("divChooseSimpleMember").style.display='none';
		document.getElementById("divChooseMultipleMember").style.display='none';
	}
}

function DeplacerDroit(l1, l2) {

	if (l1.options.selectedIndex >= 0)
		for ( var i = l1.options.length - 1; i >= 0; i--) {
			if (l1.options[i].selected) {
				o = new Option(l1.options[i].text, l1.options[i].value);
				l2.options[l2.options.length] = o;
				l1.options[i] = null;
			}
		}
	else {
		alert("Aucun membre sélectionnée");
	}
}

/*
 * Move the rights input from base,advance to grated if toAdd is true 
 * the opposite way if false
 * */
function moveRigths(base, advance, granted, toAdd) {
	if(toAdd){
		if (base.options.selectedIndex >= 0 || advance.options.selectedIndex >= 0){
			for ( var i = base.options.length - 1; i >= 0; i--) {
				if (base.options[i].selected) {
					var o = new Option(base.options[i].text, base.options[i].value);
					o.setAttribute("class", "base");
					
					granted.options[granted.options.length] = o;
					base.options[i] = null;
				}
			}
			for ( var i = advance.options.length - 1; i >= 0; i--) {
				if (advance.options[i].selected) {
					var o = new Option(advance.options[i].text, advance.options[i].value);					
					o.className = "advance";
					
					granted.options[granted.options.length] = o;
					advance.options[i] = null;
				}
			}
		}
		else {
			alert("Aucun membre sélectionnée");
		}	
	}else{
	
		if (granted.options.selectedIndex >= 0){
			for ( var i = granted.options.length - 1; i >= 0; i--) {
				if (granted.options[i].selected) {
					var o = new Option(granted.options[i].text, granted.options[i].value);
					if(granted.options[i].className == 'base'){
						base.options[base.options.length] = o;
					}
					else{
						advance.options[advance.options.length] = o;
					}
					granted.options[i] = null;
				}
			}
		}else{
			alert("Aucun membre sélectionnée");
		}
	}
}

function ModifyGroup() {
	var memberListRight= document.getElementsByName('memberListRight')
			.item(0);
	var memberListLeft = document.getElementsByName('memberListLeft')
	.item(0);
	var rigthListRight = document.getElementsByName('rigthListRight')
			.item(0);
	for ( var i = 0; i < memberListLeft.options.length; i++) {
		memberListLeft.options[i].selected = "true";
	}

	for ( var i = 0; i < memberListRight.options.length; i++) {
		memberListRight.options[i].selected = "true";
	}
	for ( var i = 0; i < rigthListRight.options.length; i++) {
		rigthListRight.options[i].selected = "true";
	}
	return true;
}

function CreateGroup() {
	var memberListLeft = document.getElementsByName('memberListRight')
			.item(0);

	var rigthListLeft = document.getElementsByName('rigthListRight')
			.item(0);
	for ( var i = 0; i < memberListLeft.options.length; i++) {
		memberListLeft.options[i].selected = "true";
	}

	for ( var i = 0; i < rigthListLeft.options.length; i++) {
		rigthListLeft.options[i].selected = "true";
	}
	return true;
}

function confirmDelete(action, msg) {
	if (confirm(msg)) {
		document.location = action;
	}
}
function confirmDelete2(formid, msg) {
	if (confirm(msg)) {
		document.getElementById(formid).submit();
	}
}