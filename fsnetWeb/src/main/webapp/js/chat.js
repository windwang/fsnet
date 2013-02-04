

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

var tim = 0;
/* Fonction qui est appelée lorsque la page est chargée */
$(document).ready(function() {
	/* On vérifie toute les 2 secondes si un nouveau message est arrivé */
	tim = setInterval("testReceive()", 2000);
	/* On réouvre les fenetres de chat actives lorsqu'on arrive sur une page */
	loadChatBoxes();
});

/* Permet de recevoir un message */
function testReceive() {
	console.log("ma requete rceive");
	$.ajax({
			type : 'POST',
			url : "/fsnetWeb/TalkMembersReceive.do",
			dataType : "json",
			success : function(data, textStatus, jqXHR) {
						$(data.lastConversation)
								.each(function(i) {
											var name = data.lastConversation[i].name;
											var str = (name).split("@");
											var msg = data.lastConversation[i].msg;
											var mailXmpp = data.lastConversation[i].mailXmpp;
											var h = ":";
											chatWith(name,
													'kevin1@master11.com');
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

										})
			}
	});

}

/* Permet de placer les fenetres de chat (cette fonction doit être appelée lorsqu'on ferme une fenetre de chat par exemple pour pouvoir replacer les fenetres et combler le trou laissé par la fenetre que l'on vient de fermer)*/
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
	var user=chatuser.toLowerCase();
	createChatBox(user);
	$("#chatbox_" + user + " .chatboxtextarea").focus();
}

/* Permet de créer une fenetre de chat*/
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
							+ '</div><div class="chatboxoptions"><a href="javascript:void(0)" onclick="javascript:toggleChatBoxGrowth(\''
							+ chatboxtitle
							+ '\')">-</a> <a href="javascript:void(0)" onclick="javascript:closeChatBox(\''
							+ chatboxtitle
							+ '\')">X</a></div><br clear="all"/></div><div class="chatboxcontent"></div><div class="chatboxinput"><textarea class="chatboxtextarea" onkeydown="javascript:return checkChatBoxInputKey(event,this,\''
							+ chatboxtitle + '\');"></textarea></div>')
			.appendTo($("body"));

	$("#chatbox_" + chatboxtitle).css('bottom', '0px');

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
/* Permet de fermer une fenetre de chat*/
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
		$('#chatbox_' + chatboxtitle + ' .chatboxinput').css('display', 'none');
	}

}

/* Permet de charger les fenêtres de chat ouvertes lors du chargement de la page */
function loadChatBoxes(){
	$
	.ajax({
		type : 'POST',
		url : '/fsnetWeb/TalkMembersGetTalks.do',
		dataType : "json",
		
		success : function(data, textStatus, jqXHR) {
			
			$(data.sessionTalks)
					.each(
							function(i) {
								var name = data.sessionTalks[i].split('@')[0];
								createChatBox(name, false);
								$.ajax({
									type : 'POST',
									url : '/fsnetWeb/TalkMembersGetTalk.do',
									data : {
										friend : name
									},
									dataType : "json",
									success : function(data,textStatus, jqXHR) {
										debugger;
										$("#chatbox_"+data.friend+" .chatboxcontent").append('<div class="chatboxmessage"><span class="chatboxinfo">'+data.conversation+'</span></div>');
										$("#chatbox_"+ data.friend + " .chatboxcontent").scrollTop(
														$("#chatbox_"+ data.friend + " .chatboxcontent")[0].scrollHeight);
									},
									error : function(data, textStatus, jqXHR) {
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

/* Permet d'envoyer un message */
function checkChatBoxInputKey(event, chatboxtextarea, chatboxtitle) {
	if (event.keyCode == 13 && event.shiftKey == 0) {
		message = $(chatboxtextarea).val();
		message = message.replace(/^\s+|\s+$/g, "");

		$(chatboxtextarea).val('');
		$(chatboxtextarea).focus();
		$(chatboxtextarea).css('height', '44px');

		if (message != '') {

			$.ajax({
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