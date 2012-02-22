<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<fieldset class="fieldsetAppli">
<<<<<<< HEAD
	<legend class="legendHome">
		<bean:message key="consultation.create" />
	</legend>
	<table class="inLineTableDashBoardFieldset fieldsetTable">
=======
  <legend class="legendHome"><bean:message key="consultation.create"/></legend>
  <table  class="inLineTableDashBoardFieldset fieldsetTable"><tr><td>
  <html:form action="CreateConsultation" method="post">
	<fieldset class="inLinefieldset">
	<legend><bean:message key="consultation.information" /></legend>
	<div class="errorMessage" ><html:errors property="consultationTitle"/></div> 
	<table >
	<tr>
		<td><label for="consultationTitle"><bean:message key="consultation.title" /> : </label></td>
		<td>
			<html:text property="consultationTitle" styleId="consultationTitle" />		
	</tr>
	<tr>
		<td><label for="consultationDescription"><bean:message key="consultation.description" /> : </label></td>
		<td><html:text property="consultationDescription" styleId="consultationDescription" /></td>
	</tr>
	</table>
	</fieldset>
	
	<fieldset class="inLinefieldset">
	<legend><bean:message key="consultation.choix" /></legend>
	<table >
	<tr>
	<td><label for="radioButtonText"><bean:message key="consultation.textAlternative"></bean:message></label> <input type="radio"  class="alternativeRadio" name="alternativeRadio" id="radioButtonText" checked="checked"/></td><td><label for="radioButtonDate"><bean:message key="consultation.dateAlternative"></bean:message></label><input type="radio" class="alternativeRadio" name="alternativeRadio" id="radioButtonDate" /></td>
	</tr>
	</table>
	
 <html:checkbox  onclick="displayChoicesOption(true)" property="nbVotersPerChoiceBox" styleId="nbVotersPerChoiceBox" /> 
							<label for="nbVotersPerChoiceBox"><bean:message key="consultationLimitVotersNumberPerChoice" /></label> 
							<html:text styleId="nbVotersPerChoice" onkeyup="updateMaxVoters()" property="nbVotersPerChoice" />
	
	<div class="plusMoins"><br/><input  type="button"  onclick="removeChoice()" class="moins"/><input type="button" onclick="addChoice()" class="plus"/><br /></div>
	<c:if test="${errorChoice}"><p class="errorMessage"><bean:message key="consultation.errorChoice"/></p></c:if>
	<c:if test="${errorMaxVotersPerChoice}"><p class="errorMessage"><bean:message key="consultation.errorMaxVotersPerChoice"/></p></c:if> 
	
	<table id="choicesTab">
	<c:forEach begin="1" end="3" var="i">
		<tr>
		<td><label for="consultationChoice${i}"><span class="i18nChoice"><bean:message key="consultation.choice" /></span> ${i} : </label></td>
		<td><html:text property="consultationChoice" styleClass="consultationChoice" styleId="consultationChoice${i}" value="" /></td>
	</tr>
	</c:forEach>
    </table>
    </fieldset>
    
     <fieldset class="inLinefieldset">
	<legend><bean:message key="consultation.droit"/></legend>
    </fieldset>
    
    <fieldset class="inLinefieldset">
	<legend><bean:message key="consultation.typeConsultation"/></legend>
	<div class="errorMessage" ><html:errors property="consultationIfNecessaryWeight"/></div> 
  	<table>
  		<tr><td><html:radio property="consultationType" value="YES_NO" styleId="YES_NO" /> </td><td><label for="YES_NO"><bean:message key="consultation.typeYesNo"/></label></td></tr> 
	  	<tr><td><html:radio property="consultationType" value="YES_NO_OTHER" styleId="YES_NO_OTHER"/></td><td><label for="YES_NO_OTHER"><bean:message key="consultation.typeYesNoOther"/></label></td></tr>
	  	<tr><td><html:radio property="consultationType" value="YES_NO_IFNECESSARY" styleId="YES_NO_IFNECESSARY" /></td><td><label for="YES_NO_IFNECESSARY"><bean:message key="consultation.typeYesNoIfNecessary"/></label><label for="consultationIfNecessaryWeight"> <bean:message key="consultation.IfNecessaryWeight"/></label><html:text property="consultationIfNecessaryWeight" styleId="consultationIfNecessaryWeight" disabled="true" /></td></tr>
	  	<tr><td><html:radio property="consultationType" value="PREFERENCE_ORDER" styleId="PREFERENCE_ORDER" /></td><td><label for="PREFERENCE_ORDER"><bean:message key="consultation.typePreferenceOrder"/></label></td></tr>
	 </table>
	 </fieldset>
	 
	 <fieldset class="inLinefieldset">
	<legend><bean:message key="consultation.option"/></legend>
	<div class="errorMessage" ><html:errors property="minChoicesVoter"/></div> 
	<div class="errorMessage" ><html:errors property="maxChoicesVoter"/></div> 
	<div class="errorMessage" ><html:errors property="closingAtMaxVoters"/></div> 
	<div class="errorMessage" ><html:errors property="deadline"/></div>
	 <c:if test="${errorChoicesVoter}"><p><bean:message key="consultation.errorChoicesVoter"/></p></c:if>
     <table>
	  	<tr><td>
			<html:checkbox  property="limitChoicesPerVoter" styleId="limitChoicesPerVoter" /></td><td><label for="limitChoicesPerVoter"><bean:message key="consultation.limitChoicesPerVoter" /> </label>
		</td></tr> 
