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
<script type="text/javascript">
</script>
</head>
<body onload="show();setFocus('inputAddInterest')">

<jsp:include page="header.jsp"></jsp:include>


<div class="wrap background">
<jsp:include page="subHeader.jsp"></jsp:include>

<div id="left">
<h2><a href="AddInterest.jsp?interet=current">Ajout d'interets </a></h2>
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
		<form id="AddInterest" method="post" action="AddInterest" >
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
				<td><input name="inputAddInterest" id="inputAddInterest" type="button" onclick="addInterest()"
					value="Ajouter"  /></td>
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

<jsp:include page="CommunityBox.jsp"></jsp:include>
</div>


<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>