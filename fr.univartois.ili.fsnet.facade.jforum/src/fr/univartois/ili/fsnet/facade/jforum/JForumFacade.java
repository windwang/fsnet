package fr.univartois.ili.fsnet.facade.jforum;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		Date date1 = new Date();
		Date date2 = new Date();
		try {
			date1 = (Date) formatter.parse("29/01/09");
			date2 = (Date) formatter.parse("04/02/09");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		lHub = new ArrayList<Hub>();
		Hub h1 = new Hub("Hub test1", date1, new ArrayList<Topic>());
		h1.setCreateur(ent1);
		Hub h2 = new Hub("Hub test2", date2, new ArrayList<Topic>());
		h2.setCreateur(ent2);

		Topic t1 = new Topic("Test topic1", date1, new ArrayList<Message>(),
				h1, ent1);

		Topic t2 = new Topic("Test topic2", date2, new ArrayList<Message>(),
				h1, ent2);

		Topic t3 = new Topic("Test topic3", date2, new ArrayList<Message>(),
				h2, ent2);
		Message m1 = new Message(
				"Message1 Message1Message1 \n Message1 Message1 ", date1, ent2,
				t1);
		Message m2 = new Message("Message2 Message2 Message2 Message2 ", date2,
				ent1, t1);
		Message m3 = new Message("Message3 Message3 \n Message3 \n Message ",
				date1, ent2, t2);
		Message m4 = new Message(
				"Message4 Message4Message4 \n Message4 Message4 ", date2, ent1,
				t3);
		Message m5 = new Message("Message5 ", date1, ent2, t3);

		addHub(h1);
		addHub(h2);
		addTopic(t1, h1);
		addTopic(t2, h1);
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

	
	public boolean addMessage(Message message, Topic topic) {
		topic.getLesMessages().add(message);
		return true;
	}

	
	public boolean addTopic(Topic topic, Hub hub) {
		hub.getLesTopics().add(topic);
		return true;
	}

	@Override
	public List<Hub> getListHub() {
		return lHub;
	}

	@Override
	public List<Hub> getListHub(Date dateBegin, Date dateEnd) {
		List<Hub> res = new ArrayList<Hub>();
		for (Hub h : lHub) {
			if ((dateBegin.compareTo(h.getDateCreation()) <= 0)
					&& (dateEnd.compareTo(h.getDateCreation()) >= 0))
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
	public List<Message> getListMessage(Date dateBegin, Date dateEnd) {
		List<Message> res = new ArrayList<Message>();
		for (Message m : getListMessage()) {
			if ((dateBegin.compareTo(m.getDateMessage()) <= 0)
					&& (dateEnd.compareTo(m.getDateMessage()) >= 0))
				res.add(m);
		}

		return res;

	}

	@Override
	public List<Message> getListMessageByEntiteSocial(
			EntiteSociale entiteSociale) {
		List<Message> res = new ArrayList<Message>();
		for (Message m : getListMessage()) {
			if (m.getPropMsg().equals(entiteSociale))
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
	public List<Topic> getListTopic(Date dateBegin, Date dateEnd) {
		List<Topic> res = new ArrayList<Topic>();
		for (Topic t : getListTopic()) {
			if ((dateBegin.compareTo(t.getDateSujet()) <= 0)
					&& (dateEnd.compareTo(t.getDateSujet()) >= 0))
				res.add(t);
		}

		return res;

	}

	@Override
	public List<Topic> getListTopicByEntiteSociale(EntiteSociale entiteSocial) {
		List<Topic> res = new ArrayList<Topic>();
		for (Topic t : getListTopic()) {
			if (t.getLesMessages().get(0).getPropMsg().equals(entiteSocial))
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

	public boolean updateHub(Hub hub) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean updateMessage(Message message) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean updateTopic(Topic topic) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addMessage(Message message) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addTopic(Topic topic) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateHub(Hub hub, Hub nouvo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateMessage(Message message, Message nouvo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateTopic(Topic topic, Topic nouvo) {
		// TODO Auto-generated method stub
		return false;
	}

}
