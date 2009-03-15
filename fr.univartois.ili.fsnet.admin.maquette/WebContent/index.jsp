<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://admin.ili.fsnet.com/" prefix="admin"%>
<html>
<head>
<link rel="icon" type="image/png" href="images/favicon.ico" />
<meta http-equiv="content-type"
	content="application/xhtml+xml; charset=UTF-8" />
<meta http-equiv="refresh" content="300;index.jsp?accueil=current">
<meta name="author" content="Luka Cvrk - www.solucija.com" />
<meta name="description" content="Site Description" />
<meta name="keywords" content="site, keywords" />
<meta name="robots" content="index, follow" />
<link rel="stylesheet" type="text/css" media="screen"
	href="css/style.css" />
<title>FSNet</title>
<script language="JavaScript" src="admin.js">
	
</script>
</head>
<body onload="showMenu();">

<jsp:include page="header.jsp"></jsp:include>


<div class="wrap background"><jsp:include page="subHeader.jsp"></jsp:include>


<div id="left">
<h2><a href="index.jsp?accueil=current" title="Accueil">Accueil</a></h2>
<jsp:include page="date.jsp"></jsp:include></div>
<div id="tableauprincipal">
<table>

	<tr>
		<th class="entete" colspan="4" scope="col">
		<h2 title="Liste des membres en attente d'inscription"><img
			src="icons/page_01_fichiers/icon_from_jimmac_musichall_cz_223.png" /><span>
		En attente d'inscription</span></h2>
		</th>
	</tr>
	<tr>
		<td>
		<table id="listToDeploy">
			<tr class="champ">
				<th width="25%" scope="row">Nom</th>
				<th width="25%" scope="row">Prénom</th>
				<th width="25%" scope="row">Email</th>
				<th width="25%" scope="row">Etat</th>
			</tr>
			<admin:inscription var="inscription" etat="En attente d'inscription">

				<tr class="details">
					<td width="25%">${inscription.entite.nom}</td>
					<td width="25%">${inscription.entite.prenom}</td>
					<td width="25%">${inscription.entite.email}</td>
					<td width="25%">${inscription.etat}</td>
				</tr>
			</admin:inscription>

		</table>
		</td>
	</tr>


</table>
</div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>