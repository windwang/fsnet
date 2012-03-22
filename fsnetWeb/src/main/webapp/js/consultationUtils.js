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
		res += '<tr><td><label for="consultationChoice'
				+ j
				+ '"> <span class="i18nChoice">'
				+ $(".i18nChoice").html()
				+ '</span> '
				+ j
				+ ' : </label></td><td><input type="text" class="consultationChoice" value="'
				+ (displayValue ? val : "") + '" id="consultationChoice' + j
				+ '" />';
		if (displayOption) {
			res += ' <label for="maxVoters'
					+ j
					+ '"><bean:message key="consultations.form.choicesOption" /> </label><input type="text" value="'
					+ val2 + '" id="maxVoters' + j
					+ '" class="consultationMaxVotersPerChoice" />';
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

function valideGroupToConsulation() {

	var groupListRight = document.getElementsByName("groupsListRight").item(0);
	
	for ( var i = 0; i < groupListRight.options.length; i++) {
		groupListRight.options[i].selected = "true";
	}
}

function validateConsultation() {

	valideGroupToConsulation();
	
	$("#consultationChoice").val("");
	$("#maxVoters").val("");

	for (j = 1; j < i; j++) {

		// 
		val = $("#consultationChoice" + j).val();

		if (val == "") {
			$("#errorChoice").css("display", "block");
			return false;
		}

		if ($("#consultationChoice").val() == "") {
			$("#consultationChoice").val(val);
		} else {
			$("#consultationChoice").val(
					$("#consultationChoice").val() + ";" + val);
		}

		//
		val2 = $("#maxVoters" + j).val();
		if (val2 != undefined) {
			if ($("#maxVoters").val() == "") {
				$("#maxVoters").val(val2);
			} else {
				$("#maxVoters").val($("#maxVoters").val() + ";" + val2);
			}
		}
	}
	
	$("#errorChoice").css("display", "none");
	
	return true;
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