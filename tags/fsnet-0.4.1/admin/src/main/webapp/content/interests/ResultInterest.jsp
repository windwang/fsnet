<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<c:choose>
    <c:when test="${not empty requestScope.interestResult}">
        <div class="cloud">
            <c:forEach var="interest" items="${requestScope.interestResult}">
                <div>
                	<html:link action="/InterestInformations">
                		<html:param name="infoInterestId" value="${interest.id}"/>
                		${interest.name}
                	</html:link>
                </div>
            </c:forEach>
        </div>
    </c:when>
    <c:otherwise>
        <bean:message key="interests.10"/>
    </c:otherwise>
</c:choose>
