package fr.univartois.ili.fsnet.trayDesktop.model;

import fr.univartois.ili.fsnet.trayDesktop.model.Options.LANG;
import fr.univartois.ili.fsnet.trayDesktop.views.FSNetTray;
import fr.univartois.ili.fsnet.webservice.Info;
import fr.univartois.ili.fsnet.webservice.InfoService;
import fr.univartois.ili.fsnet.webservice.WsPrivateMessage;
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

/**
 *
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class WSConnector {

    private final Set<WSListener> listeners = new HashSet<WSListener>();
    private Timer timer;
    private static Logger logger = Logger.getLogger(FSNetTray.class.getName());
    private StringBuilder build;
    private static final long MINUTES = 60000;
    private Info infoPort;

    public WSConnector() {
        if (verifConfig()) {
            startNotifications();
        } else {
            fireError("configuration failed");
        }
    }

    private void fireError(String message) {
        WSMessage mes = new WSMessage(message);
        for (WSListener listener : listeners) {
            listener.onError(mes);
        }
    }

    private void fireConnection(String message) {
        WSMessage mes = new WSMessage(message);
        for (WSListener listener : listeners) {
            listener.onConnection(mes);
        }
    }

    private void fireNewMessages(int nbMessages) {
        WSMessage mes = new WSMessage(String.valueOf(nbMessages));
        for (WSListener listener : listeners) {
            listener.onNewMessages(mes);
        }
    }

    public void addListener(WSListener listener) {
        if (listener == null) {
            return;
        }
        listeners.add(listener);
    }

    public void removeListener(WSListener listener) {
        listeners.remove(listener);
    }

    private boolean verifConfig() {
        if (!Options.isConfigured()) {
            return false;
        }
        try {
            InfoService infoService = new InfoService(
                    new URL(Options.getWSUrl()),
                    new QName("http://webservice.fsnet.ili.univartois.fr/", "InfoService"));
            infoPort = infoService.getInfoPort();
            return infoPort.isMember(Options.getLogin(), Options.getPassword());
        } catch (MalformedURLException ex) {
            Logger.getLogger(WSConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean changeConfig(String wSUrl, String fsnetUrl, String login, String password, LANG lang, int lag) {
        try {
            InfoService infoService = new InfoService(
                    new URL(wSUrl),
                    new QName("http://webservice.fsnet.ili.univartois.fr/", "InfoService"));
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
        } catch (MalformedURLException ex) {
            logger.log(Level.SEVERE, null, ex);
            fireError("NOCONNECTION");
        }

        return false;
    }

    /**
     * Start a timer which will display message regulary
     * @param timeLag number of seconds between two requests
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

                List<WsPrivateMessage> messages = infoPort.getNewMessages(Options.getLogin(), Options.getPassword());

                if (messages != null && messages.size() > 0) {
                    fireNewMessages(messages.size());
                }

            } catch (Exception e) {
                e.printStackTrace();
                fireError("No Connection");
            }
        }
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
