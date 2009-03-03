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

</head>
<body>
<div class="wrap background">
<jsp:include page="haut.jsp"></jsp:include>

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
		<tr>
			<th>Date de fin : ${monAnnonce.dateAnnonce}</th>
		</tr>
		<th style="width: 252" scope="col">
		<div style="text-align: center"><a href="annonces.jsp">retour
		aux annonces</a></div>
		</th>
	</fsnet:annonce>


</table>
<p>&nbsp;</p>

</div>



</div>
<jsp:include page="bas.jsp"></jsp:include>

</body>
</html>