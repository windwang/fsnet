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
 * @author alexandre thery this class is the main of android application
 */
public class FsnetMobile extends Activity {

	private static final String WEB_SERVICE_ADRESS = "http://217.108.57.93:8080/fsnetWeb/";
	private static final String MEETINGS_URL = "DisplayEvent.do?eventId=";
	private static final String MESSAGES_URL = "DisplayMessage.do?messageId=";
	private static final String ANNOUNCEMENTS_URL = "DisplayAnnounce.do?idAnnounce=";
	private InfoSettings info;
	private InfoSettingsBDD infosBDD;
	private String urlServer;
	private Thread thread_for_notification_messages;
	private SimpleMessagesNotification messagesNotification;
	private Boolean notificationOk = false;
	private Boolean notificationStop = false;
	private EventsList messagesListAdapter;
	private EventsList meetingsListAdapter;
	private EventsList announcementsListAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		info = new InfoSettings();
		infosBDD = new InfoSettingsBDD(this);
		setContentView(R.layout.main);
		setStartOrStopButtonNotificationEnabled(true);
		checkUserHasAConnection();
	}

	/**
	 * Check if the user has a connection network : wireless or 3G
	 * 
	 * @return true if he has one
	 */
	public boolean checkUserHasAConnection() {
		ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		State mobileNetwork = connec.getNetworkInfo(0).getState();
		State wirelessNetwork = connec.getNetworkInfo(1).getState();
		if (mobileNetwork == NetworkInfo.State.CONNECTED
				|| mobileNetwork == NetworkInfo.State.CONNECTING)
			return true;
		else if (wirelessNetwork == NetworkInfo.State.CONNECTED
				|| wirelessNetwork == NetworkInfo.State.CONNECTING)
			return true;
		else {
			new SimplePopUp(this, "NetWork problem",
					"Your network is not available, please turn the mobile/wireless network on");
			return false;
			
		}
	}

	/**
	 * Called when user click on test button of settings A pop up tell user if
	 * his login, password and fsnet url exists or not
	 */
	public void testConnectionToWebService(View view) {
		InfoSettings infos = new InfoSettings();
		infos.setServerUrl(getTextByEditText(R.id.fsnetAdress));
		infos.setLogin(getTextByEditText(R.id.login));
		infos.setPassword(getTextByEditText(R.id.password));
		try {
			TestConnectionResourcesProvider provider = new TestConnectionResourcesProvider(
					infos);
			boolean isUser = provider.isUser();
			if (!checkUserHasAConnection())
				;
			else if (isUser)
				new SimplePopUp(this, "Test",
						"Your configuration works ! (don't forget to save your settings)");
			else
				new SimplePopUp(this, "Test", "Configuration doesn't work");
		} catch (Exception e) {
			new SimplePopUp(this, "Error", "Configure your settings first");
		}
	}

	/**
	 * @return the number of unread messages
	 */
	public int getNbMessages() {
		try {
			infosBDD.open();
			MessagesResourcesProvider provider = new MessagesResourcesProvider(
					infosBDD.getInfoSettings(1));
			RestMessagesList messagesList = provider.unreadPrivateMessages();
			infosBDD.close();
			if (messagesList != null)
				return messagesList.size();
		} catch (Exception e) {
			return 0;
		}
		return 0;
	}

	/**
	 * Called when user clicks on announcements in the home page
	 */
	public void seeAnnouncements(View view) {
		try {
			infosBDD.open();
			if (infosBDD.getLastElement() != 0) {
				if (!checkUserHasAConnection())
					return;
				AnnouncementsResourcesProvider provider = new AnnouncementsResourcesProvider(
						infosBDD.getInfoSettings(1));
				urlServer = infosBDD.getInfoSettings(1).getServerUrl();
				RestAnnouncementList announcementList = provider
						.newAnnouncements();
				if (announcementList == null) {
					new SimplePopUp(this, "Error",
							"The webservice is not available or check your settings");
				} else {
					new SimpleToast(FsnetMobile.this, announcementList.size()
							+ " unread announcement(s)" + "\n" + "last "
							+ infosBDD.getInfoSettings(1).getMinutes()
							+ " minutes");
					String[] mStrings = new String[announcementList.size()];
					final int[] idMeetings = new int[announcementList.size()];
					for (int i = 0; i < announcementList.size(); i++) {
						mStrings[i] = new String("From : "
								+ announcementList.get(i).getFrom() + "\n"
								+ "Title : "
								+ announcementList.get(i).getTitle());
						idMeetings[i] = (announcementList.get(i).getMeetingId());
					}
					setContentView(R.layout.announcements);
					announcementsListAdapter = new EventsList(this,
							R.layout.rowlayoutannouncements,
							R.id.labelAnnouncements, mStrings);
					ListView listViewAnnouncements = (ListView) findViewById(R.id.listAnnouncements);
					listViewAnnouncements.setAdapter(announcementsListAdapter);
					listViewAnnouncements.setTextFilterEnabled(true);

					listViewAnnouncements
							.setOnItemClickListener(new OnItemClickListener() {
								@Override
								public void onItemClick(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
									Intent i = new Intent();
									i.setAction(Intent.ACTION_VIEW);
									i.setData(Uri.parse(urlServer
											+ ANNOUNCEMENTS_URL
											+ idMeetings[arg2]));
									startActivity(i);
								}

							});
				}
			} else
				new SimplePopUp(this, "Error", "Configure your settings first");
			infosBDD.close();
		} catch (Exception e) {
			new SimplePopUp(this, "Error", "Configure your settings first");
		}
	}

	/**
	 * Called when user clicks on meetings in the home page
	 */
	public void seeMeetings(View view) {
		try {
			infosBDD.open();
			if (infosBDD.getLastElement() != 0) {
				if (!checkUserHasAConnection())
					return;
				MeetingsResourcesProvider provider = new MeetingsResourcesProvider(
						infosBDD.getInfoSettings(1));
				urlServer = infosBDD.getInfoSettings(1).getServerUrl();
				RestMeetingList meetingsList = provider.newMeetings();
				if (meetingsList == null) {
					new SimplePopUp(this, "Error",
							"The webservice is not available or check your settings");
				} else {
					new SimpleToast(FsnetMobile.this, meetingsList.size()
							+ " unread meeting(s)" + "\n" + "last "
							+ infosBDD.getInfoSettings(1).getMinutes()
							+ " minutes");
					String[] mStrings = new String[meetingsList.size()];
					final int[] idMeetings = new int[meetingsList.size()];
					for (int i = 0; i < meetingsList.size(); i++) {
						mStrings[i] = new String("From : "
								+ meetingsList.get(i).getFrom() + "\n"
								+ "Title : " + meetingsList.get(i).getTitle());
						idMeetings[i] = (meetingsList.get(i).getMeetingId());
					}
					setContentView(R.layout.meetings);
					meetingsListAdapter = new EventsList(this,
							R.layout.rowlayoutmeetings, R.id.labelMeetings,
							mStrings);
					ListView listViewMeetings = (ListView) findViewById(R.id.listMeetings);
					listViewMeetings.setAdapter(meetingsListAdapter);
					listViewMeetings.setTextFilterEnabled(true);

					listViewMeetings
							.setOnItemClickListener(new OnItemClickListener() {
								@Override
								public void onItemClick(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
									Intent i = new Intent();
									i.setAction(Intent.ACTION_VIEW);
									i.setData(Uri.parse(urlServer
											+ MEETINGS_URL + idMeetings[arg2]));
									startActivity(i);
								}
							});
				}
			} else
				new SimplePopUp(this, "Error", "Configure your settings first");
			infosBDD.close();
		} catch (Exception e) {
			new SimplePopUp(this, "Error", "Configure your settings first");
		}
	}

	/**
	 * Called when user clicks on messages in the home page
	 */
	public void seeMessages(View view) {
		try {
			infosBDD.open();
			if (infosBDD.getLastElement() != 0) {
				if (!checkUserHasAConnection())
					return;
				MessagesResourcesProvider provider = new MessagesResourcesProvider(
						infosBDD.getInfoSettings(1));
				RestMessagesList messagesList = provider
						.unreadPrivateMessages();
				urlServer = infosBDD.getInfoSettings(1).getServerUrl();
				if (messagesList == null) {
					new SimplePopUp(this, "Error",
							"The webservice is not available or check your settings");
				} else {
					new SimpleToast(FsnetMobile.this, "Unread messages : "
							+ messagesList.size());
					String[] mStrings = new String[messagesList.size()];
					final int[] idMessages = new int[messagesList.size()];
					for (int i = 0; i < messagesList.size(); i++) {
						mStrings[i] = new String("From : "
								+ messagesList.get(i).getFrom() + "\n"
								+ "Subject : "
								+ messagesList.get(i).getSubject());
						idMessages[i] = (messagesList.get(i).getMessageId());
					}
					setContentView(R.layout.messages);
					messagesListAdapter = new EventsList(this,
							R.layout.rowlayoutmessages, R.id.label, mStrings);
					ListView listViewMessages = (ListView) findViewById(R.id.listMm);
					listViewMessages.setAdapter(messagesListAdapter);
					listViewMessages.setTextFilterEnabled(true);

					listViewMessages
							.setOnItemClickListener(new OnItemClickListener() {
								@Override
								public void onItemClick(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
									Intent i = new Intent();
									i.setAction(Intent.ACTION_VIEW);
									i.setData(Uri.parse(urlServer
											+ MESSAGES_URL + idMessages[arg2]));
									startActivity(i);
								}

							});
				}
			} else
				new SimplePopUp(this, "Error", "Configure your settings first");
			infosBDD.close();
		} catch (Exception e) {
			new SimplePopUp(this, "Error", "Configure your settings first");
		}
	}

	/**
	 * Update one edit text
	 * 
	 * @param idEditText
	 *            the id of editText
	 * @param newText
	 *            the new text
	 */
	public void updateEditText(int idEditText, String newText) {
		EditText et = (EditText) findViewById(idEditText);
		et.setText(newText);
	}

	/**
	 * Get the text of one editText
	 * 
	 * @param textId
	 *            the id of editText
	 * @return the text of the editText
	 */
	public String getTextByEditText(int textId) {
		EditText vw = (EditText) findViewById(textId);
		Spannable str = vw.getText();
		return str.toString();
	}

	/**
	 * Called when user wants to save his settings
	 */
	public void submitSettings(View view) {
		info.setServerUrl(getTextByEditText(R.id.fsnetAdress));
		info.setLogin(getTextByEditText(R.id.login));
		info.setPassword(getTextByEditText(R.id.password));
		if (getTextByEditText(R.id.time).matches("[0-9]+"))
			info.setMinutes(Integer.parseInt(getTextByEditText(R.id.time)));
		else
			info.setMinutes(10);
		if (info.getServerUrl() != null
				&& info.getServerUrl().matches("http://.*/fsnetWeb/")) {
			infosBDD.open();
			if (infosBDD.getLastElement() == 0)
				infosBDD.insertInfo(info);
			else {
				infosBDD.updateInfo(1, info);
			}
			if (notificationOk)
				new SimpleToast(FsnetMobile.this,
						"Configuration changed, Notification is stopped");
			else
				new SimpleToast(FsnetMobile.this, "Configuration changed");
			notificationStop = true;
			notificationOk = false;
			infosBDD.close();
		} else
			new SimplePopUp(FsnetMobile.this, "Url problem",
					"\nA good one is like\nhttp://.../fsnetWeb/");
	}

	/**
	 * Called when user wants to delete his settings
	 */
	public void deleteSettings(View view) {
		try {
			infosBDD.open();
			if (infosBDD.getLastElement() == 0)
				infosBDD.insertInfo(new InfoSettings("", "", 10,
						WEB_SERVICE_ADRESS));
			else {
				infosBDD.updateInfo(1, new InfoSettings("", "", 10,
						WEB_SERVICE_ADRESS));
			}
			setContentView(R.layout.settings);
			updateEditText(R.id.fsnetAdress, WEB_SERVICE_ADRESS);
			if (notificationOk)
				new SimpleToast(FsnetMobile.this,
						"Configuration deleted, Notification is stopped");
			else
				new SimpleToast(FsnetMobile.this, "Configuration deleted");
			notificationStop = true;
			notificationOk = false;
			infosBDD.close();
		} catch (Exception e) {

		}

	}

	/**
	 * Called when user starts or stops notification
	 * 
	 * @param startButtonValue
	 *            the value of start button
	 */
	public void setStartOrStopButtonNotificationEnabled(boolean startButtonValue) {
		Button buttonStartNotification = (Button) findViewById(R.id.startNotification);
		buttonStartNotification.setEnabled(startButtonValue);
		Button buttonStopNotification = (Button) findViewById(R.id.stopNotification);
		buttonStopNotification.setEnabled(!startButtonValue);
	}

	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.menu, menu);
		return true;
	}

	/**
	 * Called when user starts notification
	 */
	public void startNotification(View view) {
		try {
			infosBDD.open();
			if (infosBDD.getLastElement() != 0) {
				if (!checkUserHasAConnection())
					return;
				new SimpleToast(this, "Start notification");
				setStartOrStopButtonNotificationEnabled(false);
				Runnable r = new Runnable() {

					public void run() {
						int nbMessages = 0;
						int delay=10;
						while (true) {
							try {
									nbMessages = getNbMessages();
									notificationOk = true;
									if (notificationStop)
										break;
									infosBDD.open();
									delay = infosBDD.getInfoSettings(1)
												.getMinutes();
									infosBDD.close();
									Thread.sleep(delay * 60 * 1000);
									if (notificationStop)
										break;
									if ((nbMessages) > 0) {
									messagesNotification = new SimpleMessagesNotification(
											(NotificationManager) getSystemService(NOTIFICATION_SERVICE),
											getIntent(), FsnetMobile.this,
											"Fsnet mobile", "Fsnet mobile",
											"Unread messages : " + nbMessages);
									}

							} catch (InterruptedException e) {

							}
						}
						notificationStop = false;
						notificationOk = false;
					}
				};

				thread_for_notification_messages = new Thread(r);
				thread_for_notification_messages.start();
			} else {
				new SimplePopUp(this, "Error", "Configure your settings first");
			}
		} catch (Exception e) {
			new SimplePopUp(this, "Error", "Configure your settings first");
		}

		infosBDD.close();
	}

	/**
	 * Called when user stops notification
	 */
	public void stopNotification(View view) {
		new SimpleToast(this, "Stop notification");
		setStartOrStopButtonNotificationEnabled(true);
		if (notificationOk)
			notificationStop = true;
	}

	/**
	 * onOptionsItemSelected is called when user choose one item of menu
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.settings: {
			new SimpleToast(FsnetMobile.this, "Settings");
			setContentView(R.layout.settings);
			updateEditText(R.id.fsnetAdress, WEB_SERVICE_ADRESS);
			infosBDD.open();
			if (infosBDD.getLastElement() > 0) {
				InfoSettings newInfo = infosBDD.getInfoSettings(1);
				updateEditText(R.id.fsnetAdress, newInfo.getServerUrl());
				updateEditText(R.id.login, newInfo.getLogin());
				updateEditText(R.id.password, newInfo.getPassword());
				EditText et = (EditText) findViewById(R.id.time);
				if (newInfo.getMinutes() > 0)
					et.setText(newInfo.getMinutes() + "");
				else
					et.setText("" + 10);
			}
			infosBDD.close();
			return true;
		}
		case R.id.exit: {
			notificationStop = true;
			finish();
			return true;
		}
		case R.id.home: {
			new SimpleToast(FsnetMobile.this, "Home");
			setContentView(R.layout.main);
			setStartOrStopButtonNotificationEnabled(!notificationOk);
			return true;
		}
		}
		return false;
	}

	public void setMessagesNotification(
			SimpleMessagesNotification messagesNotification) {
		this.messagesNotification = messagesNotification;
	}

	public SimpleMessagesNotification getMessagesNotification() {
		return messagesNotification;
	}

	public void setMeetingsListAdapter(EventsList meetingsListAdapter) {
		this.meetingsListAdapter = meetingsListAdapter;
	}

	public EventsList getMeetingsListAdapter() {
		return meetingsListAdapter;
	}

	public void setAnnouncementsListAdapter(EventsList announcementsListAdapter) {
		this.announcementsListAdapter = announcementsListAdapter;
	}

	public EventsList getAnnouncementsListAdapter() {
		return announcementsListAdapter;
	}

}