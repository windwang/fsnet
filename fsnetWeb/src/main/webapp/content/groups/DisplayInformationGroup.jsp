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
		
		<h3><bean:message key="groups.listMember"/></h3>
		
		<table class="inLineTableMemberGroup">
                
	        <tbody>
				<c:forEach var="member" items="${allMembers}">
					<tr>
						<td class="messagePhoto"><a href="/fsnetWeb/DisplayProfile.do?id=${member.id}" class="miniature"> <img title="${member.name} ${member.firstName}" src="miniature/${member.id}.png"/></a></td>
						<td ><a href="/fsnetWeb/DisplayProfile.do?id=${member.id}">${member.name} ${member.firstName}</a></td>
					</tr>
				</c:forEach>
			</tbody>
		
		</table>
		
		
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