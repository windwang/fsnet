package fr.univartois.ili.fsnet.mobile.services.client.graphicsElements;

import android.app.Activity;
import android.widget.Toast;

public class SimpleToast {

	public SimpleToast(Activity activity,String message)
	{
		Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
	}
}
