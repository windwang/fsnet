package fr.univartois.ili.fsnet.trayDesktop.model;

import java.awt.Point;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.namespace.QName;

import fr.univartois.ili.fsnet.trayDesktop.model.Options.LANG;
import fr.univartois.ili.fsnet.trayDesktop.views.FSNetTray;
import fr.univartois.ili.fsnet.trayDesktop.views.NotificationFrame;
import fr.univartois.ili.fsnet.webservice.Info;
import fr.univartois.ili.fsnet.webservice.InfoService;
import fr.univartois.ili.fsnet.webservice.WsPrivateMessage;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

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
                    InfoService infoService = new InfoService(new URL(Options.getWSUrl()),
                            new QName("http://webservice.fsnet.ili.univartois.fr/",
                            "InfoService"));
                    infoPort = infoService.getInfoPort();
                    return infoPort.isMember(Options.getLogin(), Options.getPassword());
                } catch (MalformedURLException ex) {
                    Logger.getLogger(WSConnector.class.getName()).log(Level.SEVERE,
                            null, ex);
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
    public void changeConfig(final String wSUrl, final String fsnetUrl, final String login,
            final String password, final LANG lang, final int lag) {
        try {
            Thread r = new Thread() {

                @Override
                public void run() {
                    try {
                        InfoService infoService = new InfoService(new URL(wSUrl), new QName("http://webservice.fsnet.ili.univartois.fr/", "InfoService"));
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
            Logger.getLogger(WSConnector.class.getName()).log(Level.SEVERE, null, ex);
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
                List<WsPrivateMessage> messages = infoPort.getNewMessages(
                        Options.getLogin(), Options.getPassword());

                if (messages != null && messages.size() > 0) {
                    fireNewMessages(messages.size());
                }
            } catch (Exception e) {
                e.printStackTrace();
                fireError("No Connection");
            }
        }
    }

    public void checkWS(Point position) {
        if (frame == null) {
            frame = new NotificationFrame(position);
            if (getNbMessage() > 0) {
                frame.addPanelMessage(getNbMessage());
            }
            if (getNbDemandeC() > 0) {
                frame.addPanelContact(getNbDemandeC());
            }
        } else {
            frame.getFrame().dispose();
            frame = null;
        }

    }

    public int getNbMessage() {
        try {
            List<WsPrivateMessage> messages = infoPort.getNewMessages(Options.getLogin(), Options.getPassword());
            if (messages != null && messages.size() > 0) {
                return messages.size();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getNbDemandeC() {
        int nbC = 0;
        try {
            // nbC = infoPort.getNewDemandeCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nbC;
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
