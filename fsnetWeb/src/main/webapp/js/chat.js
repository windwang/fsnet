
var windowFocus = true;
var username;
var chatHeartbeatCount = 0;
var minChatHeartbeat = 1000;
var maxChatHeartbeat = 33000;
var chatHeartbeatTime = minChatHeartbeat;
var originalTitle;
var blinkOrder = 0;

var chatboxFocus = new Array();
var newMessages = new Array();
var newMessagesWin = new Array();
var chatBoxes = new Array();

var localizedStrings={
	    moi:{
	        'en':'me',
	        'fr':'moi'
	    },
	    compo:{
	    	'en':'is writing...',
	        'fr':'est en train d\'écrire...'	    	
	    }
	};

var tim = 0;
var tim2 = 0 ;
/* Fonction qui est appelée lorsque la page est chargée */
$(document).ready(function() {
	/* On vérifie toute les 2 secondes si un nouveau message est arrivé */
	tim = setInterval("testReceive()", 2000);
	/* On vérifie toute les 2 secondes si l'interlocuteur est en train de composer */
	tim2 = setInterval("testComposing()", 2000);
	/* On réouvre les fenetres de chat actives lorsqu'on arrive sur une page */
	loadChatBoxes();
});

/*Change the color of the element state is true => element is blue. State is false => element is orange*/
function applyBackgroundColor(element,state){	
	if (state) {
		element.css("background","#fceabb");
		element.css("background","-moz-linear-gradient(top, #fceabb 0%, #fccd4d 50%, #f8b500 51%, #fbdf93 100%)");
		element.css("background","-webkit-gradient(linear, left top, left bottom, color-stop(0%,#fceabb), color-stop(50%,#fccd4d), color-stop(51%,#f8b500), color-stop(100%,#fbdf93))");
		element.css("background","-webkit-linear-gradient(top, #fceabb 0%,#fccd4d 50%,#f8b500 51%,#fbdf93 100%)");
		element.css("background","-o-linear-gradient(top, #fceabb 0%,#fccd4d 50%,#f8b500 51%,#fbdf93 100%)");
		element.css("background","-ms-linear-gradient(top, #fceabb 0%,#fccd4d 50%,#f8b500 51%,#fbdf93 100%)");
		element.css("background","linear-gradient(to bottom, #fceabb 0%,#fccd4d 50%,#f8b500 51%,#fbdf93 100%)");
	} else {
		element.css("background","#1e5799");
		element.css("background","-moz-linear-gradient(top, #1e5799 0%, #2989d8 50%, #207cca 51%, #7db9e8 100%)"); /* FF3.6+ */
		element.css("background","-webkit-gradient(linear, left top, left bottom, color-stop(0%,#1e5799), color-stop(50%,#2989d8), color-stop(51%,#207cca), color-stop(100%,#7db9e8))"); /* Chrome,Safari4+ */
		element.css("background","-webkit-linear-gradient(top, #1e5799 0%,#2989d8 50%,#207cca 51%,#7db9e8 100%)"); /* Chrome10+,Safari5.1+ */
		element.css("background","-o-linear-gradient(top, #1e5799 0%,#2989d8 50%,#207cca 51%,#7db9e8 100%)"); /* Opera 11.10+ */
		element.css("background","-ms-linear-gradient(top, #1e5799 0%,#2989d8 50%,#207cca 51%,#7db9e8 100%)"); /* IE10+ */
		element.css("background","linear-gradient(to bottom, #1e5799 0%,#2989d8 50%,#207cca 51%,#7db9e8 100%)"); /* W3C */
	}
}

/* Method that alternates color of the title and buttons*/
function blinkChatFrame(frame, title,buttons) {
	/* True attribute refering we are in blue color */
	if (frame.data("colorBlink")) {
		document.title = document.oldTitle + " (Message)";
		applyBackgroundColor(title,true);
		applyBackgroundColor(buttons,true);
		frame.data("colorBlink",false);
		
	} else {
		document.title = document.oldTitle;
		applyBackgroundColor(title,false);
		applyBackgroundColor(buttons,false);
		frame.data("colorBlink",true);
	}
}
/* Method that cancels blinking of the frame*/
function stopBlinkChatFrame(frame, title,buttons){
	document.title = document.oldTitle;
	applyBackgroundColor(title,false);
	applyBackgroundColor(buttons,false);
	frame.data("colorBlink",true);
	clearInterval(frame[0].colorBlickTimer);
}

