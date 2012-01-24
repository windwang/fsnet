<%-- 
	author : Bouragba Mohamed
	source : /fsnetWeb/src/main/webapp/content/announces/DisplayAnnounce.jsp (Mehdi Benzaghar)
 --%>

<%@page contentType="text/html;charset=ISO-8859-1" language="java"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="js/mceTextArea.js"></script>


<html:javascript formName="/CreateAnnounce" />
<h3>
	<bean:message key="announce.create.title"/>
</h3>
<html:form action="/CreateAnnounce">
	<table id="CreateAnnounce">
		<tr>
			<td colspan="2"><html:messages id="message" /> <div class="errorMessage"><html:errors /></div>
			</td>
		</tr>
		<tr>
			<td><label for="announceTitle"><bean:message
				key="announce.title" /></label></td>
			<td><html:text property="announceTitle" styleId="announceTitle" />
			<c:import url="/InterestCheckBoxes.do" /></td>
		</tr>

		<tr>
			<td><label for="announceContent"><bean:message
				key="announce.content" /> </label></td>
			<td><html:textarea cols="40" rows="8" property="announceContent"
				styleId="announceContent" styleClass="mceTextArea"
				style="width: 100%;" /></td>
		</tr>
		<tr>
			<td><label for="announceExpiryDate">Date :</label></td>
			<td><html:text property="announceExpiryDate"
				styleId="announceExpiryDate" disabled="false" /> <html:submit
				styleClass="button">
				<bean:message key="announce.createAnnounce" />
			</html:submit></td>
		</tr>

	</table>
</html:form>

<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.7.2.custom.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-i18n.min.js"></script>
<script type="text/javascript">
	$(function() {
		$.datepicker.setDefaults($.extend( {
			minDate : 0,
			dateFormat : 'dd/mm/yy',
			showOn : 'button',
			buttonImage : 'images/calendar.gif',
			buttonImageOnly : true,
			showMonthAfterYear : false
		}));
		$("#announceExpiryDate").datepicker($.datepicker.regional['fr']);
	});
</script>
