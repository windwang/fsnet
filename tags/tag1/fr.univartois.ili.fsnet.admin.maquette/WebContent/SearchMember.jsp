<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<script language="JavaScript" src="admin.js">
</script>
</head>
<body onload="show();">

<jsp:include page="header.jsp"></jsp:include>
<div class="wrap background">
<jsp:include page="subHeader.jsp"></jsp:include>

<div id="left">
<h2><a href="SearchMember.jsp?user=current">Rechercher un membre</a></h2>
<p class="date">Date<br />
JJ-MM-AA</p>
</div>
<div id="tableauprincipal">
<table style="width: 90%">
	<tr>
		<td height="2"></td>
		<td></td>
		<td></td>
	</tr>

	<tr>
		<td valign="top" style="background-color: #EDF3F8">
		<h2 class="Style8">Rechercher Membre(s)</h2>
		</td>
		<td style="height: 38"></td>
	</tr>
	<tr>
		<td></td>
		<td style="height: 2"></td>
	</tr>

	<tr>
		<td valign="top">
		<form id="form1" method="post" action="">

		<div style="text-align: right">Date d'entrée <input type="text"
			name="textfield" /> <br />
		<br />
		Nom : <input type="text" name="textfield2" /> <br />
		<br />
		Prénom : <input type="text" name="textfield3" /> <br />
		<br />
		<input type="submit" name="Submit" value="Rechercher" /></div>

		</form>
		</td>
		<td style="height: 38"></td>
	</tr>
	<tr>
		<td></td>
		<td style="height: 2"></td>
	</tr>
	<tr>
		<td valign="top" style="background-color: #EDF3F8FFFFF">
		<h2 class="Style8">R&eacute;sultat de la Recherche</h2>
		</td>
		<td style="height: 63"></td>
	</tr>
	<tr>
		<td>
		<div style="text-align: center"></div>
		</td>
		<td style="height: 2"></td>
	</tr>
	<tr>
		<td valign="top">
		<div style="text-align: center">
		<table style="width: 100%; border: 2">
			<tr style="background-color: #CCCCCC">
				<td style="width: 64">
				<h4>Nom</h4>
				</td>
				<td style="width: 84">
				<h4>Pr&eacute;nom</h4>
				</td>
				<td style="width: 106">
				<h4>Date d'entr&eacute;e</h4>
				</td>
				<td style="width: 112">
				<h4>Afficher D&eacute;tails</h4>
				</td>
				<td style="width: 98">
				<h4>Supprimer</h4>
				</td>
			</tr>
			<tr>
				<td>Caramba</td>
				<td>Simpson</td>
				<td>10/02/2009</td>
				<td><a href="#">Détails</a></td>
				<td><a href="#">Supprimer</a></td>
			</tr>
			<tr>
				<td>Speedy</td>
				<td>Gonzalez</td>
				<td>12/01/2009</td>
				<td><a href="#">Détails</a></td>
				<td><a href="#">Supprimer</a></td>
			</tr>
		</table>
		</div>
		</td>
		<td style="height: 40"></td>
	</tr>
</table>

</div>

<jsp:include page="CommunityBox.jsp"></jsp:include>
</div>

<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>