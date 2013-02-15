<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="js/mceTextArea.js"></script>

<fieldset class="fieldsetCadre">
	<legend>
		<bean:message key="topics.title.createMessage" />
	</legend>

	<table class="inLineTable tableStyle">
		<html:form action="/CreateTopicMessage">
			<tr>
				<td><html:hidden property="topicId" value="${topicId}" /> <label
					for="messageDescription"><bean:message key="topics.form.description" /></label></td>
				<td><html:textarea property="messageDescription"
						styleId="messageDescription" styleClass="mceTextArea"
						style="width: 100%;">
					</html:textarea>
					<div class="errorMessage">
						<html:errors property="messageDescription" />
					</div></td>
			</tr>

			<tr>
				<td colspan="2" class="alignRight"><html:submit
						styleClass="btn btn-inverse">
						<bean:message key="topics.button.createMessage" />
					</html:submit></td>
			</tr>

			<tr>
				<td><bean:message key="topics.lastmessages" /></td>
				<td><c:forEach var="message" items="${lastMessages}">
						<tr>
							<td colspan="2">
								<div class="lastMessageOwner">
									<ili:getSocialEntityInfos socialEntity="${message.from}" />
								</div>
								<div class="topicTable">${message.body}</div>
							</td>
						</tr>
					</c:forEach></td>
			</tr>
		</html:form>
	</table>
</fieldset>