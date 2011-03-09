/* need jquery*/
	var SEPARATOR_PARENT_CHILD = "=";
	var SEPARATOR_MAP = ";";
	function updateParentInterest() {
		var allInterestsId = $("input:hidden")[0].value;
		var mapArray = allInterestsId.split(SEPARATOR_MAP);
		var selectedId = $("div#modify select[name=modifiedInterestId] option:selected")[0].value;
		var parentInterests = $("div#modify select[name=parentInterestId]");
		var parentToModify;
		if(selectedId==""){
			parentToModify = parentInterests.children("option[value=''] ");
		}else{
			for ( var i = 0; i < mapArray.length -1; i++) {
				var ids = mapArray[i]; 
				var parentChildInterest = ids.split(SEPARATOR_PARENT_CHILD);
				var child = parentChildInterest[0];
				var parent = parentChildInterest[1];
				if(child == selectedId){
					
					if(parent == "-1"){
						parentToModify = parentInterests.children("option[value=''] ");
					}else{
						parentToModify = parentInterests.children("option[value='"+parent+"'] ");	
					}
				}
			}
		}
		
		parentToModify.attr('selected','selected');
	}