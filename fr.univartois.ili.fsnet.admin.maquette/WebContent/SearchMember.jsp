<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://admin.ili.fsnet.com/" prefix="admin"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="icon" type="image/png" href="images/favicon.ico" />
<meta http-equiv="content-type"
	content="application/xhtml+xml; charset=UTF-8" />
<meta http-equiv="refresh" content="300;SearchMember.jsp?user=current">
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
<body onload="showMenu();${param.recherche}('rechercheVide');">
<jsp:include page="header.jsp"></jsp:include>
<div class="wrap background">
<jsp:include page="subHeader.jsp"></jsp:include>

<div id="left">
<h2><a href="SearchMember.jsp?user=current&recherche=hide" title="Rechercher un membre">Rechercher un membre</a></h2>
<jsp:include page="date.jsp"></jsp:include>
</div>
<div id="tableauprincipal">
<table style="width: 90%">
	<tr>
		<td height="2"></td>
		<td></td>
		<td></td>
	</tr>

	<tr>
		
		<td style="height: 38"></td>
	</tr>
	<tr>
		<td></td>
		<td style="height: 2"></td>
	</tr>

	<tr>
		<td valign="top">
		<form id="form1" method="post" action="SearchMember">

		<div>
		<label for="searchText">Recherche par :</label> 
		<select name="selectRecherche" id="selectRecherche">
		<option selected="selected" value="nom">Nom</option>
		<option value="prenom">Prénom</option>
		<option value="dateEntree">Date d'entrée</option>
		</select>
		 <input type="text" id="searchText"	name="searchText" size="24" />
		
		<input type="submit" name="Submit" value="Rechercher" /></div>

		</form>
		<form id="RemoveUser" method="post" action="RemoveUser">
		<table id="listToDeploy">
		
					<tr class="champ" id="enteteRecherche">
						<th>Supprimer<input id="allUsers" type="checkbox"
							name="allUsers" title="Tout supprimer"
							onclick="selectAll('allUsers','userSelected');showHideButton('removeButton','userSelected');" /></th>
						<th width="20%" scope="row">Nom</th>
						<th width="20%" scope="row">Prénom</th>
						<th width="20%" scope="row">Email</th>
						<th width="20%" scope="row">Détails</th>
						<th width="20%" scope="row">&Eacute;tat</th>
					</tr>
					
					
			
					<admin:inscription filtre="${filtre}" var="inscription" parametre="${parametre}"  >
						<tr>
							<td><input type="checkbox" name="userSelected"
								value="${inscription.entite.id}" title="Vous serez redirigé vers la page des membres"
								onclick="showHideButton('removeButton','userSelected');" /></td>
							<td width="20%">${inscription.entite.nom}</td>
							<td width="20%">${inscription.entite.prenom}</td>
							<td width="20%">${inscription.entite.email}</td>
							<td width="20%"><a href="#"
								onclick="recupPage('MemberDetails.jsp','ent','${inscription.entite.id}','side');"
								title="Cliquez pour afficher les détails de ce membre">Détails</a></td>
							<td width="20%">${inscription.etat}</td>
						</tr>					
					</admin:inscription>
					<c:if test="${vide ne 'nonVide'}">
					<p align="center" id="rechercheVide">Aucun résultat ne correspond à votre recherche !!</p>
					</c:if>
										
				</table>
				<label id="removeButton"><input
			onclick="if (!confirm('Etes-vous sûr de vouloir supprimer?')) return false;"
			type="submit" value="Supprimer" title="Supprimer" /></label></form>
		
</td>
</tr>
</table>
</div>
<div id="side"></div>
</div>

<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>