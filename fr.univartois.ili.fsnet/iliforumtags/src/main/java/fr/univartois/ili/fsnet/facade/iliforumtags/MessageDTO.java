package fr.univartois.ili.fsnet.facade.iliforumtags;

import fr.univartois.ili.fsnet.entities.Message;
import fr.univartois.ili.fsnet.facade.forum.iliforum.IliForumFacade;

public class MessageDTO {

	private Message message;

	private int nbMessUser;

	public Message getMessage() {
		return message;
	}

	public void setMessage(final Message message) {
		this.message = message;
	}

	public int getNbMessUser() {
		return nbMessUser;
	}

	public void setNbMessUser(final int nbMessUser) {
		this.nbMessUser = nbMessUser;
	}

	public MessageDTO(final Message message) {
		this.message = message;
		update();

	}

	private void update() {
		IliForumFacade iff;
		iff = IliForumFacade.getInstance();
		nbMessUser = iff.getListMessageByEntiteSocial(message.getPropMsg())
				.size();
	}
}
