$(function() {
	$(".corp_experience").css("display","none");
	$(".corp_diplome").css("display","none");
	$(".corp_certificat").css("display","none");
	$(".corp_langue").css("display","none");
	$("#ErrorCertif").css("display","none");
	$(".CvNomCertifError").css("display","none");
	$(".CvEcolCertifError").css("display","none");
	$(".CvDateCertifError").css("display","none");
	$(".CVLangueError").css("display","none");
	$(".CvEtablissmentError").css("display","none");
	$(".etudBeginDateError").css("display","none");
	$(".etudEndDateError").css("display","none");
	$(".CvEtudeDomError").css("display","none");
	$(".CvEtudeError").css("display","none");
	
	
	
	$(".CvPosteError").css("display","none");
	$(".NomEntrepriseError").css("display","none");
	$(".CvLieuError").css("display","none");
	$(".expBeginDateError").css("display","none");
	$(".expEndDateError").css("display","none");
	$(".CvSecteurError").css("display","none");
	var i=0;
	var j=0;
	var k=0;
	var z=0;
	$(".addExp").css("display","inline");
	$(".addForm").css("display","inline");
	$(".addExp").click(function(){
		$(".corp_experience").css("display","inline");
		$(".addExp").css("display","none");
		$(".CvPosteError").css("display","none");
		$(".NomEntrepriseError").css("display","none");
		$(".CvLieuError").css("display","none");
		$(".expBeginDateError").css("display","none");
		$(".expEndDateError").css("display","none");
		$(".CvSecteurError").css("display","none");
	});
	$(".SaveExp").click(function(){
		$(".CvPosteError").css("display","none");
		$(".NomEntrepriseError").css("display","none");
		$(".CvLieuError").css("display","none");
		$(".expBeginDateError").css("display","none");
		$(".expEndDateError").css("display","none");
		$(".CvSecteurError").css("display","none");
		var erreur=0;
		if($("#CvPoste").val()==''){
			$(".CvPosteError").css("display","inline");
			erreur=1;
		}
		if($("#NomEntreprise").val()==''){
			$(".NomEntrepriseError").css("display","inline");
			erreur=1;
		}
	
		if($("#CvLieu").val()==''){
			$(".CvLieuError").css("display","inline");
			erreur=1;
		}
		if($("#CvSecteur").val()==''){
			$(".CvSecteurError").css("display","inline");
			erreur=1;
		}
		if($("#expBeginDate").val()==''){
			$(".expBeginDateError").css("display","inline");
			erreur=1;
		}
		if($("#expEndDate").val()==''){
			$(".expEndDateError").css("display","inline");
			erreur=1;
		}
		
		if(erreur==0){
		
		var recapExp= "<strong>"+$("#CvPoste").val()+"</strong><p>"+$("#NomEntreprise").val()+"</p><p>"
		+$("#CvSecteur").val()+"</p><p>"+$("#CvLieu").val()+"</p><p> Date debut :"+$("#expBeginDate").val()+"</p><p> date de  fin :"+$("#expEndDate").val()+"</p>";
		
		var inputRecap='<div id="ExpInput" style="display:none;"><input type="hidden" id="CVPostehidden" name="CvPoste'+i+'" value="'+$("#CvPoste").val()+'" />'
		+'<input type="hidden" name="NomEntreprise'+i+'" value="'+$("#NomEntreprise").val()+'" />'
		+'<input type="hidden" name="CvLieu'+i+'" value="'+$("#CvLieu").val()+'" />'
		+'<input type="hidden" name="expBeginDate'+i+'" value="'+$("#expBeginDate").val()+'" />'
		+'<input type="hidden" name="CvSecteur'+i+'" value="'+$("#CvSecteur").val()+'" />'
		+'<input type="hidden" name="expEndDate'+i+'" value="'+$("#expEndDate").val()+'" /></div>';
		$(".corp_experience").css("display","none");
		var $annuler=$('<span id="supprimer'+i+'"><a onclick="changeExp('+i+')">supprimer</a></span>');
		$(".addExp").css("display","inline");
		$('.listeExperience').append('<li id="experience_'+i+'">'+recapExp+'</li>');
		$('#experience_'+i).append(inputRecap);
		$('#experience_'+i).append($annuler);
		$("#CvPoste").val('');
		$("#NomEntreprise").val('');
		$("#CvSecteur").val('');
		$("#CvLieu").val('');
		$("#expBeginDate").val('');
		$("#expEndDate").val('');
		$(".CvPosteError").css("display","none");
		$(".NomEntrepriseError").css("display","none");
		$(".CvLieuError").css("display","none");
		$(".expBeginDateError").css("display","none");
		$(".expEndDateError").css("display","none");
		i++;
		}
		
	});
	$(".annuleExp").click(function(){
		$(".corp_experience").css("display","none");
		$(".addExp").css("display","inline");
	});
	
	
	
	$(".addForm").click(function(){
		$(".corp_diplome").css("display","inline");
		$(".addForm").css("display","none");
		$(".CvEtablissmentError").css("display","none");
		$(".etudBeginDateError").css("display","none");
		$(".etudEndDateError").css("display","none");
		$(".CvEtudeDomError").css("display","none");
		$(".CvEtudeError").css("display","none");
	});
	$(".SaveForm").click(function(){
		$(".CvEtablissmentError").css("display","none");
		$(".etudBeginDateError").css("display","none");
		$(".etudEndDateError").css("display","none");
		$(".CvEtudeDomError").css("display","none");
		$(".CvEtudeError").css("display","none");
		var erreur=0;
		if($("#CvEtude").val()==''){
			$(".CvEtudeError").css("display","inline");
			erreur=1;
		}
		if($("#CvEtudeDom").val()==''){
			$(".CvEtudeDomError").css("display","inline");
			erreur=1;
		}
		if($("#etudBeginDate").val()==''){
			$(".etudBeginDateError").css("display","inline");
			erreur=1;
		}
		
		if($("#etudEndDate").val()=='' ){
			$(".etudEndDateError").css("display","inline");
			erreur=1;
		}
		if($("#CvEtablissment").val()==''){
			$(".CvEtablissmentError").css("display","inline");
			erreur=1;
		}
		
		if(erreur==0){
		var recapExp= "<strong>"+$("#CvEtude").val()+"</strong><p>"+$("#CvEtudeDom").val()+"</p><p>"
		+$("#CvEtablissment").val()+"</p><p>"+$("#CvEtudePays").val()+"</p><p> Date debut :"+$("#etudBeginDate").val()+"</p><p> date fin "+$("#etudEndDate").val()+"</p>";
		var inputRecap='<div id="FormInput" style="display:none;"><input type="hidden" name="CvEtude'+j+'" value="'+$("#CvEtude").val()+'" />'
		+'<input type="hidden" name="CvEtudeDom'+j+'" value="'+$("#CvEtudeDom").val()+'" />'
		+'<input type="hidden" name="CvEtablissment'+j+'" value="'+$("#CvEtablissment").val()+'" />'
		+'<input type="hidden" name="CvEtudePays'+j+'" value="'+$("#CvEtudePays").val()+'" />'
		+'<input type="hidden" name="etudBeginDate'+j+'" value="'+$("#etudBeginDate").val()+'" />'
		+'<input type="hidden" name="etudEndDate'+j+'" value="'+$("#etudEndDate").val()+'" /></div>';
		var $annuler=$('<span id="supprimerDip'+j+'"><a onclick="changeDip('+j+')">supprimer</a></span>');
		$(".corp_diplome").css("display","none");
		$(".addForm").css("display","inline");
		$('.listeFormation').append('<li id="formation_'+j+'">'+recapExp+'</li>');
		$('#formation_'+j).append(inputRecap);
		$('#formation_'+j).append($annuler);
		$("#CvEtude").val('');
		$("#CvEtudeDom").val('');
		$("#CvEtablissment").val('');
		$("#CvEtudePays").val('');
		$("#etudBeginDate").val('');
		$("#etudEndDate").val('');
		$(".CvEtablissmentError").css("display","none");
		$(".etudBeginDateError").css("display","none");
		$(".etudEndDateError").css("display","none");
		$(".CvEtudeDomError").css("display","none");
		$(".CvEtudeError").css("display","none");
		j++;
		}
	});
	$(".annuleForm").click(function(){
		$(".corp_diplome").css("display","none");
		$(".addForm").css("display","inline");
	});
	
	$(".addCert").click(function(){
		
		$(".corp_certificat").css("display","inline");
		$(".CvNomCertifError").css("display","none");
		$(".addCert").css("display","none");
		$(".CvNomCertifError").css("display","none");
		$(".CvDateCertifError").css("display","none");
		$(".CvEcolCertifError").css("display","none");
		
	});
	$(".SaveCert").click(function(){
		$(".CvNomCertifError").css("display","none");
		$(".CvDateCertifError").css("display","none");
		$(".CvEcolCertifError").css("display","none");
		
		var erreur=0;
		if($("#CvNomCertif").val()=='' ){
			
			$(".CvNomCertifError").css("display","inline");
			erreur=1;
			
		}
		
		if($("#CvEcolCertif").val()=='' ){
			$(".CvEcolCertifError").css("display","inline");
			erreur=1;
		}
		if($("#CvDateCertif").val()=='' ){
			$(".CvDateCertifError").css("display","inline");
			erreur=1;
		}
		if(erreur==0){
		var recapExp= "<strong>"+$("#CvNomCertif").val()+"</strong><p>"+$("#CvEcolCertif").val()+"</p><p> Date d'obtention :"+$("#CvDateCertif").val()+"</p>";
		var inputRecap='<div id="certifInput" style="display:none;"><input type="hidden" name="CvNomCertif'+k+'" value="'+$("#CvNomCertif").val()+'" />'
		+'<input type="hidden" name="CvEcolCertif'+k+'" value="'+$("#CvEcolCertif").val()+'" />'
		+'<input type="hidden" name="CvDateCertif'+k+'" value="'+$("#CvDateCertif").val()+'" /></div>';
		var $annuler=$('<span id="supprimerCert'+k+'"><a onclick="changeCert('+k+')">supprimer</a></span>');
		$(".CvNomCertifError").fadeOut();
		$(".corp_certificat").css("display","none");
		$(".addCert").css("display","inline");
		$("#certifInput").css("display","none");
		$('.listeCertification').append('<li id="certifica_'+k+'">'+recapExp+'</li>');
		$('#certifica_'+k).append(inputRecap);
		$('#certifica_'+k).append($annuler);
		$("#certifInput").css("display","none");
		$("#CvNomCertif").val('');
		$("#CvEcolCertif").val('');
		$("#CvDateCertif").val('');
		$(".CvNomCertifError").css("display","none");
		$(".CvDateCertifError").css("display","none");
		$(".CvEcolCertifError").css("display","none");
			k++;
		}
		
	});
	$(".annuleCert").click(function(){
		$(".corp_certificat").css("display","none");
		$(".addCert").css("display","inline");
	});
	
$(".addLangue").click(function(){
	$(".corp_langue").css("display","inline");
	$(".addLangue").css("display","none");
	$(".CVLangueError").css("display","none");

	});
	$(".SaveLangue").click(function(){
		$(".CVLangueError").css("display","none");
		var erreur =0;
		if($("#CVLangue").val()=='' ){
			$(".CVLangueError").css("display","inline");
			erreur=1;
		}
		if(erreur==0){
		var recapExp= "<table><tr><td><strong>"+$("#CVLangue").val()+"</strong></td><td>"+$("#niveaux").val()+"</td></tr></table>";
		var inputRecap='<div id="LangueInput" style="display:none;"><input type="hidden" name="CVLangue'+z+'" value="'+$("#CVLangue").val()+'" />'
		+'<input type="hidden" name="niveaux'+z+'" value="'+$("#niveaux").val()+'" /></div>';
		var $annuler=$('<span id="supprimerLang'+z+'"><a onclick="changeLang('+z+')">supprimer</a></span>');
		$(".corp_langue").css("display","none");
		$(".addLangue").css("display","inline");
		
		$('.listeLangues').append('<li id="Langues_'+z+'">'+recapExp+'</li>');
		$('#Langues_'+z).append(inputRecap);
		$('#Langues_'+z).append($annuler);
		$("#LangueInput").css("display","none");
		$("#CVLangue").val('');
		
		$(".CVLangueError").css("display","none");
	
			z++;
		}
		
	});
	$(".annuleLangue").click(function(){
		$(".corp_langue").css("display","none");
		$(".addLangue").css("display","inline");
	});
	$(".Edit").click(function(){
		
		$(".corp_experience").css("display","inline");
		$('#experience_'+0).css("display","none");
		
	});



});
function changeExp(iden){
	var answer = confirm ("voulez vous vraimment supprimer?")
	if(answer){
	document.getElementById('experience_'+iden).style.display = "none";
	document.getElementById('supprimer'+iden).style.display = "none";
	}
	
}
function changeDip(iden){
	var answer = confirm ("voulez vous vraimment supprimer?")
	if(answer){
	document.getElementById('formation_'+iden).style.display = "none";
	document.getElementById('supprimerDip'+iden).style.display = "none";
	}
}
function changeCert(iden){
	var answer = confirm ("voulez vous vraimment supprimer?")
	if(answer){
	document.getElementById('certifica_'+iden).style.display = "none";
	document.getElementById('supprimerCert'+iden).style.display = "none";
	}
}

function changeLang(iden){
	var answer = confirm ("voulez vous vraimment supprimer?")
	if(answer){
	document.getElementById('Langues_'+iden).style.display = "none";
	document.getElementById('supprimerLang'+iden).style.display = "none";
	}
}
