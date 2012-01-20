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

function MM_jumpMenu(targ, selObj, restore) { // v3.0
	eval(targ + ".location='" + selObj.options[selObj.selectedIndex].value
			+ "'");
	if (restore)
		selObj.selectedIndex = 0;
}

function showMenu2(id) {
	var d = document.getElementById(id);
	var dd = document.getElementById(id + "i");
	for ( var i = 1; i <= 10; i++) {
		if (document.getElementById('ssmenu' + i)) {
			document.getElementById('ssmenu' + i).style.display = 'none';
			document.getElementById('ssmenu' + i + "i").style.background = '#C6E5F9 url(../images/bullet.gif) no-repeat 7px 12px';
		}

	}
	if (d) {

		dd.style.background = "#fff url(../images/bullet.gif) no-repeat 7px 8px";
		d.style.display = 'block';

	}
}

function compare(d, page) {

	var Date_Actuelle = new Date();
	var jourCourant = Date_Actuelle.getDate();
	var moisCourant = Date_Actuelle.getMonth() + 1;
	var anneeCourant = Date_Actuelle.getFullYear();

	var jour = d.substring(0, 2);
	var mois = d.substring(3, 5);
	var annee = d.substring(6, 10);

	if(((page=="Annonce")||(page=="Evenement")) &&(anneeCourant >= annee && moisCourant >= mois && jourCourant > jour || (anneeCourant >= annee && moisCourant > mois ) || anneeCourant > annee)){
		alert('Vous ne pouvez saisir une date antérieure à celle d\'aujourd\'hui.');
		return -1;
	}
	if((page=="Profil") &&(anneeCourant <= annee && moisCourant <= mois && jourCourant <= jour || (anneeCourant <= annee && moisCourant < mois ) || anneeCourant < annee)){
		alert('Vous ne pouvez saisir une date ultérieure à celle d\'aujourd\'hui.');
		return -1;
	}
	return 0;
}

