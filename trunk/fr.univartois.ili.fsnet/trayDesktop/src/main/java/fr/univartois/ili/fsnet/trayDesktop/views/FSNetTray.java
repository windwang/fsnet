package fr.univartois.ili.fsnet.trayDesktop.views;

import fr.univartois.ili.fsnet.trayDesktop.model.Options;
import fr.univartois.ili.fsnet.trayDesktop.TrayLauncher;
import fr.univartois.ili.fsnet.trayDesktop.controls.WSControl;
import fr.univartois.ili.fsnet.trayDesktop.model.WSListener;
import fr.univartois.ili.fsnet.trayDesktop.model.WSMessage;

import java.awt.CheckboxMenuItem;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class FSnetTray has the responsibility to create a tray icon and
 * display system notifications
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class FSNetTray implements WSListener {

    private final ResourceBundle trayi18n = TrayLauncher.getBundle();
    private TrayIcon tray;
    private final WSControl control;

    /**
     *
     * @param image the icon to display in system tray
     * @param infoService the webservice to invoke
     */
    public FSNetTray(Image image, WSControl control) {
        if (image == null || control == null) {
            throw new IllegalArgumentException();
        }
        tray = new TrayIcon(image);
        initTrayIcon();
        this.control = control;
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
                TrayLauncher.showConfigFrame();
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
        tray.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(Options.getFsnetUrl()));
                } catch (URISyntaxException ex) {
                    Logger.getLogger(FSNetTray.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(FSNetTray.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        tray.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                //control.checkWS();
                Point position =  e.getLocationOnScreen();    
                control.check(position);
            }
        });
        // Add components to popup menu
        popup.add(configItem);
        popup.add(size);
        popup.add(exitItem);
        tray.setPopupMenu(popup);
    }

    @Override
    public void onNewMessages(WSMessage message) {
        tray.displayMessage(
                trayi18n.getString("NOTIFICATIONS"),
                message.getMessage() + " " + trayi18n.getString("NEWMESSAGES"),
                TrayIcon.MessageType.NONE);
    }

    @Override
    public void onError(WSMessage message) {
        tray.displayMessage(
                trayi18n.getString("NOCONNECTION"),
                trayi18n.getString("NOCONNECTION"),
                TrayIcon.MessageType.ERROR);
    }

    @Override
    public void onConnection(WSMessage message) {
    }
}
