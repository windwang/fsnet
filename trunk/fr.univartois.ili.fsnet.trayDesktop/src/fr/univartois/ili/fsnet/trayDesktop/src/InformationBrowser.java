import java.awt.*;                
import javax.swing.*;              

import java.awt.event.*;           
import javax.swing.event.*;     
import java.beans.*;               
import java.awt.print.*;         
import java.io.*;                 
import java.net.*; 
/**
* @author mohammed BENJIRA
*/
public class InformationBrowser extends JFrame implements HyperlinkListener, PropertyChangeListener
{    
	private static final long serialVersionUID = 1L;

	private JEditorPane text;     
    private JLabel message;        
    private JTextField urlField;       
    static boolean exitWhenLastWindowClosed = false;
    String home = "#";//http://www.google.fr";  

    public InformationBrowser() {
	super(); 
	text = new JEditorPane();
	text.setContentType("text/html");
	text.setText("nouveau hub créer ");
	
	text.setText("Google  : <a href=\"http://www.google.fr\" target=\"_blank\"> Goooogle <\\a><br>");
	text.setEditable(false);    	
	text.addHyperlinkListener(this); 
	text.addPropertyChangeListener(this);
	this.getContentPane().add(new JScrollPane(text),BorderLayout.CENTER);
	message = new JLabel(" Information");
	this.getContentPane().add(message, BorderLayout.SOUTH);
	urlField = new JTextField();
	urlField.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    displayPage(urlField.getText());
		}
	    });
    }

    
    public static void setExitWhenLastWindowClosed(boolean b) {
	exitWhenLastWindowClosed = b;
    }

   
    public void setHome(String home) { this.home = home; }
    public String getHome() { return home; }


    boolean visit(URL url) {
	try {
	    String href = url.toString();
	    text.setPage(url);  
	    //this.setTitle(href);     
	    urlField.setText(href);  
	    return true;            
	}
	catch (IOException ex) {     
	   
	    message.setText("Impossible de charger la page: " + ex.getMessage());
	    return false;            
	}
    }

    
    
	public void displayPage(URL url) {
	        visit(url);	
	}
    

   
    public void displayPage(String href) {
	try {
	    displayPage(new URL(href));
	}
	catch (MalformedURLException ex) {
	    message.setText("URL incorrecte: " + href);
	}
    }

   
   

   
    public void home() { displayPage(getHome()); }

   
    public void newBrowser() {
	InformationBrowser b = new InformationBrowser();
	b.setSize(this.getWidth(), this.getHeight());
	b.setVisible(true);
    }

 
    public void close() {
	this.setVisible(false);             
	this.dispose();                    
	
    }
       
   
    public void exit(boolean confirm) {
	if (!confirm ||
	    (JOptionPane.showConfirmDialog(this,  
	      "Are you sure you want to quit?",
		"Really Quit?",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION))  
	    System.exit(0);
    }

   
    public void print() {
	
	PrinterJob job = PrinterJob.getPrinterJob();
	
	if (job.printDialog()) {  
	    try { job.print(); }  
	    catch(PrinterException ex) {  
		message.setText("Couldn't print: " + ex.getMessage());
	    }
	}
    }

   
    public void hyperlinkUpdate(HyperlinkEvent e) {
	HyperlinkEvent.EventType type = e.getEventType(); 
	if (type == HyperlinkEvent.EventType.ACTIVATED) {     
	    displayPage(e.getURL());   
	}
	else if (type == HyperlinkEvent.EventType.ENTERED) { 
	    
	    message.setText(e.getURL().toString());  
	}
	else if (type == HyperlinkEvent.EventType.EXITED) {  
	    message.setText(" "); 
	}
    }

   
      
    public static void main(String[] args) throws IOException {
    	
        Color c = new Color(10);
    	InformationBrowser.setExitWhenLastWindowClosed(true);
    	InformationBrowser browser = new InformationBrowser(); 
    	browser.setSize(200, 300);
    	browser.setLocation(1080, 470);
    	browser.setBackground(c);
    	browser.setVisible(true);
    	browser.setTitle("Notifications");
    	browser.displayPage((args.length > 0) ? args[0] : browser.getHome());
    	
    	
    	
    	
       
    }

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}
}
