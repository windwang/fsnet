<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="js/mceTextArea.js"></script>
<script type="text/javascript" src="js/dmsAutoComplete.js"></script>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="privatemessages.sendM" />
	</legend>
	<table class="inLineTable tableStyle">
		<s:form action="CreatePrivateMessage">
			<c:choose>
				<c:when test="${! empty param.receiver}">
					<s:textfield id="messageTo" name="messageTo" style="width: 95%" label="%{getText('privatemessages.recipient')}"
						styleId="memberSearch" value="%{param.receiver}" />
				</c:when>
				<c:otherwise>
					<s:textfield id="messageTo" name="messageTo" label="%{getText('privatemessages.recipient')}"
						style="width: 95%" styleId="memberSearch" />
				</c:otherwise>
			</c:choose>
			<div id="searchDiv" class="ajaxSearch"></div>


			<s:textfield name="messageSubject" label="%{getText('privatemessages.subject')}"
				errorStyleClass="error" styleId="messageSubject" style="width: 95%" />

			<s:textarea name="messageBody" styleId="messageBody"
				styleClass="mceTextArea" style="width: 100%;"
				label="%{getText('privatemessages.body')}">
			</s:textarea>

			<s:submit cssClass="btn btn-inverse"
				onclick="this.disabled=true; this.value='Sending Message'; this.form.submit();" key="privatemessages.send"/>

		</s:form>
	</table>
</fieldset>

<script type="text/javascript">
	var AC = new dmsAutoComplete('memberSearch', 'searchDiv');
	AC.clearField = false;
	AC.ajaxTarget = 'AjaxSearchMember.do';
</script>