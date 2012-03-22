	function confirmDelete(action) {
			if (confirm("<bean:message key='confirmation.delete' />")) {
				window.location = action;
			}
		}
	function changeSimpleOrMultiple() {
		if (document.getElementsByName("chooseSimpleOrMultiple")[0].checked) {
			document.getElementById("divChooseSimpleMember").style.display = 'block';
			document.getElementById("divChooseMultipleMember").style.display = 'none';
			document.getElementById("divChooseMultipleFileMember").style.display = 'none';
		}
		if (document.getElementsByName("chooseSimpleOrMultiple")[1].checked) {
			document.getElementById("divChooseMultipleMember").style.display = 'block';
			document.getElementById("divChooseSimpleMember").style.display = 'none';
			document.getElementById("divChooseMultipleFileMember").style.display = 'none';
		}
		if (document.getElementsByName("chooseSimpleOrMultiple")[2].checked) {
			document.getElementById("divChooseMultipleFileMember").style.display = 'block';
			document.getElementById("divChooseSimpleMember").style.display = 'none';
			document.getElementById("divChooseMultipleMember").style.display = 'none';
		}
	}