package fr.univartois.ili.fsnet.trayDesktop.src;


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
//import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class TrayClass {
	private static String chaine;
	private static String url;
	
	public TrayClass(String chaine) {
		// TODO Auto-generated constructor stub
		this.chaine=chaine;
	}
	// public static void main(String[] args) {
	public void executeTrayIcon(){
	        /* Use an appropriate Look and Feel */
	        try {
	            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
	        } catch (UnsupportedLookAndFeelException ex) {
	            ex.printStackTrace();
	        } catch (IllegalAccessException ex) {
	            ex.printStackTrace();
	        } catch (InstantiationException ex) {
	            ex.printStackTrace();
	        } catch (ClassNotFoundException ex) {
	            ex.printStackTrace();
	        }
	        /* Turn off metal's use of bold fonts */
	        UIManager.put("swing.boldMetal", Boolean.FALSE);
	        //Schedule a job for the event-dispatching thread:
	        //adding TrayIcon.
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
	    
	    private static void createAndShowGUI() throws IOException {
	    final BufferedReader ficTexte = new BufferedReader(new FileReader(new File("C:\\Users\\benjira\\workspace\\eee\\src\\fr\\univartois\\ili\\fsnet\\trayDesktop\\src\\preferences.txt")));
		
	        //Check the SystemTray support
	        if (!SystemTray.isSupported()) {
	            System.out.println("SystemTray is not supported");
	            return;
	        }
	        final PopupMenu popup = new PopupMenu();
	        final TrayIcon trayIcon =
	                new TrayIcon(new ImageIcon("/homelocal/eb/Bureau/fsnet3.gif").getImage());
	        final SystemTray tray = SystemTray.getSystemTray();
	        
	        trayIcon.setToolTip("Notification FSNet");
	        
	        // Create a popup menu components
	        MenuItem aboutItem = new MenuItem("Preferences");
	        CheckboxMenuItem cb1 = new CheckboxMenuItem("Set auto size");
	        CheckboxMenuItem cb2 = new CheckboxMenuItem("Set tooltip");
	        Menu displayMenu = new Menu("Display");
	        MenuItem errorItem = new MenuItem("Error");
	        MenuItem warningItem = new MenuItem("Warning");
	        MenuItem infoItem = new MenuItem("Info");
	        MenuItem noneItem = new MenuItem("None");
	        MenuItem exitItem = new MenuItem("   Exit   ");
	        
	        //Add components to popup menu
	        popup.add(aboutItem);
	        /*popup.addSeparator();
	        
	        popup.add(cb2);
	        popup.addSeparator();
	        popup.add(displayMenu);
	        displayMenu.add(errorItem);
	        displayMenu.add(warningItem);
	        displayMenu.add(infoItem);
	        displayMenu.add(noneItem);*/
	        popup.add(cb1);
	        popup.add(exitItem);
	       trayIcon.setPopupMenu(popup);
	        
	        try {
	            tray.add(trayIcon);
	        } catch (AWTException e) {
	            System.out.println("TrayIcon could not be added.");
	            return;
	        }
	        
	        trayIcon.displayMessage("Notificatios",chaine , TrayIcon.MessageType.INFO);
	        
	        trayIcon.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	trayIcon.displayMessage("Notificatios", chaine, TrayIcon.MessageType.INFO);
	            	String ligne = null;
	            	 BufferedReader ficTexte = null;
					try {
						ficTexte = new BufferedReader(new FileReader(new File("C:\\Users\\benjira\\workspace\\eee\\src\\fr\\univartois\\ili\\fsnet\\trayDesktop\\src\\preference.conf")));
					} catch (FileNotFoundException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
	            	try {
						ligne = ficTexte.readLine();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
	    			
					if (ligne != null) {
	    				url=ligne;
	    			}
	            	try {
                        Desktop.getDesktop().browse(new URI(url));
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (URISyntaxException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
	            	// JOptionPane.showMessageDialog(null,"This dialog box is run from System Tray");
	            }
	        });
	        
	        aboutItem.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	               // JOptionPane.showMessageDialog(null,"This dialog box is run from the About menu item");
	            	String input=JOptionPane.showInputDialog(null);
	            	PrintWriter ecrivain;
	            	String filePath = "C:\\Users\\benjira\\workspace\\eee\\src\\fr\\univartois\\ili\\fsnet\\trayDesktop\\src\\preference.conf";
	            	File file=new File(filePath);
	            	if(input!=null && input!=""){
	            	try {
						ecrivain =  new PrintWriter(new BufferedWriter(new FileWriter(file)));
						ecrivain.println(input);
						ecrivain.close();

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            	}
	            	System.out.println("------------"+input);
	            }
	        });
	        
	        cb1.addItemListener(new ItemListener() {
	            public void itemStateChanged(ItemEvent e) {
	                int cb1Id = e.getStateChange();
	                if (cb1Id == ItemEvent.SELECTED){
	                    trayIcon.setImageAutoSize(true);
	                } else {
	                    trayIcon.setImageAutoSize(false);
	                }
	            }
	        });
	        
	        cb2.addItemListener(new ItemListener() {
	            public void itemStateChanged(ItemEvent e) {
	                int cb2Id = e.getStateChange();
	                if (cb2Id == ItemEvent.SELECTED){
	                    trayIcon.setToolTip("Sun TrayIcon");
	                } else {
	                    trayIcon.setToolTip(null);
	                }
	            }
	        });
	        
	        ActionListener listener = new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                MenuItem item = (MenuItem)e.getSource();
	                //TrayIcon.MessageType type = null;
	                System.out.println(item.getLabel());
	                if ("Error".equals(item.getLabel())) {
	                    //type = TrayIcon.MessageType.ERROR;
	                    trayIcon.displayMessage("Sun TrayIcon Demo",
	                            "This is an error message", TrayIcon.MessageType.ERROR);
	                    
	                } else if ("Warning".equals(item.getLabel())) {
	                    //type = TrayIcon.MessageType.WARNING;
	                    trayIcon.displayMessage("Sun TrayIcon Demo",
	                            "This is a warning message", TrayIcon.MessageType.WARNING);
	                    
	                } else if ("Info".equals(item.getLabel())) {
	                    //type = TrayIcon.MessageType.INFO;
	                    trayIcon.displayMessage("Sun TrayIcon Demo",
	                            "This is an info message", TrayIcon.MessageType.INFO);
	                    
	                } else if ("None".equals(item.getLabel())) {
	                    //type = TrayIcon.MessageType.NONE;
	                    trayIcon.displayMessage("Sun TrayIcon Demo",
	                            "This is an ordinary message", TrayIcon.MessageType.NONE);
	                }
	            }
	        };
	        
	        errorItem.addActionListener(listener);
	        warningItem.addActionListener(listener);
	        infoItem.addActionListener(listener);
	        noneItem.addActionListener(listener);
	        
	        exitItem.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                tray.remove(trayIcon);
	                System.exit(0);
	            }
	        });
	    }
	    
	    //Obtain the image URL
	    protected static Image createImage(String path, String description) {
	        URL imageURL = TrayClass.class.getResource(path);
	        
	        if (imageURL == null) {
	            System.err.println("Resource not found: " + path);
	            return null;
	        } else {
	            return (new ImageIcon(imageURL, description)).getImage();
	        }
	    }

	
	    public static void main(String [] args){
	    	
	    	TrayClass c = new TrayClass("bonjour");
	    	c.executeTrayIcon();
	    }
	    
}
