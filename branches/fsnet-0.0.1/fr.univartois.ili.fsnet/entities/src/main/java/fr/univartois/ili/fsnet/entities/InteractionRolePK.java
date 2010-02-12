package fr.univartois.ili.fsnet.entities;

public class InteractionRolePK {

	private int socialEntity;

	private int interaction;

	/**
	 * @return the socialEntityId
	 */
	public int getSocialEntityId() {
		return socialEntity;
	}

	/**
	 * @param socialEntityId
	 *            the socialEntityId to set
	 */
	public void setSocialEntityId(int socialEntityId) {
		this.socialEntity = socialEntityId;
	}

	/**
	 * @return the interactionId
	 */
	public int getInteractionId() {
		return interaction;
	}

	/**
	 * @param interactionId
	 *            the interactionId to set
	 */
	public void setInteractionId(int interactionId) {
		this.interaction = interactionId;
	}

}
