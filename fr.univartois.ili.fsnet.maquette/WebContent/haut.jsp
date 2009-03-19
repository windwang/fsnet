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
<div id="menu">
<dl>
	<dt><a class="${param.accueil}" href="index.jsp?accueil=current"
		title="Retour à l'accueil">Accueil</a></dt>
</dl>
<dl>
	<dt><a class="${param.profil}" href="profil.jsp?profil=current">Profil</a></dt>
</dl>
<dl>
	<dt onmouseover="showMenu('smenu1');" onmouseout="showMenu();"><a
		class="${param.info}" href="#">Information</a></dt>
	<dd id="smenu1">
	<ul">
		<li onmouseover="showMenu('smenu1');" onmouseout="showMenu();"><a
			href="AddAnnonce?idChoisi=0&info=current">Annonces</a></li>
		<li onmouseover="showMenu('smenu1');" onmouseout="showMenu();"><a
			href="toutEvenement.jsp?info=current">Evénements</a></li>
	</ul>
	</dd>
</dl>
<dl>
	<dt><a class="${param.hubs}" href="hub.jsp?hubs=current">Hubs</a></dt>
</dl>




</div>
<fsnet:login var="membre" idLogin="${entite.id}">
	Bienvenue ${civilite } ${membre.nom} ${membre.prenom} 
</fsnet:login>

<p> <a href="logout">Se déconnecter </a> </p>
<div id="logo">

<h1><a href="index.jsp?accueil=current">FSNet<br />
</a></h1>
<h2 id="slogan">Réseau social</h2>
<fsnet:dateJour var="dateJour">
	<p class="date">Date<br />
	${dateJour }</p>
</fsnet:dateJour></div>

<ul id="feature_menu">
	<li onclick="showMenu2('ssmenu1');" class="${param.Actu}"><a
		href="#" id="ssmenu1i">Actualité</a></li>
	<li onclick="showMenu2('ssmenu2');" class="${param.Actu}"><a
		href="#" id="ssmenu2i">Annonces</a></li>
	<li onclick="showMenu2('ssmenu3');" class="${param.Actu}"><a
		href="#" id="ssmenu3i">&#201;v&#233;nements</a></li>
</ul>


<div id="feature"><img src="images/feature_img.gif" alt="Featured" />


<div id="ssmenu1">
<h3><a href="AddAnnonce?idChoisi=0">Dernières annonces</a></h3>
<fsnet:annonce var="monAnnonce" nbAnnonce="2">

	<p><a href="AddAnnonce?idChoisi=${monAnnonce.id}">${monAnnonce.nom}</a>
	${monAnnonce.dateAnnonce}
	</p>

</fsnet:annonce>
<h3>Derniers &#201;v&#233;nements</h3>
<fsnet:manifestation var="maManif" nbEven="2">
	<a href="AddEvenement?id=${maManif.id}">${maManif.nom}</a>
    ${maManif.dateManif}<br />

</fsnet:manifestation></div>

<div id="ssmenu2" style="display: none">
<h3><a href="AddAnnonce?idChoisi=0">Dernières annonces</a></h3>
<fsnet:annonce var="monAnnonce" nbAnnonce="4">

	<p><a href="AddAnnonce?idChoisi=${monAnnonce.id}">${monAnnonce.nom}</a>
	<a href="AddAnnonce?idChoisi=${monAnnonce.id}">${monAnnonce.dateAnnonce}</a>
	</p>

</fsnet:annonce></div>
<div id="ssmenu3" style="display: none"><a href="top"></a>
<h3>Dernières &#233;v&#233;nements</h3>
<fsnet:manifestation var="maManif" nbEven="4">

	<p><a href="AddEvenement?id=${maManif.id}">${maManif.nom} </a>${maManif.dateManif}<br /> </p>
</fsnet:manifestation></div>
</div>


<div class="clear"></div>

</body>
</html>