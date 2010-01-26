<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<h3><bean:message key="interests.6"/></h3>
<ul>
    <c:choose>
        <c:when test="${not empty requestScope.user.interests}">
            <c:forEach var="interest" items="${requestScope.user.interests}">
                <li>
                	${interest.name}
                	<html:link styleClass="button" action="/RemoveInterest">
                		<html:param name="removedInterestId" value="${interest.id}"/>
                		<bean:message key="interests.7"/>
                	</html:link>
                </li>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <li><bean:message key="interests.8"/></li>
        </c:otherwise>
    </c:choose>
</ul>
