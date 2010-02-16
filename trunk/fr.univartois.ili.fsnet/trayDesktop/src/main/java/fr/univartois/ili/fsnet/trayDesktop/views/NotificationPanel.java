package fr.univartois.ili.fsnet.trayDesktop.views;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NotificationPanel {
	
	private JPanel panel;
	private JLabel label;
	private BorderLayout border;	
	
	public NotificationPanel(int nb) {
		this.panel = new JPanel();
		init(nb);
	}
	
	public JPanel getPanel() {
		return panel;
	}
	
	public void init(int nb){
		this.label = new JLabel("Vous avez "+nb+" nouveau(x) message(s)");
		border = new BorderLayout();
		panel.setLayout(border);
		this.panel.add(label, BorderLayout.WEST);		
	}
	
	public JLabel getLabel() {
		return label;
	}
	
	

}
