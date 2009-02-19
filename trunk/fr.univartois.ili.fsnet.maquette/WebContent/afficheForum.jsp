<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://iliforum.ili.fsnet.com/" prefix="fsnet" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Forum</title>
</head>
<body>
Forum
<table border="1">
<tr><td> Numero </td><td> Nom </td><td> Date Creation </td></tr>
<fsnet:hub var="monHub">
	<tr><td>${monHub.id}</td><td><a href="GotoTopic?idHub=${monHub.id}">${monHub.nomCommunaute}</a></td><td>${monHub.dateCreation}</td></tr>
</fsnet:hub>
</table>
<form action="CreateHub">
	<fieldset>
		<legend> Creer Hub </legend>
		<label> Nom du Hub : </label>
		<input type="text" name="nomHub" size="20" />
		<input type="submit" name="creerHub" value="creer" />
	</fieldset>

</form>
</body>
</html>