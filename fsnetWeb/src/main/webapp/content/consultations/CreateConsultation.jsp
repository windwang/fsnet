<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript" src="js/consultationUtils.js"></script>

<fieldset class="fieldsetCadre">
	<legend>
		<s:text name="consultations.title.create"></s:text>
	</legend>

	<form action="<s:url action='CreateConsultation'/>" method="POST"
		enctype="multipart/form-data">

		<fieldset class="fieldsetCadre">
			<legend>
				<s:text name="consultations.title.information" />
			</legend>

			<table class="tableStyle">
				<tr>
					<td><s:textfield name="consultationTitle"
							id="consultationTitle"
							label="%{getText('consultations.form.title')}" /></td>
				</tr>

				<tr>
					<td><s:textfield name="consultationDescription"
							id="consultationDescription"
							label="%{getText('consultations.form.description')}" /></td>
				</tr>
			</table>
		</fieldset>


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
				name="nbVotersPerChoiceBox" id="nbVotersPerChoiceBox" />
			<label for="nbVotersPerChoiceBox"><s:text
					name="consultations.form.limitVotersNumberPerChoice" /></label>
			<s:textfield id="nbVotersPerChoice" onkeyup="updateMaxVoters()"
				name="nbVotersPerChoice" />

			<div class="plusMoins">
				<br /> <input type="button" onclick="removeChoice()" class="moins" /><input
					type="button" onclick="addChoice()" class="plus" /><br />
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

			<s:hidden name="consultationChoice" />
			<s:hidden name="maxVoters" />
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
				<tr class="truc">
					<td>
					<s:select list="%{#attr.allUnderGroupsNoRights}" listValue="name" listKey="name"
						name="groupsListLeft" id="groupsListLeft" multiple="true" labelposition="top"
						label="%{getText('consultation.droits.groupsNoRights')}" />
					</td>
					<td>
					<s:a href="" action="" cssClass="btn btn-inverse" onclick="return Deplacer($('#groupsListLeft')[0],$('#groupsListRight')[0])" >
						<s:text name="groups.addMembers"/>
					</s:a>
					
					<s:a href="" action="" cssClass="btn btn-inverse" onclick="return Deplacer($('#groupsListRight')[0],$('#groupsListLeft')[0])" >
						<s:text name="groups.removeMembers"/>
					</s:a>
					
					<td>
					<s:select list="%{#attr.allUnderGroupsRights}" listKey="name" listValue="name"
						labelposition="top" name="groupsListRight" id="groupsListRight" multiple="true"
						label="%{getText('consultation.droits.groupsRights')}" />
					</td>
				<tr>
			</table>
		</fieldset>


		<fieldset class="fieldsetCadre">
			<legend>
				<s:text name="consultations.title.typeConsultation" />
			</legend>

			<table class="tableStyle">
				<tr>
					<td><input type="radio" value="YES_NO" name="consultationType" id="YES_NO" /></td>
					<td><label for="YES_NO"><s:text
								name="consultations.form.typeYesNo" /></label></td>
				</tr>

				<tr>
					<td><input type="radio" name="consultationType" value="YES_NO_OTHER"
						id="YES_NO_OTHER" /></td>
					<td><label for="YES_NO_OTHER"><s:text
								name="consultations.form.typeYesNoOther" /></label></td>
				</tr>

				<tr>
					<td><input type="radio" name="consultationType"
						id="YES_NO_IFNECESSARY" value="YES_NO_IFNECESSARY" /></td>

					<td><label for="YES_NO_IFNECESSARY"><s:text
								name="consultations.form.typeYesNoIfNecessary" /></label><label
						for="consultationIfNecessaryWeight"> <s:text
								name="consultations.form.ifNecessaryWeight" /></label> <s:textfield
							name="consultationIfNecessaryWeight"
							id="consultationIfNecessaryWeight" disabled="true" /></td>
				</tr>

				<tr>
					<td><input type="radio" name="consultationType"
						id="PREFERENCE_ORDER" value="PREFERENCE_ORDER"/></td>
					<td><label for="PREFERENCE_ORDER"><s:text
								name="consultations.form.typePreferenceOrder" /></label></td>
				</tr>
			</table>

		</fieldset>

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
					<td><s:checkbox name="limitChoicesPerVoter"
							id="limitChoicesPerVoter" label="%{getText('consultations.form.limitChoicesPerVoter')}"/></td>
				</tr>

				<tr>
					<td><s:textfield name="minChoicesVoter"
							id="minChoicesVoter" value="1" label="Min" /> <s:textfield
							name="maxChoicesVoter" styleId="maxChoicesVoter" value="1" label="Max" /></td>
				</tr>
			</table>

			<table class="tableStyle">
				<tr>
					<td><s:checkbox name="showBeforeAnswer" label="%{getText('consultations.form.showBeforeAnswer')}"
							id="showBeforeAnswer" /></td>
					
				</tr>

				<tr>
					<td><s:checkbox name="showBeforeClosing" label="%{getText('consultations.form.showBeforeClosing')}"
							id="showBeforeClosing" /></td>
					
				</tr>
			</table>

			<table class="tableStyle">
				<tr>
					<td><s:textfield name="deadline" id="deadline" label="%{getText('consultations.form.deadline')}" /></td>
				</tr>

				<tr>
					<td><s:textfield id="closingAtMaxVoters" label="%{getText('consultations.form.closingAtMaxVoters')}"
							name="closingAtMaxVoters" /></td>
				</tr>
			</table>
		</fieldset>

		<s:submit cssClass="btn btn-inverse" id="buttonConsultation"
			onclick="return validateConsultation()" key="consultations.button.create"/>
	</form>
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
