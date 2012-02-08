var inter=0;
$(document).ready(function() {
	inter=setInterval("updateShouts()", 9999);
});

function updateShouts() {
	console.log("ma requete activate");
	// $.ajax({
	// type : "POST",
	// url: '/fsnetWeb/TalkMembersActivate.do',
	// data : {
	//			
	// },
	// success : function(data) {
	// //alert("couc");
	// }
	//
	// });

	var jqxhr = $.post("/fsnetWeb/TalkMembersActivate.do", function(data,
			textStatus, jqXHR) {
		// alert("success");
		var e = $.parseJSON(jqXHR.getResponseHeader("X-JSON"));
		var valListner = [];
		if (e == null) {
			clearInterval(inter);
			$.post("/fsnetWeb/Home.do", function(data,
					textStatus, jqXHR) {});
		}
		console.log("requete activate reussi");

	});

}
