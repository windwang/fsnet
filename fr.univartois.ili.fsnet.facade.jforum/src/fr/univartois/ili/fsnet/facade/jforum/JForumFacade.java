package fr.univartois.ili.fsnet.facade.jforum;

import java.util.ArrayList;
import java.util.List;

import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.entities.Message;
import fr.univartois.ili.fsnet.entities.Topic;
import fr.univartois.ili.fsnet.facade.ForumFacade;

public class JForumFacade implements ForumFacade {
    private List<Hub> lHub;

    public JForumFacade() {
	EntiteSociale ent1 = new EntiteSociale("STACKOWIAK", "Denis",
		"d.stackowiak@gmail.com");
	EntiteSociale ent2 = new EntiteSociale("LE BERRE", "Daniel",
		"leberre@cril.univ-artois.fr");

	lHub = new ArrayList<Hub>();
	Hub h1 = new Hub("Hub test1", "04/02/2009", new ArrayList<Topic>(),
		ent1);
	Hub h2 = new Hub("Hub test2", "05/02/2009", new ArrayList<Topic>(),
		ent2);

	Topic t1 = new Topic("Test topic1", "04/02/2009",
		new ArrayList<Message>());
	
	
	Topic t2= new Topic("Test topic2", "04/02/2009",
		new ArrayList<Message>());
	
	Topic t3= new Topic("Test topic3", "04/02/2009",
		new ArrayList<Message>());
	
	Message m1= new Message("Message1 Message1Message1 \n Message1 Message1 ","04/02/09",ent2);
	Message m2= new Message("Message2 Message2 Message2 Message2 ","04/02/09",ent1);
	Message m3= new Message("Message3 Message3 \n Message3 \n Message ","04/02/09",ent2);	
	Message m4= new Message("Message4 Message4Message4 \n Message4 Message4 ","04/02/09",ent1);
	Message m5= new Message("Message5 ","04/02/09",ent2);
	
	addHub(h1);
	addHub(h2);
	addTopic(t1, h1);
	addTopic(t2,h1);
	addTopic(t3, h2);
	addMessage(m1, t1);
	addMessage(m2, t1);
	addMessage(m3, t2);
	addMessage(m4, t3);
	addMessage(m5, t3);
	
    }

    @Override
    public boolean addHub(Hub hub) {
	lHub.add(hub);
	return true;
    }

    @Override
    public boolean addMessage(Message message, Topic topic) {
	topic.getLesMessages().add(message);
	return true;
    }

    @Override
    public boolean addTopic(Topic topic, Hub hub) {
	hub.getLesTopics().add(topic);
	return true;
    }

    @Override
    public List<Hub> getListHub() {
	return lHub;
    }

    @Override
    public List<Hub> getListHub(String dateBegin, String dateEnd) {
	List<Hub> res = new ArrayList<Hub>();
	for (Hub h : lHub) {
	    if ((compareDate(h.getDateCreation(), dateBegin) > 0)
		    && (compareDate(h.getDateCreation(), dateEnd) < 0))
		res.add(h);
	}

	return res;
    }

    @Override
    public List<Hub> getListHubByEntiteSociale(EntiteSociale decideur) {
	List<Hub> res = new ArrayList<Hub>();
	for (Hub h : lHub) {
	    if (h.getCreateur().equals(decideur))
		res.add(h);
	}
	return res;
    }

    @Override
    public List<Message> getListMessage() {
	List<Message> lMsg = new ArrayList<Message>();
	for (Hub h : lHub) {
	    lMsg.addAll(getListMessageByHub(h));
	}
	return lMsg;
    }

    @Override
    public List<Message> getListMessage(String dateBegin, String dateEnd) {
	List<Message> res = new ArrayList<Message>();
	for (Message m : getListMessage()) {
	    if ((compareDate(m.getDateMessage(), dateBegin) > 0)
		    && (compareDate(m.getDateMessage(), dateEnd) < 0))
		res.add(m);
	}

	return res;

    }

    @Override
    public List<Message> getListMessageByEntiteSocial(
	    EntiteSociale entiteSociale) {
	List<Message> res = new ArrayList<Message>();
	for (Message m : getListMessage()) {
	    if (m.getQui().equals(entiteSociale))
		res.add(m);
	}
	return res;
    }

    @Override
    public List<Message> getListMessageByHub(Hub hub) {
	List<Message> res = new ArrayList<Message>();
	for (Topic t : hub.getLesTopics()) {
	    res.addAll(getListMessageByTopic(t));
	}

	return res;
    }

    @Override
    public List<Message> getListMessageByTopic(Topic topic) {

	return topic.getLesMessages();
    }

    @Override
    public List<Topic> getListTopic() {
	List<Topic> res = new ArrayList<Topic>();
	for (Hub h : lHub) {
	    res.addAll(h.getLesTopics());
	}
	return res;
    }

    @Override
    public List<Topic> getListTopic(String dateBegin, String dateEnd) {
	List<Topic> res = new ArrayList<Topic>();
	for (Topic t : getListTopic()) {
	    if ((compareDate(t.getDateSujet(), dateBegin) > 0)
		    && (compareDate(t.getDateSujet(), dateEnd) < 0))
		res.add(t);
	}

	return res;

    }

    @Override
    public List<Topic> getListTopicByEntiteSociale(EntiteSociale entiteSocial) {
	List<Topic> res = new ArrayList<Topic>();
	for (Topic t : getListTopic()) {
	    if (t.getLesMessages().get(0).getQui().equals(entiteSocial))
		res.add(t);
	}
	return res;

    }

    @Override
    public List<Topic> getListTopicByHub(Hub hub) {

	return hub.getLesTopics();
    }

    @Override
    public boolean removeHub(Hub hub) {
	return lHub.remove(hub);
    }

    @Override
    public boolean removeMessage(Message message) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean removeTopic(Topic topic) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean updateHub(Hub hub) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean updateMessage(Message message) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean updateTopic(Topic topic) {
	// TODO Auto-generated method stub
	return false;
    }

    private int compareDate(String date1, String date2) {
	String[] d1 = date1.split("/");
	String[] d2 = date2.split("/");
	Integer d1j = Integer.valueOf(d1[0]);
	Integer d1m = Integer.valueOf(d1[1]);
	Integer d1a = Integer.valueOf(d1[2]);
	Integer d2j = Integer.valueOf(d2[0]);
	Integer d2m = Integer.valueOf(d2[1]);
	Integer d2a = Integer.valueOf(d2[2]);

	if (d1a.compareTo(d2a) == 0) {
	    if (d1m.compareTo(d2m) == 0) {
		return d1j.compareTo(d2j);
	    } else
		return d1m.compareTo(d2m);
	} else
	    return d1a.compareTo(d2a);

    }
}
