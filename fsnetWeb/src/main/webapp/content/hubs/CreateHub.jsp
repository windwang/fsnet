<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<fieldset class="fieldsetCadre">
	<legend>
		<bean:message key="hubs.title.create" />
	</legend>
	<table id="CreateHub"
		class="inLineTable tableStyle">
		<html:form action="/CreateHub">
			<s:hidden name="communityId" value="${param.communityId}" />
			<tr>
				<td><label for="hubName"><bean:message
							key="hubs.form.name" /></label></td>
				<td><html:text property="hubName" styleId="hubName" /> <logic:messagesPresent
						property="hubName">
						<div class="errorMessage">
							<html:errors property="hubName" />
						</div>
					</logic:messagesPresent></td>
			</tr>
			<tr>
				<td colspan="2"><c:import url="/InterestCheckBoxes.do" /></td>
			</tr>

			<tr>
				<td colspan="2" class="tableButton"><html:submit styleClass="btn btn-inverse">
						<bean:message key="hubs.button.create" />
					</html:submit></td>
			</tr>
		</html:form>
	</table>
</fieldset>
<div class="clear"></div>