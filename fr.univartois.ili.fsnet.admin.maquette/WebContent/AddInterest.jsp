<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://admin.ili.fsnet.com/" prefix="admin"%>
<html>
<head>
<link rel="icon" type="image/png" href="images/favicon.ico" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
<div class="wrap background">
<div id="search">
<form action="">
<fieldset><input type="text" class="field" value="Mot clé" />
<input type="submit" class="button" value="" /></fieldset>
</form>
</div>
<div id="menu">
<dl>
	<dt><a href="BureauAdmin.html" title="Retour à l'accueil">Accueil</a></dt>
</dl>

<dl>
	<dt onmouseover="show('smenu1');" onmouseout="show();"><a href="#">Membres</a></dt>
	<dd id="smenu1">
	<ul>
		<li onmouseover="show('smenu1');" onmouseout="show();"><a
			href="AddUser.jsp">Ajouter un membre</a></li>
		<li onmouseover="show('smenu1');" onmouseout="show();"><a
			href="SearchMember.jsp">Rechercher un membre</a></li>
	</ul>
	</dd>
</dl>
<dl>
	<dt onmouseover="show('smenu2');" onmouseout="show();"><a
		class="current" href="#">Intérêts</a></dt>
	<dd id="smenu2">
	<ul>
		<li onmouseover="show('smenu2');" onmouseout="show();"><a
			href="AddInterest.jsp">Ajouter des intérêts</a></li>
	</ul>
	</dd>
</dl>
<dl>
	<dt><a href="#">Communautés</a></dt>
</dl>
<dl>
	<dt><a href="#">Interactions</a></dt>
</dl>
<dl>
	<dt><a href="#">Demande Insc (3)</a></dt>
</dl>
<dl>
	<dt><a href="#">Messagerie (3)</a></dt>
</dl>
<dl>
	<dt><a href="#">Rapport d'activités</a></dt>
</dl>
</div>

<div id="logo">
<h1><a href="BureauAdmin.html">FSNet<br />
</a></h1>
<h2 class="slogan">Réseau social</h2>
<h2 class="slogan">Administration</h2>
</div>

<div id="features">
<ul id="feature_menu">
	<li><a class="current" href="BureauAdmin.html">Actualité</a></li>
	<li><a href="#">AAAAAAA</a></li>
	<li><a href="#">BBBBBBBBB</a></li>
</ul>

<div id="feature"><img src="images/feature_img.gif" alt="Featured" />
<p>Une nouvelle communauté vient d'être créée.</p>
<p><a class="more" href="#">&not;Detail</a></p>
</div>
</div>

<div class="clear"></div>

<div id="left">
<h2><a href="AddInterest.jsp">Ajout d'interets </a></h2>
<p class="date">Date<br />
JJ-MM-AA</p>
</div>
<div id="tableauprincipal">
<table width="100%">
	<tr>
		<td height="2"></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>

	<tr>

		<td>
		<table>
			<tr>
				<th scope="col">
				<div align="center">Liste des interêts</div>
				</th>
			</tr>

			<admin:interet var="interet">
				<tr>
					<td width="33%">${interet.nomInteret}</td>
				</tr>
			</admin:interet>

		</table>
		<form id="AddInterest" method="post" action="AddInterest">
		<table width="100%" border="0">
			<tr>
				<th colspan="2" scope="col">
				<div align="center">Ajouter un interêt</div>
				</th>
			</tr>
			<tr>

				<th width="15%" scope="row">Intitulé</th>
				<td colspan="2" width="85%"><label> <input type="text"
					name="nom" /> </label></td>
			</tr>

			<tr>
				<td></td>
				<td colspan="2" id="interests"></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="button" onclick="addInterest()"
					value="Ajouter" /></td>
			</tr>


			<tr>
				<th scope="row">&nbsp;</th>
				<td><label>
				<div align="right"><input type="submit" name="Submit"
					value="Enregistrer" accesskey="Enregistrer" /></div>
				</label></td>
			</tr>
		</table>
		</form>
		</td>
		<td height="33" valign="top">&nbsp;</td>
		<td></td>
	</tr>
	
</table>
</div>

<div id="side">
<div class="boxtop"></div>
<div class="box">
<h3>Mes communaut&eacute;s</h3>
<a href="#"> <span class="item"> <span class="sidedate">JEE<br />
&nbsp;&nbsp;&nbsp;&nbsp;</span> <strong>Nouveauté J2EE </strong><br />
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


<div id="promo">
<center>
<div class="wrap">FSnet licence</div>
</center>
</div>

</body>
</html>