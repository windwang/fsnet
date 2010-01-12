package fr.univartois.ili.fsnet.trayDesktop;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.xml.rpc.ServiceException;
import fr.univartois.ili.fsnet.webservice.NouvellesInformations;
import fr.univartois.ili.fsnet.webservice.NouvellesService;
import fr.univartois.ili.fsnet.webservice.NouvellesServiceLocator;

/**
 * 
 * The class TrayClass.
 * 
 */
public class TrayClass {

    public static final String urlTemp = "http://code.google.com/p/fsnet/";
    private String chaine;
    private String url;
    private String filePath = System.getenv("HOME") + "/FSN/preferences.conf";
    private ImageIcon monIcone;

    /**
     * Constructor of the class TrayClass.
     *
     * @param chaine
     */
    public TrayClass(String chaine) {
        // TODO Auto-generated constructor stub

        this.chaine = chaine;
    }

    public void executeTrayIcon() throws UnknownHostException, IOException {
        /* Use Look and Feel of System */

        monIcone = new ImageIcon(getClass().getResource("/ressources/iconefsnet.png"));

        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* Turn off metal's use of bold fonts */
        // Schedule a job for the event-dispatching thread:
        // adding TrayIcon.
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {

                try {
                    createAndShowGUI();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * Create and show the GUI
     * @throws IOException
     */
    private void createAndShowGUI() throws IOException {

        // Check the SystemTray support
        if (!SystemTray.isSupported()) {

            JOptionPane.showMessageDialog(null, "SystemTray is not supported");
            //System.out.println("SystemTray is not supported");
            return;
        }
        final PopupMenu popup = new PopupMenu();
        final TrayIcon trayIcon = new TrayIcon(monIcone.getImage());
        final SystemTray tray = SystemTray.getSystemTray();

        trayIcon.setToolTip("Notification FSNet");

        // Create a popup menu components
        MenuItem configItem = new MenuItem("Preferences");
        CheckboxMenuItem size = new CheckboxMenuItem("Set auto size");
        MenuItem exitItem = new MenuItem("   Exit   ");

        // Add components to popup menu
        popup.add(configItem);
        popup.add(size);
        popup.add(exitItem);
        trayIcon.setPopupMenu(popup);

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return;
        }

        trayIcon.displayMessage("Notifications", chaine,
                TrayIcon.MessageType.INFO);

        final File fichier = new File(filePath);
        if (!fichier.exists()) {
            File fb = new File(System.getenv("HOME") + "/FSN");
            fb.mkdirs();
            try {
                fichier.createNewFile();
                PrintWriter ecrivain1 = new PrintWriter(new BufferedWriter(
                        new FileWriter(fichier)));
                ecrivain1.println(urlTemp);
                ecrivain1.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

        trayIcon.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                trayIcon.displayMessage("Notifications", chaine,
                        TrayIcon.MessageType.INFO);
                String ligne = null;
                BufferedReader ficTexte = null;
                try {
                    ficTexte = new BufferedReader(new FileReader(fichier));
                } catch (FileNotFoundException e3) {
                    File fb = new File(System.getenv("HOME") + "/FSN");
                    fb.mkdirs();
                    File fichier = new File(System.getenv("HOME") + "/FSN/preferences.conf");
                    try {
                        fichier.createNewFile();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                }

                try {
                    ligne = ficTexte.readLine();
                } catch (IOException e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }

                if (ligne != null) {
                    url = ligne;
                    try {
                        Desktop.getDesktop().browse(new URI(url));
                    } catch (IOException e1) {
                        try {
                            Desktop.getDesktop().browse(new URI(urlTemp));
                        } catch (IOException e2) {
                            // TODO Auto-generated catch block
                            e2.printStackTrace();
                        } catch (URISyntaxException e2) {
                            // TODO Auto-generated catch block
                            e2.printStackTrace();
                        }
                    } catch (URISyntaxException e1) {
                        try {
                            Desktop.getDesktop().browse(new URI(urlTemp));
                        } catch (IOException e2) {
                            // TODO Auto-generated catch block
                            e2.printStackTrace();
                        } catch (URISyntaxException e2) {
                            // TODO Auto-generated catch block
                            e2.printStackTrace();
                        }
                    }

                }

            }
        });

        configItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // JOptionPane.showMessageDialog(null,"This dialog box is run from the About menu item");
                String input = JOptionPane.showInputDialog(null);
                PrintWriter ecrivain;
                File file = new File(filePath);
                if (input != null && !input.equals("")) {
                    try {
                        ecrivain = new PrintWriter(new BufferedWriter(
                                new FileWriter(file)));
                        ecrivain.println(input);
                        ecrivain.close();

                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }

            }
        });

        size.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                int cb1Id = e.getStateChange();
                if (cb1Id == ItemEvent.SELECTED) {
                    trayIcon.setImageAutoSize(true);
                } else {
                    trayIcon.setImageAutoSize(false);
                }
            }
        });

        exitItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                System.exit(0);
            }
        });

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {

            public void run() {
                NouvellesService nv = new NouvellesServiceLocator();
                NouvellesInformations nvs = null;
                try {
                    nvs = nv.getNouvellesInformationsPort();
                } catch (ServiceException e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }
                try {
                    int nbNewEvents = nvs.getNumberOfNewEvents();
                    if (nbNewEvents > 0) {
                        trayIcon.displayMessage("Notificatios", "il y a " + nbNewEvents + " Nouveaux Evenements",
                                TrayIcon.MessageType.INFO);
                    }
                } catch (RemoteException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }


            }
        }, 1 * 10000, 1 * 10000);

    }

    // Obtain the image URL
    protected static Image createImage(String path, String description) {
        URL imageURL = TrayClass.class.getResource(path);

        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }

    public static void main(String[] args) throws UnknownHostException, IOException, ServiceException {
        NouvellesService nv = new NouvellesServiceLocator();
        NouvellesInformations nvs = nv.getNouvellesInformationsPort();
        TrayClass c = new TrayClass("Il y a " + nvs.getNumberOfNewEvents() + " nouveau(x) évenement(s) ");
        c.executeTrayIcon();
    }
}
