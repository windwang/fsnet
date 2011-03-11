package fr.univartois.ili.fsnet.mobile.services.client;


import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import fr.univartois.ili.fsnet.mobile.services.client.data.AnnouncementsResourcesProvider;
import fr.univartois.ili.fsnet.mobile.services.client.data.InfoSettings;
import fr.univartois.ili.fsnet.mobile.services.client.data.MeetingsResourcesProvider;
import fr.univartois.ili.fsnet.mobile.services.client.data.MessagesResourcesProvider;
import fr.univartois.ili.fsnet.mobile.services.client.data.TestConnectionResourcesProvider;
import fr.univartois.ili.fsnet.mobile.services.client.db.InfoSettingsBDD;
import fr.univartois.ili.fsnet.mobile.services.client.graphicsElements.SimpleMessagesNotification;
import fr.univartois.ili.fsnet.mobile.services.client.graphicsElements.SimplePopUp;
import fr.univartois.ili.fsnet.mobile.services.client.graphicsElements.SimpleToast;
import fr.univartois.ili.fsnet.mobile.services.client.rest.RestAnnouncementList;
import fr.univartois.ili.fsnet.mobile.services.client.rest.RestMeetingList;
import fr.univartois.ili.fsnet.mobile.services.client.rest.RestMessagesList;


/**
 * 
 * 
 * @author alexandre thery
 *
 */
public class FsnetMobile extends Activity {

