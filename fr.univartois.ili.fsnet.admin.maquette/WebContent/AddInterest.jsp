<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://admin.ili.fsnet.com/" prefix="admin"%>
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
<body onload="showMenu();${param.showHide}('listToDeploy');">

<jsp:include page="header.jsp"></jsp:include>


<div class="wrap background"><jsp:include page="subHeader.jsp"></jsp:include>

<div id="left">
<h2><a
	href="AddInterest.jsp?interet=current&showHide=hide&deploy=[%2B]&titleDeploy=D%E9ployer la liste"
	title="Ajout d'intérêts">Ajout d'int&eacute;r&ecirc;ts </a></h2>
<jsp:include page="date.jsp"></jsp:include></div>
<div id="tableauprincipal">
<table width="100%">

	<tr>

		<td>
		<form id="RemoveInterest" method="post" action="RemoveInterest">
		<table>
			<tr>
				<th class="entete" colspan="2" scope="col">
				<h2><img
					src="icons/page_01_fichiers/icon_from_jimmac_musichall_cz_073.png" /><span><a
					id="deployButton" href="#" title="${param.titleDeploy}"
					onclick="deploy('deployButton','listToDeploy','interestSelected','allInterests');">${param.deploy}</a>
				Liste des interêts</span></h2>
				</th>
			</tr>
			<tr>
				<td>
				<table id="listToDeploy">
					<tr class="champ">
						<th>Supprimer<input id="allInterests" type="checkbox"
							name="allInterests" title="Tout supprimer"
							onclick="selectAll('allInterests','interestSelected');showHideButton('removeButton','interestSelected');" /></th>
						<th width="99%" scope="row">Intitulé</th>
					</tr>
					<admin:interet var="interet">
						<tr>
							<td><input type="checkbox" name="interestSelected"
								value="${interet.id}" title="Supprimer"
								onclick="showHideButton('removeButton','interestSelected');" /></td>
							<td width="99%" align="center">${interet.nomInteret}</td>
						</tr>
					</admin:interet>
				</table>
				</td>
			</tr>

		</table>
		<label id="removeButton"><input
			onclick="if (!confirm('Etes-vous sûr de vouloir supprimer?')) return false;"
			type="submit" value="Supprimer" title="Supprimer" /></label></form>
		<form id="AddInterest" method="post" action="AddInterest">
		<table width="100%">
			<tr>
				<th class="entete"></th>
				<th class="entete" colspan="2" scope="col">
				<h2>Ajouter un intérêt</h2>
				</th>
			</tr>
			<tr>

				<th class="enteteIntitule" width="15%" scope="row">Intitulé</th>
				<td colspan="2" width="85%"><label> <input
					class="moreInteret" type="text" name="Intitule" title="Intitulé" />
				</label></td>
			</tr>

			<tr>
				<td></td>
				<td colspan="2" id="interests"></td>
			</tr>
			<tr>
				<td></td>
				<td><input class="addButton" name="inputAddInterest"
					id="inputAddInterest" type="button" onclick="addInterest()"
					value="Ajouter" /></td>
			</tr>


		</table>
		<label class="button"> <input type="submit" name="Submit"
			value="Enregistrer" title="Enregistrer" /> </label></form>
		</td>
	</tr>

</table>
</div>

</div>


<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>