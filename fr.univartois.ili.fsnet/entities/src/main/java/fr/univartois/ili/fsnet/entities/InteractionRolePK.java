package fr.univartois.ili.fsnet.entities;

public class InteractionRolePK {

	private Long socialEntityId;

	private Long interactionId;

	/**
	 * @return the socialEntityId
	 */
	public Long getSocialEntityId() {
		return socialEntityId;
	}

	/**
	 * @param socialEntityId
	 *            the socialEntityId to set
	 */
	public void setSocialEntityId(Long socialEntityId) {
		this.socialEntityId = socialEntityId;
	}

	/**
	 * @return the interactionId
	 */
	public Long getInteractionId() {
		return interactionId;
	}

	/**
	 * @param interactionId
	 *            the interactionId to set
	 */
	public void setInteractionId(Long interactionId) {
		this.interactionId = interactionId;
	}

}
