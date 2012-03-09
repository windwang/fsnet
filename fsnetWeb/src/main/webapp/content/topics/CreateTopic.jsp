<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="js/mceTextArea.js"></script>



<fieldset class="fieldsetAppli">
	<legend class="legendHome">
		<bean:message key="topics.title.create" />
	</legend>

	<table id="CreateTopic"
		class="inLineTableDashBoardFieldset fieldsetTable">
		<html:form action="/CreateTopic">

			<html:hidden property="hubId" value="${param.hubId}" />
			
			<tr>
				<td><label for="topicSubject"> <bean:message
							key="topics.form.subject" />
				</label></td>
				<td><html:text property="topicSubject" styleId="eventName"
						errorStyleClass="error" /> <logic:messagesPresent
						property="topicSubject">
						<div class="errorMessage">
							<html:errors property="topicSubject" />
						</div>
					</logic:messagesPresent></td>
			</tr>
			
			<tr>
				<td colspan="2"><c:import url="/InterestCheckBoxes.do" /></td>
			</tr>
			
			<tr>
				<td><label for="messageDescription"> <bean:message
							key="topics.form.description" />
				</label></td>
				<td><html:textarea property="messageDescription"
						styleId="messageDescription" styleClass="mceTextArea"
						style="width: 100%;">
					</html:textarea>
					<div class="errorMessage">
						<html:errors property="messageDescription" />
					</div></td>
			</tr>
			<tr>
				<td colspan="2" align="right"><html:submit styleClass="button alignRight">
						<bean:message key="topics.button.create" />
					</html:submit></td>
			</tr>
		</html:form>
	</table>
</fieldset>