/* Permet de recevoir un message */
function testReceive() {
	$
			.ajax({
				type : 'POST',
				url : "/fsnetWeb/TalkMembersReceive.do",
				dataType : "json",
				success : function(data, textStatus, jqXHR) {
					$(data.lastConversation)
							.each(
									function(i) {
										var name = data.lastConversation[i].name;
										var str = (name).split("@");
										var msg = data.lastConversation[i].msg;
										var mailXmpp = data.lastConversation[i].mailXmpp;
										var h = ":";
										chatWith(name, 'kevin1@master11.com');
										$("#" + name).remove();
										$("#chatbox_" + name
														+ " .chatboxcontent")
												.append(
														'<div class="chatboxmessage"><span class="chatboxinfo" id='
																+ name
																+ '>'
																+ msg
																+ '</span></div>');
										$(
												"#chatbox_" + name
														+ " .chatboxcontent")
												.scrollTop(
														$("#chatbox_"
																+ name
																+ " .chatboxcontent")[0].scrollHeight);
										/* Notify by a sound */
										$.notification('notification.wav');
										document.oldTitle=document.title;
										/* Add a timer to alternate color */
										$("#chatbox_" + name)[0].colorBlickTimer = setInterval(
												function() {
													blinkChatFrame(
															$("#chatbox_"
																	+ name),
															$("#chatbox_"
																	+ name
																	+ " .chatboxhead"),
																	$("#chatbox_"
																			+ name
																			+ " .chat_button"))
												}, 1000);
										/*cancel on focus*/
										$("#chatbox_" + name+" .chatboxinput input").bind("focus",function(){stopBlinkChatFrame($("#chatbox_"+ name),
													$("#chatbox_"+ name+ " .chatboxhead"),$("#chatbox_"+ name+ " .chat_button"))});
									})
				}
			});
}


/* Permet de savoir si l'interlocuteur est en train d'écrire */
function testComposing() {
	$
	.ajax({
		type : 'POST',
		url : "/fsnetWeb/TalkMembersIsComposing.do",
		dataType : "json",
		success : function(data, textStatus, jqXHR) {
			$(data.isComposing)
					.each(
							function(i) {
								var name = data.isComposing[i].name;
								var bool = data.isComposing[i].isComposing;
								
								if ( (bool==true)) {
									console.log("Composing !") ;
								}
							});
			}
		});
}

/*
 * Permet de placer les fenetres de chat (cette fonction doit être appelée
 * lorsqu'on ferme une fenetre de chat par exemple pour pouvoir replacer les
 * fenetres et combler le trou laissé par la fenetre que l'on vient de fermer)
 */
function restructureChatBoxes() {
	align = 0;
	for (x in chatBoxes) {
		chatboxtitle = chatBoxes[x];

		if ($("#chatbox_" + chatboxtitle).css('display') != 'none') {
			if (align == 0) {
				$("#chatbox_" + chatboxtitle).css('right', '20px');
			} else {
				width = (align) * (225 + 7) + 20;
				$("#chatbox_" + chatboxtitle).css('right', width + 'px');
			}
			align++;
		}
	}
}

function chatWith(chatuser, mailUser) {
	var user = chatuser.toLowerCase();
	createChatBox(user);
	$("#chatbox_" + user + " .chatboxtextarea").focus();
}

