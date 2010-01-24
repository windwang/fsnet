<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<h3>Mes intérêts</h3>
<ul>
    <c:choose>
        <c:when test="${not empty requestScope.user.lesinterets}">
            <c:forEach var="interest" items="${requestScope.user.lesinterets}">
                <li>
                	${interest.nomInteret}
                	<html:link styleClass="button" action="/RemoveInterest">
                		<html:param name="removedInterestId" value="${interest.id}"/>
                		Retirer
                	</html:link>
                </li>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <li>Aucun intérêt</li>
        </c:otherwise>
    </c:choose>
</ul>
