package fr.univartois.ili.fsnet.trayDesktop;

import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;
import fr.univartois.ili.fsnet.webservice.NouvellesInformations;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * The class FSnetTray has the responsibility to create a tray icon and
 * manage system notifications
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
// TODO when message from EntiteSociale to EntiteSociale will be implemented add personal messages here
public class FSNetTray {

    private static final String urlTemp = "http://code.google.com/p/fsnet/";
    private TrayIcon tray;
    private final NouvellesInformations nvs;
    private static Logger logger = Logger.getLogger(FSNetTray.class.getName());
    private Timer timer;
    private StringBuilder build;

    /**
     *
     * @param image the icon to display in system tray
     * @param nvs the webservice to invoke
     */
    public FSNetTray(Image image, NouvellesInformations nvs) {
        if (image == null || nvs == null) {
            throw new IllegalArgumentException();
        }
        tray = new TrayIcon(image);
        this.build = new StringBuilder();
        this.nvs = nvs;
        initTrayIcon();
    }

    /**
     *
     * @return the builded tray icon
     */
    public TrayIcon getTrayIcon() {
        return tray;
    }

    /**
     * Initialize tray icon's parameters
     */
    private void initTrayIcon() {
        tray.setToolTip(TrayLauncher.trayi18n.getString("NOTIFICATIONFSNET"));
        // Create a popup menu components
        final PopupMenu popup = new PopupMenu();

        MenuItem configItem = new MenuItem(TrayLauncher.trayi18n.getString("CONFIGURATION"));
        configItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new ConfigurationFrame().show();
            }
        });

        // TODO mettre ca dans la config
        CheckboxMenuItem size = new CheckboxMenuItem(TrayLauncher.trayi18n.getString("SETAUTOSIZE"));
        size.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                int cb1Id = e.getStateChange();
                if (cb1Id == ItemEvent.SELECTED) {
                    tray.setImageAutoSize(true);
                } else {
                    tray.setImageAutoSize(false);
                }
            }
        });

        MenuItem exitItem = new MenuItem(TrayLauncher.trayi18n.getString("EXIT"));
        exitItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Add components to popup menu
        popup.add(configItem);
        popup.add(size);
        popup.add(exitItem);
        tray.setPopupMenu(popup);
    }

    /**
     *
     * @return the number of new announces
     * @throws RemoteException if the webservice is unreachable
     */
    private int getNumberOfNewAnnonce() throws RemoteException {
        return nvs.getNumberOfNewAnnonce();
    }

    /**
     *
     * @return the number of new events
     * @throws RemoteException if the webservice is unreachable
     */
    private int getNumberOfNewEvents() throws RemoteException {
        return nvs.getNumberOfNewEvents();
    }

    /**
     *
     * @return a formatted message
     */
    private String getMessage() {
        build.delete(0, build.length());
        try {
            int announces = getNumberOfNewAnnonce();
            int events = getNumberOfNewEvents();

            if (announces > 0) {
                build.append(TrayLauncher.trayi18n.getString("THEREIS")).append(" ").append(announces).
                        append(" ").append(TrayLauncher.trayi18n.getString("NEWANNOUNCES"));
            }
            if (events > 0) {
                build.append("THEREIS").append(" ").append(events).
                        append(" ").append(TrayLauncher.trayi18n.getString("NEWEVENTS"));
            }
        } catch (RemoteException e1) {
            logger.log(Level.SEVERE, e1.getLocalizedMessage());
            build.append(TrayLauncher.trayi18n.getString("NOCONNECTION "));
            stopNotifications();
        }
        return build.toString();
    }

    /**
     * Start a timer which will check new events
     * @param timeLag number of seconds between two requests
     */
    public void startNotifications(long timeLag) {
        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                tray.displayMessage(TrayLauncher.trayi18n.getString("NOTIFICATIONS"),
                        getMessage(),
                        TrayIcon.MessageType.INFO);
            }
        }, 0, timeLag * 1000);
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
