<%-- 
    Document   : CreateHub
    Author     : Audrey Ruellan et Cerelia Besnainou
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<ul>
<c:forEach var="hub" items="${hubResults}">
	
  <li>${hub.nomCommunaute}</li>
  <c:if test="${sessionScope.user.id eq hub.createur.id}">
		<a class="button">supprimer ${hub.id}</a>
	</c:if>
</c:forEach>
</ul>