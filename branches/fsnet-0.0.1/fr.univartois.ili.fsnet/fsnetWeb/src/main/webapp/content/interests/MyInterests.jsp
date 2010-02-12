<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<h3><bean:message key="interests.6"/></h3>
<c:choose>
	<c:when test="${not empty requestScope.user.interests}">
		<div class="cloud">
			<c:forEach var="interest" items="${requestScope.user.interests}">
				<div>
					<html:link action="/InterestInformations">
						<html:param name="infoInterestId" value="${interest.id}"/>
						${interest.name}
					</html:link>
					<html:link styleClass="button" action="/RemoveInterest">
						<html:param name="removedInterestId" value="${interest.id}"/>
						<bean:message key="interests.7"/>
					</html:link>
				</div>
			</c:forEach>
		</div>
	</c:when>
	<c:otherwise>
		<bean:message key="interests.8"/>
	</c:otherwise>
</c:choose>

