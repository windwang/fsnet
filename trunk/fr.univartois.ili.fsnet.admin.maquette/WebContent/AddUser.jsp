<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://admin.ili.fsnet.com/" prefix="admin"%>
<html>
<head>
<link rel="icon" type="image/png" href="images/favicon.ico" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="refresh"
	content="300;AddUser.jsp?user=current&showHide=hide&deploy=[%2B]&titleDeploy=D%E9ployer la liste">
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
<body onload="showMenu();${param.showHide}('listToDeploy');">

<jsp:include page="header.jsp"></jsp:include>
<div class="wrap background"><jsp:include page="subHeader.jsp"></jsp:include>

<div id="left">
<h2><a
	href="AddUser.jsp?user=current&showHide=hide&deploy=[%2B]&titleDeploy=D%E9ployer la liste"
	title="Ajout de membre">Ajout de membre </a></h2>
<jsp:include page="date.jsp"></jsp:include></div>
<div id="tableauprincipal">
<table width="100%">
	<tr>
		<td>

		<form id="RemoveUser" method="post" action="RemoveUser">
		<table>
			<tr>
				<th class="entete" colspan="4" scope="col">
				<h2><img
					src="icons/page_01_fichiers/icon_from_jimmac_musichall_cz_223.png" /><span><a
					id="deployButton" href="#" title="${param.titleDeploy}"
					onclick="deploy('deployButton','listToDeploy','userSelected','allUsers');">${param.deploy}</a>
				Liste des membres</span></h2>
				</th>
			</tr>
			<tr>
				<td>
				<table id="listToDeploy">
					<tr class="champ">
						<th>Supprimer<input id="allUsers" type="checkbox"
							name="allUsers" title="Tout supprimer"
							onclick="selectAll('allUsers','userSelected');showHideButton('removeButton','userSelected');" /></th>
						<th width="20%" scope="row">Nom</th>
						<th width="20%" scope="row">Prénom</th>
						<th width="20%" scope="row">Email</th>
						<th width="20%" scope="row">Détails</th>
						<th width="20%" scope="row">Etat</th>
					</tr>
					<admin:inscription var="inscription">

						<tr>
							<td><input type="checkbox" name="userSelected"
								value="${inscription.entite.id}" title="Supprimer"
								onclick="showHideButton('removeButton','userSelected');" /></td>
							<td width="20%">${inscription.entite.nom}</td>
							<td width="20%">${inscription.entite.prenom}</td>
							<td width="20%">${inscription.entite.email}</td>
							<td width="20%"><a href="#" onclick="recupPage('MemberDetails.jsp','ent','${inscription.entite.id}','side');">Détails</a></td>
							<td width="20%">${inscription.etat}</td>
						</tr>
					</admin:inscription>

				</table>
				</td>
			</tr>
		</table>
		<label id="removeButton"><input
			onclick="if (!confirm('Etes-vous sûr de vouloir supprimer?')) return false;"
			type="submit" value="Supprimer" title="Supprimer" /></label></form>
		<form id="AddUser" method="post" action="AddUser">
		<table>
			<tr>
				<th class="entete"></th>
				<th class="entete" colspan="2" scope="col">
				<h2>Ajouter un membre</h2>
				</th>
			</tr>

			<tr class="champ">
				<th scope="row">Nom</th>
				<td><label> <input type="text" name="Nom" title="Nom" />
				</label></td>
			</tr>
			<tr class="champ">
				<th scope="row">Prénom</th>
				<td><label> <input type="text" name="Prenom"
					title="Prénom" /> </label></td>
			</tr>
			<tr class="champ">
				<th scope="row">Email</th>
				<td><label> <input type="text" name="Email"
					title="Email" /> </label></td>
			</tr>
		</table>
		<label class="button"> <input type="submit" name="Submit"
			value="Enregistrer" title="Enregistrer" /> </label></form>
		</td>

	</tr>

</table>
</div>
<div id="side"></div>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>