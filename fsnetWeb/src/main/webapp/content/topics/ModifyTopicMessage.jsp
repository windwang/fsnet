<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="js/mceTextArea.js"></script>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="topics.title.modifyMessage" />
	</legend>

	<table id="CreateTopic" class="inLineTable tableStyle">

		<s:form action="/ModifyTopicMessage">
			<s:hidden property="topicId" value="%{topicId}" />
			<s:hidden property="messageId" value="%{message.id}" />


			<tr>
				<td><label for="messageDescription">
				<s:text name="topics.form.description" /></label></td>
				<td><s:textarea cols="60" rows="8"
						property="messageDescription" styleId="messageDescription"
						cssClass="mceTextArea" style="width: 100%;"
						value="%{message.body}">
					</s:textarea></td>
			</tr>

			<tr>
				<td colspan="2" class="tableButton"><s:submit
						styleClass="btn btn-inverse">
						<s:text name="topics.button.updateMessage" />
					</s:submit></td>
			</tr>
		</s:form>
	</table>
</fieldset>