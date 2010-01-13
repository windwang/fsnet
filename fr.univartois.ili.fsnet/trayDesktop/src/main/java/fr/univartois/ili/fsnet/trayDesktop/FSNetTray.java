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
public class FSNetTray {

    public static final String urlTemp = "http://code.google.com/p/fsnet/";
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

    private void initTrayIcon() {
        tray.setToolTip("Notification FSNet");
        // Create a popup menu components
        final PopupMenu popup = new PopupMenu();
        CheckboxMenuItem size = new CheckboxMenuItem("Set auto size");
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

        MenuItem exitItem = new MenuItem("   Exit   ");
        exitItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Add components to popup menu
        popup.add(size);
        popup.add(exitItem);
        tray.setPopupMenu(popup);
    }

    private int getNumberOfNewAnnonce() {
        try {
            return nvs.getNumberOfNewAnnonce();
        } catch (RemoteException e1) {
            logger.log(Level.SEVERE, e1.getLocalizedMessage());
            stopNotifications();
        }
        return 0;
    }

    private int getNumberOfNewEvents() {
        try {
            return nvs.getNumberOfNewEvents();
        } catch (RemoteException e1) {
            logger.log(Level.SEVERE, e1.getLocalizedMessage());
            stopNotifications();
        }
        return 0;
    }

    private String getMessage() {
        build.delete(0, build.length());
        int announces = getNumberOfNewAnnonce();
        int events = getNumberOfNewEvents();
        if (announces > 0) {
            build.append("Il y a ").append(getNumberOfNewAnnonce()).append(" nouvelles annonces\n");
        }
        if (events > 0) {
            build.append("Il y a ").append(getNumberOfNewEvents()).append(" nouveaux événements\n");
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
                tray.displayMessage("Notifications",
                        getMessage(),
                        TrayIcon.MessageType.INFO);
            }
        }, 0, timeLag * 1000);
    }

    /**
     * Stop notifications
     */
    public void stopNotifications() {
        if (timer != null) {
            timer.cancel();
        }
    }
}
