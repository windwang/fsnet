package fr.univartois.ili.fsnet.facade.jforumtags.tagLibs;

import fr.univartois.ili.fsnet.facade.jforumtags.entities.EntiteSociale;
import fr.univartois.ili.fsnet.facade.jforumtags.entities.Hub;
import fr.univartois.ili.fsnet.facade.jforumtags.entities.Message;
import fr.univartois.ili.fsnet.facade.jforumtags.entities.Topic;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


public class MessageTag extends TagSupport {

	
	private Topic topicTemp;
	private Hub hub;
	private String dateDebut;
	private String dateFin;
	private EntiteSociale entiteSociale;
	private Iterator <Message> it;
	private String var;
	private Topic topic;
	
	
	
	public void setVar(String var) {
		this.var = var;
	}
	
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	public void setDateDebut(String dateDebut) {
		this.dateDebut = dateDebut;
	}
	public void setDateFin(String dateFin) {
		this.dateFin = dateFin;
	}
	public void setEntiteSociale(EntiteSociale entiteSociale) {
		this.entiteSociale = entiteSociale;
	}
	
	public int doStartTag() throws JspException {
		if(dateDebut!=null && dateFin!=null){
			it=getListMessage(dateDebut,dateFin).iterator();
		}
		
		else if(entiteSociale!=null){
			it=getListMessageByEntiteSocial(entiteSociale).iterator();
		}
		
		else if(hub!=null){
			it=getListMessageByHub(hub).iterator();
		}
		
		else if(topic!=null){
			it=getListMessageByTopic(topic).iterator();
		}
		
		else it=getListMessage().iterator();;
			if (updateContext()) {
				return EVAL_BODY_INCLUDE;
			}
		
		return SKIP_BODY;
	}
	
	
	private List getListMessage() {
		Message m1 = new Message();
		m1.setAuteur("Mehdi");
		m1.setPostTime("22/03/1985 15h00");
		m1.setText("Message1 sans conditions");
		
		Message m2 = new Message();
		m2.setAuteur("Mouez");
		m2.setPostTime("20/10/1982 22h00");
		m2.setText("Message2 sans conditions");
		List liste=new ArrayList<Message>();
		liste.add(m1);
		liste.add(m2);
		return liste;
		//return null;
	}

	private List getListMessageByTopic(Topic topic2) {
		// TODO Auto-generated method stub
		Message m1 = new Message();
		m1.setAuteur("Mehdi");
		m1.setPostTime("22/03/1985 15h00");
		m1.setText("Message1 by topic");
		
		Message m2 = new Message();
		m2.setAuteur("Mouez");
		m2.setPostTime("20/10/1982 22h00");
		m2.setText("Message2 by topic");
		List liste=new ArrayList<Message>();
		liste.add(m1);
		liste.add(m2);
		return liste;
		//return null;
	}

	private List getListMessageByHub(Hub hub2) {
		// TODO Auto-generated method stub
		Message m1 = new Message();
		m1.setAuteur("Mehdi");
		m1.setPostTime("22/03/1985 15h00");
		m1.setText("Message1 by hub");
		
		Message m2 = new Message();
		m2.setAuteur("Mouez");
		m2.setPostTime("20/10/1982 22h00");
		m2.setText("Message2 by hub");
		List liste=new ArrayList<Message>();
		liste.add(m1);
		liste.add(m2);
		return liste;
		//return null;
	}

	private List getListMessageByEntiteSocial(EntiteSociale entiteSociale2) {
		Message m1 = new Message();
		m1.setAuteur("Mehdi");
		m1.setPostTime("22/03/1985 15h00");
		m1.setText("Message1 by entite sociale");
		
		Message m2 = new Message();
		m2.setAuteur("Mouez");
		m2.setPostTime("20/10/1982 22h00");
		m2.setText("Message2 by entite sociale");
		List liste=new ArrayList<Message>();
		liste.add(m1);
		liste.add(m2);
		return liste;
		//return null;
	}

	private List getListMessage(String dateDebut2, String dateFin2) {
		// TODO Auto-generated method stub
		Message m1 = new Message();
		m1.setAuteur("Mehdi");
		m1.setPostTime("22/03/1985 15h00");
		m1.setText("Message1 by dates");
		
		Message m2 = new Message();
		m2.setAuteur("Mouez");
		m2.setPostTime("20/10/1982 22h00");
		m2.setText("Message2 by entite dates");
		List liste=new ArrayList<Message>();
		liste.add(m1);
		liste.add(m2);
		return liste;
		//return null;
	}

	private boolean updateContext() {
		// TODO Auto-generated method stub
		
		if(it.hasNext()){
			Message mess;
			mess=it.next();
			
			pageContext.setAttribute(var,mess);
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