>>>>>>> 350e7e0d09b5647037daad32851f46e4ad9b39d6
		<tr>
			<td><html:form action="CreateConsultation" method="POST">
					<fieldset class="inLinefieldset">
						<legend>
							<bean:message key="consultation.information" />
						</legend>
						<div class="errorMessage">
							<html:errors property="consultationTitle" />
						</div>
						<table>
							<tr>
								<td><label for="consultationTitle"><bean:message
											key="consultation.title" /> : </label></td>
								<td><html:text property="consultationTitle"
										styleId="consultationTitle" />
							</tr>
							<tr>
								<td><label for="consultationDescription"><bean:message
											key="consultation.description" /> : </label></td>
								<td><html:text property="consultationDescription"
										styleId="consultationDescription" /></td>
							</tr>
						</table>
					</fieldset>

					<fieldset class="inLinefieldset">
						<legend>
							<bean:message key="consultation.choix" />
						</legend>
						<table>
							<tr>
								<td><label for="radioButtonText"><bean:message
											key="consultation.textAlternative"></bean:message></label> <input
									type="radio" class="alternativeRadio" name="alternativeRadio"
									id="radioButtonText" checked="checked" /></td>
								<td><label for="radioButtonDate"><bean:message
											key="consultation.dateAlternative"></bean:message></label><input
									type="radio" class="alternativeRadio" name="alternativeRadio"
									id="radioButtonDate" /></td>
							</tr>
						</table>

						<html:checkbox onclick="displayChoicesOption(true)"
							property="nbVotersPerChoiceBox" styleId="nbVotersPerChoiceBox" />
						<label for="nbVotersPerChoiceBox"><bean:message
								key="consultationLimitVotersNumberPerChoice" /></label>
						<html:text styleId="nbVotersPerChoice" onkeyup="updateMaxVoters()"
							property="nbVotersPerChoice" />

						<div class="plusMoins">
							<br /> <input type="button" onclick="removeChoice()"
								class="moins" /><input type="button" onclick="addChoice()"
								class="plus" /><br />
						</div>
						<c:if test="${errorChoice}">
							<p class="errorMessage">
								<bean:message key="consultation.errorChoice" />
							</p>
						</c:if>
						<c:if test="${errorMaxVotersPerChoice}">
							<p class="errorMessage">
								<bean:message key="consultation.errorMaxVotersPerChoice" />
							</p>
						</c:if>

						<table id="choicesTab">
							<c:forEach begin="1" end="3" var="i">
								<tr>
									<td><label for="consultationChoice${i}"><span
											class="i18nChoice"><bean:message
													key="consultation.choice" /></span> ${i} : </label></td>
									<td><html:text property="consultationChoice"
											styleClass="consultationChoice"
											styleId="consultationChoice${i}" value="" /></td>
								</tr>
							</c:forEach>
						</table>
					</fieldset>

					<fieldset class="inLinefieldset">
						<legend>
							<bean:message key="consultation.droit" />
						</legend>

						<c:if test="${errorRights}">
							<p class="errorMessage">
								<bean:message key="consultation.errorRights" />
							</p>
						</c:if>
						<table>
							<tr>
								<td ROWSPAN="2">
									<div>
										<bean:message key="consultation.groupsNoRights" />
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
										onclick="Deplacer(this.form.groupsListLeft,this.form.groupsListRight)">
										<bean:message key="groups.addMembers" />
									</html:button></td>
								<td ROWSPAN="2">
									<div>
										<bean:message key="consultation.groupsRights" />
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
										onclick="Deplacer(this.form.groupsListRight,this.form.groupsListLeft)">
										<bean:message key="groups.removeGroups" />
									</html:button></td>
							</tr>
						</table>
					</fieldset>
					<fieldset class="inLinefieldset">
						<legend>
							<bean:message key="consultation.typeConsultation" />
						</legend>
						<div class="errorMessage">
							<html:errors property="consultationIfNecessaryWeight" />
						</div>
						<table>
							<tr>
								<td><html:radio property="consultationType" value="YES_NO"
										styleId="YES_NO" /></td>
								<td><label for="YES_NO"><bean:message
											key="consultation.typeYesNo" /></label></td>
							</tr>
							<tr>
								<td><html:radio property="consultationType"
										value="YES_NO_OTHER" styleId="YES_NO_OTHER" /></td>
								<td><label for="YES_NO_OTHER"><bean:message
											key="consultation.typeYesNoOther" /></label></td>
							</tr>
							<tr>
								<td><html:radio property="consultationType"
										value="YES_NO_IFNECESSARY" styleId="YES_NO_IFNECESSARY" /></td>
								<td><label for="YES_NO_IFNECESSARY"><bean:message
											key="consultation.typeYesNoIfNecessary" /></label><label
									for="consultationIfNecessaryWeight"> <bean:message
											key="consultation.IfNecessaryWeight" /></label> <html:text
										property="consultationIfNecessaryWeight"
										styleId="consultationIfNecessaryWeight" disabled="true" /></td>
							</tr>
							<tr>
								<td><html:radio property="consultationType"
										value="PREFERENCE_ORDER" styleId="PREFERENCE_ORDER" /></td>
								<td><label for="PREFERENCE_ORDER"><bean:message
											key="consultation.typePreferenceOrder" /></label></td>
							</tr>
						</table>
					</fieldset>

					<fieldset class="inLinefieldset">
						<legend>
							<bean:message key="consultation.option" />
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
								<bean:message key="consultation.errorChoicesVoter" />
							</p>
						</c:if>
						<table>
							<tr>
								<td><html:checkbox property="limitChoicesPerVoter"
										styleId="limitChoicesPerVoter" /></td>
								<td><label for="limitChoicesPerVoter"><bean:message
											key="consultation.limitChoicesPerVoter" /> </label></td>
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
											key="consultation.showBeforeAnswer" /></label></td>
							</tr>
							<tr>
								<td><html:checkbox property="showBeforeClosing"
										styleId="showBeforeClosing" /></td>
								<td><label for="showBeforeClosing"><bean:message
											key="consultation.showBeforeClosing" /></label></td>
							</tr>
						</table>
						<table>
							<tr>
								<td><label for="deadline"><bean:message
											key="consultation.deadline" /> : </label></td>
								<td><html:text property="deadline" styleId="deadline" /></td>
							</tr>
							<tr>
								<td><label for="closingAtMaxVoters"><bean:message
											key="consultation.closingAtMaxVoters" /> : </label></td>
								<td><html:text styleId="closingAtMaxVoters"
										property="closingAtMaxVoters" /></td>
							</tr>
						</table>
					</fieldset>
					<html:submit styleClass="button" styleId="buttonConsultation" onclick="Valider()">
						<bean:message key="consultation.create" />
					</html:submit>

				</html:form></td>
		</tr>
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
	var i = 4;
	function addChoice() {
		i++;
		displayChoicesOption(true);
	}
	function removeChoice() {
		if (i > 2) {
			i--;
		}
		displayChoicesOption(true);
	}

	function displayChoices(displayValue, displayOption) {
		var res = '';
		for (j = 1; j < i; j++) {
			val = $("#consultationChoice" + j).val();
			val2 = $("#maxVoters" + j).val();
			if (val == undefined)
				val = "";
			if (val2 == undefined) {
				val2 = $("#nbVotersPerChoice").val();
				if (val2 == undefined) {
					val2 = "";
				}
			}
			res += '<tr><td><label for="consultationChoice'+j+'"> <span class="i18nChoice">'
					+ $(".i18nChoice").html()
					+ '</span> '
					+ j
					+ ' : </label></td><td><input type="text" name="consultationChoice" class="consultationChoice" value="'
					+ (displayValue ? val : "")
					+ '" id="consultationChoice'
					+ j + '" />';
			if (displayOption) {
				res += ' <label for="maxVoters'+j+'"><bean:message key="consultation.choicesOption" /> </label><input type="text" name="maxVoters" value="'+val2+'" id="maxVoters'+j+'" class="consultationMaxVotersPerChoice" />';
			}
			res += '</td></tr>';
		}
		$("#choicesTab").html(res);
		if ($("#radioButtonDate").attr('checked')) {
			$(".consultationChoice").datepicker($.datepicker.regional['fr']);
		}
	}

	function displayChoicesOption(displayValue) {
		if ($("#nbVotersPerChoiceBox").attr('checked')) {
			$("#nbVotersPerChoice").attr("disabled", false);
			displayChoices(displayValue, true);
		} else {
			$("#nbVotersPerChoice").attr("disabled", true);
			displayChoices(displayValue, false);
		}
	}

	function displayLimitChoicesPerVoter() {
		if ($("#limitChoicesPerVoter").attr('checked')) {
			$("#minChoicesVoter").attr("disabled", false);
			$("#maxChoicesVoter").attr("disabled", false);
		} else {
			$("#minChoicesVoter").attr("disabled", true);
			$("#maxChoicesVoter").attr("disabled", true);
		}
	}

	function updateMaxVoters() {
		for (j = 1; j < i; j++) {
			$("#maxVoters" + j).val($("#nbVotersPerChoice").val());
		}
	}

	function Deplacer(l1, l2) {

		if (l1.options.selectedIndex >= 0)
			for ( var i = l1.options.length - 1; i >= 0; i--) {
				if (l1.options[i].selected) {
					o = new Option(l1.options[i].text, l1.options[i].value);
					l2.options[l2.options.length] = o;
					l1.options[i] = null;
				}
			}
		else {
			alert("Aucun élément sélectionné");
		}
	}

	function Valider() {

		var groupListRight = document.getElementsByName('groupsListRight').item(
				0);

		for ( var i = 0; i < groupListRight.options.length; i++) {
			groupListRight.options[i].selected = "true";
		}

		return true;
	}
</script>
