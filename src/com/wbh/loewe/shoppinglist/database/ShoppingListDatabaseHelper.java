package com.wbh.loewe.shoppinglist.database;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class ShoppingListDatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "shoppinglistdatabase";
	private static final int DATABASE_VERSION = 1;
	private final Context mContext;

	public ShoppingListDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		mContext = context;
	}
	
	// Method is called during creation of the database
	@Override
	public void onCreate(SQLiteDatabase database) {
		//ShoppingListDatabase.onCreate(database);
	}

	// Method is called during an upgrade of the database,
	// e.g. if you increase the database version
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		//ShoppingListDatabase.onUpgrade(database, oldVersion, newVersion);
	}
	
    // Creates a empty database on the system and rewrites it with your own database.
	public void createDataBase() throws IOException {
		
    	boolean dbExist = checkDataBase();
    	
    	if(!dbExist) {
 
    		// By calling this method and empty database will be created into the default system path
            // of your application so we are gonna be able to overwrite that database with our database.
        	this.getReadableDatabase();
        	try {
    			copyDataBase();
    		} catch (IOException e) {
        		throw new Error("Error copying database");
        	}
    	}
    }
 
	// Check if the database already exist to avoid re-copying the file each time you open the application.
    // @return true if it exists, false if it doesn't
    private boolean checkDataBase() {
 
    	SQLiteDatabase checkDB = null;
 
    	try {
    		String myPath = "/data/data/"+ mContext.getPackageName() +"/databases/" + DATABASE_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    	} catch (SQLiteException e) {
    		//database does't exist yet.
    	}
 
    	if(checkDB != null){
    		checkDB.close();
    	}
    	return checkDB != null ? true : false;
    }
 
    // Copies your database from your local assets-folder to the just created empty database in the
    // system folder, from where it can be accessed and handled.
    // This is done by transfering bytestream.
    private void copyDataBase() throws IOException {
 
    	//Open your local db as the input stream
    	InputStream myInput = mContext.getAssets().open(DATABASE_NAME);
 
    	// Path to the just created empty db
    	String outFileName = "/data/data/"+ mContext.getPackageName() +"/databases/"+ DATABASE_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
    }
}
