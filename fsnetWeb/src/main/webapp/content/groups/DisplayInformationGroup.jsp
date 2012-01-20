<%-- 
		 Author : BOURAGBA Mohamed
		
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<h3><bean:message key="groups.title"/></h3>
<c:choose>
	<c:when test="${ socialGroup != null }">
		<div>${ socialGroup.name }</div>
		<h3><bean:message key="groups.description.message"/></h3>
		<p> ${ socialGroup.description } </p>
		
		<h3><bean:message key="groups.members"/></h3>
		<ul>
			<c:foreach var="member" items="${allMembers}">
				<li>${member.name} ${member.firstname}</li>
			</c:foreach>
		</ul>
		
		
		<c:if test="${ sessionScope.isMasterGroup }">		
		<h3><bean:message key="groups.rights.title"/></h3>
		
		<c:choose>
			<c:when test="${ fn:length(socialGroup.rights) != 0 }">
				<table class="inLineTable">
					<c:forEach var="right"  items="${ socialGroup.rights }">
						<tr>
							<td>
								<bean:message key="groups.rights.${ right }"/>							
							</td>
						</tr>
					</c:forEach>
				</table>
			</c:when>
			<c:otherwise>
					<p><bean:message key="groups.noRights.title"/></p>
				</c:otherwise>
		</c:choose>
		</c:if>
	</c:when>
	<c:otherwise>
		<p><bean:message key="groups.noGroup"/></p>
	</c:otherwise>
</c:choose>