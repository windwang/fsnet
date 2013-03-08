<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib uri="../../WEB-INF/ili.tld" prefix="ili"%>

<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="js/mceTextArea.js"></script>

<s:if test="theMessage == null || theMessage.equals('')">
	<h3>
		<s:text name="privatemessages.impossible" />
	</h3>
</s:if>

<fieldset class="fieldsetCadre">
	<legend>${theMessage.subject}</legend>

	<s:if test="theMessage != null && !theMessage.equals('')" >
		<table class="topicTable inLineTable">
			<tr class="topicHeader">
				<td><s:text name="privatemessages.to" /> : <ili:getSocialEntityInfos
						socialEntity="${theMessage.to}" /> ${theMessage.to.email} <span
					style="float: right"> <s:property value="creationDate" />
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

	</s:if>
</fieldset>