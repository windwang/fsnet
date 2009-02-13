package fr.univartois.ili.fsnet.facade.iliforum;

import java.util.Date;
import java.util.List;

import fr.univartois.ili.fsnet.entities.EntiteSociale;
import fr.univartois.ili.fsnet.entities.Hub;
import fr.univartois.ili.fsnet.entities.Message;
import fr.univartois.ili.fsnet.entities.Topic;
import fr.univartois.ili.fsnet.facade.ForumFacade;

public class IliForumFacade implements ForumFacade {

	@Override
	public boolean addHub(Hub hub) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addMessage(Message message, Topic topic) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addTopic(Topic topic, Hub hub) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Hub> getListHub() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Hub> getListHub(Date dateBegin, Date dateEnd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Hub> getListHubByEntiteSociale(EntiteSociale decideur) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> getListMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> getListMessage(Date dateBegin, Date dateEnd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> getListMessageByEntiteSocial(
			EntiteSociale entiteSociale) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> getListMessageByHub(Hub hub) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> getListMessageByTopic(Topic topic) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Topic> getListTopic() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Topic> getListTopic(Date dateBegin, Date dateEnd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Topic> getListTopicByEntiteSociale(EntiteSociale entiteSocial) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Topic> getListTopicByHub(Hub hub) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeHub(Hub hub) {
		// TODO Auto-generated method stub
		return false;
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

}
