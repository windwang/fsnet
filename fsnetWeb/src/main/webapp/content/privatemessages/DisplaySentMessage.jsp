<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="js/mceTextArea.js"></script>

<logic:empty name="theMessage" scope="request">
	<h3>
		<s:text name="privatemessages.impossible" />
	</h3>
</logic:empty>

<fieldset class="fieldsetCadre">
	<legend>${theMessage.subject}</legend>

	<logic:notEmpty name="theMessage" scope="request">
		<table class="topicTable inLineTable">
			<tr class="topicHeader">
				<td><s:text name="privatemessages.to" /> : <ili:getSocialEntityInfos
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
		<s:a action="/DeletePrivateMessage?fromPage=out" styleClass="btn btn-inverse"
			style="float: right">
			<s:param name="messageId" value="%{theMessage.id}" />
			<s:text name="privatemessages.delete" />
		</s:a>

	</logic:notEmpty>
</fieldset>