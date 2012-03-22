<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean" %>
<html>
<head>
<meta charset="utf-8"/>
<meta content="initial-scale=1, minimum-scale=1, width=device-width"
	name="viewport"/>
<title><bean:message key="error-500.title"/></title>
<LINK REL="StyleSheet" HREF="css/error-500.css" TYPE="text/css" MEDIA="screen"/>
</head>
<body>

	<a href="/fsnetWeb/Home.do"> <img src="images/FSNET.png" height="49px"
		width="230px">
	</a>
	<bean:message key="error-500.message"/>

</body>
</html>