package fr.univartois.ili.fsnet.trayDesktop.views;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;

import javax.swing.JFrame;


public class NotificationFrame {
	
	private JFrame frame;
	private FlowLayout flow;
	private Point point;
	private Point position;
	
	
	public NotificationFrame(Point position) {		
		frame = new JFrame("Fsnet");
		this.position=position;
		init(position);
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public void addPanelMessage(int nbMessage){
		NotificationPanel panel = new NotificationPanel(nbMessage,"nouveaux messages");
		this.frame.add(panel.getPanel());
		this.frame.pack();
		initPosition(position);
		this.frame.setLocation(point.x,point.y);
	}
	
	public void addPanelContact(int nbContact){
		NotificationPanel panel = new NotificationPanel(nbContact,"nouvelles demandes de contacts");
		this.frame.add(panel.getPanel());
		this.frame.pack();
		initPosition(position);
		this.frame.setLocation(point.x,point.y);
		
	}
	
	public void init(Point position){		
		this.frame.setUndecorated(true);
		flow = new FlowLayout();
		this.frame.setVisible(true);
		this.frame.setLayout(flow);				
		this.frame.pack();
		initPosition(position);
		this.frame.setLocation(point.x,point.y);
	}
	
	public void initPosition(Point position){
		point = new Point();
		Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int screenheight = (int)tailleEcran.getHeight();
		int screenwidht = (int)tailleEcran.getWidth();
		int framewidht = this.frame.getWidth();
		int frameheight = this.frame.getHeight();
		point.x=position.x;
		point.y=position.y;
		if ((framewidht+position.x)>screenwidht){
			point.x= position.x -(framewidht + position.x - screenwidht);
		}
		if((frameheight+position.y)>screenheight){
			point.y= position.y - (frameheight + position.y -screenheight);
		}
	}
	

}
