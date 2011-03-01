function initFacebook(key){
	FB.init({
		appId  : key,
		status : true, // verifie le statut de la connexion
		cookie : true, // active les cookies pour que le serveur puisse accéder à la session
		xfbml  : true  // active le XFBML (HTML de Facebook)
	}); 
} 

function recupInfos(response){
	var url = 'http://graph.facebook.com/' + response.id + '/picture';
	if(response.birthday!=undefined){
		var date = response.birthday.split('/');
	}

	$('#facebook_profile_text').html('<p><a href="' + response.link + '"><strong>' + response.name + '</strong></a><br /><a href="#" id="facebook_logout">Deconnexion</a>');

	var user_data_use="";
	var user_data_useless="";

	if(document.getElementById("name").value==""){
		document.getElementById("name").value=response.last_name;
		user_data_use+='<li>Votre nom : '+ response.last_name + '</li>';
	}else{
		user_data_useless+='<li>Votre nom : '+ response.last_name + '</li>';
	}
	if(document.getElementById("firstName").value==""){
		document.getElementById("firstName").value=response.first_name;
		user_data_use+='<li>Votre prenom : '+ response.first_name + '</li>';
	}else{
		user_data_useless+='<li>Votre prenom : '+ response.first_name + '</li>';
	}

	if(document.getElementById("dateOfBirth").value=="" && response.birthday!=undefined){
		document.getElementById("dateOfBirth").value=date[1]+'/'+date[0]+'/'+date[2];
		user_data_use+='<li>Votre date de naissance : ' + date[1]+'/'+date[0]+'/'+date[2] + '</li>';
	}else{
		if(response.birthday==undefined){
			user_data_useless+='<li>Votre date de naissance : Non définie </li>';
		}else{
			user_data_useless+='<li>Votre date de naissance : '+date[1]+'/'+date[0]+'/'+date[2]+'</li>';
		}
	}

	if(document.getElementById("mail").value=="" && response.email!=undefined){
		document.getElementById("mail").value=response.email;
		user_data_use+='<li>Votre adresse email : ' + response.email + '</li>';
	}else{
		if(response.email==undefined){
			user_data_useless+='<li>Votre adresse email : Non définie </li>';
		}else{
			user_data_useless+='<li>Votre adresse email : ' + response.email + '</li>';
		}
	}

	document.getElementById("sexe").value=response.gender;
	user_data_use+='<li>Votre genre : '+ response.gender + '</li>';

	if(document.getElementById("city").value=="" && response.location!=undefined){
		document.getElementById("city").value=response.location.name;
		user_data_use+='<li>Votre ville : '+ response.location.name + '</li>';
	}else{
		if(response.location==undefined){
			user_data_useless+='<li>Votre ville : Non définie </li>';
		}else{
			user_data_useless+='<li>Votre ville : '+ response.location.name + '</li>';
		}
	}

	var user_data='<li>Données importées ajoutées : </li>'+user_data_use+'<br/><li>Données importées déjà présentes : '+user_data_useless;

	$('#facebook_user_data').html(user_data); 
}

$(document).ready(function() {
	FB.Event.subscribe('auth.login', function(response) {
		FB.api('/me', function(response) { 
			recupInfos(response);
		});
		$('#facebook_button_box').hide();
		$('#facebook_profile').show();
	});

	FB.getLoginStatus(function(response) {
		if (response.session) {
			FB.api('/me', function(response) { 
				recupInfos(response);
			});
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
