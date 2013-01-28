jQuery(function ($) {
	
	var support = {
			message: null,
			
		init: function () {
			$('a#support').click(function (e) {
				e.preventDefault();
	
				// load the contact form using ajax
				$.get("content/support/support.jsp", function(data){
									
					//alert(data.length);
					//jQuery v1.9.0 examines the string to see if it looks like HTML (i.e., it starts with <tag ... >)
					var index = data.indexOf('<');
					data=data.substr(index,data.length);
					// Mozilla Firefox fix
					if(jQuery.browser.mozilla)
						data = "<div>" + data + "</div>";
					
					// create a modal dialog with the data	
					$(data).modal({
						position: ["25%","35%"],
						overlayId: 'support-overlay',
						containerId: 'support-container',
						onOpen: support.open,
						onShow: support.show,
						onClose: support.close
					});
				});
			});
		},
		open: function (dialog) {
			var h = 240;	
			dialog.overlay.fadeIn(200, function () {
				dialog.container.fadeIn(200, function () {
					dialog.data.fadeIn(200, function () {
						$('#support-container .support-content').animate({
							height: h
						});		
					});
				});
			});
			
		},
		show : function (dialog) {
			$('.support-cancel').click(function (e) {
				e.preventDefault();
				$('#support-container .support-content').animate({
					height: 40
				},function () {
					$('#support-container .support-message').fadeOut();
					$('#support-container .support-title').html('Goodbye...');
					$('#support-container form').fadeOut(200);
					dialog.overlay.fadeOut(200, function () {
						dialog.container.fadeOut(200, function () {
										$.modal.close();	
						});
					});
				});
				location.reload();
			});
		},
		close: function (dialog) {
			
		}
	};
		support.init();		
});

function validate(){
	
	if(navigator.browserLanguage)
	 {
	 var language = navigator.browserLanguage ;
	  } else {
	 var language = navigator.language ;
	  }
	
	if(($('#support-container #supportTitle').val())&&($('#support-container #supportContent').val())){
		
		$('#support-container .support-message').fadeOut();
		$('#support-container form').fadeOut(200);
		$('#support-container .support-content').animate({
			height: 40
		});
		
		if (language.indexOf('fr') > -1){
			$('#support-container .support-title').html('Votre message a été envoyé...');
		}else{
			$('#support-container .support-title').html('Your message has send...');
		}
		
		return true;
	  
	}else{
		var message ='';
		if((!$('#support-container #supportTitle').val())&&(!$('#support-container #supportContent').val())){
			if (language.indexOf('fr') > -1){
				message += 'Attention: pas de sujet et pas de message';
			}else{
				message += 'Warning: No subject and no message';
			}
		}else{
			if (!$('#support-container #supportTitle').val()) {
				if (language.indexOf('fr') > -1){
					message += 'Attention: pas de sujet ';
				}else{
					message += 'Warning: No subject';
				}
			}
			
			if (!$('#support-container #supportContent').val()) {
				if (language.indexOf('fr') > -1){
					message += 'Attention: pas de message';
				}else{
					message += 'Warning: No message';
				}
			}
		}
			if (message.length > 0) {
				$('#support-container .support-message').animate({
					height: '20px'
				},function () { $('#support-container .support-message')
						.html($('<div class="support-error"></div>').append(message))
						.fadeIn(200);});	
		}
			return false;
	}
}

function refuserToucheEntree(event)
{
    // Compatibilité IE / Firefox
    if(!event && window.event) {
        event = window.event;
    }
    // IE
    if(event.keyCode == 13) {
        event.returnValue = false;
        event.cancelBubble = true;
    }
    // DOM
    if(event.which == 13) {
        event.preventDefault();
        event.stopPropagation();
    }
}


