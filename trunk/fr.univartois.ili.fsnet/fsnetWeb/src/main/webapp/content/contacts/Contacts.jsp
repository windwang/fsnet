<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 


<jsp:include page="/content/contacts/AskContact.jsp"/>
<h2>Liste de vos contacts:</h2>
<c:forEach var="contact" items="${listContacts}">
    -${contact.nom}
</c:forEach>

<h2>Liste des demandes reçues:</h2>
<c:forEach var="contact" items="${listDemands}">
    -${contact.nom}
</c:forEach>

<h2>Liste de vos demandes effectuées:</h2>
<c:forEach var="contact" items="${listRequests}">
    -${contact.nom}
</c:forEach>

