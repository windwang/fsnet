package fr.univartois.ili.fsnet.trayDesktop.views;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

import fr.univartois.ili.fsnet.trayDesktop.model.Options;
import javax.swing.BoxLayout;

public class NotificationFrame {

    private JFrame frame;
    private Point point;
    private Point position;

    public NotificationFrame(Point position) {
        frame = new JFrame("Fsnet");
        this.position = position;
        init(position);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void addPanelMessage(int nbMessage) {
        System.out.println("new panel with " + nbMessage + " messages");
        NotificationPanel panel = new NotificationPanel(nbMessage,
                "nouveaux messages");
        this.frame.add(panel.getPanel());
        this.frame.pack();
        initPosition(position);
        this.frame.setLocation(point.x, point.y);
        panel.getLabel().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(Options.getFsnetUrl()+"/Inbox.do"));

                } catch (URISyntaxException ex) {
                    Logger.getLogger(FSNetTray.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(FSNetTray.class.getName()).log(Level.SEVERE, null, ex);
                }
                frame.dispose();
            }
        });        
    }

    public void addPanelContact(int nbContact) {
        NotificationPanel panel = new NotificationPanel(nbContact,
                "nouvelles demandes de contacts");
        this.frame.add(panel.getPanel());
        this.frame.pack();
        initPosition(position);
        this.frame.setLocation(point.x, point.y);

    }

    public void init(Point position) {
        this.frame.setUndecorated(true);
        BoxLayout flow = new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS);
        this.frame.setVisible(true);
        this.frame.setLayout(flow);
        this.frame.pack();
        initPosition(position);
        this.frame.setLocation(point.x, point.y);
    }

    public void initPosition(Point position) {
        point = new Point();
        Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int screenheight = (int) tailleEcran.getHeight();
        int screenwidht = (int) tailleEcran.getWidth();
        int framewidht = this.frame.getWidth();
        int frameheight = this.frame.getHeight();
        point.x = position.x;
        point.y = position.y;
        if ((framewidht + position.x) > screenwidht) {
            point.x = position.x - (framewidht + position.x - screenwidht);
        }
        if ((frameheight + position.y) > screenheight) {
            point.y = position.y - (frameheight + position.y - screenheight);
        }
    }
}