/* Permet de créer une fenetre de chat */
function createChatBox(chatboxtitle, minimizeChatBox) {
	if ($("#chatbox_" + chatboxtitle).length > 0) {
		if ($("#chatbox_" + chatboxtitle).css('display') == 'none') {
			$("#chatbox_" + chatboxtitle).css('display', 'block');
			restructureChatBoxes();
		}
		$("#chatbox_" + chatboxtitle + " .chatboxtextarea").focus();
		return;
	}

	$(" <div />")
			.attr("id", "chatbox_" + chatboxtitle)
			.addClass("chatbox")
			.html(
					'<div class="chatboxhead"><div class="chatboxtitle">'
							+ chatboxtitle.split('_')[0]
							+ '</div><div class="chatboxoptions"><a class="chat_button" href="javascript:void(0)" onclick="javascript:toggleChatBoxGrowth(\''
							+ chatboxtitle
							+ '\')">-</a> <a class="chat_button" href="javascript:void(0)" onclick="javascript:closeChatBox(\''
							+ chatboxtitle
							+ '\')">x</a></div><br clear="all"/></div><div class="chatboxcontent"></div><div class="chatboxcomposing"><p>&nbsp</p></div><div class="chatboxinput"><form><input type="text" class="chatboxtextarea" onkeyup="javascript:return checkComposing(event,this,\''
							+ chatboxtitle + '\');" onkeydown="javascript:return checkChatBoxInputKey(event,this,\''
							+ chatboxtitle + '\');"></input></form></div>')
			.appendTo($("body"));

	chatBoxeslength = 0;

	for (x in chatBoxes) {
		if ($("#chatbox_" + chatBoxes[x]).css('display') != 'none') {
			chatBoxeslength++;
		}
	}

	if (chatBoxeslength == 0) {
		$("#chatbox_" + chatboxtitle).css('right', '20px');
	} else {
		width = (chatBoxeslength) * (225 + 7) + 20;
		$("#chatbox_" + chatboxtitle).css('right', width + 'px');
	}

	chatBoxes.push(chatboxtitle);

	if (minimizeChatBox == 1) {
		minimizedChatBoxes = new Array();

		if ($.cookie('chatbox_minimized')) {
			minimizedChatBoxes = $.cookie('chatbox_minimized').split(/\|/);
		}
		minimize = 0;
		for (j = 0; j < minimizedChatBoxes.length; j++) {
			if (minimizedChatBoxes[j] == chatboxtitle) {
				minimize = 1;
			}
		}

		if (minimize == 1) {
			$('#chatbox_' + chatboxtitle + ' .chatboxcontent').css('display',
					'none');
			$('#chatbox_' + chatboxtitle + ' .chatboxinput').css('display',
					'none');
		}
	}

	chatboxFocus[chatboxtitle] = false;

	$("#chatbox_" + chatboxtitle + " .chatboxtextarea").blur(
			function() {
				chatboxFocus[chatboxtitle] = false;
				$("#chatbox_" + chatboxtitle + " .chatboxtextarea")
						.removeClass('chatboxtextareaselected');
			}).focus(
			function() {
				chatboxFocus[chatboxtitle] = true;
				newMessages[chatboxtitle] = false;
				$('#chatbox_' + chatboxtitle + ' .chatboxhead').removeClass(
						'chatboxblink');
				$("#chatbox_" + chatboxtitle + " .chatboxtextarea").addClass(
						'chatboxtextareaselected');
			});

	$("#chatbox_" + chatboxtitle)
			.click(
					function() {
						if ($('#chatbox_' + chatboxtitle + ' .chatboxcontent')
								.css('display') != 'none') {
							$("#chatbox_" + chatboxtitle + " .chatboxtextarea")
									.focus();
						}
					});

	$("#chatbox_" + chatboxtitle).show();
}
/* Permet de fermer une fenetre de chat */
function closeChatBox(chatboxtitle) {
	$('#chatbox_' + chatboxtitle).css('display', 'none');
	restructureChatBoxes();
	$.ajax({
		type : 'POST',
		url : '/fsnetWeb/TalkMembersCloseTalk.do',
		data : {
			friend : chatboxtitle
		},
		success : function(data, textStatus, jqXHR) {

		},
		error : function(e) {
			// alert('Error: ' + e);
		}
	});

}

