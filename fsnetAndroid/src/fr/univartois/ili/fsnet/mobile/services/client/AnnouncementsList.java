package fr.univartois.ili.fsnet.mobile.services.client;


import android.content.Context;
import android.widget.ArrayAdapter;


/**
 * 
 * @author alexandre thery
 *
 */
public class AnnouncementsList extends ArrayAdapter<String>{

	public AnnouncementsList(Context context, int resource, int textViewResourceId,
			Object[] objects) {
		super(context, resource, textViewResourceId, (String[]) objects);
	}



}
