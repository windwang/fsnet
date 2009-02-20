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
<script type="text/JavaScript">
	function MM_jumpMenu(targ, selObj, restore) { //v3.0
		eval(targ + ".location='" + selObj.options[selObj.selectedIndex].value
				+ "'");
		if (restore)
			selObj.selectedIndex = 0;
	}
	//
</script>
<style type="text/css">
<!--
.Style1 {
	color: #FF6600
}
-->
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="wrap background">
<div id="search">
<form action="">
<fieldset><input type="text" class="field" value="Mot clé" />
<input type="submit" class="button" value="" /></fieldset>
</form>
</div>
<ul id="menu">
	<li><a  href="index.jsp">Accueil</a></li>
	<li><a href="profil.jsp">Profil</a></li>
	<li><a class="current" href="hub.jsp">Hubs</a></li>
	<li><a href="#">Interaction</a></li>
	<li><a href="#">Messagerie</a></li>
	<li><a href="annonces.jsp">Annonces</a></li>

</ul>
<fsnet:login var="membre" idLogin="${idLogin}">
	Bienvenue M. ${membre.nom} ${membre.prenom} 
</fsnet:login>
<div id="logo">
<h1><a href="http://www.google.com">FSNet<br />
</a></h1>
<h2 id="slogan">Réseau social</h2>
</div>

<ul id="feature_menu">
	<li><a class="current" href="">actualité</a></li>
	<li><a href="#">aaaaaaa</a></li>
	<li><a href="#">BBBBBBBBB</a></li>
</ul>

<div id="feature"><img src="images/feature_img.gif" alt="Featured" />
<p>Une nouvelle communauté vient d'être créée</p>
<p><a class="more" href="#">&not;Detail</a></p>
</div>

<div class="clear"></div>
<a href="hub.jsp">FSNet</a> - <a href="GotoTopic?idHub=${monHub.id}">${monHub.nomCommunaute}</a>
<table style="padding: 6; width: 100%; align: center" border="1">
	<thead>
		<tr style="text-align: center">
			<td class="thead">
			<div style="text-align: center">Topic</div>
			</td>
			<td class="thead">Dernier message</td>
			<td class="thead">Messages</td>
		</tr>
	</thead>
	<fsnet:topic var="topic" hub="${monHub}">
		<tbody>
			<tr>
				<td class="alt1active" id="h${topic.id}" style="text-align: left">
				<table style="padding: 0; border: 0">
					<tbody>
						<tr>
							<td><img
								src="icons/page_01_fichiers/icon_from_jimmac_musichall_cz_272.png"
								alt="" style="width: 48; height: 48; border: 0"
								id="forum_statusicon_${topic.id}" /></td>
							<td><img alt=""
								src="Général Java - Forum des développeurs_fichiers/clear.gif"
								style="width: 9; border: 0; height: 1" /></td>
							<td>
							<div><a href="GotoMessage?idTopic=${topic.id}"> <strong>${topic.sujet}</strong></a></div>
							</td>
						</tr>
					</tbody>
				</table>
				</td>
				<td class="alt2"></td>
				<td class="alt2" />
			</tr>
		</tbody>
	</fsnet:topic>
	<tfoot>
		<tr>
			<td colspan="4">
			<form action="CreateTopic">
			<fieldset><legend> Creer Topic </legend>
			<p><label> Nom : </label> <input type="text" name="nomTopic"
				size="80%" /></p>
			<p><label> Message : </label></p>
			<p><textarea name="contenuMessage"
				cols="100" rows="5"></textarea> <input type="submit"
				name="creertopic" value="creer" /></p>
			</fieldset>
			</form>
			</td>
		</tr>
	</tfoot>


</table>
 

<p id="ad">&nbsp;</p>
</div>

<div id="promo" style="text-align: center">
<div class="wrap">FSnet licence</div>
</div>
</body>
</html>