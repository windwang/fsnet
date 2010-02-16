package fr.univartois.ili.fsnet.trayDesktop.views;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;

import javax.swing.JFrame;


public class NotificationFrame {
	
	private JFrame frame;
	private FlowLayout flow;
	private Point point;
	
	
	public NotificationFrame(Point position) {		
		frame = new JFrame("Fsnet");
		init(position);
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public void init(Point position){		
		this.frame.setUndecorated(true);
		flow = new FlowLayout();this.frame.setVisible(true);
		this.frame.setLayout(flow);	
		NotificationPanel panel = new NotificationPanel(4);
		this.frame.add(panel.getPanel());
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
