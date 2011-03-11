package fr.univartois.ili.fsnet.mobile.services.client;


import android.content.Context;
import android.widget.ArrayAdapter;


/**
 * 
 * @author alexandre thery
 *
 */
public class MeetingsList extends ArrayAdapter<String>{

	public MeetingsList(Context context, int resource, int textViewResourceId,
			Object[] objects) {
		super(context, resource, textViewResourceId, (String[]) objects);
	}



}