	private static final String WEB_SERVICE_ADRESS = "http://217.108.57.93:8080/fsnetWeb/";
	private static final String MEETINGS_URL= "DisplayEvent.do?eventId=";
	private static final String MESSAGES_URL= "DisplayMessage.do?messageId=";
	private static final String ANNOUNCEMENTS_URL= "DisplayAnnounce.do?idAnnounce=";
	private InfoSettings info;
	private InfoSettingsBDD infosBDD;
	private String urlServer;
	private Thread thread_for_notification_messages;
	private SimpleMessagesNotification messagesNotification;
	private Boolean notificationOk=false;
	private Boolean notificationStop=false;
	private MessagesList messagesListAdapter;
	private MeetingsList meetingsListAdapter;
	private AnnouncementsList announcementsListAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        info = new InfoSettings();
        infosBDD = new InfoSettingsBDD(this);
        setContentView(R.layout.main);
        setStartOrStopButtonNotificationEnabled(true);
        if(!checkConnection())
        {
        	new SimplePopUp(this,"NetWork problem","Your network is not available, please turn the mobile/wireless network on");
        }
     }
    public boolean checkConnection()
    {
        ConnectivityManager connec =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        State mobileNetwork= connec.getNetworkInfo(0).getState();
        State wirelessNetwork= connec.getNetworkInfo(1).getState();
        if ( mobileNetwork == NetworkInfo.State.CONNECTED ||  mobileNetwork == NetworkInfo.State.CONNECTING)
        	return true;
        if ( wirelessNetwork == NetworkInfo.State.CONNECTED ||  wirelessNetwork == NetworkInfo.State.CONNECTING)
        	return true;
        return false;
    }
    public int getNbMessages()
    {
    	infosBDD.open();
    	if(infosBDD!=null && infosBDD.getLastElement()!=0)
    	{
			MessagesResourcesProvider provider = 
				new MessagesResourcesProvider(infosBDD.getInfoSettings(1));
			RestMessagesList messagesList = provider.unreadPrivateMessages();
			infosBDD.close();
			if(messagesList!=null)
				return messagesList.size();
    	}
		return 0;
    }
    public void seeAnnouncements(View view){
    	infosBDD.open();
        
    	if(!checkConnection())
        {
        	new SimplePopUp(this,"NetWork problem","Your network is not available, please turn the mobile/wireless network on");
        }
        else if(infosBDD!=null && infosBDD.getLastElement()!=0)
    	{
			AnnouncementsResourcesProvider provider = 
				new AnnouncementsResourcesProvider(infosBDD.getInfoSettings(1));
			urlServer = infosBDD.getInfoSettings(1).getServerUrl();
			RestAnnouncementList announcementList = provider.newAnnouncements();
			if(announcementList==null)
			{
				new SimplePopUp(this,"Error","The webservice is not available or check your settings");
			}
			else 
			{
				new SimpleToast(FsnetMobile.this, "Unread announcements : "+announcementList.size());
				String[] mStrings = new String[announcementList.size()];
				final int[] idMeetings = new int[announcementList.size()];
				for(int i = 0;i<announcementList.size();i++){
					mStrings[i] = new String(
							"From : "+announcementList.get(i).getFrom()+"\n"+
							"Title : "+announcementList.get(i).getTitle()
							);
					idMeetings[i] = (announcementList.get(i).getMeetingId());
				}
				setContentView(R.layout.announcements);
				announcementsListAdapter = new AnnouncementsList(this, R.layout.rowlayoutannouncements,R.id.labelAnnouncements, mStrings);
				ListView listViewAnnouncements = (ListView)findViewById(R.id.listAnnouncements);
				listViewAnnouncements.setAdapter(announcementsListAdapter);
				listViewAnnouncements.setTextFilterEnabled(true);
			
				listViewAnnouncements.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
						Intent i = new Intent();
						i.setAction(Intent.ACTION_VIEW);
						i.setData(Uri.parse(urlServer+ANNOUNCEMENTS_URL+idMeetings[arg2]));
						startActivity(i);
					}
		        	
				});
			}
    	}
    	else new SimplePopUp(this,"Error","Configure your settings first");
    	infosBDD.close();
    }
    public void seeMeetings(View view){
    	infosBDD.open();
        if(!checkConnection())
        {
        	new SimplePopUp(this,"NetWork problem","Your network is not available, please turn the mobile/wireless network on");
        }
        else if(infosBDD!=null && infosBDD.getLastElement()!=0)
    	{
			MeetingsResourcesProvider provider = 
				new MeetingsResourcesProvider(infosBDD.getInfoSettings(1));
			urlServer = infosBDD.getInfoSettings(1).getServerUrl();
			RestMeetingList meetingsList = provider.newMeetings();
			if(meetingsList==null)
			{
				new SimplePopUp(this,"Error","The webservice is not available or check your settings");
			}
			else 
			{
				new SimpleToast(FsnetMobile.this, "Unread meetings : "+meetingsList.size());
				String[] mStrings = new String[meetingsList.size()];
				final int[] idMeetings = new int[meetingsList.size()];
				for(int i = 0;i<meetingsList.size();i++){
					mStrings[i] = new String(
							"From : "+meetingsList.get(i).getFrom()+"\n"+
							"Title : "+meetingsList.get(i).getTitle()
							);
					idMeetings[i] = (meetingsList.get(i).getMeetingId());
				}
				setContentView(R.layout.meetings);
				meetingsListAdapter = new MeetingsList(this, R.layout.rowlayoutmeetings,R.id.labelMeetings, mStrings);
				ListView listViewMeetings = (ListView)findViewById(R.id.listMeetings);
				listViewMeetings.setAdapter(meetingsListAdapter);
				listViewMeetings.setTextFilterEnabled(true);
			
				listViewMeetings.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						Intent i = new Intent();
						i.setAction(Intent.ACTION_VIEW);
						i.setData(Uri.parse(urlServer+MEETINGS_URL+idMeetings[arg2]));
						startActivity(i);

					}
		        	
				});
			}
    	}
    	else new SimplePopUp(this,"Error","Configure your settings first");
    	infosBDD.close();
    }
    public void testConnectionToWebService(View view){
    	if(!checkConnection())
    		new SimplePopUp(this,"NetWork problem","Your network is not available, please turn the mobile/wireless network on");
    	else {
    		InfoSettings infos = new InfoSettings();
	        infos.setServerUrl(getTextByEditTextId(R.id.fsnetAdress));
	        infos.setLogin(getTextByEditTextId(R.id.login));
	        infos.setPassword(getTextByEditTextId(R.id.password));
	    	TestConnectionResourcesProvider provider = 
				new TestConnectionResourcesProvider(infos);
			boolean isUser = provider.isUser();
			if(isUser)
				new SimplePopUp(this,"Test","Your configuration works ! (don't forget to save your settings)");
			else new SimplePopUp(this,"Test","Configuration doesn't work :");
    	}
    }
    public boolean checkServerUrl(String serverUrl)
    {
    	return serverUrl.matches("http://.*/fsnetWeb/");
    }
    public void seeMessages(View view)
    {
    	infosBDD.open();
        if(!checkConnection())
        {
        	new SimplePopUp(this,"NetWork problem","Your network is not available, please turn the mobile/wireless network on");
        }
        else if(infosBDD!=null && infosBDD.getLastElement()!=0)
    	{
			MessagesResourcesProvider provider = 
				new MessagesResourcesProvider(infosBDD.getInfoSettings(1));
			RestMessagesList messagesList = provider.unreadPrivateMessages();
			urlServer = infosBDD.getInfoSettings(1).getServerUrl();
			if(messagesList==null)
			{
				new SimplePopUp(this,"Error","The webservice is not available or check your settings");
			}
			else 
			{
				new SimpleToast(FsnetMobile.this, "Unread messages : "+messagesList.size());
				String[] mStrings = new String[messagesList.size()];
				final int[] idMessages = new int[messagesList.size()];
				for(int i = 0;i<messagesList.size();i++){
					mStrings[i] = new String(
							"From : "+messagesList.get(i).getFrom()+"\n"+
							"Subject : "+messagesList.get(i).getSubject()
							);
					idMessages[i] = (messagesList.get(i).getMessageId());
				}
				setContentView(R.layout.messages);
				messagesListAdapter = new MessagesList(this, R.layout.rowlayoutmessages,R.id.label, mStrings);
				ListView listViewMessages = (ListView)findViewById(R.id.listMm);
				listViewMessages.setAdapter(messagesListAdapter);
				listViewMessages.setTextFilterEnabled(true);
			
				listViewMessages.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						if(infosBDD!=null)
						{
							infosBDD.open();
							Intent i = new Intent();
							i.setAction(Intent.ACTION_VIEW);
							i.setData(Uri.parse(urlServer+MESSAGES_URL+idMessages[arg2]));
							startActivity(i);
							infosBDD.close();
						}

					}
		        	
				});
			}
    	}
    	else new SimplePopUp(this,"Error","Configure your settings first");
    	infosBDD.close();
    }
    public void updateEditText(int idEditText,String string)
    {
  	  	EditText et = (EditText)findViewById(idEditText);
  	  	et.setText(string);
    }
    public String getTextByEditTextId(int id)
    {
    	EditText vw = (EditText)findViewById(id);
        Spannable str = vw.getText();
        return str.toString();
    }

    public void submitSettings(View view) {
        info.setServerUrl(getTextByEditTextId(R.id.fsnetAdress));
        info.setLogin(getTextByEditTextId(R.id.login));
        info.setPassword(getTextByEditTextId(R.id.password));
        if(getTextByEditTextId(R.id.time).matches("[0-9]+"))
        	info.setMinutes(Integer.parseInt(getTextByEditTextId(R.id.time)));
        else info.setMinutes(10);
        if(info.getServerUrl()!=null && checkServerUrl(info.getServerUrl())){
            infosBDD.open();
            if(infosBDD.getLastElement()==0)
            	infosBDD.insertInfo(info);
            else{
            	infosBDD.updateInfo(1, info);
            }
            new SimpleToast(FsnetMobile.this, "Configuration saved");
            infosBDD.close();
        }
        
        else new SimplePopUp(FsnetMobile.this, "Error", "Url problem\nA good one is like\nhttp://.../fsnetWeb/");
    }
    
    public void deleteSettings(View view) {
        
        infosBDD.open();
        if(infosBDD.getLastElement()==0)
        	infosBDD.insertInfo(new InfoSettings());
        else{
        	infosBDD.updateInfo(1, new InfoSettings());
        }
        setContentView(R.layout.settings);
        new SimpleToast(FsnetMobile.this, "Configuration deleted");
        infosBDD.close();
    }
    public void setStartOrStopButtonNotificationEnabled(boolean startButtonValue)
    {
		Button buttonStartNotification = (Button) findViewById(R.id.startNotification);
		buttonStartNotification.setEnabled(startButtonValue);
		Button buttonStopNotification = (Button) findViewById(R.id.stopNotification);
		buttonStopNotification.setEnabled(!startButtonValue);
    }
    public void stopNotification(View view){
    	new SimpleToast(this, "Stop notification");
    	setStartOrStopButtonNotificationEnabled(true);
    	if(notificationOk)
    		notificationStop=true;
    }
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.menu, menu);
        return true;
     }
    public void startNotification(View view) {
    	if(!checkConnection())
    		new SimplePopUp(this,"NetWork problem","Your network is not available, please turn the mobile/wireless network on");
    	else if(infosBDD!=null)
    	{
    	new SimpleToast(this, "Start notification");	
    	setStartOrStopButtonNotificationEnabled(false);
	    Runnable r = new Runnable(){
	
	    	public void run() {
	    		int nbMessages=0;
	    		while(true){
	    			try {
		    			if((nbMessages=getNbMessages())>0){
		    				notificationOk = true;
		    				int delay=0;
		    				if(infosBDD!=null){
		    					infosBDD.open();
		    					delay=infosBDD.getInfoSettings(1).getMinutes();
		    					infosBDD.close();
		    				}
		    				Thread.sleep(delay*60*1000);
		    				if(notificationStop) break;
			    			messagesNotification = new SimpleMessagesNotification(
			    				(NotificationManager)getSystemService(NOTIFICATION_SERVICE),
			    				getIntent(),FsnetMobile.this,"Fsnet mobile","Fsnet mobile", "Unread messages : "+nbMessages
			    				);
		    				}
		        		} catch (InterruptedException e) {
		        					
		        		}
	    			}
		    		notificationStop=false;
		    		notificationOk=false;
	    		}
	    	};
	    	
	    	thread_for_notification_messages = new Thread(r);
	    	thread_for_notification_messages.start();
    	}
    	else new SimplePopUp(this,"Error","Configure your settings first");
    }
    
    
    
      public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId()) {
            case R.id.settings:
            	new SimpleToast(FsnetMobile.this, "Settings");
                setContentView(R.layout.settings); 
    	    	updateEditText(R.id.fsnetAdress,WEB_SERVICE_ADRESS);
                infosBDD.open();
                if(infosBDD.getLastElement()>0)
                {
        	    	InfoSettings newInfo = infosBDD.getInfoSettings(1);
        	    	updateEditText(R.id.fsnetAdress,newInfo.getServerUrl());
        	    	updateEditText(R.id.login,newInfo.getLogin());
        	    	updateEditText(R.id.password,newInfo.getPassword());
        	      	EditText et = (EditText)findViewById(R.id.time);
        	    	if(newInfo.getMinutes()>0)
        	    		et.setText(newInfo.getMinutes()+"");
        	    	else et.setText(""+10);
                }
            	infosBDD.close();
               return true;
           case R.id.exit:
        	   notificationStop=true;
               finish();
               return true;
           case R.id.home:
        	   new SimpleToast(FsnetMobile.this, "Home");
               setContentView(R.layout.main); 
               setStartOrStopButtonNotificationEnabled(!notificationOk);
               return true;
         }
         return false;
      }
	public void setMessagesNotification(SimpleMessagesNotification messagesNotification) {
		this.messagesNotification = messagesNotification;
	}
	public SimpleMessagesNotification getMessagesNotification() {
		return messagesNotification;
	}
	public void setMeetingsListAdapter(MeetingsList meetingsListAdapter) {
		this.meetingsListAdapter = meetingsListAdapter;
	}
	public MeetingsList getMeetingsListAdapter() {
		return meetingsListAdapter;
	}
	public void setAnnouncementsListAdapter(AnnouncementsList announcementsListAdapter) {
		this.announcementsListAdapter = announcementsListAdapter;
	}
	public AnnouncementsList getAnnouncementsListAdapter() {
		return announcementsListAdapter;
	}


}