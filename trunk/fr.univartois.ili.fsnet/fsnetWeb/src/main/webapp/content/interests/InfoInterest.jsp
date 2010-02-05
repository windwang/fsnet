<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<c:choose>
	<c:when test="${not empty requestScope.interest}">
		<h2>${requestScope.interest.name}</h2>
		<c:if test="${not empty requestScope.interest.entities}">
			<h3><bean:message key="interests.14"/></h3>
			<ul>
				<c:forEach var="socialEntities" items="${requestScope.interest.entities}">
					<li>
						<html:link action="/DisplayProfile">
							<html:param name="id" value="${socialEntities.id}"/>
							${socialEntities.firstName} ${socialEntities.name}								
						</html:link>
					</li>
				</c:forEach>
			</ul>
		</c:if>
		<c:if test="${not empty requestScope.Hub}">
			<h3><bean:message key="pageTitle.4"/></h3>
			<ul>
				<c:forEach var="hub" items="${requestScope.Hub}">
					<li>
						<html:link action="/DisplayHub">
							<html:param name="hubId" value="${hub.id}"/>
							${hub.title}								
						</html:link>
					</li>
				</c:forEach>
			</ul>
		</c:if>
	</c:when>
	<c:otherwise>
		<bean:message key="interests.8"/>
	</c:otherwise>
</c:choose>
