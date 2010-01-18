package fr.univartois.ili.fsnet.trayDesktop;

import fr.univartois.ili.fsnet.webservice.NouvellesInformations;
import fr.univartois.ili.fsnet.webservice.NouvellesServiceLocator;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.xml.rpc.ServiceException;

/**
 * The class TrayLauncher creates an instance of the web service and an instance
 * of FSNetTray and add the tray to the system tray
 * 
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class TrayLauncher {

    private static ResourceBundle trayi18n;
    private static Logger logger = Logger.getLogger(TrayLauncher.class.getName());
    private static NouvellesInformations nvs;
    private static FSNetTray c;
    private static final long MINUTES_LAG = 2;

    private TrayLauncher() {
    }

    public static void main(String[] args) throws IOException, ServiceException {
        if (!SystemTray.isSupported()) {
            JOptionPane.showMessageDialog(null, "SystemTray is not supported");
        } else {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                logger.log(Level.INFO, "Unable to change L&F");
            }
            Options.loadOptions();
            if (!Options.isConfigured()) {
                new ConfigurationFrame().show();
            }
            NouvellesServiceLocator nv = new NouvellesServiceLocator();
            nvs = nv.getNouvellesInformationsPort();
            reload();
        }
    }

    /**
     *
     * @param path
     * @return the image to display in the tray
     */
    private static Image getTrayImage(String path) {
        URL imageURL = FSNetTray.class.getResource(path);
        if (imageURL == null) {
            logger.log(Level.SEVERE, "Resource not found: " + path);
            return null;
        } else {
            return new ImageIcon(imageURL).getImage();
        }
    }

    /**
     * Load or reaload the tray
     */
    public static final void reload() {
        if (c != null) {
            c.stopNotifications();
            SystemTray.getSystemTray().remove(c.getTrayIcon());
        }
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                Image icon = getTrayImage("/ressources/iconefsnet.png");
                if (icon != null) {
                    try {
                        trayi18n = ResourceBundle.getBundle("ressources/Trayi18n", Options.getLocale());
                        c = new FSNetTray(icon, nvs);
                        SystemTray.getSystemTray().add(c.getTrayIcon());
                        c.startNotifications(MINUTES_LAG);

                    } catch (AWTException ex) {
                        logger.log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    public static final ResourceBundle getBundle() {
        return trayi18n;
    }
}
