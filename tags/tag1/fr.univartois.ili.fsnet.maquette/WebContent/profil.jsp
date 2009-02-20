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
<style type="text/css"></style>
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
	<li><a href="index.jsp">Accueil</a></li>
	<li><a class="current" href="profil.jsp">Profil</a></li>
	<li><a href="hub.jsp">Hubs</a></li>
	<li><a href="#">Interaction</a></li>
	<li><a href="#">Messagerie</a></li>
	<li><a href="annonces.jsp">Annonces</a></li>

</ul>
<fsnet:login var="membre" idLogin="${idLogin}">
	Bienvenue M. ${membre.nom} ${membre.prenom} 

<div id="logo">
<h1><a href="http://code.google.com/p/fsnet/">FSNet<br />
</a></h1>
<h2 id="slogan">Réseau social</h2>
</div>

<ul id="feature_menu">
	<li><a class="current" href="">Actualité</a></li>
	<li><a href="#">AAAAAAA</a></li>
	<li><a href="#">BBBBBBBBB</a></li>
</ul>

<div id="feature"><img src="images/feature_img.gif" alt="Featured" />
<p>Une nouvelle communauté vient d'être créée</p>
<p><a class="more" href="#">&not;Detail</a></p>
</div>

<div class="clear"></div>

<div id="left">
<h2><a href="#">Mon profil </a></h2>

<form action="CompleteProfil" method="post" id="completeProfil">
<table>
	<tr>
		<td style="height:19" colspan="3">
		<h4>&nbsp;</h4>
		</td>
		<td colspan="2">
		<h4>&nbsp;</h4>
		</td>
		<td style="width:4" colspan="3" rowspan="17">&nbsp;</td>
	</tr>
	<tr>
		<td style="height:2"></td>
		<td style="width:40"></td>
		<td style="width:304"></td>
		<td style="width:1"></td>
		<td style="width:451"></td>
	</tr>
	<tr>
		<td style="height:27" colspan="3" rowspan="5"><strong>Nom :</strong></td>
		<td colspan="2"></td>
	</tr>
	<tr>
		<td colspan="2"></td>
	</tr>
	<tr>
		<td colspan="2"></td>
	</tr>
	<tr>
		<td colspan="2"></td>
	</tr>

	<tr>
		<td style="height:27" colspan="2"><label> <input type="text"
			name="nom" value="${membre.nom }" disabled="disabled"/> </label></td>
	</tr>
	<tr>
		<td style="height:22" colspan="3"><strong>Pr&eacute;nom : </strong></td>
		<td colspan="2"> <label > <input type="text"
			name="prenom" value="${membre.prenom }" disabled="disabled"/> </label></td>
	</tr>
	<tr>
		<td style="height:29" colspan="3"><strong>E-mail : </strong></td>
		<td colspan="2"> <label > <input type="text"
			name="email" value="${membre.email }" /> </label></td>
	</tr>
	<tr>
		<td style="height:21" colspan="3"><strong>Date d'entr&eacute;e
		:</strong></td>
		<td colspan="2">28/12/2008</td>
	</tr>
	<tr>
		<td style="height:29" colspan="3"><strong>Date de naissance :</strong></td>
		<td colspan="2"> <label > <input type="text"
			name="dateNaissance" value="${membre.dateNaissance }" /> </label> <strong> </strong>(JJ/MM/AAAA)</td>
	</tr>
	<tr>
		<td style="height:29" colspan="3"><strong>sexe :</strong></td>
		<td colspan="2"><label> <select name="sexe">
			<option>M</option>
			<option>F</option>
		</select> </label></td>
	</tr>
	<tr>
		<td style="height:29" colspan="3"><strong>Adresse : </strong></td>
		<td colspan="2"><label> <input type="text"
			name="adresse" value="${membre.adresse }"/> </label></td>
	</tr>
	<tr>
		<td style="height:29" colspan="3"><strong>Num&eacute;ro de
		t&eacute;l&eacute;phone : </strong></td>
		<td colspan="2"><label> <input type="text"
			name="telephone" value="${membre.numTel }"/> </label></td>
	</tr>
	<tr>
		<td style="height:29" colspan="3"><strong>Profession :</strong></td>
		<td colspan="2"><label> <input type="text"
			name="profession" value="${membre.profession }"/> </label></td>
	</tr>
	<tr>
		<td style="height:29" colspan="3"><strong>Mot de passe : </strong></td>
		<td colspan="2"><label> <input type="password"
			name="mdp1" disabled="disabled"/> </label></td>
	</tr>
	<tr>
		<td style="height:29" colspan="3"><strong>Confirmer mot de
		passe : </strong></td>
		<td colspan="2"><label> <input type="text"
			name="mdp2" disabled="disabled" /> </label></td>
	</tr>
	<tr>
	
		<td></td>
		<td colspan="7" rowspan="2" valign="top">
		<div style="text-align:right;"><label>
		<input type="submit" name="Submit"
			value="Valider" />
		</label></div>
		</td>
	</tr>

	<tr>
		<td></td>
	</tr>
</table>
</form>

</div>
</fsnet:login>

</div>

</body>
</html>