<%@page contentType="text/html;charset=ISO-8859-1" language="java"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<script type="text/javascript" src="js/tiny_mce/tiny_mce.js"></script>
<script type="text/javascript" src="js/mceTextArea.js"></script>
<script type="text/javascript" src="js/consultationUtils.js"></script>

<html:javascript formName="/CreateAnnounce" />
<fieldset class="fieldsetAppli">
	<legend class="legendHome">
		<bean:message key="announce.title.create" />
	</legend>

	<table id="CreateAnnounce" class="inLineTable fieldsetTableAppli">
		<html:form action="/CreateAnnounce">
			<tr>
				<td><label for="announceTitle"><bean:message
							key="announce.form.title" /></label></td>
				<td><html:text property="announceTitle" styleId="announceTitle" />
					<div class="errorMessage">
						<html:errors property="announceTitle" />
					</div> <c:import url="/InterestCheckBoxes.do" /></td>
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
						styleId="announceExpiryDate" disabled="false" />
					<div class="errorMessage">
						<html:errors property="announceExpiryDate" />
					</div></td>
			</tr>

			<tr>
				<td><label for="groupsListLeft"><bean:message
							key="announces.title.droit" /></label></td>
				<td><table class="inLineTable fieldsetTableAppli">
						<c:if test="${errorAnnounceRights}">
							<p class="errorMessage">
								<bean:message key="announces.droits.errorRights" />
							</p>
						</c:if>
						<tr>
							<td rowspan="2">
								<div>
									<bean:message key="announces.droits.groupsNoRights" />
								</div> <html:select property="groupsListLeft" styleClass="select"
									size="5" multiple="multiple">

									<c:forEach var="socialGroup" items="${allUnderGroupsNoRights}">

										<c:if test="${socialGroup.isEnabled}">
											<html:option value="${socialGroup.name}">${socialGroup.name}</html:option>
										</c:if>

									</c:forEach>
								</html:select>
							</td>
							<td><html:button property=""
									onclick="return Deplacer(this.form.groupsListLeft,this.form.groupsListRight)">
									<bean:message key="groups.addMembers" />
								</html:button></td>
							<td rowspan="2">
								<div>
									<bean:message key="consultation.droits.groupsRights" />
								</div> <html:select property="groupsListRight" styleClass="select"
									size="5" multiple="multiple">

									<c:forEach var="socialGroup" items="${allUnderGroupsRights}">
										<html:option value="${socialGroup.name}">${socialGroup.name}</html:option>
									</c:forEach>
								</html:select>
							</td>
						</tr>
						<tr>
							<td><html:button property=""
									onclick="return Deplacer(this.form.groupsListRight,this.form.groupsListLeft)">
									<bean:message key="groups.removeMembers" />
								</html:button></td>
						</tr>
					</table></td>
			</tr>
			<tr>
				<td colspan="2" class="tableButton"><html:submit
						styleClass="button" onclick="return valideGroupToAnnounce()">
						<bean:message key="announce.button.create" />
					</html:submit></td>
			</tr>
		</html:form>
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
