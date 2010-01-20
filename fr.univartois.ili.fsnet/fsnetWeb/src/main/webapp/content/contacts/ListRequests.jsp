<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h2>Liste de vos demandes effectuées:</h2>
<c:forEach var="contact" items="${requestScope.listRequests.requested}">
    -${contact.nom}
</c:forEach>