<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="js/mceTextArea.js"></script>

<fieldset class="fieldsetAppli">
	<legend class="legendHome">
		<bean:message key="events.title.create" />
	</legend>

	<table id="UpdateEvent"
		class="inLineTableDashBoardFieldset fieldsetTable">
		<html:form action="/UpdateEvent">
			<tr>
				<td><label for="eventName"> <bean:message
							key="events.form.title" />
				</label></td>
				<td><html:text property="eventName" styleId="eventName"
						errorStyleClass="error" /> <html:hidden property="eventId"
						styleId="id" /> <logic:messagesPresent property="eventName">
						<div class="errorMessage">
							<html:errors property="eventName" />
						</div>
					</logic:messagesPresent></td>
			</tr>
			<tr>
				<td colspan="2"><c:import url="/InterestCheckBoxes.do" /></td>
			</tr>

			<tr>
				<td><label for="eventDescription"> <bean:message
							key="events.form.description" /> :
				</label></td>
				<td><html:textarea property="eventDescription"
						styleId="eventDescription" errorStyleClass="error"
						styleClass="mceTextArea" style="width: 100%;" /> <logic:messagesPresent
						property="eventDescription">
						<div class="errorMessage">
							<html:errors property="eventDescription" />
						</div>
					</logic:messagesPresent></td>
			</tr>

			<tr>
				<td><label for="eventAddress"> <bean:message
							key="events.form.address" /> :
				</label></td>
				<td><html:text property="eventAddress" styleId="eventAddress"
						errorStyleClass="error" /> <logic:messagesPresent
						property="eventAddress">
						<div class="errorMessage">
							<html:errors property="eventAddress" />
						</div>
					</logic:messagesPresent></td>
			</tr>

			<tr>
				<td><label for="eventCity"> <bean:message
							key="events.form.city" /> :
				</label></td>
				<td><html:text property="eventCity" styleId="eventCity"
						errorStyleClass="error" /> <logic:messagesPresent
						property="eventCity">
						<div class="errorMessage">
							<html:errors property="eventCity" />
						</div>
					</logic:messagesPresent></td>
			</tr>

			<tr>
				<td><label for="eventBeginDate"> <bean:message
							key="events.form.beginDate" /> :
				</label></td>
				<td><html:text property="eventBeginDate"
						styleId="eventBeginDate" errorStyleClass="error" /> <logic:messagesPresent
						property="eventBeginDate">
						<div class="errorMessage">
							<html:errors property="eventBeginDate" />
						</div>
					</logic:messagesPresent></td>
			</tr>

			<tr>
				<td><label for="eventEndDate"> <bean:message
							key="events.form.endDate" /> :
				</label></td>
				<td><html:text property="eventEndDate" styleId="eventEndDate"
						errorStyleClass="error" /> <logic:messagesPresent
						property="eventEndDate">
						<div class="errorMessage">
							<html:errors property="eventEndDate" />
						</div>
					</logic:messagesPresent></td>
			</tr>

			<tr>
				<td><label for="eventRecallTime"> <bean:message
							key="events.form.recall" /> :
				</label></td>
				<td><html:text property="eventRecallTime"
						styleId="eventRecallTime" errorStyleClass="error" /> <html:select
						property="eventRecallTypeTime" styleId="eventRecallTypeTime">
						<html:option value="minute">
							<bean:message key="events.form.recall.minute" />
						</html:option>
						<html:option value="hour">
							<bean:message key="events.form.recall.hour" />
						</html:option>
						<html:option value="day">
							<bean:message key="events.form.recall.day" />
						</html:option>
					</html:select> <logic:messagesPresent property="eventRecallTime">
						<div class="errorMessage">
							<html:errors property="eventRecallTime" />
						</div>
					</logic:messagesPresent></td>
			</tr>
			
			<tr>
				<td colspan="2" align="right"><html:submit styleClass="button">
						<bean:message key="events.button.update" />
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

		$('#eventBeginDate').datetimepicker();
		$('#eventEndDate').datetimepicker();

	});
</script>


