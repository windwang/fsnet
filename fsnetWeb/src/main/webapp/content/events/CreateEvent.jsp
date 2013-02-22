<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="js/mceTextArea.js"></script>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="events.title.create" />
	</legend>
	<table id="CreateEvent" class="inLineTable tableStyle">
		<s:form action="/CreateEvent">
			<tr>
				<td><label for="eventName"> <s:textfield
							key="events.form.title" />
				</label></td>
				<td><s:textfield property="eventName" styleId="eventName"
						errorStyleClass="error" /> <!-- 						 <logic:messagesPresent	property="eventName"> 
					<div class="errorMessage">
						<html:errors property="eventName" />
					</div>
 					 </logic:messagesPresent> --></td>
			</tr>
			<tr>
				<td></td>
				<td><c:import url="/InterestCheckBoxes.do" /></td>
			</tr>

			<tr>
				<td><label for="eventDescription"> <s:textfield
							key="events.form.description" />
				</label></td>
				<td><s:textarea property="eventDescription"
						styleId="eventDescription" errorStyleClass="error"
						styleClass="mceTextArea" style="width: 100%;" /> <!-- 						<logic:messagesPresent -->
					<!-- 						property="eventDescription"> --> <!--<div class="errorMessage">
						<html:errors property="eventDescription" />
					</div>  					</logic:messagesPresent> --></td>
			</tr>

			<tr>
				<td><label for="eventAddress"> <s:textfield
							key="events.form.address" />
				</label></td>
				<td><s:textfield property="eventAddress" styleId="eventAddress"
						errorStyleClass="error" /> <!-- 						<logic:messagesPresent -->
					<!-- 						property="eventAddress"> --> <!-- 					<div class="errorMessage"> -->
					<!-- 						<html:errors property="eventAddress" /> --> <!-- 					</div>  -->
					<!-- 										</logic:messagesPresent> --></td>
			</tr>

			<tr>
				<td><label for="eventCity"> <s:textfield
							key="events.form.city" />
				</label></td>
				<td><s:textfield property="eventCity" styleId="eventCity"
						errorStyleClass="error" /> <!-- 						<logic:messagesPresent -->
					<!-- 						property="eventCity"> --> <!-- 					<div class="errorMessage"> -->
					<!-- 						<html:errors property="eventCity" /> --> <!-- 					</div>  -->
					<!-- 										</logic:messagesPresent> --></td>
			</tr>

			<tr>
				<td><label for="eventBeginDate"> <s:textfield
							key="events.form.beginDate" />
				</label></td>
				<td><s:textfield property="eventBeginDate"
						styleId="eventBeginDate" errorStyleClass="error" /> <!-- 						<logic:messagesPresent -->
					<!-- 						property="eventBeginDate"> --> <!-- <div class="errorMessage">
						<html:errors property="eventBeginDate" />
					</div>  					</logic:messagesPresent> --></td>
			</tr>

			<tr>
				<td><label for="eventEndDate"> <s:textfield
							key="events.form.endDate" />
				</label></td>
				<td><s:textfield property="eventEndDate" styleId="eventEndDate"
						errorStyleClass="error" /> <!-- 						<logic:messagesPresent -->
					<!-- 						property="eventEndDate"> --> <!-- <div class="errorMessage">
						<html:errors property="eventEndDate" />
					</div> 				</logic:messagesPresent> --></td>
			</tr>

			<tr>
				<td><label for="eventRecallTime"> <s:textfield
							key="events.form.recall" />
				</label></td>
				<td><s:textfield property="eventRecallTime"
						styleId="eventRecallTime" errorStyleClass="error"
						value="%{recallDefaultValue}" /> <!-- 						events.form.recall.hour_minute_day a creer !!!! -->
					<s:select list="events.form.recall.hour_minute_day"
						property="eventRecallTypeTime" styleId="eventRecallTypeTime">
					</s:select> <!-- 					<logic:messagesPresent property="eventRecallTime"> --> <!--<div class="errorMessage">
						<html:errors property="eventRecallTime" />
					</div>  					</logic:messagesPresent> -->
			</tr>

			<tr>
				<td colspan="2" class="tableButton"><s:submit
						styleClass="button btn btn-inverse">
						<s:text name="events.button.create" />
					</s:submit></td>
			</tr>
		</s:form>
	</table>
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