function GnooCalendar(n, min, max, page) {

	this.format = new String("fr");
	this.name = new String(n);
	this.tag = new String();
	this.pageCourante = new String(page);
	this.title = new String("Calendrier");
	this.date = new Date();
	this.moving = new Boolean(false);
	this.vis = new Boolean(false);
	this.free = new Boolean(false);
	this.curYear = new Number(this.date.getFullYear());
	this.maxYear = new Number(this.curYear + max);
	this.minYear = new Number(this.curYear - min);
	this.curMonth = new Number(this.date.getMonth());

	/*
	 * mList() retourne la liste des mois
	 */
	this.mList = function() {
		var tmp = "<table border='0' cellpadding='0' cellspacing='0' class='Gtab'>";

		tmp += "<tr><td align='center' colspan='2' class='Gname'>";
		if (document.layers) {
			tmp += "<table width='100%'>";
			tmp += "<tr>";
			tmp += "<td class='Gname' align='center'>" + this.title + "</td>";
			tmp += "<td><a href='javascript://' onclick='" + this.name
					+ ".hide();' class='Gname'>X</a></td>";
			tmp += "</tr>";
			tmp += "</table>";
		} else {
			tmp += "<span style='float:left; text-indent:20px;'>" + this.title
					+ "</span>";
			tmp += "<span style='float:right;'>";
			tmp += "<a href='javascript://' onclick='" + this.name
					+ ".hide();' class='Gname'>X</a>";
			tmp += "</span>";
		}
		tmp += "</td></tr>";
		tmp += "<tr>";
		tmp += "<td class='Gtxt'><select name='" + this.name
				+ "month' class='Gtxt' ";
		tmp += "onchange='" + this.name
				+ ".setMonth(this[this.selectedIndex].value)' >\n";
		for ( var i = 0; i < this.month.length; i++) {
			tmp += "<option value='" + i + "'";
			if (this.curMonth == i)
				tmp += " selected";
			tmp += ">" + this.month[i] + "</option>\n";
		}
		tmp += "</select></td><td class='Gtxt'>\n";
		tmp += this.yList();
		tmp += "</td></tr></table>";
		tmp += this.dList();
		return tmp;
	}
	/*
	 * yList() retourne la liste des années
	 */
	this.yList = function() {
		var tmp = "<select name='" + this.name + "year' class='Gtxt' ";
		tmp += "onchange='" + this.name
				+ ".setYear(this[this.selectedIndex].value);' >\n";
		for ( var i = this.minYear; i <= this.maxYear; i += 1) {
			tmp += "<option value='" + i + "'";
			if (this.curYear == i)
				tmp += " selected";
			tmp += ">" + i + "</option>\n";
		}
		tmp += "</select>\n";
		return tmp;
	}

	this.dayCell = function(d, n) {
		var tmp = new String("");
		var now = new Date();
		if (this.checkDate(d) == now.getDate()
				&& this.curMonth == now.getMonth()
				&& this.curYear == now.getFullYear()) {
			tmp += "<td class='Gc'";
		} else {
			tmp += "<td class='Gc" + n + "'";
		}
		if (!document.layers) {
			tmp += "title='" + this.checkDate(d) + " "
					+ this.month[this.curMonth] + " " + this.curYear + "'";
			tmp += " onmousedown='" + this.name + ".getDate(\"";
			tmp += this.formatDate(d);
			tmp += "\");' ";
			tmp += " onmouseover='this.className=this.className+\"on\";' ";
			tmp += " onmouseout='this.className= this.className.substring(0,this.className.indexOf(\"on\"));'";
		} else
			tmp += " width='22' height='16' ";
		tmp += ">";
		return tmp;
	}

	/*
	 * dList() retourne le tableau des jours
	 */
	this.dList = function() {
		var cur = new Number(1);
		var tmpDate = new Date();
		var tmp = new String(
				"<table border='0' cellpadding='0' cellspacing='0' class='Gtab'>");
		tmpDate.setDate(cur);
		tmpDate.setMonth(this.curMonth);
		tmpDate.setFullYear(this.curYear);
		tmp += "<tr>\n";
		for ( var i = 1; i < this.day.length; i++)
			tmp += "<td class='Gh" + i + "'>" + this.day[i] + "</td>\n";
		tmp += "<td class='Gh0'>" + this.day[0] + "</td>\n";
		tmp += "</tr>\n";
		for ( var j = 0; j < 6; j++) {
			tmp += "<tr>\n";
			for ( var i = 1; i < this.day.length; i++) {
				tmpDate.setDate(cur);
				if (cur <= 31 && i == tmpDate.getDay()
						&& this.curMonth == tmpDate.getMonth()) {
					tmp += this.dayCell(cur, i);
					tmp += this.getLink(cur);
					cur += 1;
				} else {
					tmp += "<td class='Gc" + i + "'";
					tmp += ">&nbsp;";
				}
				tmp += "</td>\n";
				tmpDate.setDate(cur);
			}
			if (cur == tmpDate.getDate() && this.curMonth == tmpDate.getMonth()) {
				tmp += this.dayCell(cur, 0);
				tmp += this.getLink(cur);
				cur += 1;
			} else {
				tmp += "<td class='Gc0'";
				tmp += ">&nbsp;";
			}
			tmp += "</td>\n</tr>\n";
		}
		tmp += "</table>\n";
		return tmp;
	}
	/*
	 * getLink(c) retourne la balise d'un lien
	 */
	this.getLink = function(c) {
		var tmp = new String("");
		if (document.layers) {
			tmp = "<a href='javascript://' ";
			tmp += "onclick='" + this.name + ".getDate(\"";
			tmp += this.formatDate(c);
			tmp += "\");' class='NSday'>" + (c) + "</a>";
		} else {
			tmp = (c);
		}
		return tmp;
	}
	/*
	 * setMonth( Integer m ) modifie le mois à afficher
	 */
	this.setMonth = function(m) {
		this.curMonth = m;
		this.show();
		return;
	}
	/*
	 * getYear( Integer y ) modifie l'année à afficher
	 */
	this.getYear = function(y) {
		if (document.layers) {
			for ( var i = 0; i < document.layers[this.div].document.forms[this.name
					+ "_form"][this.name + "year"].length; i++) {
				if (document.layers[this.div].document.forms[this.name
						+ "_form"][this.name + "year"][i].value == y) {
					document.layers[this.div].document.forms[this.name
							+ "_form"][this.name + "year"].selectedIndex = i;
					this.setYear(y);
					return;
				}
			}
		} else {
			for ( var i = 0; i < document.forms[this.name + "_form"].elements[this.name
					+ "year"].length; i++) {
				if (document.forms[this.name + "_form"].elements[this.name
						+ "year"][i].value == y) {
					document.forms[this.name + "_form"].elements[this.name
							+ "year"].selectedIndex = i;
					this.setYear(y);
					return;
				}
			}
		}
		return;
	}

	/*
	 * getMonth( Integer m ) modifie le mois à afficher
	 */
	this.getMonth = function(d) {
		if (document.layers) {
			for ( var i = 0; i < document.layers[this.div].document.forms[this.name
					+ "_form"][this.name + "month"].length; i++) {
				if (document.layers[this.div].document.forms[this.name
						+ "_form"][this.name + "month"][i].value == d) {
					document.layers[this.div].document.forms[this.name
							+ "_form"][this.name + "month"].selectedIndex = i;
					this.setMonth(d);
					return;
				}
			}
		} else {
			for ( var i = 0; i < document.forms[this.name + "_form"].elements[this.name
					+ "month"].length; i++) {
				if (document.forms[this.name + "_form"].elements[this.name
						+ "month"][i].value == d) {
					document.forms[this.name + "_form"].elements[this.name
							+ "month"].selectedIndex = i;
					this.setMonth(d);
					return;
				}
			}
		}
		return;
	}
	/*
	 * setYear( Integer y ) modifie l'année à afficher
	 */
	this.setYear = function(y) {
		this.curYear = y;
		this.show();
		return;
	}

	/*
	 * hide() masque le calendrier
	 */
	this.hide = function() {
		if (document.layers)
			document.layers[this.div].visibility = 'hide';
		else {
			document.getElementById(this.div).style.visibility = 'hidden';
			document.getElementById(this.div).style.display = 'none';
		}
		this.vis = false;
		// this.endMove();
		return;
	}

	/*
	 * getDate() retourne la date selectionnée dans le champs cible
	 */
	this.getDate = function(d) {
		
		if (compare(d, this.pageCourante) == 0) {
			if (this.target != null) {
				this.target.value = d;
				// this.hide();
			} else {
				this.target.focus();
			}

		}
		this.hide();
		return;
	}

	/*
	 * show() affiche le calendrier
	 */
	this.show = function() {
		this.vis = true;
		this.tag = "<form name='" + this.name + "_form' method='post'>\n";
		this.tag += this.mList();
		this.tag += "</form>\n";
		if (document.layers) {
			with (document.layers[this.div]) {
				document.open("text/html");
				document.write(this.tag);
				document.close();
				visibility = 'show';
			}
		} else {
			document.getElementById(this.div).innerHTML = "" + this.tag;
			document.getElementById(this.div).style.visibility = 'visible';
			document.getElementById(this.div).style.display = 'block';
		}
		return;
	}
	/*
	 * init( String d ) initialise le Calendrier à la date actuelle paramêtres :
	 * d : nom du calque d'affichage obj : objet dont la propriété value va
	 * recevoir le String de la date
	 */
	this.init = function(d, obj) {
		this.div = d;
		this.target = obj;
		this.date = new Date();
		this.date.setDate(1);
		this.curMonth = this.date.getMonth();
		this.curYear = this.date.getFullYear();
		if (!self.movingCalendar)
			self.movingCalendar = null;
		return;
	}
	/*
	 * checkDate( Integer d ) paramêtre : le jour d'une date
	 */
	this.checkDate = function(d) {
		if (parseInt(d) <= 9)
			return "0" + parseInt(d);
		return parseInt(d);
	}

	/*
	 * formatDate( Integer d, Integer m, Integer y ) paramêtre : le jour d'une
	 * date
	 */
	this.formatDate = function(c) {
		var tmp = "";

		tmp = this.checkDate(c) + "/";
		tmp += this.checkDate(1 + parseInt(this.curMonth)) + "/";
		tmp += this.curYear;

		return tmp;

	}
	return this;
}
/** *************************** */
GnooCalendar.prototype.day = [ "D", "L", "M", "M", "J", "V", "S" ];
GnooCalendar.prototype.month = [ "Janvier", "F&eacute;vrier", "Mars", "Avril",
		"Mai", "Juin", "Juillet", "Ao&ucirc;t", "Septembre", "Octobre",
		"Novembre", "D&eacute;cembre" ];

/** *************************** */

function validation(f) {
	  if (f.pwd1.value == '' || f.pwd2.value == '' || f.nom.value == '' || f.prenom.value == '' || f.email.value == '' || f.dateNaissance.value == '' || f.sexe.value == '' || f.adresse.value == '' || f.profession.value == '' || f.telephone.value == '') {

	    alert('Tous les champs ne sont pas remplis');
	    f.pwd1.focus();
	    return false;
	    }
	  else if (f.pwd1.value != f.pwd2.value) {
	    alert('Ce ne sont pas les mêmes mots de passe!');
	    f.pwd1.focus();
	    return false;
	    }
	  else if (f.pwd1.value == f.pwd2.value) {
	    return true;
	    }
	  else {
	    f.pwd1.focus();
	    return false;
	    }
	  }
