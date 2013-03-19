<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="js/mceTextArea.js"></script>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="events.title.create" />
	</legend>
	<s:form action="CreateEvent" theme="css_xhtml">
		<table id="CreateEvent" class="inLineTable tableStyle">


			<tr>
				<td><label for="eventDescription"> <s:text
							name="events.form.description" />
				</label></td>
				<td><s:textarea property="eventDescription"  name="eventDescription" id="eventDescription"
						styleId="eventDescription" errorStyleClass="error"
						styleClass="mceTextArea" style="width: 100%;" /></td>
			</tr>

			<tr>
				<td><label for="eventName"> <s:text
							name="events.form.title" />
				</label></td>
				<td><s:textfield property="eventName" styleId="eventName" name="eventName" id="eventName"
						errorStyleClass="error" /></td>

			</tr>

			<tr>
				<td></td>
				<td><s:include value="<s:url action='InterestCheckBoxes'/>" />
				</td>
			</tr>


			<tr>
				<td><label for="eventAddress"> <s:text
							name="events.form.address" />
				</label></td>
				<td><s:textfield name="eventAddress" id="eventAddress" property="eventAddress" styleId="eventAddress"
						errorStyleClass="error" /></td>
			</tr>

			<tr>
				<td><label for="eventCity"> <s:text
							name="events.form.city" />
				</label></td>
				<td><s:textfield name="eventCity" id="eventCity" property="eventCity" styleId="eventCity"
						errorStyleClass="error" /></td>
			</tr>

			<tr>
				<td><label for="eventBeginDate"> <s:text
							name="events.form.beginDate" />
				</label></td>
				<td><s:textfield name="eventBeginDate" id="eventBeginDate" />
				</td>
			</tr>

			<tr>
				<td><label for="eventEndDate"> <s:text
							name="events.form.endDate" />
				</label></td>
				<td><s:textfield name="eventEndDate" id="eventEndDate" /></td>
			</tr>

			<tr>
				<td><label for="eventRecallTime"> <s:text
							name="events.form.recall" />
				</label></td>
				<td><s:textfield name="eventRecallTime" id="eventRecallTime"
						value="%{recallDefaultValue}" /> <s:select
						name="eventRecallTypeTime"
						list="#{'minute':getText('events.form.recall.minute'),'hour':getText('events.form.recall.hour'),'day':getText('events.form.recall.day')}" /></td>
			</tr>

			<tr>
				<td colspan="2" class="tableButton"><s:submit
						value="%{getText('events.button.create')}"
						styleClass="button btn btn-inverse">
					</s:submit></td>
			</tr>
		</table>

	</s:form>
</fieldset>

<script type="text/javascript" src="js/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript">
	$(function() {
		$.datepicker.setDefaults($.extend({
			minDate : 0,
			dateFormat : 'dd/mm/yy',
			showOn : 'both',
			buttonImage : 'images/calendar.gif',
			buttonImageOnly : true,
			showMonthAfterYear : false
		}));
		$.datepicker.setDefaults($.datepicker.regional['fr']);

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


