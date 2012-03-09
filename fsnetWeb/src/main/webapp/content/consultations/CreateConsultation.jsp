<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<script type="text/javascript" src="js/consultationUtils.js"></script>

<fieldset class="fieldsetAppli">
	<legend class="legendHome">
		<bean:message key="consultations.title.create" />
	</legend>

	<table class="inLineTableDashBoardFieldset fieldsetTable">
		<html:form action="CreateConsultation" method="post">
			<tr>
				<td>
					<fieldset class="inLinefieldset">
						<legend>
							<bean:message key="consultations.title.information" />
						</legend>

						<div class="errorMessage">
							<html:errors property="consultationTitle" />
						</div>

						<table>
							<tr>
								<td><label for="consultationTitle"><bean:message
											key="consultations.form.title" /> : </label></td>
								<td><html:text property="consultationTitle"
										styleId="consultationTitle" />
							</tr>

							<tr>
								<td><label for="consultationDescription"><bean:message
											key="consultations.form.description" /> : </label></td>
								<td><html:text property="consultationDescription"
										styleId="consultationDescription" /></td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>

			<tr>
				<td>

					<fieldset class="inLinefieldset">
						<legend>
							<bean:message key="consultations.title.choix" />
						</legend>

						<table>
							<tr>
								<td><label for="radioButtonText"><bean:message
											key="consultations.form.textAlternative"></bean:message></label> <input
									type="radio" class="alternativeRadio" name="alternativeRadio"
									id="radioButtonText" checked="checked" /></td>

								<td><label for="radioButtonDate"><bean:message
											key="consultations.form.dateAlternative"></bean:message></label><input
									type="radio" class="alternativeRadio" name="alternativeRadio"
									id="radioButtonDate" /></td>
							</tr>
						</table>

						<html:checkbox onclick="displayChoicesOption(true)"
							property="nbVotersPerChoiceBox" styleId="nbVotersPerChoiceBox" />
						<label for="nbVotersPerChoiceBox"><bean:message
								key="consultations.form.limitVotersNumberPerChoice" /></label>
						<html:text styleId="nbVotersPerChoice" onkeyup="updateMaxVoters()"
							property="nbVotersPerChoice" />

						<div class="plusMoins">
							<br /> <input type="button" onclick="removeChoice()"
								class="moins" /><input type="button" onclick="addChoice()"
								class="plus" /><br />
						</div>

						<c:if test="${errorChoice}">
							<p class="errorMessage">
								<bean:message key="consultations.error.choice" />
							</p>
						</c:if>
						<c:if test="${errorMaxVotersPerChoice}">
							<p class="errorMessage">
								<bean:message key="consultations.error.maxVotersPerChoice" />
							</p>
						</c:if>

						<table id="choicesTab">
							<c:forEach begin="1" end="3" var="i">
								<tr>
									<td><label for="consultationChoice${i}"><span
											class="i18nChoice"><bean:message
													key="consultations.form.choice" /></span> ${i} : </label></td>

									<td><html:text property="consultationChoice"
											styleClass="consultationChoice"
											styleId="consultationChoice${i}" value="" /></td>
								</tr>
							</c:forEach>
						</table>
					</fieldset>
				</td>
			</tr>

			<tr>
				<td>

					<fieldset class="inLinefieldset">
						<legend>
							<bean:message key="consultations.title.droit" />
						</legend>
					</fieldset>
				</td>
			</tr>

			<tr>
				<td>
					<fieldset class="inLinefieldset">
						<legend>
							<bean:message key="consultations.title.typeConsultation" />
						</legend>

						<div class="errorMessage">
							<html:errors property="consultationIfNecessaryWeight" />
						</div>

						<table>
							<tr>
								<td><html:radio property="consultationType" value="YES_NO"
										styleId="YES_NO" /></td>
								<td><label for="YES_NO"><bean:message
											key="consultations.form.typeYesNo" /></label></td>
							</tr>

							<tr>
								<td><html:radio property="consultationType"
										value="YES_NO_OTHER" styleId="YES_NO_OTHER" /></td>
								<td><label for="YES_NO_OTHER"><bean:message
											key="consultations.form.typeYesNoOther" /></label></td>
							</tr>

							<tr>
								<td><html:radio property="consultationType"
										value="YES_NO_IFNECESSARY" styleId="YES_NO_IFNECESSARY" /></td>

								<td><label for="YES_NO_IFNECESSARY"><bean:message
											key="consultations.form.typeYesNoIfNecessary" /></label><label
									for="consultationIfNecessaryWeight"> <bean:message
											key="consultations.form.ifNecessaryWeight" /></label> <html:text
										property="consultationIfNecessaryWeight"
										styleId="consultationIfNecessaryWeight" disabled="true" /></td>
							</tr>

							<tr>
								<td><html:radio property="consultationType"
										value="PREFERENCE_ORDER" styleId="PREFERENCE_ORDER" /></td>
								<td><label for="PREFERENCE_ORDER"><bean:message
											key="consultations.form.typePreferenceOrder" /></label></td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>

			<tr>
				<td>

					<fieldset class="inLinefieldset">
						<legend>
							<bean:message key="consultations.title.option" />
						</legend>

						<div class="errorMessage">
							<html:errors property="minChoicesVoter" />
						</div>

						<div class="errorMessage">
							<html:errors property="maxChoicesVoter" />
						</div>

						<div class="errorMessage">
							<html:errors property="closingAtMaxVoters" />
						</div>

						<div class="errorMessage">
							<html:errors property="deadline" />
						</div>

						<c:if test="${errorChoicesVoter}">
							<p>
								<bean:message key="consultations.error.choicesVoter" />
							</p>
						</c:if>

						<table>
							<tr>
								<td><html:checkbox property="limitChoicesPerVoter"
										styleId="limitChoicesPerVoter" /></td>
								<td><label for="limitChoicesPerVoter"><bean:message
											key="consultations.form.limitChoicesPerVoter" /> </label></td>
							</tr>

							<tr>
								<td><label for="minChoicesVoter">Min : </label></td>
								<td><html:text property="minChoicesVoter"
										styleId="minChoicesVoter" value="1" /> <label
									for="maxChoicesVoter">Max : </label> <html:text
										property="maxChoicesVoter" styleId="maxChoicesVoter" value="1" /></td>
							</tr>
						</table>

						<table>
							<tr>
								<td><html:checkbox property="showBeforeAnswer"
										styleId="showBeforeAnswer" /></td>
								<td><label for="showBeforeAnswer"><bean:message
											key="consultations.form.showBeforeAnswer" /></label></td>
							</tr>

							<tr>
								<td><html:checkbox property="showBeforeClosing"
										styleId="showBeforeClosing" /></td>
								<td><label for="showBeforeClosing"><bean:message
											key="consultations.form.showBeforeClosing" /></label></td>
							</tr>
						</table>

						<table>
							<tr>
								<td><label for="deadline"><bean:message
											key="consultations.form.deadline" /> : </label></td>
								<td><html:text property="deadline" styleId="deadline" /></td>
							</tr>

							<tr>
								<td><label for="closingAtMaxVoters"><bean:message
											key="consultations.form.closingAtMaxVoters" /> : </label></td>
								<td><html:text styleId="closingAtMaxVoters"
										property="closingAtMaxVoters" /></td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>

			<tr>
				<td align="right"><html:submit styleClass="button"
						styleId="buttonConsultation">
						<bean:message key="consultations.button.create" />
					</html:submit></td>
			</tr>
		</html:form>
	</table>
