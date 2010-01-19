package fr.univartois.ili.fsnet.trayDesktop;

import fr.univartois.ili.fsnet.webservice.Info;
import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class FSnetTray has the responsibility to create a tray icon and
 * manage system notifications
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
// TODO when message from EntiteSociale to EntiteSociale will be implemented add personal messages here
public class FSNetTray {

    private final ResourceBundle trayi18n = TrayLauncher.getBundle();
    private TrayIcon tray;
    private final Info ins;
    private static Logger logger = Logger.getLogger(FSNetTray.class.getName());
    private Timer timer;
    private StringBuilder build;
    private static final long MINUTES = 60000;

    /**
     *
     * @param image the icon to display in system tray
     * @param infoService the webservice to invoke
     */
    public FSNetTray(Image image, Info info) {
        if (image == null || info == null) {
            throw new IllegalArgumentException();
        }
        tray = new TrayIcon(image);
        this.build = new StringBuilder();
        this.ins = info;
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
        tray.setToolTip(trayi18n.getString("NOTIFICATIONFSNET"));
        // Create a popup menu components
        final PopupMenu popup = new PopupMenu();

        MenuItem configItem = new MenuItem(trayi18n.getString("CONFIGURATION"));
        configItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new ConfigurationFrame().show();
            }
        });

        // TODO mettre ca dans la config
        CheckboxMenuItem size = new CheckboxMenuItem(trayi18n.getString("SETAUTOSIZE"));
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

        MenuItem exitItem = new MenuItem(trayi18n.getString("EXIT"));
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
        return ins.getNewAnnouncementCount(Options.getLogin(), Options.getPassword());

    }

    /**
     *
     * @return the number of new events
     * @throws RemoteException if the webservice is unreachable
     */
    private int getNumberOfNewEvents() throws RemoteException {
        return ins.getNewEventsCount(Options.getLogin(), Options.getPassword());
    }

    /**
     *
     * @return a formatted message
     */
    private String getMessage(int announces, int events) {
        build.delete(0, build.length());

        if (announces > 0) {
            build.append(trayi18n.getString("THEREIS")).append(" ").append(announces).
                    append(" ").append(trayi18n.getString("NEWANNOUNCES"));
        }
        if (events > 0) {
            build.append(trayi18n.getString("THEREIS")).append(" ").append(events).
                    append(" ").append(trayi18n.getString("NEWEVENTS"));
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

                try {
                    int announces = getNumberOfNewAnnonce();
                    int events = getNumberOfNewEvents();
                    if (announces + events > 0) {
                        String message = getMessage(announces, events);
                        tray.displayMessage(
                                trayi18n.getString("NOTIFICATIONS"),
                                message,
                                TrayIcon.MessageType.INFO);
                    }
                } catch (RemoteException e1) {
                    logger.log(Level.SEVERE, e1.getLocalizedMessage());
                    build.append(trayi18n.getString("NOCONNECTION "));
                    stopNotifications();
                }

            }
        }, 0, timeLag * MINUTES);
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
