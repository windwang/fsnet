package fr.univartois.ili.fsnet.facade.jforumtags.tagLibs;

import fr.univartois.ili.fsnet.facade.jforumtags.entities.EntiteSociale;
import fr.univartois.ili.fsnet.facade.jforumtags.entities.Hub;
import fr.univartois.ili.fsnet.facade.jforumtags.entities.Message;
import fr.univartois.ili.fsnet.facade.jforumtags.entities.Topic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


public class TopicTag extends TagSupport {
	private Hub hub;
	private String dateDebut;
	private String dateFin;
	private EntiteSociale entiteSociale;
	private String var;
	private Iterator<Topic> it;
	
	
	public void setVar(String var) {
		this.var = var;
	}
	
	public void setHub(Hub hub) {
		this.hub = hub;
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
	
	
	
	@Override
	public int doStartTag() throws JspException {
//		Message m1 = new Message();
//		m1.setAuteur("Mehdi");
//		m1.setPostTime("22/03/1985 15h00");
//		m1.setText("Mon messgae de test...");
//		
//		Message m2 = new Message();
//		m2.setAuteur("Mouez");
//		m2.setPostTime("20/10/1982 22h00");
//		m2.setText("Mon messgae de test...");
//		Topic jpa=new Topic();
//		jpa.setNom("jpa");
//		
//		Topic struts=new Topic();
//		struts.setNom("struts");
//		struts.setMessage(m1);
//		jpa.setMessage(m2);
//		
//		
//		monHUB=new Hub();
//		monHUB.setNom("Java");
//		monHUB.setTopic(jpa);
//		monHUB.setTopic(struts);
//		it=monHUB.getTopics().iterator();
		
		if(dateDebut!=null && dateFin!=null){
			it=getListTopic(dateDebut,dateFin).iterator();
		}
		
		else if(entiteSociale!=null){
			it=getListTopicByEntiteSociale(entiteSociale).iterator();
		}
			
		else if(hub!=null){
			it=getListTopicByHub(hub).iterator();
		}
		
		else it=getListTopic().iterator();
		
		if (updateContext()) {
				return EVAL_BODY_INCLUDE;
			}
		
		return SKIP_BODY;
	}

	private List getListTopic() {
		Topic topic1=new Topic();
		topic1.setNom("topic1 sans conditions");
		
		Topic topic2=new Topic();
		topic2.setNom("topic2 sans conditions");
		
		List liste=new ArrayList<Topic>();
		liste.add(topic1);
		liste.add(topic2);
		return liste;
		//return null;
	}

	private List getListTopicByHub(Hub hub2) {
		// TODO Auto-generated method stub
		Topic topic1=new Topic();
		topic1.setNom("topic1 par hub");
		
		Topic topic2=new Topic();
		topic2.setNom("topic2 par hub");
		
		List liste=new ArrayList<Topic>();
		liste.add(topic1);
		liste.add(topic2);
		return liste;
		//return null;
	}

	private List getListTopicByEntiteSociale(EntiteSociale entiteSociale2) {
		// TODO Auto-generated method stub
		Topic topic1=new Topic();
		topic1.setNom("topic1 par entite sociale");
		
		Topic topic2=new Topic();
		topic2.setNom("topic2 par entite sociale");
		
		List liste=new ArrayList<Topic>();
		liste.add(topic1);
		liste.add(topic2);
		return liste;
		//return null;
	}

	private List getListTopic(String dateDebut2, String dateFin2) {
		Topic topic1=new Topic();
		topic1.setNom("topic1 par dates");
		
		Topic topic2=new Topic();
		topic2.setNom("topic2 par dates");
		
		List liste=new ArrayList<Topic>();
		liste.add(topic1);
		liste.add(topic2);
		return liste;
		//return null;
	}

	private boolean updateContext() {
		// TODO Auto-generated method stub
		
		if(it.hasNext()){
			Topic topicTemp;
			topicTemp=it.next();
			
			pageContext.setAttribute(var,topicTemp);
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
