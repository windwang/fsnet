<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>

<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>

<script type="text/javascript" src="js/mceTextArea.js"></script>

<fieldset class="fieldsetCadre">
	<legend>
		<bean:message key="topics.title.modifyMessage" />
	</legend>

	<table id="CreateTopic" class="inLineTable tableStyle">
		<html:form action="/ModifyTopicMessage">
			<html:hidden property="topicId" value="${topicId}" />
			<html:hidden property="messageId" value="${message.id}" />

			<tr>
				<td><label for="messageDescription"><bean:message
							key="topics.form.description" /></label></td>
				<td><html:textarea cols="60" rows="8"
						property="messageDescription" styleId="messageDescription"
						styleClass="mceTextArea" style="width: 100%;"
						value="${message.body}">
					</html:textarea> <logic:messagesPresent property="messageDescription">
						<div class="errorMessage">
							<html:errors property="messageDescription" />
						</div>
					</logic:messagesPresent></td>
			</tr>

			<tr>
				<td colspan="2" class="tableButton"><html:submit
						styleClass="btn btn-inverse">
						<bean:message key="topics.button.updateMessage" />
					</html:submit></td>
			</tr>
		</html:form>
	</table>
</fieldset>