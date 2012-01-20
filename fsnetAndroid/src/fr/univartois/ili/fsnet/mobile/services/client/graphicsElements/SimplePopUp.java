package fr.univartois.ili.fsnet.mobile.services.client.graphicsElements;

import android.app.Activity;
import android.app.AlertDialog;

public class SimplePopUp {

	private String title;
	private String message;
	private String button;
    public SimplePopUp(Activity activity,String title,String message,String button)
    {
        new AlertDialog.Builder(activity)
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(button, null)
        .show();
    }
    public SimplePopUp(Activity activity,String title,String message)
    {
    	this(activity,title,message,"Ok");
    }
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public void setButton(String button) {
		this.button = button;
	}
	public String getButton() {
		return button;
	}
}
