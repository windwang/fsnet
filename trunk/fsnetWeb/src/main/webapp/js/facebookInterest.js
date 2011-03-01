function initFacebook(key){
	FB.init({
		appId  : key,
		status : true, // verifie le statut de la connexion
		cookie : true, // active les cookies pour que le serveur puisse accéder à la session
		xfbml  : true  // active le XFBML (HTML de Facebook)
	}); 
} 

function recupInterests(response){
	
	FB.api('/me/interests', function(response) {
		var messages = '<br/>Vos interets : <ul>';
		var result='';
		var cpt=0;
		$.each(response.data, function(key, value) {
			if(cpt==0){
				result+=value.name;
			}else{
				result+=';'+value.name;
			}
			cpt++;
			messages += '<li>' + value.name + '</li>';                          

		}); 

		messages += '</ul><br/>PS : Pensez à valider vos intérêts importés en cliquant sur le bouton créer';
		document.getElementById('createdInterestName').value=result;
		$('#facebook_profile_text').html('<a href="#" id="facebook_logout">Deconnexion</a>');

		$('#facebook_interests').html(messages);

	});
}

$(document).ready(function() {
	FB.Event.subscribe('auth.login', function(response) {
		recupInterests(response);
		$('#facebook_button_box').hide();
		$('#facebook_profile').show();
	});

	FB.getLoginStatus(function(response) {
		if (response.session) {
			recupInterests(response);
			$('#facebook_button_box').hide();
			$('#facebook_profile').show();
		}
	});

	$('#facebook_logout').live('click', function() {
		FB.logout(function(response) {
			$('#facebook_button_box').show();
			$('#facebook_profile').hide();
		});
		return false;
	});			
});