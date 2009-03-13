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
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.lang.System;


/**
 * 
 * The class TrayClass.
 *  
 */


public class TrayClass {
	public static final String urlTemp="http://www.google.com";
	private static String chaine;
	private static String url;
	private static String filePath =System.getenv("HOME")+"/FSN/preferences.conf";
	
	/**
	 * Constructor of the class TrayClass.
	 * @param chaine
	 */
	
	public TrayClass(String chaine) {
		// TODO Auto-generated constructor stub
		this.chaine=chaine;
	}
	
	public void executeTrayIcon(){
	        /* Use Look and Feel of System */
		
		
		
		 try {
	            javax.swing.UIManager.setLookAndFeel(
	                    javax.swing.UIManager.getSystemLookAndFeelClassName());
	        }
	        catch (Exception e){e.printStackTrace();}
	        
	        
	        /* Turn off metal's use of bold fonts */
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
	    
	
	
	/**
	 * ctreate and show the GUI
	 * @throws IOException
	 */
	    private static void createAndShowGUI() throws IOException {
	    	
	    	
	    //Check the SystemTray support
	        if (!SystemTray.isSupported()) {
	            System.out.println("SystemTray is not supported");
	            return;
	        }
	        final PopupMenu popup = new PopupMenu();
	        final TrayIcon trayIcon =
	                new TrayIcon(new ImageIcon("fsnet3.gif").getImage());
	        final SystemTray tray = SystemTray.getSystemTray();
	        
	        trayIcon.setToolTip("Notification FSNet");
	        
	        // Create a popup menu components
	        MenuItem configItem = new MenuItem("Preferences");
	        CheckboxMenuItem cb1 = new CheckboxMenuItem("Set auto size");
	        MenuItem exitItem = new MenuItem("   Exit   ");
	        
	        //Add components to popup menu
	        popup.add(configItem);
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
	        
	       
	       //---------------------- 
	        final File fichier = new File(filePath);
			if(!fichier.exists()){
				File fb = new File(System.getenv("HOME")+"/FSN"); 
				fb.mkdirs();
				try {
					fichier.createNewFile();
					PrintWriter ecrivain1 = new PrintWriter(new BufferedWriter(new FileWriter(fichier)));
					ecrivain1.println("http://www.google.com");
					ecrivain1.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
	        
	        
	        trayIcon.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	trayIcon.displayMessage("Notificatios", chaine, TrayIcon.MessageType.INFO);
	            	String ligne = null;
	            	 BufferedReader ficTexte = null;
					try {
						ficTexte = new BufferedReader(new FileReader(fichier));
					} catch (FileNotFoundException e3) {
						File fb = new File(System.getenv("HOME")+"/FSN"); 
						fb.mkdirs();
						File fichier = new File(System.getenv("HOME")+"/FSN/preferences.conf");
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
	    				url=ligne;
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
	            	String input=JOptionPane.showInputDialog(null);
	            	PrintWriter ecrivain;
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
	    	
	    	TrayClass c = new TrayClass("Nouvel evenement : Soiree le 31 juillet ");
	    	c.executeTrayIcon();
	    }
	    
}
