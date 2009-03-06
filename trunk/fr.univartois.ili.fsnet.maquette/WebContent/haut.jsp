<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://iliforum.ili.fsnet.com/" prefix="fsnet"%>
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


</head>
<body>

<div id="search">
<form action="">
<fieldset><input type="text" class="field" value="Mot clé" />
<input type="submit" class="button" value="" /></fieldset>
</form>
</div>
<ul id="menu">
	<li><a href="index.jsp">Accueil</a></li>
	<li><a href="profil.jsp">Profil</a></li>
	<li><a href="hub.jsp">Hubs</a></li>
	<li><a href="#">Interaction</a></li>
	<li><a href="#">Messagerie</a></li>
	<li><a href="AddAnnonce?idChoisi=0">Annonces</a></li>

</ul>

<fsnet:login var="membre" idLogin="${idLogin}">
	Bienvenue M. ${membre.nom} ${membre.prenom} 
	</fsnet:login>

<div id="logo">
<h1><a href="http://www.google.com">FSNet<br />
</a></h1>
<h2 id="slogan">Réseau social</h2>
<fsnet:dateJour var="dateJour">
	<p class="date">Date<br />
	${dateJour }</p>
</fsnet:dateJour></div>

<ul id="feature_menu">
	<li><a class="current" href="">Actualité</a></li>
	<li><a href="#">Annonces</a></li>
	<li><a href="#">Evénements</a></li>
</ul>

<div id="feature"><img src="images/feature_img.gif" alt="Featured" />



<h3><a href="AddAnnonce?idChoisi=0">Dernières annonces</a> </h3>
	<fsnet:annonce var="monAnnonce" nbAnnonce="2">
	
<p>
			<a href="AddAnnonce?idChoisi=${monAnnonce.id}">${monAnnonce.nom}</a>
			<a href="AddAnnonce?idChoisi=${monAnnonce.id}">${monAnnonce.dateAnnonce}</a>
	</p>
		
		</fsnet:annonce>
	<h3>Derniers événements</h3>
	<fsnet:manifestation var="maManif" nbAnnonce="1">
	<a href="#">${maManif.nom}</a>
	
	</fsnet:manifestation>
</div>


<div class="clear"></div>

</body>
</html>