<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="js/mceTextArea.js"></script>
<script type="text/javascript" src="js/dmsAutoComplete.js"></script>



<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="privatemessages.sendM" />
	</legend>
	<table class="inLineTable tableStyle">
		<s:form action="/CreatePrivateMessage">

			<tr>
				<td><label for="memberSearch"> <s:text name="privatemessages.recipient" />
				</label></td>
				<td><c:choose>
						<c:when test="${! empty param.receiver}">
							<s:textfield property="messageTo" errorStyleClass="error"
								style="width: 95%" styleId="memberSearch"
								value="%{param.receiver}" />
						</c:when>
						<c:otherwise>
							<s:textfield property="messageTo" errorStyleClass="error"
								style="width: 95%" styleId="memberSearch" />
						</c:otherwise>
					</c:choose>
					<div id="searchDiv" class="ajaxSearch"></div></td>
			</tr>

			<tr>
				<td><label for="messageSubject"> <s:text name="privatemessages.subject" />
				</label></td>
				<td><s:textfield property="messageSubject"
						errorStyleClass="error" styleId="messageSubject" style="width: 95%" /> </td>
			</tr>

			<tr>
				<td><label for="messageBody"> <s:text name="privatemessages.body" />
				</label></td>
				<td><s:textarea property="messageBody" styleId="messageBody"
						styleClass="mceTextArea" style="width: 100%;">
					</s:textarea> </td>
			</tr>

			<tr>
				<td colspan="2" class="alignRight"><s:submit
						styleClass="btn btn-inverse"
						onclick="this.disabled=true; this.value='Sending Message'; this.form.submit();">
						<s:text name="privatemessages.send" />
					</s:submit></td>
			</tr>
		</s:form>
	</table>
</fieldset>

<script type="text/javascript">
	var AC = new dmsAutoComplete('memberSearch', 'searchDiv');
	AC.clearField = false;
	AC.ajaxTarget = 'AjaxSearchMember.do';
</script>