package fr.univartois.ili.fsnet.chat.gwt.client;

public class Utils {
	
	public static String buildUserProfileLink(int userId, String innerHtml) {
		String link = "<a href=\"DisplayProfile.do?id=";
		link += userId;
		link += ">";
		link += innerHtml;
		link += "</a>";
		return link;
	}
	
	public static String buildUserProfileButton(int userId, String innerHtml) {
		String link = "<a class=\"button\" href=\"DisplayProfile.do?id=";
		link += userId;
		link += ">";
		link += innerHtml;
		link += "</a>";
		return link;
	}

}
