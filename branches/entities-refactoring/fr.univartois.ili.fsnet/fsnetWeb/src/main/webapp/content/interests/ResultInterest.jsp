<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<c:choose>
    <c:when test="${not empty requestScope.interestResult}">
        <ul>
            <c:forEach var="interest" items="${requestScope.interestResult}">
                <li>${interest.name}</li>
            </c:forEach>
        </ul>
    </c:when>
    <c:otherwise>
    	<bean:message key="interests.10"/>
    </c:otherwise>
</c:choose>
