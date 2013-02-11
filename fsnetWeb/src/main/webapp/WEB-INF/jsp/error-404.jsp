<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>

<html>
<head>
<meta charset="utf-8"/>
<meta content="initial-scale=1, minimum-scale=1, width=device-width"	name="viewport"/>

<title><bean:message key="error-404.title" /></title>
<link rel="StyleSheet" href="${pageContext.request.contextPath}/css/error-404.css" type="text/css" media="screen"/>

</head>
<body>
	<a href="${pageContext.request.contextPath}/Home.do"> <img src="${pageContext.request.contextPath}/images/FSNET.png"
		height="49px" width="230px" alt="Logo">
	</a>
	<bean:message key="error-404.message" />

</body>
</html>
