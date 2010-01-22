<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h3>Mes intérêts</h3>
<ul>
	<c:choose>
		<c:when test="${not empty user.lesinterets}">	
			<c:forEach var="interest" items="${user.lesinterets}">
			    <li>${interest.nomInteret}</li>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<li>Aucun intérêt</li>
		</c:otherwise>
	</c:choose>
</ul>
    