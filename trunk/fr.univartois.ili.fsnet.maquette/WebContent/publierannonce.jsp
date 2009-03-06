<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://iliforum.ili.fsnet.com/" prefix="fsnet"%>
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

<!-- FICHIER DE STYLE DU CALENDRIER //-->
<link rel='stylesheet' href="css/calendar.css" />
<!-- FICHIER DE SCRIPT DU CALENDRIER //-->
<script type="text/javascript" src="maquette.js"></script>
<script type="text/javascript">
<!--
/******************************/
/**self.defaultStatus = "GnooCalendar 1.4";
/******************************/
/* 
* instanciation de l'objet
*/
var CL = new GnooCalendar("CL", 0, 20 );

/******************************/
function init()
{
	CL.init("calend", document.forms["testform"].elements["dateFinAnnonce"]);
	

	
}
/******************************/
//-->
</script>
</head>
<body onload="showMenu();init();">
<div class="wrap background"><jsp:include page="haut.jsp"></jsp:include>

<a name="top"></a>

<div id="left">
<h2><a href="#">Annonces</a></h2>
<table width="445">
	<tr>
		<th style="width: 181" scope="col">Entrer votre texte</th>
		<th style="width: 252" scope="col">
		<div style="text-align: right">2000 caractères maximums</div>
		</th>
	</tr>
</table>
<p>&nbsp;</p>
<form name='testform' method="post" action="AddAnnonce" id="annonce"
	style='margin: 0px;'>

<table style="width: 633">
	<tr>
		<th style="width: 193" scope="row">Titre</th>
		<td style="width: 228">
		<div style="align: right" class="Style1"><label
			style="text-align: left"> <input name="titreAnnonce"
			type="text" size="50" value="${titre}" /> </label></div>
		</td>
	</tr>
	<tr>
		<th style="width: 193" scope="row">Contenu</th>
		<td style="width: 228"><textarea name="contenuAnnonce" cols="40"
			rows="10">${contenu}</textarea></td>

	</tr>
	<tr>
		<th style="width: 193" scope="row">Date de fin</th>
		<td><input type='text' name="dateFinAnnonce" value="${datefin}"
			size="8" /> <input type='button' name='show1' onclick='CL.show();'
			value='Afficher le calendrier' /></td>
	</tr>
	<tr>
		<th style="width: 193" scope="row"></th>
		<td style="width: 228" id="calend">&nbsp;</td>
	</tr>
	<tr>
		<th scope="row"></th>
		<td>
		<div style="text-align: right"><input name="submit"
			type="submit" value="Publier" /></div>
		</td>
	</tr>




</table>

</form>
<br />
<!-- <div id="calend">&nbsp;</div>--></div>
<jsp:include page="bas.jsp"></jsp:include>
</div>
</body>
</html>