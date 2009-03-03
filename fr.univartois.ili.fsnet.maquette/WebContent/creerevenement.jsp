<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://iliforum.ili.fsnet.com/" prefix="fsnet"%>
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
<div class="wrap background">
<jsp:include page="haut.jsp"></jsp:include>

<div id="left">
<h2><a href="#">&#201;v&#233;nement</a></h2>
<table width="445">
	<tr>
		<th style="width: 181" scope="col">Entrer votre texte</th>
		<th style="width: 252" scope="col">
		<div style="text-align: right">2000 caract&#232;res maximums</div>
		</th>
	</tr>
</table>
<p>&nbsp;</p>
<form action="AddEvenement" method="post" id="evenement">
<table style="width: 433">

	<tr>
		<th style="width: 193" scope="row">Titre</th>
		<td style="width: 228">
		<div style="align: right" class="Style1"><label
			style="text-align: left"> <input name="titreEvenemt"
			type="text" size="50" /> </label></div>
		</td>
	</tr>
	<tr>
		<th scope="row">Contenu</th>
		<td><textarea name="contenuEvenement" cols="40" rows="10"></textarea></td>
	</tr>
<tr>
	<th scope="row"></th>
	<td>
	<div style="text-align: right"><input name="submit" type="submit"
		value="Publier" /></div>
	</td>
	</tr>
</table>
</form>
</div>

</div>

<jsp:include page="bas.jsp" />
</body>
</html>