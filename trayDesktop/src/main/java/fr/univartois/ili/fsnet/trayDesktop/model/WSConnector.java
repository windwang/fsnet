package fr.univartois.ili.fsnet.trayDesktop.model;

import java.awt.Point;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.namespace.QName;

import fr.univartois.ili.fsnet.trayDesktop.model.Options.LANG;
import fr.univartois.ili.fsnet.trayDesktop.views.FSNetTray;
import fr.univartois.ili.fsnet.trayDesktop.views.NotificationFrame;
import fr.univartois.ili.fsnet.webservice.Info;
import fr.univartois.ili.fsnet.webservice.InfoService;
import fr.univartois.ili.fsnet.webservice.WsPrivateMessage;

/**
 * The model of the application. Communicate with the webservice and notify
 * listeners of new events
 * 
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class WSConnector {

	private final Set<WSListener> listeners = new HashSet<WSListener>();
	private Timer timer;
	private static Logger logger = Logger.getLogger(FSNetTray.class.getName());
	private static final long MINUTES = 60000;
	private Info infoPort;
	private NotificationFrame frame;

	public WSConnector() {
		if (verifConfig()) {
			startNotifications();
		} else {
			fireError("configuration failed");
		}
	}

	/**
	 * Notify listeneners and error occured.
	 * 
	 * @param message
	 */
	private void fireError(String message) {
		WSMessage mes = new WSMessage(message);
		for (WSListener listener : listeners) {
			listener.onError(mes);
		}
	}

	/**
	 * Notify listeners the connection is effective
	 * 
	 * @param message
	 */
	private void fireConnection(String message) {
		WSMessage mes = new WSMessage(message);
		for (WSListener listener : listeners) {
			listener.onConnection(mes);
		}
	}

	/**
	 * Notify listeners new messages have been received
	 * 
	 * @param nbMessages
	 */
	private void fireNewMessages(int nbMessages) {
		WSMessage mes = new WSMessage(String.valueOf(nbMessages));
		for (WSListener listener : listeners) {
			listener.onNewMessages(mes);
		}
	}

	private void fireNewEvent(int nbEvent) {
		WSMessage mes = new WSMessage("" + nbEvent);
		for (WSListener listener : listeners) {
			listener.onNewEvent(mes);
		}
	}

	private void fireNewContact(int nbContact) {
		WSMessage mes = new WSMessage("" + nbContact);
		for (WSListener listener : listeners) {
			listener.onNewContact(mes);
		}
	}

	private void fireNewAnnouncement(int nbAnnouncement) {
		WSMessage mes = new WSMessage("" + nbAnnouncement);
		for (WSListener listener : listeners) {
			listener.onNewAnnouncement(mes);
		}
	}

	private void fireNewConsultation(int nbConsultation) {
		WSMessage mes = new WSMessage("" + nbConsultation);
		for (WSListener listener : listeners) {
			listener.onNewConsultation(mes);
		}
	}

	private void fireNewNotification(int nbNotifications) {
		WSMessage mes = new WSMessage("" + nbNotifications);
		for (WSListener listener : listeners) {
			listener.onNewNotification(mes);
		}
	}

	/**
	 * Add a WSListener
	 * 
	 * @param listener
	 */
	public void addListener(WSListener listener) {
		if (listener == null) {
			return;
		}
		listeners.add(listener);
	}

	/**
	 * Remove a WSListener
	 * 
	 * @param listener
	 */
	public void removeListener(WSListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Check if the config is valid
	 * 
	 * @return
	 */
	private boolean verifConfig() {
		if (!Options.isConfigured()) {
			return false;
		}

		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<Boolean> future = executor.submit(new Callable<Boolean>() {

			@Override
			public Boolean call() {
				try {
					InfoService infoService = new InfoService(new URL(Options
							.getWSUrl()), new QName(
							"http://webservice.fsnet.ili.univartois.fr/",
							"InfoService"));
					infoPort = infoService.getInfoPort();
					return infoPort.isMember(Options.getLogin(),
							Options.getPassword());
				} catch (MalformedURLException ex) {
					Logger.getLogger(WSConnector.class.getName()).log(
							Level.SEVERE, null, ex);
				}
				return false;
			}
		});
		try {
			return future.get(5, TimeUnit.SECONDS);
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * Try to change the config and notify listeners with the result
	 * 
	 * @param wSUrl
	 * @param fsnetUrl
	 * @param login
	 * @param password
	 * @param lang
	 * @param lag
	 */
	public void changeConfig(final String wSUrl, final String fsnetUrl,
			final String login, final String password, final LANG lang,
			final int lag) {
		try {
			Thread r = new Thread() {

				@Override
				public void run() {
					try {
						InfoService infoService = new InfoService(
								new URL(wSUrl),
								new QName(
										"http://webservice.fsnet.ili.univartois.fr/",
										"InfoService"));
						infoPort = infoService.getInfoPort();
						if (infoPort.isMember(login, password)) {
							Options.setWSUrl(wSUrl);
							Options.setFsnetUrl(fsnetUrl);
							Options.setLogin(login);
							Options.setPassword(password);
							Options.setLanguage(lang);
							Options.setLag(lag);
							Options.saveOptions();
							startNotifications();
							fireConnection("VALIDCONFIGURATION");
						} else {
							fireError("BADLOGIN");
						}
					} catch (Exception ex) {
						logger.log(Level.SEVERE, null, ex);
						fireError("NOCONNECTION");
					}
				}
			};
			r.start();
			r.join(5000);
		} catch (InterruptedException ex) {
			Logger.getLogger(WSConnector.class.getName()).log(Level.SEVERE,
					null, ex);
			fireError("NOCONNECTION");
		}
	}

	/**
	 * Start a timer which will display message regulary
	 * 
	 * @param timeLag
	 *            number of seconds between two requests
	 */
	public void startNotifications() {
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				checkWS();
			}
		}, 0, MINUTES * Options.getLag());
	}

	/**
	 * Display a message with new alerts
	 */
	public void checkWS() {
		if (infoPort == null) {
			fireError("No Connection");
		} else {
			try {
				boolean newMessage, newContact, newAnnounce, newEvent, newConsultation;
				int i=0;
				newMessage = (getNbMessage() > 0);
				newContact = (getNbDemandeC() > 0);
				newAnnounce = (getNbAnnouncement() > 0);
				newEvent = (getNbEvent() > 0);
				newConsultation = (getNbConsultation() > 0);
				
				if(newMessage){i++;}
				if(newContact){i++;}
				if(newAnnounce){i++;}
				if(newEvent){i++;}
				if(newConsultation){i++;}

				fireNotification(i,newMessage,newContact,newAnnounce,newEvent,newConsultation);

			} catch (Exception e) {
				//e.printStackTrace();
				Logger.getLogger(WSConnector.class.getName()).log(Level.SEVERE,
						null, e);
				fireError("No Connection");
			}
		}
	}
	
	public void fireNotification(int i,boolean newMessage,boolean newContact,boolean newAnnounce,boolean newEvent,boolean newConsultation){
		if(i==1){
			if (newMessage) {
				fireNewMessages(getNbMessage());
			} else if (newContact) {
				fireNewContact(getNbDemandeC());
			} else if (newAnnounce) {
				fireNewAnnouncement(getNbAnnouncement());
			} else if (newEvent) {
				fireNewEvent(getNbEvent());
			} else if (newConsultation) {
				fireNewConsultation(getNbConsultation());
			}
		}else if(i>1){
			fireNewNotification(getNbMessage() + getNbDemandeC() + getNbAnnouncement() + getNbEvent() + getNbConsultation());
		}
	}

	public void checkWS(Point position) {
		if (infoPort == null) {
			fireError("No Connection");
		} else {
			try {
				if (getNbMessage() > 0 || getNbDemandeC() > 0
						|| getNbAnnouncement() > 0) {
					if (frame == null) {
						frame = new NotificationFrame(position);
						if (getNbMessage() > 0) {
							frame.addPanelMessage(getNbMessage());
						}
						if (getNbDemandeC() > 0) {
							frame.addPanelContact(getNbDemandeC());
						}
						if (getNbAnnouncement() > 0) {
							frame.addPanelAnnouncement(getNbAnnouncement());
						}
						if (getNbEvent() > 0) {
							frame.addPanelEvent(getNbEvent());
						}
						if (getNbConsultation() > 0) {
							frame.addPanelConsultation(getNbConsultation());
						}
					} else {
						frame.getFrame().dispose();
						frame = null;
					}
				}
			} catch (Exception e) {
				//e.printStackTrace();
				Logger.getLogger(WSConnector.class.getName()).log(Level.SEVERE,
						null, e);
				fireError("No Connection");
			}
		}

	}

	public int getNbMessage() {
		try {
			List<WsPrivateMessage> messages = infoPort.getNewMessages(
					Options.getLogin(), Options.getPassword());
			if (messages != null && messages.size() > 0) {
				return messages.size();
			}
		} catch (Exception e) {
			//e.printStackTrace();
			Logger.getLogger(WSConnector.class.getName()).log(Level.SEVERE,
					null, e);
		}
		return 0;
	}

	public int getNbDemandeC() {
		int nbC = 0;
		try {
			nbC = infoPort.getNewDemandeCount(Options.getLogin(),
					Options.getPassword());
		} catch (Exception e) {
			//e.printStackTrace();
			Logger.getLogger(WSConnector.class.getName()).log(Level.SEVERE,
					null, e);
		}
		return nbC;
	}

	public int getNbAnnouncement() {
		try {
			return infoPort.getNewAnnouncementCount(Options.getLogin(),
					Options.getPassword());
		} catch (Exception e) {
			//e.printStackTrace();
			Logger.getLogger(WSConnector.class.getName()).log(Level.SEVERE,
					null, e);
		}
		return 0;
	}

	public int getNbEvent() {
		try {
			return infoPort.getNewEventsCount(Options.getLogin(),
					Options.getPassword());
		} catch (Exception e) {
			//e.printStackTrace();
			Logger.getLogger(WSConnector.class.getName()).log(Level.SEVERE,
					null, e);
		}
		return 0;
	}

	private int getNbConsultation() {
		try {
			return infoPort.getNewConsultationCount(Options.getLogin(),
					Options.getPassword());
		} catch (Exception e) {
			//e.printStackTrace();
			Logger.getLogger(WSConnector.class.getName()).log(Level.SEVERE,
					null, e);
		}
		return 0;
	}

	/**
	 * Stop tray notifications
	 */
	public void stopNotifications() {
		if (timer != null) {
			timer.cancel();
		}
	}
}
