jQuery(function ($) {
	
	var support = {
			message: null,
		init: function () {
			$('a#support').click(function (e) {
				e.preventDefault();
	
				// load the contact form using ajax
				$.get("content/support/support.jsp", function(data){
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
	if(($('#support-container #supportTitle').val())&&($('#support-container #supportContent').val())){
		return true;
	}else{
		if(navigator.browserLanguage)
		 {
		 var language = navigator.browserLanguage ;
		  } else {
		 var language = navigator.language ;
		  }
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

