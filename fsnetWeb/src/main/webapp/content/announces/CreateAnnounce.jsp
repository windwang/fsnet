<%@page contentType="text/html;charset=ISO-8859-1" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="js/mceTextArea.js"></script>
<script type="text/javascript" src="js/consultationUtils.js"></script>

<!--  <html:javascript formName="/CreateAnnounce" /> --> <!-- Dragon -->
<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="announce.title.create" />
	</legend>

	<table id="CreateAnnounce" class="inLineTable tableStyle">
		<s:form action="CreateAnnounce">
			<tr>
				<td><label for="announceTitle"><s:text
							name="announce.form.title" /></label></td>
				<td><s:textfield property="announceTitle"
						styleId="announceTitle"/>	
				 <%-- <c:import url="/InterestCheckBoxes.do" /> --%>
				</td> 
			</tr>

			<tr>
				<td><label for="announceContent"><s:text
							name="announce.form.content" /> </label></td>
				<td><s:textarea cols="40" rows="8" property="announceContent"
						styleId="announceContent" styleClass="mceTextArea"
						style="width: 100%;" /> </td>
			</tr>

			<tr>
				<td><label for="announceExpiryDate"><s:text
							name="announce.form.date" /></label></td>
							
				<td><s:textfield name="announceExpiryDate"
						id="announceExpiryDate" disabled="false" /></td>
			</tr>

			<tr>
				<td><label for="groupsListLeft"><s:text
							name="announces.title.droit" /></label></td>
				<td><table class="inLineTable tableStyle">
						<c:if test="${errorAnnounceRights}">
							<p class="errorMessage">
								<s:text name="announces.droits.errorRights" />
							</p>
						</c:if>
						<tr>
							<td rowspan="2">
								<div>

									<s:text name="announces.droits.groupsNoRights" />
								</div> 	
								<s:select styleClass="select" list="%{#attr.allUnderGroupsNoRights}" listValue="name"
										name="groupsListLeft" />
							</td>
							<td><s:submit type="button"
									onclick="return Deplacer(this.form.groupsListLeft,this.form.groupsListRight)">
									<s:text name="groups.addMembers" />
								</s:submit></td>
							<td rowspan="2">
								<div>
									<s:text name="consultation.droits.groupsRights" />
								</div> 
								<s:select styleClass="select" list="%{#attr.allUnderGroupsNoRights}" listValue="name"
										name="groupsListRight" />
							</td>
						</tr>
						<tr>
							<td><s:submit type="button"
									onclick="return Deplacer(this.form.groupsListRight,this.form.groupsListLeft)">
									<s:text name="groups.removeMembers" />
								</s:submit></td>
						</tr>
					</table>
			<tr>
				<td colspan="2" class="tableButton"><s:submit
						styleClass="btn btn-inverse"
						>
						<s:text name="announce.button.create" />
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

		$('#announceExpiryDate').datetimepicker();

	});
</script>
