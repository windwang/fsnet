<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="js/mceTextArea.js"></script>


<fieldset class="fieldsetAppli">
	<legend class="legendHome">
		<bean:message key="announce.title.modify" />
	</legend>
	<table id="ModifyAnnounce"
		class="inLineTable fieldsetTableAppli">
		<html:form action="/ModifyAnnounce">
			<html:hidden property="idAnnounce" />
			
			<tr>
				<td><label for="announceTitle"><bean:message
							key="announce.form.title" /></label></td>
				<td><html:text property="announceTitle" styleId="announceTitle" />
					<div class="errorMessage">
						<html:errors property="announceTitle" />
					</div></td>
			</tr>

			<tr>
				<td><label for="announceContent"><bean:message
							key="announce.form.content" /> </label></td>
				<td><html:textarea cols="40" rows="8"
						property="announceContent" styleId="announceContent"
						styleClass="mceTextArea" style="width: 100%;" />
					<div class="errorMessage">
						<html:errors property="announceContent" />
					</div></td>
			</tr>

			<tr>
				<td><label for="announceExpiryDate"><bean:message
							key="announce.form.date" /></label></td>
				<td><html:text property="announceExpiryDate"
						styleId="announceExpiryDate" /><div class="errorMessage">
						<html:errors property="announceExpiryDate" />
					</div></td>
			</tr>

			<tr>
				<td colspan="2" class="tableButton"><html:submit styleClass="button">
						<bean:message key="announce.button.modify" />
					</html:submit></td>
			</tr>

		</html:form>
	</table>
</fieldset>

<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.17.custom.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-i18n.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-timepicker-addon.js"></script>

<script type="text/javascript">
	$(function() {

		$.datepicker.setDefaults($.datepicker.regional['fr']);
		$.datepicker.setDefaults($.extend({
			minDate : 0,
			dateFormat : 'dd/mm/yy',
			showOn : 'both',
			buttonImage : 'images/calendar.gif',
			buttonImageOnly : true,
			showMonthAfterYear : false
		}));
		$.timepicker.regional['fr'] = {
			timeOnlyTitle : 'Temps',
			timeText : 'Temps',
			hourText : 'Heure',
			minuteText : 'Minute',
			secondText : 'Seconde',
			millisecText : 'milliseconde',
			currentText : 'Maintenant',
			closeText : 'Ok',
			ampm : false,
			timeFormat : 'hh:mm',
		};
		$.timepicker.setDefaults($.timepicker.regional['fr']);

		$('#announceExpiryDate').datetimepicker();

	});
</script>

