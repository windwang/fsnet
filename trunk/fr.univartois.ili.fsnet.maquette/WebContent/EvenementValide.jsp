<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://iliforum.ili.fsnet.com/" prefix="fsnet"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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
<script language="JavaScript" src="maquette.js">
</script>
</head>
<body onload="showMenu();">


<div class="wrap background"><jsp:include page="hautValidation.jsp" />


<div id="left">
<h2><a href="#">Accueil</a></h2>

<table width="479">
	

	<tr>
		<td style="height: 38"></td>
		<td valign="top" style="bgcolor: #EDF3F8">
		<h2><img
			src="icons/page_01_fichiers/icon_from_jimmac_musichall_cz_223.png"
			style="width: 36; height: 32" alt="" /> <span class="Style8">Mon
		r&eacute;seau </span></h2>
		</td>
	</tr>
	<tr>
		<td style="height: 20"></td>
		<td>&nbsp;</td>
	</tr>

	<tr>
		<td style="height: 56"></td>
		<td valign="top">
		<ul>
			<li><a href="hub.jsp">Créer un Hub</a></li>
		</ul>
		</td>
	</tr>
	<tr>
		<td style="height: 17"></td>
		<td></td>
	</tr>
	<tr>
		<td style="height: 40"></td>
		<td valign="top" style="bgcolor: #EDF3F8">
		<h2><span class="Style8"><img
			src="icons/page_01_fichiers/icon_from_jimmac_musichall_cz_073.png"
			style="width: 34; height: 29" alt="" /> Int&eacute;ractions </span></h2>
		</td>
	</tr>
	<tr>
		<td style="height: 20"></td>
		<td>&nbsp;</td>
	</tr>

	<tr>
		<td style="height: 110"></td>
		<td valign="top">
		<ul>
			<li><a href="publierannonce.jsp">Créer une annonce</a></li>
			<li><a href="annonces.jsp">Accéder aux annonces</a></li>
			<li><a href="creerevenement.jsp">Créer un événement</a></li>
			<li><a href="toutEvenement.jsp">Acceder aux événements</a></li>
		</ul>
		</td>
	</tr>
	<tr>
		<td style="height: 88"></td>
		<td>&nbsp;</td>
	</tr>
</table>
<!-- 
<table width="479">
	<tr>
		<td style="width: 6; height: 33"></td>
		<td style="width: 450; valign: top; bgcolor: #EDF3F8">
		<h2 class="Style8"><img
			src="icon_from_jimmac_musichall_cz_270.png"
			style="width: 32; height: 27" alt="" /> Ma messagerie</h2>
		</td>
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
	</tr>
	<tr>
		<td style="height: 15"></td>
		<td></td>
	</tr>
	<tr>
		<td style="height: 38"></td>
		<td valign="top" style="bgcolor: #EDF3F8">
		<h2><img
			src="icons/page_01_fichiers/icon_from_jimmac_musichall_cz_223.png"
			style="width: 36; height: 32" alt="" /> <span class="Style8">Mon
		r&eacute;seau </span></h2>
		</td>
	</tr>
	<tr>
		<td style="height: 20"></td>
		<td>&nbsp;</td>
	</tr>

	<tr>
		<td style="height: 56"></td>
		<td valign="top">
		<ul>
			<li><a href="#">Communauté 1</a></li>
			<li><a href="#">Communauté 2</a></li>
			<li><a href="#">Communauté 3</a></li>
		</ul>
		</td>
	</tr>
	<tr>
		<td style="height: 17"></td>
		<td></td>
	</tr>
	<tr>
		<td style="height: 40"></td>
		<td valign="top" style="bgcolor: #EDF3F8">
		<h2><span class="Style8"><img
			src="icons/page_01_fichiers/icon_from_jimmac_musichall_cz_073.png"
			style="width: 34; height: 29" alt="" /> Int&eacute;ractions </span></h2>
		</td>
	</tr>
	<tr>
		<td style="height: 20"></td>
		<td>&nbsp;</td>
	</tr>

	<tr>
		<td style="height: 110"></td>
		<td valign="top">
		<ul>
			<li><a href="CreerCommun.jsp">Créer communauté</a></li>
			<li><a href="#">S'inscrire à une communauté</a></li>
			<li><a href="#">Gérer communauté</a></li>
			<li><a href="creerevenement.jsp">Créer événement</a></li>
		</ul>
		</td>
	</tr>
	<tr>
		<td style="height: 88"></td>
		<td>&nbsp;</td>
	</tr>
</table>
-->
</div>

<jsp:include page="side.jsp" />
</div>


<jsp:include page="bas.jsp" />

</body>
</html>
