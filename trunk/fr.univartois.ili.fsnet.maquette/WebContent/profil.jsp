<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://iliforum.ili.fsnet.com/" prefix="fsnet"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" type="image/png" href="images/favicon.ico" />
<meta http-equiv="content-type"
	content="application/xhtml+xml; charset=UTF-8" />
<meta name="author" content="Luka Cvrk - www.solucija.com" />
<meta name="description" content="Site Description" />
<meta name="keywords" content="site, keywords" />
<meta name="robots" content="index, follow" />
<link rel="stylesheet" type="text/css" media="screen"
	href="css/style.css" />
<title>FSNet</title>
<!-- <style type="text/css"></style>-->



<!-- FICHIER DE STYLE DU CALENDRIER //-->
<link rel='stylesheet' href="css/calendar.css" />
<!-- FICHIER DE SCRIPT DU CALENDRIER //-->
<script type="text/javascript" src="maquette.js"></script>
<script type="text/javascript">
	/******************************/
	/**self.defaultStatus = "GnooCalendar 1.4";
	 /******************************/
	/* 
	 * instanciation de l'objet
	 */
	var CL = new GnooCalendar("CL", 40, 10);

	/******************************/
	function init() {
		CL.init("calend", document.forms["testform"].elements["dateNaissance"]);

	}
	/******************************/
	//-->
</script>
</head>
<body onload="showMenu();init();">
<div class="wrap background"><jsp:include page="haut.jsp"></jsp:include>
<fsnet:login var="membre" idLogin="${entite.id}"  >
	
	<div id="left">
	<h2><a href="#">Mon profil </a></h2>

	<form name="testform" action="CompleteProfil" method="post"
		id="completeProfil" style='margin: 0px;'>
	<table>
		<tr>
			<td style="height: 19" colspan="3">
			<h4>&nbsp;</h4>
			</td>
			<td colspan="2">
			<h4>&nbsp;</h4>
			</td>
			<td style="width: 4" colspan="3" rowspan="17">&nbsp;</td>
		</tr>
		<tr>
			<td style="height: 2"></td>
			<td style="width: 40"></td>
			<td style="width: 304"></td>
			<td style="width: 1"></td>
			<td style="width: 451"></td>
		</tr>

		<tr>
			<td style="height: 27" colspan="3" rowspan="5"><strong>Nom
			:</strong></td>
			<td colspan="2"></td>
		</tr>
		<tr>
			<td colspan="2"></td>
		</tr>
		<tr>
			<td colspan="2"></td>
		</tr>

		<tr>
			<td colspan="2"></td>
		</tr>

		<tr>
			<td style="height: 27" colspan="2"><label> <input
				type="text" name="nom" value="${membre.nom }" /> </label></td>
		</tr>
		<tr>
			<td style="height: 22" colspan="3"><strong>Pr&eacute;nom
			: </strong></td>
			<td colspan="2"><label> <input type="text" name="prenom"
				value="${membre.prenom }" /> </label></td>
		</tr>
		<tr>
			<td style="height: 29" colspan="3"><strong>E-mail : </strong></td>
			<td colspan="2"><label> <input type="text" name="email"
				value="${membre.email }" /> </label></td>
		</tr>

		<tr>
			<td style="height: 21" colspan="3"><strong>Date
			d'entr&eacute;e : </strong></td>
			<td colspan="2"><label> <input type="text" name="mdp1"
				disabled="disabled" value="${membre.dateEntree}" /></label></td>
		</tr>
		<tr>
			<td style="height: 29" colspan="3"><strong>Date de
			naissance :</strong></td>
			<td colspan="2"><label> <input type="text"
				name="dateNaissance" value="${membre.dateNaissance }" />
			<button type='button' name='show1' onclick="CL.show();"
><img
				src="images/button_calendar.gif" height="15" width="15" /></button>
			</label></td>
		</tr>

		<tr>

			<th style="width: 193" scope="row"></th>
			<td style="width: 228" id="calend"></td>
		</tr>
		<tr>
		
			<td style="height: 29" colspan="3"><strong>sexe :</strong></td>
			<td colspan="2">Homme : <INPUT type="radio" name="sexe" ${checkM}
				value="M"> Femme : <INPUT type="radio" name="sexe" ${checkF} value="F">
			</td>


		</tr>

		<tr>
			<td style="height: 29" colspan="3"><strong>Adresse : </strong></td>
			<td colspan="2"><label> <input type="text"
				name="adresse" value="${membre.adresse }" /> </label></td>
		</tr>
		<tr>
			<td style="height: 29" colspan="3"><strong>Num&eacute;ro
			de t&eacute;l&eacute;phone : </strong></td>
			<td colspan="2"><label> <input type="text"
				name="telephone" value="${membre.numTel }" /> </label></td>
		</tr>
		<tr>
			<td style="height: 29" colspan="3"><strong>Profession :</strong></td>
			<td colspan="2"><label> <input type="text"
				name="profession" value="${membre.profession }" /> </label></td>
		</tr>
		<tr>
			<td style="height: 29" colspan="3"><strong>Mot de passe
			: </strong></td>
			<td colspan="2"><label> <input type="password"
				name="mdp1" disabled="disabled" /> </label></td>
		</tr>
		<tr>
			<td style="height: 29" colspan="3"><strong>Confirmer
			mot de passe : </strong></td>
			<td colspan="2"><label> <input type="text" name="mdp2"
				disabled="disabled" /> </label></td>
		</tr>

		<tr>
			<td>
			<h2><a href="#">Mes intérêts </a></h2>
			</td>
		</tr>

		<fsnet:interet var="interet">
			<tr>
				<td><input type="checkbox" name="interestSelected"
					value="${interet.id}" title="Sélectionner" /></td>
				<td width="33%">${interet.nomInteret}</td>
			</tr>
		</fsnet:interet>

		<tr>

			<td></td>
			<td colspan="7" rowspan="2" valign="top">
			<div style="text-align: right;"><label> <input
				type="submit" name="Submit" value="Valider" /> </label></div>
			</td>
		</tr>

		<tr>
			<td></td>
		</tr>
	</table>
	</form>

	</div>
</fsnet:login></div>
<jsp:include page="bas.jsp"></jsp:include>

</body>
</html>