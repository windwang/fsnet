package fr.univartois.ili.fsnet.facade.jforumtags.tagLibs;

import fr.univartois.ili.fsnet.facade.jforumtags.entities.EntiteSociale;
import fr.univartois.ili.fsnet.facade.jforumtags.entities.Hub;
import fr.univartois.ili.fsnet.facade.jforumtags.entities.Message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


public class HubTag extends TagSupport{

	private String var;
	private String dateDebut;
	private String dateFin;
	private EntiteSociale decideur;
	private Iterator <Hub> it; 
	
	
	public void setVar(String var) {
		this.var = var;
	}
	
	public void setDateDebut(String dateDebut) {
		this.dateDebut = dateDebut;
	}
	public void setDateFin(String dateFin) {
		this.dateFin = dateFin;
	}
	public void setDecideur(EntiteSociale decideur) {
		this.decideur = decideur;
	}
	public int doStartTag() throws JspException {
		if(dateDebut!=null && dateFin!=null){
			it=getListHub(dateDebut,dateFin).iterator();
		}
		
		else if(decideur!=null){
			it=getListHubByEntiteSociale(decideur).iterator();
		}
		
		else it=getListHub().iterator();
			if (updateContext()) {
				return EVAL_BODY_INCLUDE;
			}
		
		return SKIP_BODY;
	}

	private List getListHub() {
		// TODO Auto-generated method stub
		Hub hub=new Hub();
		hub.setNom("hub 1 sans conditions");
		Hub hub2=new Hub();
		hub2.setNom("hub2 sans conditions");
		List liste = new ArrayList<Hub>();
		liste.add(hub);
		liste.add(hub2);
		return liste;
		//return null;
	}

	private List getListHubByEntiteSociale(EntiteSociale decideur2) {
		// TODO Auto-generated method stub
		Hub hub=new Hub();
		hub.setNom("hub 1 par dates");
		Hub hub2=new Hub();
		hub2.setNom("hub2 par dates");
		List liste = new ArrayList<Hub>();
		liste.add(hub);
		liste.add(hub2);
		return liste;
		//return null;
	}

	private List getListHub(String dateDebut2, String dateFin2) {
		// TODO Auto-generated method stub
		Hub hub=new Hub();
		hub.setNom("hub 1 par dates");
		Hub hub2=new Hub();
		hub2.setNom("hub2 par dates");
		List liste = new ArrayList<Hub>();
		liste.add(hub);
		liste.add(hub2);
		return liste;
		//return null;
	}

	private boolean updateContext() {
		if(it.hasNext()){
			Hub hub;
			hub=it.next();
			
			pageContext.setAttribute(var,hub);
			return true;
		}
		return false;
	}
	
	public int doAfterBody() throws JspException {

		if (updateContext()) {
			return EVAL_BODY_AGAIN;
		}
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		pageContext.removeAttribute(var);
		return super.doEndTag();
	}
	
}
