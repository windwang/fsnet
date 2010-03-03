package fr.univartois.ili.fsnet.trayDesktop.views;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class NotificationPanel {
	
	private JPanel panel;
	private JLabel label;
	private BorderLayout border;	
	
	public NotificationPanel(int nb,String message) {
		this.panel = new JPanel();
		init(nb,message);
	}
	
	public JPanel getPanel() {
		return panel;
	}
	
	public void init(int nb,String message){
		this.label = new JLabel("Vous avez "+nb+" "+message);
		border = new BorderLayout();
		panel.setLayout(border);
		this.panel.add(label, BorderLayout.WEST);		
	}
	
	public JLabel getLabel() {
		return label;
	}
	
	

}
