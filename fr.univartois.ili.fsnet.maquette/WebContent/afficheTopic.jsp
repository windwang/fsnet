<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://iliforum.ili.fsnet.com/" prefix="fsnet" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Topic</title>
</head>
<body>
Topic
<table border="1">
<tr><td> Numero </td><td> Nom </td><td> Date Creation </td></tr>
<fsnet:topic var="monTopic" hub="${monHub}">
	<tr><td>${monTopic.id}</td><td><a href="GotoMessage?idTopic=${monTopic.id}">${monTopic.sujet}</a></td><td>${monTopic.dateSujet}</td></tr>
</fsnet:topic>
</table>
<form action="CreateTopic">
	<fieldset>
		<legend> Creer Topic </legend>
		<label> Nom du Topic : </label>
		<input type="text" name="nomTopic" size="20" />
		<label> Message : </label>
		<textarea name="contenuMessage"></textarea>
		<input type="submit" name="creertopic" value="creer" />
	</fieldset>
</form>
</body>
</html>