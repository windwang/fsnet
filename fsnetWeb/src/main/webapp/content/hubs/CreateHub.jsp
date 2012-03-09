<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>

<fieldset class="fieldsetAppli">
	<legend class="legendHome">
		<bean:message key="hubs.title.create" />
	</legend>
	<table id="CreateHub"
		class="inLineTableDashBoardFieldset fieldsetTable">
		<html:form action="/CreateHub">
			<html:hidden property="communityId" value="${param.communityId}" />
			<tr>
				<td><label for="hubName"><bean:message
							key="hubs.form.name" /> :</label></td>
				<td><html:text property="hubName" styleId="hubName" />
					<logic:messagesPresent property="hubName">
						<div class="errorMessage">
							<html:errors property="hubName" />
							<html:errors property="createdHubName" />
						</div>
					</logic:messagesPresent></td>
			</tr>

			<tr>
				<td colspan="2"><c:import url="/InterestCheckBoxes.do" /></td>
			</tr>

			<tr>
				<td colspan="2" align="right"><html:submit styleClass="button">
						<bean:message key="hubs.button.create" />
					</html:submit></td>
			</tr>
		</html:form>
	</table>
</fieldset>
<div class="clear"></div>