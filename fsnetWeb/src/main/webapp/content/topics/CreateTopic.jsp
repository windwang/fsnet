<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="js/mceTextArea.js"></script>

<fieldset class="fieldsetCadre">
	<legend>
		<s:bean name="topics.title.create" />
	</legend>

	<table id="CreateTopic" class="inLineTable tableStyle">
		<s:form action="/CreateTopic">
			<s:hidden name="hubId" value="%{param.hubId}" />

			<tr>
				<td><label for="topicSubject"><s:text
							name="topics.form.subject" /> </label></td>
				<td><s:textfield property="topicSubject" styleId="topicSubject"
						errorStyleClass="error" /></td>
			</tr>

			<tr>
				<td></td>
				<td><c:import url="/InterestCheckBoxes.do" /></td>
			</tr>

			<tr>
				<td><label for="messageDescription"> <s:text
							name="topics.form.description" />
				</label></td>
				<td><s:textarea property="messageDescription"
						styleId="messageDescription" styleClass="mceTextArea"
						style="width: 100%;">
					</s:textarea></td>
			</tr>
			<tr>
				<td colspan="2" class="tableButton"><s:submit
						styleClass="btn btn-inverse alignRight">
						<s:text name="topics.button.create" />
					</s:submit></td>
			</tr>
		</s:form>
	</table>
</fieldset>