<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript" src="js/consultationUtils.js"></script>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="consultations.title.create"></s:text>
	</legend>

	<table class="inLineTable tableStyle">
		<s:form action="CreateConsultation" method="POST"
			enctype="multipart/form-data">
			<tr>
				<td>
					<fieldset class="fieldsetCadre">
						<legend>
							<s:text name="consultations.title.information" />
						</legend>

						<table class="tableStyle">
							<tr>
								<td><label for="consultationTitle"><s:text
											name="consultations.form.title" /></label></td>
								<td><s:textfield property="consultationTitle"
										styleId="consultationTitle" /></td>
							</tr>

							<tr>
								<td><label for="consultationDescription"> <s:text
											name="consultations.form.description" /> :
								</label></td>
								<td><s:textfield property="consultationDescription"
										styleId="consultationDescription" /></td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>

			<tr>
				<td>
					<fieldset class="fieldsetCadre">
						<legend>
							<s:text name="consultations.title.choix" />
						</legend>

						<table class="tableStyle">
							<tr>
								<td><label for="radioButtonText"><s:text
											name="consultations.form.textAlternative"></s:text></label> <input
									type="radio" class="alternativeRadio" name="alternativeRadio"
									id="radioButtonText" checked="checked" /></td>

								<td><label for="radioButtonDate"><s:text
											name="consultations.form.dateAlternative"></s:text></label><input
									type="radio" class="alternativeRadio" name="alternativeRadio"
									id="radioButtonDate" /></td>
							</tr>
						</table>

						<s:checkbox onclick="displayChoicesOption(true)"
							property="nbVotersPerChoiceBox" styleId="nbVotersPerChoiceBox" />
						<label for="nbVotersPerChoiceBox"><s:text
								name="consultations.form.limitVotersNumberPerChoice" /></label>
						<s:textfield styleId="nbVotersPerChoice"
							onkeyup="updateMaxVoters()" property="nbVotersPerChoice" />

						<div class="plusMoins">
							<br /> <input type="button" onclick="removeChoice()"
								class="moins" /><input type="button" onclick="addChoice()"
								class="plus" /><br />
						</div>

						<div id="errorChoice">
							<div class="errorMessage">
								<s:text name="consultations.error.choice" />
							</div>
						</div>

						<c:if test="${errorMaxVotersPerChoice}">
							<div class="errorMessage">
								<s:text name="consultations.error.maxVotersPerChoice" />
							</div>
						</c:if>

						<s:hidden name="consultationChoice" styleId="consultationChoice" />
						<s:hidden property="maxVoters" styleId="maxVoters" />
						<table id="choicesTab" class="tableStyle">
							<c:forEach begin="1" end="3" var="i">
								<tr>
									<td><label for="consultationChoice${i}"><span
											class="i18nChoice"><s:text
													name="consultations.form.choice" /></span> ${i} : </label></td>

									<td><input type="text" id="consultationChoice${i}"
										class="consultationChoice" value="" /></td>
								</tr>
							</c:forEach>
						</table>
					</fieldset>
				</td>
			</tr>

			<tr>
				<td>

					<fieldset class="fieldsetCadre">
						<legend>
							<s:text name="consultations.title.droit" />
						</legend>
						<c:if test="${errorRights}">
							<p class="errorMessage">
								<s:text name="consultation.droits.errorRights" />
							</p>
						</c:if>
						<table class="tableStyle">
							<tr>
								<td ROWSPAN="2">
									<div>
										<s:text name="consultation.droits.groupsNoRights" />
									</div> <s:select list="%{allUnderGroupsNoRights}" listValue="name"
										name="groupsListLeft" />
								</td>
								<td><s:submit type="button"
										onclick="return Deplacer(this.form.groupsListLeft,this.form.groupsListRight)">
										<s:text name="groups.addMembers" />
									</s:submit></td>
								<td ROWSPAN="2">
									<div>
										<s:text name="consultation.droits.groupsRights" />
									</div> <s:select list="%{allUnderGroupsNoRights}" listValue="name"
										name="groupsListRight" />
								</td>
							</tr>
							<tr>
								<td><s:submit type="button"
										onclick="return Deplacer(this.form.groupsListRight,this.form.groupsListLeft)"></s:submit>
								</td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>

			<tr>
				<td>
					<fieldset class="fieldsetCadre">
						<legend>
							<s:text name="consultations.title.typeConsultation" />
						</legend>



						<table class="tableStyle">
							<tr>
								<td>
								<td><s:radio name="consultationType" list="{YES_NO}"
										styleId="YES_NO" /></td>
								<td><label for="YES_NO"><s:text
											name="consultations.form.typeYesNo" /></label></td>
							</tr>

							<tr>
								<td><s:radio name="consultationType" list="{YES_NO_OTHER}"
										styleId="YES_NO_OTHER" /></td>
								<td><label for="YES_NO_OTHER"><s:text
											name="consultations.form.typeYesNoOther" /></label></td>
							</tr>

							<tr>
								<td><s:radio name="consultationType"
										list="{YES_NO_IFNECESSARY}" styleId="YES_NO_IFNECESSARY" /></td>

								<td><label for="YES_NO_IFNECESSARY"><s:text
											name="consultations.form.typeYesNoIfNecessary" /></label><label
									for="consultationIfNecessaryWeight"> <s:text
											name="consultations.form.ifNecessaryWeight" /></label> <s:textfield
										property="consultationIfNecessaryWeight"
										styleId="consultationIfNecessaryWeight" disabled="true" /></td>
							</tr>

							<tr>
								<td><s:radio name="consultationType"
										list="{PREFERENCE_ORDER}" styleId="PREFERENCE_ORDER" /></td>
								<td><label for="PREFERENCE_ORDER"><s:text
											name="consultations.form.typePreferenceOrder" /></label></td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>

			<tr>
				<td>

					<fieldset class="fieldsetCadre">
						<legend>
							<s:text name="consultations.title.option" />
						</legend>


						<c:if test="${errorChoicesVoter}">
							<p>
								<s:text name="consultations.error.choicesVoter" />
							</p>
						</c:if>

						<table class="tableStyle">
							<tr>
								<td><s:checkbox property="limitChoicesPerVoter"
										styleId="limitChoicesPerVoter" /></td>
								<td><label for="limitChoicesPerVoter"><s:text
											name="consultations.form.limitChoicesPerVoter" /> </label></td>
							</tr>

							<tr>
								<td><label for="minChoicesVoter">Min : </label></td>
								<td><s:textfield property="minChoicesVoter"
										styleId="minChoicesVoter" value="1" /> <label
									for="maxChoicesVoter">Max : </label> <s:textfield
										property="maxChoicesVoter" styleId="maxChoicesVoter" value="1" /></td>
							</tr>
						</table>

						<table class="tableStyle">
							<tr>
								<td><s:checkbox property="showBeforeAnswer"
										styleId="showBeforeAnswer" /></td>
								<td><label for="showBeforeAnswer"><s:text
											name="consultations.form.showBeforeAnswer" /></label></td>
							</tr>

							<tr>
								<td><s:checkbox property="showBeforeClosing"
										styleId="showBeforeClosing" /></td>
								<td><label for="showBeforeClosing"><s:text
											name="consultations.form.showBeforeClosing" /></label></td>
							</tr>
						</table>

						<table class="tableStyle">
							<tr>
								<td><label for="deadline"><s:text
											name="consultations.form.deadline" /> : </label></td>
								<td><s:textfield property="deadline" styleId="deadline" /></td>
							</tr>

							<tr>
								<td><label for="closingAtMaxVoters"><s:text
											name="consultations.form.closingAtMaxVoters" /> : </label></td>
								<td><s:textfield styleId="closingAtMaxVoters"
										property="closingAtMaxVoters" /></td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>

			<tr>
				<td class="tableButton"><s:submit styleClass="btn btn-inverse"
						styleId="buttonConsultation"
						onclick="return validateConsultation()">
						<s:text name="consultations.button.create" />
					</s:submit></td>
			</tr>
		</s:form>
	</table>
</fieldset>

<script type="text/javascript">
	$(function() {
		$.datepicker.setDefaults($.extend({
			minDate : "+0D",
			dateFormat : 'dd/mm/yy',
			showOn : 'both',
			buttonImage : 'images/calendar.gif',
			buttonImageOnly : true,
			showMonthAfterYear : false
		}));
		$.datepicker.setDefaults($.datepicker.regional['fr']);

		$("#deadline").datepicker();

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

		$("#errorChoice").css("display", "none");

		displayChoicesOption(true);

		displayLimitChoicesPerVoter();

	});
</script>
