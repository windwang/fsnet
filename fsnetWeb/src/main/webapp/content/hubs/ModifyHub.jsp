<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:if test="${not empty hubResults}">
	<fieldset class="fieldsetAppli">
		<legend class="legendHome">
			<bean:message key="hubs.title.modify" />
		</legend>

		<table class="inLineTable fieldsetTableAppli">
			<html:form action="/ModifyYourHub" method="POST">
				<html:hidden property="communityId" value="${param.communityId}" />
				<tr>
					<td><bean:message key="hubs.form.oldName" /></td>
					<td><html:select property="oldHubId" styleClass="select">
							<html:option value="">
							</html:option>
							<c:forEach var="hub" items="${hubResults}">
								<c:if test="${sessionScope.userId eq hub.creator.id}">
									<html:option value="${hub.id}">${hub.title}</html:option>
								</c:if>
							</c:forEach>
						</html:select>
						<div class="errorMessage">
							<html:errors property="oldHubId" />
						</div></td>
				</tr>

				<tr>
					<td><bean:message key="hubs.form.newName" /></td>
					<td><html:text property="newHubName" />
						<div class="errorMessage">
							<html:errors property="newHubName" />
						</div></td>
				</tr>

				<tr>
					<td colspan="2" class="tableButton"><html:submit styleClass="button">
							<bean:message key="hubs.button.modify" />
						</html:submit></td>
				</tr>
			</html:form>
		</table>
	</fieldset>
</c:if>
