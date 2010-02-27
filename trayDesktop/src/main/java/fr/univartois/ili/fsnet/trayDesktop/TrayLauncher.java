package fr.univartois.ili.fsnet.trayDesktop;

import java.awt.AWTException;
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

import fr.univartois.ili.fsnet.trayDesktop.controls.WSControl;
import fr.univartois.ili.fsnet.trayDesktop.model.Options;
import fr.univartois.ili.fsnet.trayDesktop.model.WSConnector;
import fr.univartois.ili.fsnet.trayDesktop.views.ConfigurationFrame;
import fr.univartois.ili.fsnet.trayDesktop.views.FSNetTray;

/**
 * The class TrayLauncher creates an instance of the web service and an instance
 * of FSNetTray and add the tray to the system tray
 * 
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class TrayLauncher {

    private static ResourceBundle trayi18n;
    private static Logger logger = Logger.getLogger(TrayLauncher.class.getName());
    private static FSNetTray tray;
    private static WSConnector connector;
    private static WSControl control;
    private static ConfigurationFrame confFrame;

    /**
     * Open a configuration frame
     */
    public static void showConfigFrame() {
        confFrame = new ConfigurationFrame(control);
        connector.addListener(confFrame);
        confFrame.show();
    }

    /**
     * Called when the configuration is done
     */
    public static final void configurationValidated() {
        connector.removeListener(confFrame);
        confFrame = null;
        reload();
    }

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
            Options.loadOptions();
            connector = new WSConnector();
            control = new WSControl(connector);
            reload();
        }
    }

    /**
     * Load or reaload the tray
     */
    public static final void reload() {
        if (tray != null) {
            connector.removeListener(tray);            
            SystemTray.getSystemTray().remove(tray.getTrayIcon());
        }

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {


                trayi18n = ResourceBundle.getBundle("ressources/Trayi18n", Options.getLocale());
                if (!Options.isConfigured()) {
                    showConfigFrame();
                } else {
                    ImageIcon icon = getImageIcon("/ressources/iconefsnet.png");
                    if (icon != null) {
                        try {
                            tray = new FSNetTray(icon.getImage(), control);
                            connector.addListener(tray);
                            SystemTray.getSystemTray().add(tray.getTrayIcon());
                        } catch (AWTException ex) {
                            logger.log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
    }

    /**
     *
     * @return the i18n bundle
     */
    public static final ResourceBundle getBundle() {
        return trayi18n;
    }

    /**
     *
     * @param path
     * @return the image to display in the tray
     */
    public static ImageIcon getImageIcon(String path) {
        URL imageURL = FSNetTray.class.getResource(path);
        if (imageURL == null) {
            logger.log(Level.SEVERE, "Resource not found: " + path);
            return null;
        } else {
            return new ImageIcon(imageURL);
        }
    }
}
