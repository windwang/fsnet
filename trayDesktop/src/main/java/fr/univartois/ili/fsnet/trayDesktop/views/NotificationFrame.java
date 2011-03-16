package fr.univartois.ili.fsnet.trayDesktop.views;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import fr.univartois.ili.fsnet.trayDesktop.TrayLauncher;
import fr.univartois.ili.fsnet.trayDesktop.model.Options;

public class NotificationFrame {

	private JFrame frame;
	private Point point;
	private Point position;
	private final ResourceBundle trayi18n = TrayLauncher.getBundle();

	public NotificationFrame(Point position) {
		frame = new JFrame("Fsnet");
		this.position = position;
		init(position);
	}

	public JFrame getFrame() {
		return frame;
	}

	public void addPanelMessage(int nbMessage) {
		final NotificationPanel panel = new NotificationPanel(nbMessage,
				trayi18n.getString("NEWMESSAGES"));
		this.frame.add(panel.getPanel());
		this.frame.pack();
		Dimension dim = this.frame.getSize();
		Dimension test = new Dimension(dim.width + 10, dim.height);
		this.frame.setMinimumSize(test);
		initPosition(position);
		this.frame.setLocation(point.x, point.y);
		final Color normalColor = panel.getPanel().getBackground();
		panel.getLabel().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(
							new URI(Options.getFsnetUrl() + "/Inbox.do"));

				} catch (URISyntaxException ex) {
					Logger.getLogger(FSNetTray.class.getName()).log(
							Level.SEVERE, null, ex);
				} catch (IOException ex) {
					Logger.getLogger(FSNetTray.class.getName()).log(
							Level.SEVERE, null, ex);
				}
				frame.dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				panel.getPanel().setBackground(new Color(0xc6c2bf));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				panel.getPanel().setBackground(normalColor);
			}

		});
	}

	public void addPanelContact(int nbContact) {
		final NotificationPanel panel = new NotificationPanel(nbContact,
				trayi18n.getString("NEWCONTACT"));
		this.frame.add(panel.getPanel());
		this.frame.pack();
		Dimension dim = this.frame.getSize();
		Dimension test = new Dimension(dim.width + 10, dim.height);
		this.frame.setMinimumSize(test);
		initPosition(position);
		this.frame.setLocation(point.x, point.y);
		final Color normalColor = panel.getPanel().getBackground();
		panel.getLabel().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(
							new URI(Options.getFsnetUrl() + "/Contacts.do"));

				} catch (URISyntaxException ex) {
					Logger.getLogger(FSNetTray.class.getName()).log(
							Level.SEVERE, null, ex);
				} catch (IOException ex) {
					Logger.getLogger(FSNetTray.class.getName()).log(
							Level.SEVERE, null, ex);
				}
				frame.dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				panel.getPanel().setBackground(new Color(0xc6c2bf));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				panel.getPanel().setBackground(normalColor);
			}
		});
	}

	public void addPanelAnnouncement(int nbAnnouncement) {
		final NotificationPanel panel = new NotificationPanel(nbAnnouncement,
				trayi18n.getString("NEWANNOUNCEMENT"));
		this.frame.add(panel.getPanel());
		this.frame.pack();
		Dimension dim = this.frame.getSize();
		Dimension test = new Dimension(dim.width + 10, dim.height);
		this.frame.setMinimumSize(test);
		initPosition(position);
		this.frame.setLocation(point.x, point.y);
		final Color normalColor = panel.getPanel().getBackground();
		panel.getLabel().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(
							new URI(Options.getFsnetUrl() + "/Announces.do"));

				} catch (URISyntaxException ex) {
					Logger.getLogger(FSNetTray.class.getName()).log(
							Level.SEVERE, null, ex);
				} catch (IOException ex) {
					Logger.getLogger(FSNetTray.class.getName()).log(
							Level.SEVERE, null, ex);
				}
				frame.dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				panel.getPanel().setBackground(new Color(0xc6c2bf));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				panel.getPanel().setBackground(normalColor);
			}
		});
	}

	public void addPanelEvent(int nbEvent) {
		final NotificationPanel panel = new NotificationPanel(nbEvent,
				trayi18n.getString("NEWEVENT"));
		this.frame.add(panel.getPanel());
		this.frame.pack();
		Dimension dim = this.frame.getSize();
		Dimension test = new Dimension(dim.width + 10, dim.height);
		this.frame.setMinimumSize(test);
		initPosition(position);
		this.frame.setLocation(point.x, point.y);
		final Color normalColor = panel.getPanel().getBackground();
		panel.getLabel().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(
							new URI(Options.getFsnetUrl() + "/Events.do"));

				} catch (URISyntaxException ex) {
					Logger.getLogger(FSNetTray.class.getName()).log(
							Level.SEVERE, null, ex);
				} catch (IOException ex) {
					Logger.getLogger(FSNetTray.class.getName()).log(
							Level.SEVERE, null, ex);
				}
				frame.dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				panel.getPanel().setBackground(new Color(0xc6c2bf));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				panel.getPanel().setBackground(normalColor);
			}
		});
	}

	public void addPanelConsultation(int nbConsultation) {

		final NotificationPanel panel = new NotificationPanel(nbConsultation,
				trayi18n.getString("NEWCONSULTATION"));
		this.frame.add(panel.getPanel());
		this.frame.pack();
		Dimension dim = this.frame.getSize();
		Dimension test = new Dimension(dim.width + 10, dim.height);
		this.frame.setMinimumSize(test);
		initPosition(position);
		this.frame.setLocation(point.x, point.y);
		final Color normalColor = panel.getPanel().getBackground();
		panel.getLabel().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop()
							.browse(new URI(Options.getFsnetUrl()
									+ "/Consultations.do"));

				} catch (URISyntaxException ex) {
					Logger.getLogger(FSNetTray.class.getName()).log(
							Level.SEVERE, null, ex);
				} catch (IOException ex) {
					Logger.getLogger(FSNetTray.class.getName()).log(
							Level.SEVERE, null, ex);
				}
				frame.dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				panel.getPanel().setBackground(new Color(0xc6c2bf));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				panel.getPanel().setBackground(normalColor);
			}
		});
	}

	public void init(Point position) {
		this.frame.setUndecorated(true);
		BoxLayout flow = new BoxLayout(frame.getContentPane(),
				BoxLayout.PAGE_AXIS);
		this.frame.setVisible(true);
		this.frame.setLayout(flow);
		this.frame.pack();
		initPosition(position);
		this.frame.setLocation(point.x, point.y);
	}

	public void initPosition(Point position) {
		point = new Point();
		Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit()
				.getScreenSize();
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
