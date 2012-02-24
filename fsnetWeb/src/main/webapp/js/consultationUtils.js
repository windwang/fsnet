	var i = 4;
	function addChoice(){
		i++;
		displayChoicesOption(true);
	}
	function removeChoice(){
		if(i>2){
			i--;
		}
		displayChoicesOption(true);
	}
	
	function displayChoices(displayValue,displayOption){
		var res='';
		for(j=1;j<i;j++){
			val=$("#consultationChoice"+j).val();
			val2=$("#maxVoters"+j).val();
			if(val == undefined)
				val = "";
			if(val2 == undefined){
				val2=$("#nbVotersPerChoice").val();
				if(val2 == undefined){
					val2 = "";
				}
			}
			res+='<tr><td><label for="consultationChoice'+j+'"> <span class="i18nChoice">'+$(".i18nChoice").html()+ '</span> ' +j+' : </label></td><td><input type="text" name="consultationChoice" class="consultationChoice" value="'+(displayValue?val:"")+'" id="consultationChoice'+j+'" />';
			if(displayOption){
				res+=' <label for="maxVoters'+j+'"><bean:message key="consultation.choicesOption" /> </label><input type="text" name="maxVoters" value="'+val2+'" id="maxVoters'+j+'" class="consultationMaxVotersPerChoice" />';
			}
			res+='</td></tr>';
		}
		$("#choicesTab").html(res);
		if($("#radioButtonDate").attr('checked')){
			$(".consultationChoice").datepicker($.datepicker.regional['fr']);
		}
	}
	
	function displayChoicesOption(displayValue){
		if($("#nbVotersPerChoiceBox").attr('checked')){
			$("#nbVotersPerChoice").attr("disabled",false);
			displayChoices(displayValue,true);
		}else{
			$("#nbVotersPerChoice").attr("disabled",true);
			displayChoices(displayValue,false);
		}
	}
	
	function displayLimitChoicesPerVoter(){
		if($("#limitChoicesPerVoter").attr('checked')){
	    	$("#minChoicesVoter").attr("disabled",false);
	    	$("#maxChoicesVoter").attr("disabled",false);
	    }else{
	    	$("#minChoicesVoter").attr("disabled",true);
	    	$("#maxChoicesVoter").attr("disabled",true);
	    }
	}
	
	function updateMaxVoters(){
		for(j=1;j<i;j++){
			$("#maxVoters"+j).val($("#nbVotersPerChoice").val());
		}
	}