<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<fieldset class="fieldsetCadre">
	<legend>
			<bean:message key="groups.titleInformation" />
		</legend>
			
	<c:choose>
		<c:when test="${ socialGroup != null }">
			<table class="inLineTable tableStyle">
			<tr>
			<td>
			<fieldset class="inLinefieldset">
				<legend>
					<bean:message key="groups.title" />
				</legend>

				<div>
					<c:forEach var="group" items="${antecedantsOfGroup}">
						<html:link action="/DisplayInformationGroup">
							<html:param name="idGroup" value="${ group.id }" />
					${group.name}
				</html:link>
						<bean:message key="groups.addGroups" />
					</c:forEach>
					<html:link action="/DisplayInformationGroup">
						<html:param name="idGroup" value="${ socialGroup.id }" />
				${ socialGroup.name }
			</html:link>
				</div>
			</fieldset>
			</td>
			</tr>
			
			
			<tr>
			<td>
			<fieldset class="inLinefieldset">
				<legend>
					<bean:message key="groups.description.message" />
				</legend>

				<p>${ socialGroup.description }</p>
			</fieldset>
			</td>
			</tr>
			
			<tr>
			<td>
			<fieldset class="inLinefieldset">
				<legend>
					<bean:message key="groups.listGroup" />
				</legend>

				<c:forEach items="${childsOfGroup}" var="cGroup" varStatus="status">
					<html:link action="/DisplayInformationGroup">
						<html:param name="idGroup" value="${ cGroup.id }" />
				${ cGroup.name }
			</html:link>
					<c:if test="${status.count < fn:length(childsOfGroup)}"> | </c:if>
				</c:forEach>
			</fieldset>
			</td>
			</tr>

			<tr>
			<td>
			<fieldset class="inLinefieldset">
				<legend>
					<bean:message key="groups.listMember" />
				</legend>

				<c:forEach var="member" items="${allMembers}">
					<a href="/fsnetWeb/DisplayProfile.do?id=${member.id}"
						class="miniature"> <img
						title="${member.name} ${member.firstName}"
						src="miniature/${member.id}.png" alt="miniature" />
					</a>
				</c:forEach>
			</fieldset>
			</td>
			</tr>

			<c:if test="${ sessionScope.isMasterGroup }">
				<tr>
				<td>
				<fieldset class="inLinefieldset">
					<legend>
						<bean:message key="groups.rights.title" />
					</legend>
					<c:choose>
						<c:when test="${ fn:length(socialGroup.rights) != 0 }">
								
								<c:forEach var="right" items="${ socialGroup.rights }">
									
									<p><bean:message key="groups.rights.${ right }" /></p>
									
								</c:forEach>
								
						</c:when>
						<c:otherwise>
							<p>
								<bean:message key="groups.noRights.title" />
							</p>
						</c:otherwise>
					</c:choose>
				</fieldset>
			</td>
			</tr>	
			</c:if>
			</table>
		</c:when>
		<c:otherwise>
			<p>
				<bean:message key="groups.noGroup" />
			</p>
		</c:otherwise>
		
	</c:choose>
	
</fieldset>