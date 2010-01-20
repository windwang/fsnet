<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

Liste des demandes reçues:
<c:forEach var="contact" items="${requestScope.listDemands.asked}">
    -${contact.nom}
</c:forEach>