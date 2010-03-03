<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>Populate DB</title>
<link rel="stylesheet" type="text/css" media="screen"
	href="css/style.css" />
</head>
<body>
	<jsp:include page="/WEB-INF/jspf/globalActions.jsp"/>
	<div class="clear"></div>
	
	<c:forEach var="entity" items="${entities}">
		<div class="entity">
			<div class="entityHeader">
				<div class="entityHeader">
					<span class="entityName">${entity.name}</span> 
					<a href="CleanEntity?entity=${entity.name}" class="actionButton"> 
						Clean 
					</a>
				</div>
			</div>
			<c:forEach var="attribute" items="${entity.declaredAttributes}">
				<div class="attribute">
					<span class="attributeName">${attribute.name}</span> : 
					<span class="attributeType">${attribute.javaType}</span>
				</div>
			</c:forEach>
		</div>
	</c:forEach>
	
</body>
</html>
