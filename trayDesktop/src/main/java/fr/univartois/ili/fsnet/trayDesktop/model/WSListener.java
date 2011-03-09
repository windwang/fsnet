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

	// void onNewEvent(WsPrivateMessage message);
	// void onNewEvent(WsPrivateMessage message);

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
