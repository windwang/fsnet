<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="js/mceTextArea.js"></script>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="topics.title.createMessage" />
	</legend>

	<table class="inLineTable tableStyle">
		<s:form action="/CreateTopicMessage">
			<tr>
				<td><s:hidden property="topicId" value="%{topicId}" /> <label
					for="messageDescription"><s:text
							name="topics.form.description" /></label></td>
				<td><s:textarea property="messageDescription"
						styleId="messageDescription" styleClass="mceTextArea"
						style="width: 100%;">
					</s:textarea></td>
			</tr>

			<tr>
				<td colspan="2" class="alignRight"><s:submit
						styleClass="btn btn-inverse">
						<s:text name="topics.button.createMessage" />
					</s:submit></td>
			</tr>

			<tr>
				<td><s:text name="topics.lastmessages" /></td>
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
		</s:form>
	</table>
</fieldset>