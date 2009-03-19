<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://admin.ili.fsnet.com/" prefix="admin"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div id="logo">
<h1><a href="index.jsp?accueil=current" title="Accueil">FSNet<br />
</a></h1>
<h2 class="slogan">Réseau social</h2>
<h2 class="slogan">Administration</h2>
</div>

<div id="features">
<ul id="feature_menu">
	<li><a class="current" href="#">Actualité</a></li>
</ul>

<div id="feature"><img src="images/feature_img.gif" alt="Featured" />
<h3>Les derniers inscrits</h3>
<ul>
	<admin:inscription var="inscription" etat="Inscrit">
		<li><a href="#"
			onclick="recupPage('MemberDetails.jsp','ent','${inscription.entite.id}','side');show('side');"
			title="Cliquez pour afficher les détails de ce membre">${inscription.entite.nom}
		${inscription.entite.prenom}</a></li>
	</admin:inscription>
</ul>
</div>
</div>

<div class="clear"></div>
</body>
</html>