package fr.univartois.ili.fsnet.mobile.services.client;


import android.content.Context;
import android.widget.ArrayAdapter;


/**
 * 
 * @author alexandre thery
 *
 */
public class EventsList extends ArrayAdapter<String>{

	public EventsList(Context context, int resource, int textViewResourceId,
			Object[] objects) {
		super(context, resource, textViewResourceId, (String[]) objects);
		
	}



}
