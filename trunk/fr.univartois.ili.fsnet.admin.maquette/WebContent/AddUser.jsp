<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://admin.ili.fsnet.com/" prefix="admin"%>
<html>
<head>
<link rel="icon" type="image/png" href="images/favicon.ico" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
<body onload="showMenu();hide('listToDeploy');">

<jsp:include page="header.jsp"></jsp:include>
<div class="wrap background"><jsp:include page="subHeader.jsp"></jsp:include>

<div id="left">
<h2><a href="AddUser.jsp?user=current">Ajout de membre </a></h2>
<jsp:include page="date.jsp"></jsp:include>
</div>
<div id="tableauprincipal">
<table width="100%">
	<tr>
		<td>

		<form id="RemoveUser" method="post" action="RemoveUser">
		<table>
			<tr>
				<th colspan="4" scope="col">
				<div align="center"><a id="deployButton" href="#" title="Déployer la liste" onclick="deploy('deployButton','listToDeploy','userSelected');">[+]</a> Liste des membres</div>
				</th>
			</tr>
			<tr>
			<td>
			<table id="listToDeploy">
			<tr>
				<th></th>
				<th width="33%" scope="row">Nom</th>
				<th width="33%" scope="row">Prénom</th>
				<th width="33%" scope="row">Email</th>
			</tr>
			<admin:entite var="entite">

				<tr>
					<td><input type="checkbox" name="userSelected"
						value="${entite.id}" onclick="showHideButton('removeButton','userSelected');" /></td>
					<td width="33%">${entite.nom}</td>
					<td width="33%">${entite.prenom}</td>
					<td width="33%">${entite.email}</td>
				</tr>
			</admin:entite>
			
			</table>
			</td>
			</tr>
		</table>
		<label id="removeButton"><input
			onclick="if (!confirm('Etes-vous sûr de vouloir supprimer?')) return false;"
			type="submit" value="Supprimer" title="Supprimer" /></label></form>
		<form id="AddUser" method="post" action="AddUser">
		<table width="100%">
			<tr>
				<th colspan="2" scope="col">
				<div align="center">Ajouter un membre</div>
				</th>
			</tr>

			<tr>
				<th width="33%" scope="row">Nom</th>
				<td width="67%"><label> <input type="text" name="Nom"
					title="Nom" /> </label></td>
			</tr>
			<tr>
				<th scope="row">Prénom</th>
				<td width="67%"><label> <input type="text"
					name="Prenom" title="Prénom" /> </label></td>
			</tr>
			<tr>
				<th scope="row">Email</th>
				<td width="67%"><label> <input type="text" name="Email"
					title="Email" /> </label></td>
			</tr>
		</table>
		<label class="button"> <input type="submit" name="Submit"
			value="Enregistrer" title="Enregistrer" /> </label></form>
		</td>

	</tr>

</table>
</div>

<jsp:include page="CommunityBox.jsp"></jsp:include></div>


<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>