var tim = 0;
$(document).ready(function() {
	tim = setInterval("testReceive()", 2000);
});

function testReceive() {
	console.log("ma requete rceive");
	var jqxhr = $
			.post(
					"/fsnetWeb/TalkMembersReceive.do",
					function(data, textStatus, jqXHR) {
						// alert("success");
						var e = $.parseJSON(jqXHR.getResponseHeader("X-JSON"));
						var lastConversation = [];
						if(e==null){
							clearInterval(tim);
							$.post("/fsnetWeb/Home.do", function(data,
									textStatus, jqXHR) {});
						}
						$(e.lastConversation)
								.each(
										function(i) {

											var name = e.lastConversation[i].name;
											var str = (name).split("@");
											var msg = e.lastConversation[i].msg;
											var mailXmpp = e.lastConversation[i].mailXmpp;
											var h = ":";
											chatWith(name,
													'kevin1@master11.com');
											// $("#chatbox_"+name+"
											// .chatboxcontent").remove();
											// $("#chatboxinfo")
											$("#" + name).remove();
											$(
													"#chatbox_"
															+ name
															+ " .chatboxcontent")
													.append(
															'<div class="chatboxmessage"><span class="chatboxinfo" id='
																	+ name
																	+ '>'
																	+ msg
																	+ '</span></div>');
											$(
													"#chatbox_"
															+ name
															+ " .chatboxcontent")
													.scrollTop(
															$("#chatbox_"
																	+ name
																	+ " .chatboxcontent")[0].scrollHeight);

										});

					}).error(function() {
				clearInterval(tim);
			});

	// $
	// .ajax({
	// cache: false,
	// type : "POST",
	// url : '/fsnetWeb/TalkMembersReceive.do',
	// success : function(data, textStatus, jqXHR) {
	// var e = $.parseJSON(jqXHR.getResponseHeader("X-JSON"));
	// var lastConversation = [];
	// $(e.lastConversation)
	// .each(
	// function(i) {
	//
	// var name = e.lastConversation[i].name;
	// var str = (name).split("@");
	// var msg = e.lastConversation[i].msg;
	// var mailXmpp = e.lastConversation[i].mailXmpp;
	// var h = ":";
	// chatWith(name, 'kevin1@master11.com');
	// // $("#chatbox_"+name+"
	// // .chatboxcontent").remove();
	// // $("#chatboxinfo")
	// $("#" + name).remove();
	// $(
	// "#chatbox_" + name
	// + " .chatboxcontent")
	// .append(
	// '<div class="chatboxmessage"><span class="chatboxinfo" id='
	// + name
	// + '>'
	// + msg
	// + '</span></div>');
	// $(
	// "#chatbox_" + name
	// + " .chatboxcontent")
	// .scrollTop(
	// $("#chatbox_"
	// + name
	// + " .chatboxcontent")[0].scrollHeight);
	//
	// });
	//
	// },
	// error : function(e) {
	//
	// }
	//
	// });
}