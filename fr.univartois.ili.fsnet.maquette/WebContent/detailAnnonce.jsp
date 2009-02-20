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
<style type="text/css">
<!--
.Style1 {
	font-size: 1em;
	font-weight: bold;
}
-->
</style>
<link href="vente voiture" />
</head>
<body>
<div class="wrap background">
<div id="search">
<form action="">
<fieldset><input type="text" class="field" value="Mot clÃ©" />
<input type="submit" class="button" value="" /></fieldset>
</form>
</div>
<ul id="menu">
	<li><a class="current" href="index.html">Accueil</a></li>
	<li><a href="profil.jsp">Mon Profil</a></li>
	<li><a href="hub.jsp">Mon r&egrave;seau</a></li>
	<li><a href="#">Interaction</a></li>
	<li><a href="#">Messagerie</a></li>
	<li><a href="annonces.jsp">Annonces</a></li>

</ul>
Bienvenue Mr XXXXXXXXX
<div id="logo">
<h1><a href="http://www.google.com">FSNet<br />
</a></h1>
<h2 id="slogan">Réseau social</h2>
</div>

<ul id="feature_menu">
	<li><a class="current" href="">Actualité</a></li>
	<li><a href="#">AAAAAAA</a></li>
	<li><a href="#">BBBBBBBBB</a></li>
</ul>

<div id="feature"><img src="images/feature_img.gif" alt="Featured" />
<p>Une nouvelle communauté vient d'être crée.</p>
<p><a class="more" href="#">&not;Detail</a></p>
</div>

<div class="clear"></div>

<div id="left">
<h2><a href="#">Annonces</a></h2>

<p>&nbsp;</p>
<table width="433">
	<fsnet:annonce var="monAnnonce" idChoisi="${idChoisi}">
		<tr> 
			<th>titre : ${monAnnonce.nom}</th>
			
		</tr>
		<tr>
		<th>contenu : ${monAnnonce.contenu}</th>
		</tr>
		<th style="width: 252" scope="col">
		<div style="text-align: center"><a href="annonces.jsp">retour aux annonces</a></div>
		</th>
	</fsnet:annonce>

	
</table>
<p>&nbsp;</p>
<hr />
<p class="Style1">&nbsp;</p>
<p class="date">Date<br />
JJ-MM-AA</p>
<p class="subtitle"></p>
</div>

<div id="side">
<div class="boxtop"></div>
<div class="box">
<h3>Mes communaut&eacute;s</h3>
<a href="#"> <span class="item"> <span class="sidedate">JEE<br />
&nbsp;&nbsp;&nbsp;&nbsp;</span> <strong>NouveautÃ© J2EE </strong><br />
Detail</span> </a> <a href="#"> <span class="item"> <span
	class="sidedate">JAVA&nbsp;&nbsp;&nbsp;&nbsp;</span> <strong>Eclipse
... </strong><br />
Detail </span> </a> <a href="#"> <span class="item last"> <span
	class="sidedate">JSP<br />
</span> <strong>Nouveauté JSP </strong><br />
Detail</span> </a></div>
<div class="boxbottom"></div>
</div>
<p id="ad">&nbsp;</p>
</div>

<div class="promo" style="text-align: center">
<div class="wrap">FSnet licence</div>
</div>

</body>
</html>