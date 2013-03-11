<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="js/mceTextArea.js"></script>


<fieldset class="fieldsetCadre">
	<legend>${theMessage.subject}</legend>

	<c:choose>
		<c:when test="${empty requestScope.conversationMessages.resultList}">
			<s:form action="DeleteMultiMessages2.do?fromPage=in">
				<table class="topicTable inLineTable tableStyle">
					<c:forEach items="${requestScope.conversationMessages1.resultList}"
						var="message">
						<tr class="topicHeader">
							<td><input type="checkbox" name="selectedMessages"
								value="${message.id}" /></td>
							<td><s:text name="privatemessages.from" /> : |<ili:getSocialEntityInfos
									socialEntity="${message.from}" />| <span style="float: right">
									<s:property value="message.creationDate" />
							</span></td>
						</tr>
						<tr>
							<td class="topicHeader"></td>
							<td>${message.body}</td>
						</tr>
					</c:forEach>
				</table>
				<s:submit cssClass="btn btn-inverse" key="privatemessages.delete" />
			</s:form>

			<ili:interactionFilter user="${ socialEntity }"
				right="${ rightAnswerMessage }">
				<a class="button"
					onclick="document.getElementById('quickResponse').style.display='table'">
					<img src="images/quickResponse.png" style="vertical-align: bottom"
					alt="Quick response" /> <s:text
						name="privatemessages.Quickresponse" />
				</a>
			</ili:interactionFilter>



			<s:debug/>
			<s:form action="CreatePrivateMessage.do">
				
				<s:hidden name="messageTo" value="%{#attr.theMessage.from.email}" />
				<s:hidden name="messageSubject" value="RE: %{#attr.theMessage.subject}" />
				<table id="quickResponse tableStyle"
					style="width: 100%; display: none; margin-top: 10px;">
					<tr>
						<td><label for="messageTo"> <s:text
									name="privatemessages.to" /> :
						</label></td>

						<td>
						<c:out value="${theMessage.from.name} ${theMessage.from.firstName}" /></td>

					</tr>
					<tr>
						<td><label for="messageSubject"> <s:text
									name="privatemessages.subject" /> :
						</label></td>

						<td>
						<c:out
								value="RE: ${theMessage.subject}" /></td>
					</tr>
					<tr>
						<td colspan="2"><s:textarea name="messageBody"
								cssClass="mceTextArea" style="width:100%">
							</s:textarea></td>
					</tr>

					<tr>
						<td colspan="2" class="alignRight"><s:submit
								cssClass="btn btn-inverse" key="privatemessages.send"
								onclick="this.disabled=true; this.value='Sending Message'; this.form.submit();"/>
								
					</tr>
				</table>
			</s:form>
		</c:when>
		<c:otherwise>

			<s:form action="/DeleteMultiMessages2.do?fromPage=in">
				<table class="topicTable inLineTable">
					<c:forEach items="${requestScope.conversationMessages.resultList}"
						var="message">
						<tr class="topicHeader">
							<td><input type="checkbox" name="selectedMessages"
								value="${message.id}" /></td>
							<td><s:text name="privatemessages.from" /> : |<ili:getSocialEntityInfos
									socialEntity="${message.from}" />| <span style="float: right">
									<s:property value="message.creationDate" />
							</span></td>
						</tr>
						<tr>
							<td class="topicHeader"></td>
							<td>${message.body}</td>
						</tr>
					</c:forEach>
				</table>
				<s:submit cssClass="btn btn-inverse" key="privatemessages.delete" />
			</s:form>

			<ili:interactionFilter user="${socialEntity}"
				right="${rightAnswerMessage}">
				<a class="btn btn-inverse"
					onclick="document.getElementById('quickResponse').style.display='table'">
					<img src="images/quickResponse.png" style="vertical-align: bottom"
					alt="Quick response" /> <s:text name="privatemessages.Quickresponse" />
				</a>
			</ili:interactionFilter>
	 
			<s:form action="CreatePrivateMessage.do">
				<s:hidden name="messageTo" value="%{#attr.theMessage.from.email}" />
				<s:hidden name="messageSubject" value="RE: %{#attr.theMessage.subject}" />
				<table id="quickResponse"
					style="width: 100%; display: none; margin-top: 10px;">
					<tr>
						<td><label for="messageTo"> <s:text
									name="privatemessages.to" /> :
						</label></td>
						<td><c:out value="${theMessage.from.name} ${theMessage.from.firstName}"/> </td>
					</tr>
					<tr>
						<td><label for="messageSubject"> <s:text
									name="privatemessages.subject" /> :
						</label></td>
						<td><c:out value="RE: ${theMessage.subject}"/>
						</td>
					</tr>
					<tr>
						<td colspan="2"><s:textarea name="messageBody"
								cssClass="mceTextArea" style="width:100%">
							</s:textarea></td>
					</tr>

					<tr>
						<td colspan="2" class="alignRight"><s:submit
								cssClass="btn btn-inverse" key="privatemessages.send"
								onclick="this.disabled=true; this.value='Sending Message'; this.form.submit();"/>
					</tr>
				</table>
			</s:form>

		</c:otherwise>
	</c:choose>
</fieldset>
