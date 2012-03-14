<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<fieldset class="fieldsetAppli">
	<legend class="legendHome">
		<bean:message key="communities.title.create" />
	</legend>

	<table id="CreateCommunity"
		class="inLineTable fieldsetTableAppli">
		<html:form action="/CreateCommunity">
			<tr>
				<td><label for="communityName"> <bean:message
							key="communities.form.name" />
				</label></td>
				<td><html:text property="communityName" styleId="name"
						errorStyleClass="error" /> <logic:messagesPresent property="communityName">
						<div class="errorMessage">
							<html:errors property="communityName" />
						</div>
					</logic:messagesPresent></td>
			</tr>

			<tr>
				<td colspan="2"><c:import url="/InterestCheckBoxes.do" /></td>
			</tr>

			<tr>
				<td colspan="2" class="tableButton"><html:submit styleClass="button">
						<bean:message key="communities.button.validate" />
					</html:submit></td>
			</tr>
		</html:form>
	</table>
</fieldset>
