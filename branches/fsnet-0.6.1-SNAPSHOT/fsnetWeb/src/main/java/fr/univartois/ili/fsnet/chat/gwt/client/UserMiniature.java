package fr.univartois.ili.fsnet.chat.gwt.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;

public class UserMiniature extends Composite {

	private Image image;
	
	public UserMiniature(final int userId, final String userName) {
		image = new Image("miniature/" + userId + ".png");
		image.addStyleName("loggedUserMiniature");
		image.setTitle(userName);
		initWidget(image);
		image.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.Location.replace("DisplayProfile.do?id=" + userId);
			}
		});
	}
	
}
