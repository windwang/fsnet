package fr.univartois.ili.fsnet.trayDesktop;

import fr.univartois.ili.fsnet.webservice.NouvellesInformations;
import fr.univartois.ili.fsnet.webservice.NouvellesService;
import fr.univartois.ili.fsnet.webservice.NouvellesServiceLocator;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.xml.rpc.ServiceException;

/**
 * The class TrayLauncher creates an instance of the web service and an instance
 * of FSNetTray and add the tray to the system tray
 * 
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class TrayLauncher {

    private static Logger logger = Logger.getLogger(TrayLauncher.class.getName());

    public static void main(String[] args) throws UnknownHostException, IOException, ServiceException {
        if (!SystemTray.isSupported()) {
            JOptionPane.showMessageDialog(null, "SystemTray is not supported");
        } else {
            NouvellesService nv = new NouvellesServiceLocator();
            NouvellesInformations nvs = nv.getNouvellesInformationsPort();
            Image icon = getTrayImage("/ressources/iconefsnet.png");
            if (icon != null) {
                try {
                    FSNetTray c = new FSNetTray(icon, nvs);
                    SystemTray.getSystemTray().add(c.getTrayIcon());
                    c.startNotifications(10);
                } catch (AWTException ex) {
                    logger.log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     *
     * @param path
     * @return the image to display in the tray
     */
    private static final Image getTrayImage(String path) {
        URL imageURL = FSNetTray.class.getResource(path);
        if (imageURL == null) {
            logger.log(Level.SEVERE, "Resource not found: " + path);
            return null;
        } else {
            return new ImageIcon(imageURL).getImage();
        }
    }
}
