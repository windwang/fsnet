package fr.univartois.ili.fsnet.trayDesktop.model;

/**
 * All classes implementing this interface will be notified of changes in
 * application state
 * 
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public interface WSListener {

	/**
	 * Called when new PrivateMessages have been received
	 * 
	 * @param message
	 */
	void onNewMessages(WSMessage message);

	/**
	 * Called when a new Event is created
	 * 
	 * @param message
	 */
	void onNewEvent(WSMessage message);

	/**
	 * Called when a new Contact add you
	 * 
	 * @param message
	 */
	void onNewContact(WSMessage mes);

	/**
	 * Called when a new Annoucement is created
	 * 
	 * @param message
	 */
	void onNewAnnouncement(WSMessage mes);

	/**
	 * Called when you receive more than one kind of notification (new message,
	 * new contact, new event , new announce)
	 * 
	 * @param message
	 */
	void onNewNotification(WSMessage mes);

	/**
	 * Called when a new Consultation is created
	 * 
	 * @param message
	 */
	void onNewConsultation(WSMessage mes);

	/**
	 * Called when the system encountred an error
	 * 
	 * @param message
	 */
	void onError(WSMessage message);

	/**
	 * Called when the connection to the webservice is ready
	 * 
	 * @param message
	 */
	void onConnection(WSMessage message);

}