function toggleChatBoxGrowth(chatboxtitle) {
	if ($('#chatbox_' + chatboxtitle + ' .chatboxcontent').css('display') == 'none') {

		var minimizedChatBoxes = new Array();

		if ($.cookie('chatbox_minimized')) {
			minimizedChatBoxes = $.cookie('chatbox_minimized').split(/\|/);
		}

		var newCookie = '';

		for (i = 0; i < minimizedChatBoxes.length; i++) {
			if (minimizedChatBoxes[i] != chatboxtitle) {
				newCookie += chatboxtitle + '|';
			}
		}

		newCookie = newCookie.slice(0, -1);

		$.cookie('chatbox_minimized', newCookie);
		$('#chatbox_' + chatboxtitle + ' .chatboxcontent').css('display',
				'block');
		$('#chatbox_' + chatboxtitle + ' .chatboxcomposing').css('display',
		'block');
		$('#chatbox_' + chatboxtitle + ' .chatboxinput')
				.css('display', 'block');
		$("#chatbox_" + chatboxtitle + " .chatboxcontent")
				.scrollTop(
						$("#chatbox_" + chatboxtitle + " .chatboxcontent")[0].scrollHeight);
	} else {

		var newCookie = chatboxtitle;

		if ($.cookie('chatbox_minimized')) {
			newCookie += '|' + $.cookie('chatbox_minimized');
		}

		$.cookie('chatbox_minimized', newCookie);
		$('#chatbox_' + chatboxtitle + ' .chatboxcontent').css('display',
				'none');
		$('#chatbox_' + chatboxtitle + ' .chatboxcomposing').css('display',
		'none');
		$('#chatbox_' + chatboxtitle + ' .chatboxinput').css('display', 'none');
	}

}

/* Permet de charger les fenêtres de chat ouvertes lors du chargement de la page */
function loadChatBoxes() {
	$
			.ajax({
				type : 'POST',
				url : '/fsnetWeb/TalkMembersGetTalks.do',
				dataType : "json",

				success : function(data, textStatus, jqXHR) {

					$(data.sessionTalks)
							.each(
									function(i) {
										var name = data.sessionTalks[i]
												.split('@')[0];
										createChatBox(name, false);
										$
												.ajax({
													type : 'POST',
													url : '/fsnetWeb/TalkMembersGetTalk.do',
													data : {
														friend : name
													},
													dataType : "json",
													success : function(data,
															textStatus, jqXHR) {
														$(
																"#chatbox_"
																		+ data.friend
																		+ " .chatboxcontent")
																.append(
																		'<div class="chatboxmessage"><span class="chatboxinfo">'
																				+ data.conversation
																				+ '</span></div>');
														$(
																"#chatbox_"
																		+ data.friend
																		+ " .chatboxcontent")
																.scrollTop(
																		$("#chatbox_"
																				+ data.friend
																				+ " .chatboxcontent")[0].scrollHeight);
													},
													error : function(data,
															textStatus, jqXHR) {
														alert("ça marche pas !! :'(");
													}
												})
									});

				},
				error : function(e) {
					// alert('Error: ' + e);
				}
			});
}



function checkComposing(event, chatboxtextarea, chatboxtitle) {
	if (event.keyCode != 13){
		message = $(chatboxtextarea).val();
		if (message != ''){
			
			$.ajax({
				type : 'POST',
				url : '/fsnetWeb/TalkMemberComposing.do',
				data : {
					toFriend : chatboxtitle
				},
				dataType : "json",
	//			success : function(data, textStatus, jqXHR) {
			});
		}
	}
	return false ;
}


function addIsComposing(friend){
	var locale = navigator.language;
	var friendIsComposing = friend.split('_')[0]+' '+localizedStrings['compo'][locale] ;
	$("#chatbox_" + friend + " .chatboxcomposing").html('<p>'+friendIsComposing+'</p>');
}

function removeIsComposing(friend){
	$("#chatbox_" + friend + " .chatboxcomposing").html('<p>&nbsp</p>');
}


