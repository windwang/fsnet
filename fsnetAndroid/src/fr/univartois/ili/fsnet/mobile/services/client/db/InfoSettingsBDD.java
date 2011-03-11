package fr.univartois.ili.fsnet.mobile.services.client.db;

import fr.univartois.ili.fsnet.mobile.services.client.data.InfoSettings;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
/**
 * 
 * @author alexandre thery
 *
 */
public class InfoSettingsBDD {

	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "settings_info";
 
	private static final String SETTINGS_TABLES = "settings_table";
	private static final String ID = "id";
	private static final String LOGIN = "login";
	private static final String PASSWORD = "password";
	private static final String MINUTES = "minutes";
	private static final String SERVERURL = "serverUrl";
	private SQLiteDatabase bdd;
 
	private FsnetDatabase fsnetDatabase;
 
	public InfoSettingsBDD(Context context){
		fsnetDatabase = new FsnetDatabase(context, NOM_BDD, null, VERSION_BDD);
	}
 
	public void open(){
		bdd = fsnetDatabase.getWritableDatabase();
	}
 
	public void close(){
		bdd.close();
	}
 
	public SQLiteDatabase getBDD(){
		return bdd;
	}
 
	public long insertInfo(InfoSettings info){

		ContentValues values = new ContentValues();
		values.put(LOGIN, info.getLogin());
		values.put(PASSWORD, info.getPassword());
		values.put(SERVERURL, info.getServerUrl());
		values.put(MINUTES, info.getMinutes());
		return bdd.insert(SETTINGS_TABLES, null, values);
	}
 
	public int updateInfo(int id,InfoSettings info){
		ContentValues values = new ContentValues();
		values.put(LOGIN, info.getLogin());
		values.put(PASSWORD, info.getPassword());
		values.put(SERVERURL, info.getServerUrl());
		values.put(MINUTES, info.getMinutes());
		return bdd.update(SETTINGS_TABLES, values, ID + " = " +id, null);
	}
 
	public int removeInfoWithID(int id){
		return bdd.delete(SETTINGS_TABLES, ID + " = " +id, null);
	}

	public InfoSettings getInfoSettings(int id){
		Cursor c = bdd.query(SETTINGS_TABLES, new String[] {LOGIN, PASSWORD, SERVERURL,MINUTES}, ID+" = " + id, null, null, null, null);
		return cursorToInfo(c);
	}
	public int getLastElement()
	{
		Cursor c = bdd.query(SETTINGS_TABLES, new String[] {LOGIN, PASSWORD, SERVERURL,MINUTES}, null, null, null, null, null);
		if(c!=null)
			return c.getCount();
		return 0;
	}
	public InfoSettings cursorToInfo(Cursor c){
		if (c.getCount() == 0)
			return null;
		
		c.moveToFirst();
		InfoSettings infos = new InfoSettings();
		infos.setLogin(c.getString(0));
		infos.setPassword(c.getString(1));
		infos.setServerUrl(c.getString(2));
		infos.setMinutes(Integer.parseInt(c.getString(3)));
		c.close();
 
		return infos;
	}

}
