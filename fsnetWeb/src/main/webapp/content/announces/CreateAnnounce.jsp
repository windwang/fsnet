<%-- 
    Document   : CreateAnnounce
    Created on : 18 janv. 2010, 18:06:12
    Author     : Mehdi Benzaghar <mehdi.benzaghar at gmail.com>
--%>

<%@page contentType="text/html;charset=ISO-8859-1" language="java"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="js/mceTextArea.js"></script>


<html:javascript formName="/CreateAnnounce" />
<h3>
	<bean:message key="announce.create.title"/>
</h3>
<table  class="inLineTable"><tr><td>
<html:form action="/CreateAnnounce">
	<table id="CreateAnnounce">
		<tr>
			<td><label for="announceTitle"><bean:message
				key="announce.title" /></label></td>
			<td><html:text property="announceTitle" styleId="announceTitle" />
			<c:import url="/InterestCheckBoxes.do" /></td>
		</tr>
		<logic:messagesPresent property="announceTitle">
			<tr class="errorMessage">
				<td colspan="2"><html:errors property="announceTitle" /></td>
			</tr>
		</logic:messagesPresent>
		<tr>
			<td><label for="announceContent"><bean:message
				key="announce.content" /> </label></td>
			<td><html:textarea cols="40" rows="8" property="announceContent"
				styleId="announceContent" styleClass="mceTextArea"
				style="width: 100%;" /></td>
		</tr>
		<logic:messagesPresent property="announceContent">
			<tr class="errorMessage">
				<td colspan="2"><html:errors property="announceContent" /></td>
			</tr>
		</logic:messagesPresent>
		<tr>
			<td><label for="announceExpiryDate">Date :</label></td>
			<td><html:text property="announceExpiryDate"
				styleId="announceExpiryDate" disabled="false" /><html:submit
				styleClass="button">
				<bean:message key="announce.createAnnounce" />
			</html:submit></td>
		</tr>
		<logic:messagesPresent property="announceExpiryDate">
			<tr class="errorMessage">
				<td colspan="2"><html:errors property="announceExpiryDate" /></td>
			</tr>
		</logic:messagesPresent>

	</table>
</html:form>
</td></tr></table>

<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.17.custom.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-i18n.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-timepicker-addon.js"></script>

<script type="text/javascript">
$(function() {

    $.datepicker.setDefaults($.datepicker.regional['fr']);
    $.datepicker.setDefaults($.extend(
            {
                minDate: 0,
                dateFormat: 'dd/mm/yy',
                showOn: 'both',
                buttonImage: 'images/calendar.gif',
                buttonImageOnly: true,
                showMonthAfterYear: false
            }));
    $.timepicker.regional['fr'] = {
    		timeOnlyTitle: 'Temps',
    		timeText: 'Temps',
    		hourText: 'Heure',
    		minuteText: 'Minute',
    		secondText: 'Seconde',
    		millisecText: 'milliseconde',
    		currentText: 'Maintenant',
    		closeText: 'Ok',
    		ampm: false,
    		timeFormat: 'hh:mm',
    	};
    $.timepicker.setDefaults($.timepicker.regional['fr']);
    
	$('#announceExpiryDate').datetimepicker();

});
</script> 
