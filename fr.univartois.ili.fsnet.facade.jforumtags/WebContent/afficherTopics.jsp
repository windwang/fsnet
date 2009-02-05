<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/hub" prefix="hub" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>


<hub:message var="message" dateDebut="20kk" dateFin="23bb" >
${message.text} </br>
</hub:message>
<!--<hub:topic var="topic">-->
<!--Topic : ${topic.nom}</br>-->
<!--<hub:message var="message" topic="${topic}">-->
<!--Messages: ${message.auteur} </br> ${message.postTime} </br> ${message.text}</br>-->
<!--</hub:message>-->
<!--</hub:topic>-->


</body>
</html>