</fieldset>


<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.7.2.custom.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-i18n.min.js"></script>
<script type="text/javascript">
	$(function() {
		$.datepicker.setDefaults($.extend({
			minDate : 0,
			dateFormat : 'dd/mm/yy',
			showOn : 'button',
			buttonImage : 'images/calendar.gif',
			buttonImageOnly : true,
			showMonthAfterYear : false
		}));

		$("#deadline").datepicker($.datepicker.regional['fr']);

		if ($("#YES_NO_IFNECESSARY").attr('checked')) {
			$("#consultationIfNecessaryWeight").attr("disabled", false);
		} else {
			$("#consultationIfNecessaryWeight").attr("disabled", true);
		}

		$("#YES_NO_IFNECESSARY").click(function(e) {
			$("#consultationIfNecessaryWeight").attr("disabled", false);
		});

		$("#YES_NO").click(function(e) {
			$("#consultationIfNecessaryWeight").attr("disabled", true);
		});

		$("#YES_NO_OTHER").click(function(e) {
			$("#consultationIfNecessaryWeight").attr("disabled", true);
		});

		$("#PREFERENCE_ORDER").click(function(e) {
			$("#consultationIfNecessaryWeight").attr("disabled", true);
		});

		$('.alternativeRadio').click(function(e) {
			displayChoicesOption(false);
		});

		$("#limitChoicesPerVoter").click(function(e) {
			displayLimitChoicesPerVoter();
		});

		displayChoicesOption(true);

		displayLimitChoicesPerVoter();

	});
</script>
