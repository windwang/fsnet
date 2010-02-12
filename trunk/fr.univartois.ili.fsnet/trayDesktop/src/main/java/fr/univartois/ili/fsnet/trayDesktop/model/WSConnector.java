/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.univartois.ili.fsnet.trayDesktop.model;

import com.sun.xml.internal.ws.client.ClientTransportException;
import fr.univartois.ili.fsnet.trayDesktop.views.FSNetTray;
import fr.univartois.ili.fsnet.webservice.Info;
import fr.univartois.ili.fsnet.webservice.InfoService;
import fr.univartois.ili.fsnet.webservice.WsPrivateMessage;
import java.awt.TrayIcon;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
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

        if (verifyConfig()) {
            fireConnection("configuration success");
            startNotifications(Options.getLag());
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

    public boolean verifyConfig() {
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

    /**
     * Start a timer which will display message regulary
     * @param timeLag number of seconds between two requests
     */
    public void startNotifications(long timeLag) {
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
    private void checkWS() {
        try {
            List<WsPrivateMessage> messages = infoPort.getNewMessages(Options.getLogin(), Options.getPassword());

            if (messages != null && messages.size() > 0) {
                fireNewMessages(messages.size());
            }

        } catch (Exception e) {
            fireError("No Connection");
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
