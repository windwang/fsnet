<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="js/mceTextArea.js"></script>


<fieldset class="fieldsetAppli">
	<legend class="legendHome">${theMessage.subject}</legend>
	
	<c:choose>
		<c:when test="${empty requestScope.conversationMessages.resultList}">
			<html:form action="/DeleteMultiMessages2">
				<table class="topicTable inLineTable fieldsetTableAppli">
					<c:forEach items="${requestScope.conversationMessages1.resultList}"
						var="message">
						<tr class="topicHeader"="2">
							<td><html:multibox property="selectedMessages"
									value="${message.id}" /></td>
							<td><bean:message key="privatemessages.from" /> : |<ili:getSocialEntityInfos
									socialEntity="${message.from}" />| <span style="float: right">
									<bean:write name="message" property="creationDate"
										format="dd/MM/yyyy HH:mm" />
							</span></td>
						</tr>
						<tr>
							<td class="topicHeader"></td>
							<td>${message.body}</td>
						</tr>
					</c:forEach>
				</table>
				<html:submit styleClass="button">
					<bean:message key="privatemessages.delete" />
				</html:submit>
			</html:form>

			<ili:interactionFilter user="${ socialEntity }"
				right="${ rightAnswerMessage }">
				<a class="button"
					onclick="document.getElementById('quickResponse').style.display='table'">
					<img src="images/quickResponse.png" style="vertical-align: bottom"
					alt="Quick response" /> <bean:message
						key="privatemessages.Quickresponse" />
				</a>
			</ili:interactionFilter>




			<html:form action="/CreatePrivateMessage">
				<html:hidden property="messageTo" value="${theMessage.from.email}" />
				<table id="quickResponse"
					style="width: 100%; display: none; margin-top: 10px;">
					<tr>
						<td><label for="messageTo"> <bean:message
									key="privatemessages.to" /> :
						</label></td>
						<td><html:text property="messageTo" disabled="true"
								errorStyleClass="error"
								value="${theMessage.from.name} ${theMessage.from.firstName}"
								readonly="true" /></td>
					</tr>
					<tr>
						<td><label for="messageSubject"> <bean:message
									key="privatemessages.subject" /> :
						</label></td>
						<td><html:text property="messageSubject"
								errorStyleClass="error" value="RE: ${theMessage.subject}" /></td>
					</tr>
					<tr>
						<td colspan="2"><html:textarea property="messageBody"
								styleClass="mceTextArea" style="width:100%">
							</html:textarea></td>
					</tr>

					<tr>
						<td colspan="2" class="alignRight"><html:submit
								styleClass="button"
								onclick="this.disabled=true; this.value='Sending Message'; this.form.submit();">
								<bean:message key="privatemessages.send" />
							</html:submit></td>
					</tr>
				</table>
			</html:form>
		</c:when>
		<c:otherwise>
	
			<html:form action="/DeleteMultiMessages2">
				<table class="topicTable inLineTable fieldsetTableAppli">
					<c:forEach items="${requestScope.conversationMessages.resultList}"
						var="message">
						<tr class="topicHeader">
							<td><html:multibox property="selectedMessages"
									value="${message.id}" /></td>
							<td><bean:message key="privatemessages.from" /> : |<ili:getSocialEntityInfos
									socialEntity="${message.from}" />| <span style="float: right">
									<bean:write name="message" property="creationDate"
										format="dd/MM/yyyy HH:mm" />
							</span></td>
						</tr>
						<tr>
							<td class="topicHeader"></td>
							<td>${message.body}</td>
						</tr>
					</c:forEach>
				</table>
				<html:submit styleClass="button">
					<bean:message key="privatemessages.delete" />
				</html:submit>
			</html:form>

			<ili:interactionFilter user="${ socialEntity }"
				right="${ rightAnswerMessage }">
				<a class="button"
					onclick="document.getElementById('quickResponse').style.display='table'">
					<img src="images/quickResponse.png" style="vertical-align: bottom"
					alt="Quick response" /> <bean:message
						key="privatemessages.Quickresponse" />
				</a>
			</ili:interactionFilter>

			<html:form action="/CreatePrivateMessage">
				<html:hidden property="messageTo" value="${theMessage.from.email}" />
				<table id="quickResponse"
					style="width: 100%; display: none; margin-top: 10px;">
					<tr>
						<td><label for="messageTo"> <bean:message
									key="privatemessages.to" /> :
						</label></td>
						<td><html:text property="messageTo" disabled="true"
								errorStyleClass="error"
								value="${theMessage.from.name} ${theMessage.from.firstName}"
								readonly="true" /></td>
					</tr>
					<tr>
						<td><label for="messageSubject"> <bean:message
									key="privatemessages.subject" /> :
						</label></td>
						<td><html:text property="messageSubject"
								errorStyleClass="error" value="RE: ${theMessage.subject}" /></td>
					</tr>
					<tr>
						<td colspan="2"><html:textarea property="messageBody"
								styleClass="mceTextArea" style="width:100%">
							</html:textarea></td>
					</tr>

					<tr>
						<td colspan="2" class="alignRight"><html:submit
								styleClass="button"
								onclick="this.disabled=true; this.value='Sending Message'; this.form.submit();">
								<bean:message key="privatemessages.send" />
							</html:submit></td>
					</tr>
				</table>
			</html:form>

	</c:otherwise>
	</c:choose>
</fieldset>
