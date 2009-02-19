<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://iliforum.ili.fsnet.com/" prefix="fsnet" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Message</title>
</head>
<body>
Message
<table border="1">
<tr><td> Numero </td><td> Nom </td><td> Date Creation </td></tr>
<fsnet:message var="monMess" topic="${monTopic}">
	<tr><td>${monMess.id}</td><td>${monMess.contenu}</td><td>${monMess.dateMessage}</td></tr>
</fsnet:message>
</table>
<form action="CreateMessage">
	<fieldset>
		<legend> Repondre Message </legend>
		<label> Contenu : </label>
		<textarea name="contenuMessage"></textarea>
		<input type="submit" name="répondre" value="creer" />
	</fieldset>
</form>
</body>
</html>