/* Permet d'envoyer un message */
function checkChatBoxInputKey(event, chatboxtextarea, chatboxtitle) {
	if (event.keyCode == 13 && event.shiftKey == 0) {
		message = $(chatboxtextarea).val();
		message = message.replace(/^\s+|\s+$/g, "");

		$(chatboxtextarea).val('');
		$(chatboxtextarea).focus();

		if (message != '') {

			$
					.ajax({
						type : 'POST',
						url : '/fsnetWeb/TalkMembersSend.do',
						data : {
							msg : message,
							toFriend : chatboxtitle
						},
						dataType : "json",
						success : function(data, textStatus, jqXHR) {
							$(data.lastConversation)
									.each(
											function(i) {

												var name = data.lastConversation[i].name;
												var str = (name).split("@");
												var msg = data.lastConversation[i].msg;
												var mailXmpp = data.lastConversation[i].mailXmpp;
												var h = ":";

												$(
														"#chatbox_"
																+ chatboxtitle
																+ " .chatboxcontent")
														.append(
																'<div class="chatboxmessage"><span class="chatboxinfo" id='
																		+ chatboxtitle
																		+ '>'
																		+ msg
																		+ '</span></div>');
												$(
														"#chatbox_"
																+ chatboxtitle
																+ " .chatboxcontent")
														.scrollTop(
																$("#chatbox_"
																		+ chatboxtitle
																		+ " .chatboxcontent")[0].scrollHeight);

											});

						},
						error : function(e) {
							// alert('Error: ' + e);
						}

					});

		}
		chatHeartbeatTime = minChatHeartbeat;
		chatHeartbeatCount = 1;

		return false;
	}else {
		
	}

	// alert("send message4 "+$(chatboxtextarea).val());
	var adjustedHeight = chatboxtextarea.clientHeight;
	var maxHeight = 94;

	if (maxHeight > adjustedHeight) {
		adjustedHeight = Math.max(chatboxtextarea.scrollHeight, adjustedHeight);
		if (maxHeight)
			adjustedHeight = Math.min(maxHeight, adjustedHeight);
		if (adjustedHeight > chatboxtextarea.clientHeight)
			$(chatboxtextarea).css('height', adjustedHeight + 8 + 'px');
	} else {
		$(chatboxtextarea).css('overflow', 'auto');
	}

}


/**
 * Cookie plugin
 * 
 * Copyright (c) 2006 Klaus Hartl (stilbuero.de) Dual licensed under the MIT and
 * GPL licenses: http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl.html
 * 
 */

jQuery.cookie = function(name, value, options) {
	if (typeof value != 'undefined') { // name and value given, set cookie
		options = options || {};
		if (value === null) {
			value = '';
			options.expires = -1;
		}
		var expires = '';
		if (options.expires
				&& (typeof options.expires == 'number' || options.expires.toUTCString)) {
			var date;
			if (typeof options.expires == 'number') {
				date = new Date();
				date.setTime(date.getTime()
						+ (options.expires * 24 * 60 * 60 * 1000));
			} else {
				date = options.expires;
			}
			expires = '; expires=' + date.toUTCString(); // use expires
			// attribute,
			// max-age is not
			// supported by IE
		}
		// CAUTION: Needed to parenthesize options.path and options.domain
		// in the following expressions, otherwise they evaluate to undefined
		// in the packed version for some reason...
		var path = options.path ? '; path=' + (options.path) : '';
		var domain = options.domain ? '; domain=' + (options.domain) : '';
		var secure = options.secure ? '; secure' : '';
		document.cookie = [ name, '=', encodeURIComponent(value), expires,
				path, domain, secure ].join('');
	} else { // only name given, get cookie
		var cookieValue = null;
		if (document.cookie && document.cookie != '') {
			var cookies = document.cookie.split(';');
			for ( var i = 0; i < cookies.length; i++) {
				var cookie = jQuery.trim(cookies[i]);
				// Does this cookie string begin with the name we want?
				if (cookie.substring(0, name.length + 1) == (name + '=')) {
					cookieValue = decodeURIComponent(cookie
							.substring(name.length + 1));
					break;
				}
			}
		}
		return cookieValue;
	}
};