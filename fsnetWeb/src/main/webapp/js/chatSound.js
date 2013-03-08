(function($){
	/* the argument is the relative path to the file */
  $.extend({
    notification: function(){
    	/*Get the base URI*/
      var base=document.location.href.match(/(http:\/\/[^\/]+:?\d*\/[^\/]+\/).*/)[1];
      return $("<embed src='"+base+arguments[0]+"' hidden='true' autostart='true' loop='false' class='playSound'>").appendTo('body');
    }
  });

})(jQuery);