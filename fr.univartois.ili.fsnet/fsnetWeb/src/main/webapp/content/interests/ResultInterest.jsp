<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
    <c:when test="${not empty requestScope.interestResult}">
        <ul>
            <c:forEach var="interest" items="${requestScope.interestResult}">
                <li>${interest.nomInteret}</li>
            </c:forEach>
        </ul>
    </c:when>
    <c:otherwise>
		Aucun résultat
    </c:otherwise>
</c:choose>
