<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<html>
<head>
<meta charset="utf-8">
<meta content="initial-scale=1, minimum-scale=1, width=device-width"
	name="viewport">
<title>Error 404 (Not Found)!!1</title>
<style>
* {
	margin: 0;
	padding: 0
}

html,code {
	font: 15px/22px arial, sans-serif
}

html {
	background: #fff;
	color: #222;
	padding: 15px
}

body {
	margin: 7% auto 0;
	max-width: 390px;
	min-height: 180px;
	padding: 30px 0 15px
}

*>body {
	background: url(images/error.png) 100% 5px no-repeat;
	padding-right: 205px
}

p {
	margin: 11px 0 22px;
	overflow: hidden
}

ins {
	color: #777;
	text-decoration: none
}

a img {
	border: 0
}

@media screen and (max-width:772px) {
	body {
		background: none;
		margin-top: 0;
		max-width: none;
		padding-right: 0
	}
}
</style>
</head>
<body>
	<a href="/fsnetWeb/Home.do"> <img src="images/FSNET.png" height="55px"
		width="150px">
	</a>
<!-- <p> <b> 404. </b><ins>C'est une erreur. </ ins> </p> L'URL demandée n'a pas été trouvée sur ce serveur. <ins> Vérifier si la syntaxe est correcte ou essayer plus tard. </ ins> </ p> -->
	<bean:message key="error-404.message"/>
</body>
</html>