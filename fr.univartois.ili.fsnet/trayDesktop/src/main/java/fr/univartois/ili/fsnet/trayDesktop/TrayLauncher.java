package fr.univartois.ili.fsnet.trayDesktop;

import fr.univartois.ili.fsnet.webservice.InfoService;
import java.awt.AWTException;
import java.awt.SystemTray;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.xml.namespace.QName;

/**
 * The class TrayLauncher creates an instance of the web service and an instance
 * of FSNetTray and add the tray to the system tray
 * 
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class TrayLauncher {

    private static ResourceBundle trayi18n;
    private static Logger logger = Logger.getLogger(TrayLauncher.class.getName());
    private static FSNetTray c;

    private TrayLauncher() {
    }

    public static void main(String[] args) throws IOException {
        if (!SystemTray.isSupported()) {
            JOptionPane.showMessageDialog(null, "SystemTray is not supported");
        } else {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                logger.log(Level.INFO, "Unable to change L&F");
            }
            reload();
        }
    }

    /**
     *
     * @param path
     * @return the image to display in the tray
     */
    static ImageIcon getImageIcon(String path) {
        URL imageURL = FSNetTray.class.getResource(path);
        if (imageURL == null) {
            logger.log(Level.SEVERE, "Resource not found: " + path);
            return null;
        } else {
            return new ImageIcon(imageURL);
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
                Options.loadOptions();

                trayi18n = ResourceBundle.getBundle("ressources/Trayi18n", Options.getLocale());
                ImageIcon icon = getImageIcon("/ressources/iconefsnet.png");
                if (icon != null) {
                    try {
                        InfoService infoService;
                        try {
                            infoService = new InfoService(new URL(Options.getUrl()), new QName("http://webservice.fsnet.ili.univartois.fr/", "InfoService"));
                            c = new FSNetTray(icon.getImage(), infoService.getInfoPort());
                            SystemTray.getSystemTray().add(c.getTrayIcon());
                            c.startNotifications(Options.getLag());
                        } catch (MalformedURLException ex) {
                            new ConfigurationFrame().show();
                        }
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
