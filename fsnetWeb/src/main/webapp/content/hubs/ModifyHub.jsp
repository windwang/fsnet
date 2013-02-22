<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<c:if test="${not empty hubResults}">
	<fieldset class="fieldsetCadre">
		<legend>
			<s:text name="hubs.title.modify" />
		</legend>

		<table class="inLineTable tableStyle">
<<<<<<< HEAD
			<s:form action="/ModifyYourHub" method="POST">
				<s:hidden name="communityId" value="%{param.communityId}" />
=======
			<html:form action="/ModifyYourHub" method="POST">
				<s:hidden name="communityId" value="${param.communityId}" />
>>>>>>> 75d3d39903713564d348b5ead824e237db25c0ee
				<tr>
					<td><label for="oldHubName"><s:text name="hubs.form.oldName" /></label></td>
					<td>
						<s:select property="oldHubId" styleClass="select" styleId="oldHubName" list="%{hubResults}" listKey="%{allGroups.id}" listValue="%{allGroups.title}"/>
					</td>
				</tr>

				<tr>
					<td><label for="newHubName">
					<s:text name="hubs.form.newName" /></label></td>
					<td><s:textfield property="newHubName" styleId="newHubName" /></td>
				</tr>

				<tr>
					<td colspan="2" class="tableButton">
					<s:submit
							styleClass="btn btn-inverse">
							<s:text name="hubs.button.modify" />
						</s:submit></td>
				</tr>
			</s:form>
		</table>
	</fieldset>
</c:if>
