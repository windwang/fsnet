<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://admin.ili.fsnet.com/" prefix="admin"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="icon" type="image/png" href="images/favicon.ico" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="refresh"
	content="300;AddInterest.jsp?interet=current&showHide=hide&deploy=[%2B]&titleDeploy=D%E9ployer la liste">
<meta name="author" content="Luka Cvrk - www.solucija.com" />
<meta name="description" content="Site Description" />
<meta name="keywords" content="site, keywords" />
<meta name="robots" content="index, follow" />
<link rel="stylesheet" type="text/css" media="screen"
	href="css/style.css" />
<title>FSNet</title>
<script language="JavaScript" src="admin.js">
</script>
<script type="text/javascript">
</script>
</head>
<body onload="showMenu();${param.showHide}('listToDeploy');${param.recherche}('rechercheVide')">
<jsp:include page="header.jsp"></jsp:include>


<div class="wrap background"><jsp:include page="subHeader.jsp"></jsp:include>

<div id="left">
<h2><a
	href="SearchInterest.jsp?interet=current&recherche=hide"
	title="Recherche d'intérêts">Recherche d'int&eacute;r&ecirc;ts </a></h2>
<jsp:include page="date.jsp"></jsp:include></div>
<div id="tableauprincipal">
<table width="100%" align="left">

	<tr>

	

		<td>
		
		<table align="left">
			
				<th>
				
				</th>
			
			
			<tr>
			<td>
			<form id="rechercheInteret" method="post" action="SearchInterest?redirection=SearchInterest.jsp">
	<label for="searchText">Recherche : </label><input name="searchText" id="searchText" type="text" />
	<input type="submit" name="rechercherInteret" value="Rechercher" />
		</form></td></tr>
		<form id="RemoveInterest" method="post" action="RemoveInterest?redirection=SearchInterest.jsp">
		<tr>
				<td>
				<table id="listToDeploy">
					<tr class="champ">
						<th>Supprimer<input id="allInterests" type="checkbox"
							name="allInterests" title="Tout supprimer"
							onclick="selectAll('allInterests','interestSelected');showHideButton('removeButton','interestSelected');" /></th>
						<th width="99%" scope="row">Intitulé</th>
					</tr>
					<admin:interet var="interet" parametre="${parametre}">
						<tr>
							<td><input type="checkbox" name="interestSelected"
								value="${interet.id}" title="Supprimer"
								onclick="showHideButton('removeButton','interestSelected');" /></td>
							<td width="99%" align="center">${interet.nomInteret}</td>
						</tr>
					</admin:interet>
					<c:if test="${vide ne 'nonVide'}">
					<p align="center" id="rechercheVide">Aucun résultat ne correspond à votre recherche !!</p>
					</c:if>
				</table>
				</td>
			</tr>

		</table>
		<label id="removeButton"><input
			onclick="if (!confirm('Etes-vous sûr de vouloir supprimer?')) return false;"
			type="submit" value="Supprimer" title="Supprimer" /></label>
			</form>
		</td>
	</tr>

</table>
</div>
<div id="side"></div>
</div>


<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>