package fr.univartois.ili.fsnet.mobile.services.client.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * 
 * @author alexandre thery
 *
 */
public class FsnetDatabase extends SQLiteOpenHelper{

		 
		private static final String SETTINGS_TABLES = "settings_table";
	 
		private static final String CREATE_BDD = "CREATE TABLE " + SETTINGS_TABLES + " ("
		+ "id INTEGER PRIMARY KEY AUTOINCREMENT, login TEXT,password TEXT,serverUrl TEXT,minutes INTEGER);";
	 
		public FsnetDatabase(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
		}
	 
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_BDD);
		}
	 
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE " + SETTINGS_TABLES + ";");
			onCreate(db);
		}
	
}
