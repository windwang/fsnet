$(function() {



	$(".corp_experience").css("display","none");
	$(".corp_diplome").css("display","none");
	$(".corp_loisir").css("display","none");
	$(".corp_langue").css("display","none");
	$("#ErrorCertif").css("display","none");
	$(".CvNomLoisirError").css("display","none");
	
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
	var f=0;
	var tabExp=new Array();
	
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
			tabExp[i]=i;
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
		$('.listeExperience').append('<li class="liste" id="experience_'+i+'">'+recapExp+'</li>');
		$('#experience_'+i).append(inputRecap);
		//$('#experience_'+i).append($annuler);
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
	
	
	
	$(".addDip").click(function(){
		$(".corp_diplome").css("display","inline");
		$(".addDip").css("display","none");
		$(".CvEtablissmentError").css("display","none");
		$(".etudBeginDateError").css("display","none");
		$(".etudEndDateError").css("display","none");
		$(".CvEtudeDomError").css("display","none");
		$(".CvEtudeError").css("display","none");
	});
	$(".SaveDip").click(function(){
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
		var inputRecap='<div id="DipInput" style="display:none;"><input type="hidden" name="CvEtude'+j+'" value="'+$("#CvEtude").val()+'" />'
		+'<input type="hidden" name="CvEtudeDom'+j+'" value="'+$("#CvEtudeDom").val()+'" />'
		+'<input type="hidden" name="CvEtablissment'+j+'" value="'+$("#CvEtablissment").val()+'" />'
		+'<input type="hidden" name="CvEtudePays'+j+'" value="'+$("#CvEtudePays").val()+'" />'
		+'<input type="hidden" name="CvEtudeVille'+j+'" value="'+$("#CvEtudeVille").val()+'" />'
		+'<input type="hidden" name="etudBeginDate'+j+'" value="'+$("#etudBeginDate").val()+'" />'
		+'<input type="hidden" name="etudEndDate'+j+'" value="'+$("#etudEndDate").val()+'" /></div>';
		var $annuler=$('<span id="supprimerDip'+j+'"><a onclick="changeDip('+j+')">supprimer</a></span>');
		$(".corp_diplome").css("display","none");
		$(".addForm").css("display","inline");
		$('.listeDiplome').append('<li class="liste" id="diplome_'+j+'">'+recapExp+'</li>');
		$('#diplome_'+j).append(inputRecap);
		//$('#formation_'+j).append($annuler);
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
	$(".annuleDip").click(function(){
		$(".corp_diplome").css("display","none");
		$(".addDip").css("display","inline");
	});
	//formation
	$(".addForm").click(function(){
		$(".corp_formation").css("display","inline");
		$(".addForm").css("display","none");
		$(".CvEtablissmentformError").css("display","none");		
		$(".CvFormationError").css("display","none");
		$(".DateObtentionError").css("display","none");
	});
	$(".SaveForm").click(function(){
		$(".CvEtablissmentformError").css("display","none");
		$(".DateObtentionError").css("display","none");
		$(".CvFormationError").css("display","none");
		
		var erreur=0;
		
		if($("#CvEtablissmentform").val()==''){
			$(".CvEtablissmentformError").css("display","inline");
			erreur=1;
		}
		if($("#DateObtention").val()==''){
			$(".DateObtentionError").css("display","inline");
			erreur=1;
		}
		if($("#CvFormation").val()==''){
			$(".CvFormationError").css("display","inline");
			erreur=1;
		}
		
		if(erreur==0){
		var recapExp= "<strong>"+$("#CvFormation").val()+"</strong><p>"
		+$("#CvEtablissmentform").val()+"</p><p>"+$("#CvFormPays").val()+"-"+$("#CvFormVille").val()+"</p><p> "+$("#DateObtention").val();
		var inputRecap='<div id="FormInput" style="display:none;"><input type="hidden" name="CvFormation'+f+'" value="'+$("#CvFormation").val()+'" />'
		
		+'<input type="hidden" name="CvEtablissmentform'+f+'" value="'+$("#CvEtablissmentform").val()+'" />'
		+'<input type="hidden" name="CvFormPays'+f+'" value="'+$("#CvFormPays").val()+'" />'
		+'<input type="hidden" name="CvFormVille'+f+'" value="'+$("#CvEtudeVille").val()+'" />'
		+'<input type="hidden" name="DateObtention'+f+'" value="'+$("#DateObtention").val()+'" />'

		
		$(".corp_diplome").css("display","none");
		$(".addForm").css("display","inline");
		$('.listeFormation').append('<li class="liste" id="formation_'+f+'">'+recapExp+'</li>');
		$('#formation_'+j).append(inputRecap);
		
		$("#CvFormation").val('');
		
		$("#CvEtablissmentform").val('');
		$("#CvFormPays").val('');
		$("#DateObtention").val('');
	
		$(".CvEtablissmentformError").css("display","none");
		$(".DateObtentionError").css("display","none");
	
		$(".CvFormationError").css("display","none");
		f++;
		}
	});
	$(".annuleForm").click(function(){
		$(".corp_diplome").css("display","none");
		$(".addForm").css("display","inline");
	});
	
	$(".addLoisir").click(function(){
		
		$(".corp_loisir").css("display","inline");
		$(".CvNomLoisirError").css("display","none");
		$(".addLoisir").css("display","none");
		
	
		
	});
	$(".SaveLoisir").click(function(){
		$(".CvNomLoisirError").css("display","none");
		
		var erreur=0;
		if($("#CvNomLoisir").val()=='' ){
			
			$(".CvNomLoisirError").css("display","inline");
			erreur=1;
			
		}
		
		
		if(erreur==0){
		var recapExp= "<strong>"+$("#CvNomLoisir").val()+"</strong><br/>";
		var inputRecap='<div id="loisirInput" style="display:none;"><input type="hidden" name="CvNomLoisir'+k+'" value="'+$("#CvNomLoisir").val()+'" /></div>'

		var $annuler=$('<span id="supprimerLoisir'+k+'"><a onclick="changeLoisir('+k+')">supprimer</a></span>');
		$(".CvNomLoisirError").fadeOut();
		$(".corp_loisir").css("display","none");
		$(".addLoisir").css("display","inline");
		$("#loisirInput").css("display","none");
		$('.listeLoisir').append('<li class="liste" id="loisir_'+k+'">'+recapExp+'</li>');
		$('#loisir_'+k).append(inputRecap);
		//$('#loisir_'+k).append($annuler);
		$("#loisirInput").css("display","none");
		$("#CvNomLoisir").val('');
		
		$(".CvNomLoisirError").css("display","none");
	
			k++;
		}
		
	});
	$(".annuleLoisir").click(function(){
		$(".corp_loisir").css("display","none");
		$(".addLoisir").css("display","inline");
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
		
		$('.listeLangues').append('<li class="liste" id="Langues_'+z+'">'+recapExp+'</li>');
		$('#Langues_'+z).append(inputRecap);
		//$('#Langues_'+z).append($annuler);
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
	$("#envoi").click(function(){
		var nbExp='<input type="hidden" name="nbexp" value="'+i+'" />';
		var nbForm='<input type="hidden" name="nbform" value="'+j+'" />';
		var nblangue='<input type="hidden" name="nblangue" value="'+k+'" />';
		var nbloisir='<input type="hidden" name="nbloisir" value="'+z+'" />';
		$('.listeExperience').append(nbExp);
		$('.listeFormation').append(nbForm);
		$('.listeLangues').append(nblangue);
		$('.listeLoisir').append(nbloisir);
	   
	});
	

});

	
}
