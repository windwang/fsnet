<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="js/mceTextArea.js"></script>

<logic:empty name="theMessage" scope="request">
	<h3>
		<bean:message key="privatemessages.impossible" />
	</h3>
</logic:empty>

<fieldset class="fieldsetAppli">
	<legend class="legendHome">${theMessage.subject}</legend>

	<logic:notEmpty name="theMessage" scope="request">
		<table class="topicTable inLineTable fieldsetTableAppli">
			<tr class="topicHeader">
				<td><bean:message key="privatemessages.to" /> : <ili:getSocialEntityInfos
						socialEntity="${theMessage.to}" /> ${theMessage.to.email} <span
					style="float: right"> <bean:write name="theMessage"
							property="creationDate" formatKey="date.format" />
				</span></td>
			</tr>
			<tr>
				<td>${theMessage.body}</td>
			</tr>
		</table>

		<!-- TODO factorise this code with createMessage.jsp -->
		<html:link action="/DeletePrivateMessage?fromPage=out" styleClass="button"
			style="float: right">
			<html:param name="messageId" value="${theMessage.id}" />
			<bean:message key="privatemessages.delete" />
		</html:link>

	</logic:notEmpty>
</fieldset>