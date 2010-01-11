<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://admin.ili.fsnet.com/" prefix="admin"%>
<html>
<head>
<link rel="icon" type="image/png" href="images/favicon.ico" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="refresh" content="300;RecupInfo">
<meta name="author" content="Luka Cvrk - www.solucija.com" />
<meta name="description" content="Site Description" />
<meta name="keywords" content="site, keywords" />
<meta name="robots" content="index, follow" />
<link rel="stylesheet" type="text/css" media="screen"
	href="css/style.css" />
<title>FSNET - Rapport d'activité</title>
<script language="JavaScript" src="admin.js">
</script>
<script type="text/javascript">
</script>
</head>
<body onload="showMenu();">
<jsp:include page="header.jsp"></jsp:include>
<div class="wrap background">
<jsp:include page="subHeader.jsp"></jsp:include>

<div id="left">
<h2><a href="RecupInfo" title="Rapport d'activités">Rapport d'activités</a></h2>
<jsp:include page="date.jsp"></jsp:include>
</div>

<div id="tableauprincipal">
<table>
	<tr>
		<td>
			Nombre de personnes inscrites
		</td>
		<td>
		 	${nbInscrit}
		</td>
	</tr>
	<tr>
		<td>
			Nombre total d'annonces 
		</td>
		<td>
			${nbAnnoncesTot}
		</td>
	</tr>
	<tr>
		<td>
			Nombre d'annonces en ligne 
		</td>
		<td>
			${nbAnnoncesPub}
		</td>
	</tr>
	<tr>
		<td>
			Nombre total de manifestations 
		</td>
		<td>
			${nbEvenementTot}
		</td>
	</tr>
	<tr>
		<td>
			Nombre de manifestations en ligne 
		</td>
		<td>
			${nbEvenementPub}
		</td>
	</tr>
		<tr>
		<td>
			Nombre total de hubs/forums
		</td>
		<td>
			${nbHubTot }
		</td>
	</tr>
	<tr>
	<td><strong>Liste des hubs</strong></td>
	</tr>
	<tr>
		<td>

			<admin:hub var="hub">
 				Pour le hub " ${hub.nomCommunaute } ",  il y a ${nbTopics } topic(s) :<br/>
 				<admin:topic var="topic" hub="${hub}">
	 				-- Topic " ${topic.sujet } " contenant ${nbMessages } message(s)<br/>
 				</admin:topic>
 				<br>
			</admin:hub>
		</td>
	</tr>
</table>

</div>
<div id="side"></div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>