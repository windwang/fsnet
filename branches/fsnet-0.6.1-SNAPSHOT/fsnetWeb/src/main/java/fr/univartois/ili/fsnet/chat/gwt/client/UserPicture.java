package fr.univartois.ili.fsnet.chat.gwt.client;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;

public class UserPicture extends Composite {

	private Image image;
	
	private final int userId;
	
	private Set<MiniatureClickedListener> listeners = new HashSet<MiniatureClickedListener>();
	
	public void addMiniatureClickedListener(MiniatureClickedListener listener) {
		listeners.add(listener);
	}

	public UserPicture(final int userId, final String userName, final boolean isMiniature) {
		this.userId = userId;
		if (isMiniature) {
			image = new Image("miniature/" + userId + ".png");
		} else {
			image = new Image("avatar/" + userId + ".png");
		}
		image.addStyleName("loggedUserMiniature");
		image.setTitle(userName);
		initWidget(image);
		final UserPicture thisUserPicture = this;
		image.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				for (MiniatureClickedListener listener : listeners) {
					listener.onMiniatureClicked(thisUserPicture);
				}
			}
		});
	}

	public int getUserId() {
		return userId;
	}

}
