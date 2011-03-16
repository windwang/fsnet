package fr.univartois.ili.fsnet.trayDesktop.views;

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

import fr.univartois.ili.fsnet.trayDesktop.TrayLauncher;
import fr.univartois.ili.fsnet.trayDesktop.controls.WSControl;
import fr.univartois.ili.fsnet.trayDesktop.model.Options;
import fr.univartois.ili.fsnet.trayDesktop.model.WSListener;
import fr.univartois.ili.fsnet.trayDesktop.model.WSMessage;

/**
 * The class FSnetTray has the responsibility to create a tray icon and display
 * system notifications
 * 
 * @author Matthieu Proucelle <matthieu.proucelle at gmail.com>
 */
public class FSNetTray implements WSListener {

	private final ResourceBundle trayi18n = TrayLauncher.getBundle();
	private TrayIcon tray;
	private final WSControl control;
	private boolean newMessage, newContact, newEvent, newAnnounce,
			newConsultation;

	/**
	 * 
	 * @param image
	 *            the icon to display in system tray
	 * @param infoService
	 *            the webservice to invoke
	 */
	public FSNetTray(Image image, WSControl control) {
		if (image == null || control == null) {
			throw new IllegalArgumentException();
		}
		tray = new TrayIcon(image);
		initTrayIcon();
		this.control = control;
		newMessage = false;
		newContact = false;
		newEvent = false;
		newAnnounce = false;
		newConsultation = false;
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
		CheckboxMenuItem size = new CheckboxMenuItem(
				trayi18n.getString("SETAUTOSIZE"));
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
					if (newMessage)
						Desktop.getDesktop().browse(
								new URI(Options.getFsnetUrl() + "/Inbox.do"));
					else if (newContact)
						Desktop.getDesktop()
								.browse(new URI(Options.getFsnetUrl()
										+ "/Contacts.do"));
					else if (newAnnounce)
						Desktop.getDesktop()
								.browse(new URI(Options.getFsnetUrl()
										+ "/Announces.do"));
					else if (newEvent)
						Desktop.getDesktop().browse(
								new URI(Options.getFsnetUrl() + "/Events.do"));
					else if (newConsultation)
						Desktop.getDesktop().browse(
								new URI(Options.getFsnetUrl()
										+ "/Consultations.do"));
					else
						Desktop.getDesktop().browse(
								new URI(Options.getFsnetUrl()));
				} catch (URISyntaxException ex) {
					Logger.getLogger(FSNetTray.class.getName()).log(
							Level.SEVERE, null, ex);
				} catch (IOException ex) {
					Logger.getLogger(FSNetTray.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		});

		tray.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// control.checkWS();
				Point position = e.getLocationOnScreen();
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
		newMessage = true;
		newContact = newAnnounce = newEvent = newConsultation = false;
		tray.displayMessage(trayi18n.getString("NOTIFICATIONS"),
				message.getMessage() + " " + trayi18n.getString("NEWMESSAGES"),
				TrayIcon.MessageType.NONE);

	}

	@Override
	public void onNewEvent(WSMessage message) {
		newEvent = true;
		newContact = newAnnounce = newMessage = newConsultation = false;
		tray.displayMessage(trayi18n.getString("NOTIFICATIONS"),
				message.getMessage() + " " + trayi18n.getString("NEWEVENT"),
				TrayIcon.MessageType.NONE);
	}

	@Override
	public void onNewContact(WSMessage mes) {
		newContact = true;
		newMessage = newAnnounce = newEvent = newConsultation = false;
		tray.displayMessage(trayi18n.getString("NOTIFICATIONS"),
				mes.getMessage() + " " + trayi18n.getString("NEWCONTACT"),
				TrayIcon.MessageType.NONE);

	}

	@Override
	public void onNewAnnouncement(WSMessage mes) {
		newAnnounce = true;
		newContact = newMessage = newEvent = newConsultation = false;
		tray.displayMessage(trayi18n.getString("NOTIFICATIONS"),
				mes.getMessage() + " " + trayi18n.getString("NEWANNOUNCEMENT"),
				TrayIcon.MessageType.NONE);
	}

	@Override
	public void onNewNotification(WSMessage mes) {
		newContact = newAnnounce = newEvent = newMessage = newConsultation = false;
		tray.displayMessage(trayi18n.getString("NOTIFICATIONS"),
				mes.getMessage() + " " + trayi18n.getString("NEWNOTIFICATION"),
				TrayIcon.MessageType.NONE);

	}

	@Override
	public void onNewConsultation(WSMessage mes) {
		newConsultation = true;
		newContact = newAnnounce = newMessage = newEvent = false;
		tray.displayMessage(trayi18n.getString("NOTIFICATIONS"),
				mes.getMessage() + " " + trayi18n.getString("NEWCONSULTATION"),
				TrayIcon.MessageType.NONE);
	}

	@Override
	public void onError(WSMessage message) {
		tray.displayMessage(trayi18n.getString("NOCONNECTION"),
				trayi18n.getString("NOCONNECTION"), TrayIcon.MessageType.ERROR);
	}

	@Override
	public void onConnection(WSMessage message) {
	}
}
