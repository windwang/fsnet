/** Permet de fixer dynamiquement le menu **/


function fixe() {
	
	//$('#menuLateral').css("top",$(window).scrollTop());
	$('#menuLateral').stop();
	$('#menuLateral').animate({
		top:$(window).scrollTop()
	},'slow');
}

$(document).ready(function(){
	
	if($(window).width()>767){
		$(document).on("scroll",fixe);
	}
	
	$(window).resize(function(){
		if($(window).width()>767){
			$('#menuLateral').css("position","relative");
			$(document).on("scroll",fixe);
				
		}else{
			$(document).off("scroll",fixe);
			$('#menuLateral').css("position","static");
		}
		
	});

});