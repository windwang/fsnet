(function($) {
	/* the argument is the relative path to the file */
	$
			.extend({
				notification : function() {
					/* Get the base URI */
					var base = document.location.href
							.match(/(http:\/\/[^\/]+:?\d*\/[^\/]+\/).*/)[1];
					$('.chatNotification').remove();

					return $(
							'<audio src="'
									+ base
									+ arguments[0]
									+ '" class="chatNotification" autoplay="autoplay" hidden/>')
							.appendTo("body");
				}
			});

})(jQuery);