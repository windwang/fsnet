<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:if test="${not empty hubResults}">
	<fieldset class="fieldsetAppli">
		<legend class="legendHome">
			<bean:message key="hubs.title.modify" />
		</legend>
		
		<c:set var="hub" value="${hubResults}" />
		
		<table class="inLineTableDashBoardFieldset fieldsetTable">
			<html:form action="/ModifyYourHub" method="post">
				<html:hidden property="communityId" value="${param.communityId}" />
				<tr>
					<td colspan="2">
						<div class="errorMessage">
							<html:errors property="hubId" />
						</div>
						<div class="errorMessage">
							<html:errors property="hubAlreadyExistsErrors" />
						</div>
					</td>
				</tr>
				
				<tr>
					<td><html:select property="hubId" styleClass="select">
							<html:option value="">
							</html:option>
							<c:forEach var="hub" items="${hubResults}">
								<c:if test="${sessionScope.userId eq hub.creator.id}">
									<html:option value="${hub.id}">${hub.title}</html:option>
								</c:if>
							</c:forEach>
						</html:select></td>
					<td><html:text property="modifiedHubName" /></td>
				</tr>
				
				<tr>
					<td colspan="2"><html:submit styleClass="button">
							<bean:message key="hubs.button.modify" />
						</html:submit></td>
				</tr>
			</html:form>
		</table>
	</fieldset>
</c:if>
