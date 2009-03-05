<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://admin.ili.fsnet.com/" prefix="admin"%>
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
<body onload="showMenu();">

<jsp:include page="header.jsp"></jsp:include>


<div class="wrap background"><jsp:include page="subHeader.jsp"></jsp:include>


<div id="left">
<h2><a href="index.jsp?accueil=current">Accueil</a></h2>
<jsp:include page="date.jsp"></jsp:include>
</div>
<div id="tableauprincipal">
<table>
	<tr>
		<td style="height: 2"></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>

	<tr>

		<td style="height: 33"></td>
		<td style="background-color: #EDF3F8">
		<h2 class="Style8"><img
			src="icons/page_01_fichiers/icon_from_jimmac_musichall_cz_270.png"
			style="height: 27; width: 32" alt="" /> Ma messagerie</h2>
		</td>
		<td></td>
	</tr>

	<tr>
		<td style="height: 2"></td>

		<td></td>
	</tr>
	<tr>
		<td style="height: 38"></td>
		<td valign="top">
		<ul>
			<li><a href="#">slt c coucou</a></li>
			<li><a href="#">slt c kiki </a></li>
		</ul>
		</td>
		<td></td>
	</tr>
	<tr>
		<td style="height: 2"></td>
		<td></td>
		<td></td>
	</tr>

	<tr>
		<td style="height: 38"></td>
		<td valign="top" style="background-color: #EDF3F8">
		<h2><img
			src="icons/page_01_fichiers/icon_from_jimmac_musichall_cz_223.png"
			style="width: 36; height: 32" alt="" /> <span class="Style8">Lister
		membres </span></h2>
		</td>
		<td></td>
	</tr>
	<tr>
		<td style="height: 2"></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td style="height: 63"></td>
		<td valign="top">
		<div style="text-align: center"><a href="#"></a>
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
				<td>Bart</td>
				<td>Simpson</td>
				<td>10/02/2009</td>
				<td><a href="#">D&eacute;tails</a></td>
				<td><a href="#">Supprimer</a></td>
			</tr>
			<tr>
				<td>Speedy</td>
				<td>Gonzalez</td>
				<td>12/01/2009</td>
				<td><a href="#">D&eacute;tails</a></td>
				<td><a href="#">Supprimer</a></td>
			</tr>
		</table>
		</div>
		</td>
		<td></td>
	</tr>
	<tr>
		<td style="height: 2"></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td style="height: 40"></td>
		<td valign="top" style="bgcolor: #EDF3F8">
		<h2><span class="Style8"> <img
			src="icons/page_01_fichiers/icon_from_jimmac_musichall_cz_073.png"
			style="width: 34; height: 29" alt="" /> Lister communaut&eacute;s </span></h2>
		</td>
		<td></td>
	</tr>
	<tr>
		<td style="height: 2"></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td style="height: 56"></td>
		<td valign="top">
		<ul>
			<li><a href="#">Communaut&eacute; 1</a></li>
			<li><a href="#">Communaut&eacute; 2</a></li>
			<li><a href="#">Communaut&eacute; 3</a></li>
		</ul>
		</td>
		<td></td>
	</tr>
	<tr>
		<td style="height: 2"></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td style="height: 47"></td>
		<td valign="top" style="background-color: #EDF3F8">
		<h2><span class="Style8"><img
			src="icons/page_01_fichiers/icon_from_jimmac_musichall_cz_073.png"
			style="height: 29; width: 34" alt="" /> Interactions </span></h2>
		</td>
		<td></td>
	</tr>
	<tr>
		<td style="height: 2"></td>

		<td></td>
		<td></td>
	</tr>
	<tr>
		<td style="height: 38"></td>
		<td valign="top">
		<ul>
			<li><a href="#">Liste d'interaction &agrave; journaliser</a></li>
			<li><a href="#">Liste d'interaction à valider</a></li>
		</ul>
		</td>
		<td></td>
	</tr>
	<tr>
		<td style="height: 2"></td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td style="height: 50"></td>
		<!--  <td></td> -->
		<td valign="top" style="background-color: #EDF3F8">
		<h2><span class="Style8"><img
			src="icons/page_01_fichiers/icon_from_jimmac_musichall_cz_073.png"
			style="width: 34; height: 29" alt="" /></span> <span class="Style8">Demandes
		d'inscription en attente </span><a href="#">(3)</a></h2>
		</td>
		<td></td>
	</tr>
	<tr>
		<td style="height: 2"></td>
		<td></td>
		<td></td>
		<!-- <td></td> -->
	</tr>
	<tr>
		<td style="height: 68"></td>
		<!-- <td></td> -->
		<td valign="top">
		<ul>
			<li><a href="#">Demande 1</a></li>
			<li><a href="#">Demande 2</a></li>
			<li><a href="#">Demande 3</a></li>
		</ul>
		</td>
		<td></td>
	</tr>
</table>
</div>

<jsp:include page="CommunityBox.jsp"></jsp:include></div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>