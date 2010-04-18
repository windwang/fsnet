package fr.univartois.ili.fsnet.chat.gwt.client;


import com.gargoylesoftware.htmlunit.html.DomText;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UserInfosPanel extends Composite {

	private PopupPanel popup;
	
	private UserPicture userPicture;
	
	private int userId;
	
	private String name;
	
	public UserInfosPanel(final String name, final int userId) {
		this.userId = userId;
		this.name = name;
		popup = new PopupPanel(true);
		userPicture = new UserPicture(userId, name, false);
		buildUI();
	}
	
	public void show(int left, int top) {
		popup.setPopupPosition(left, top);
		popup.show();
	}
	
	public void hide() {
		popup.hide();
	}
	
	private void buildUI() {
		final VerticalPanel vPanel = new VerticalPanel();
		final HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.add(userPicture);
		hPanel.add(vPanel);
		popup.add(hPanel);
		vPanel.add(new HTML(Utils.buildUserProfileLink(userId, name)));
		Button chatButton = new Button("Chater");
		HTML profilButton = new HTML(Utils.buildUserProfileButton(userId, "Profil"));
		chatButton.addStyleName("button");
		chatButton.addStyleName("chatButton");
		vPanel.add(chatButton);
		
		vPanel.add(profilButton);
	}
	
}
