package com.wbh.loewe.shoppinglist.database;

import java.io.IOException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ShoppingListDatabaseAdapter {

	/* http://android-developers.de/tutorials-faqs/der-umgang-sqlite-404.html */
	/* http://www.vogella.de/articles/AndroidSQLite/article.html */
	/* http://android-developers.de/tutorials-faqs/der-umgang-der-sqlite-datenbank-414.html */
	/* http://www.codeproject.com/KB/android/AndroidSQLite.aspx */
	/* http://www.androidpit.de/de/android/forum/thread/413639/ExpandableListAdapter-und-SQLite */
	
	private Context context;
	private SQLiteDatabase db;
	private ShoppingListDatabaseHelper dbHelper;
	
	public ShoppingListDatabaseAdapter(Context context) {
		this.context = context;
	}

	public ShoppingListDatabaseAdapter open() throws SQLException {
		dbHelper = new ShoppingListDatabaseHelper(context);
		try {
			dbHelper.createDataBase();
			db = dbHelper.getWritableDatabase();
		} catch (IOException ioe) {
	 		throw new Error("Unable to create database");
	 	}
		return this;
	}

	public void close() {
		dbHelper.close();
	}
	
	/* Return a Cursor over the list of all DataSets from table of the parameter */
	public Cursor fetchAllDataSets(String table) {
		return db.rawQuery("SELECT * FROM "+ table, new String [] {});
	}
	
	/* Create a new article and return the rowid of new dataset */
	public long createArticle(ContentValues aValues) {
		return db.insert(ShoppingListDatabase.TABLE_NAME_ARTICLE, null, aValues);
	}
	
	/* update article and return if succeed or not */
	public boolean  updateArticle(long aID, ContentValues aValues) {
		return db.update(ShoppingListDatabase.TABLE_NAME_ARTICLE, aValues, ShoppingListDatabase.FIELD_NAME_ID + "=" + aID, null) > 0;
	} 
	
	/* delete article and return if succeed or not */
	public boolean deleteArticle(long aID) {
		return db.delete(ShoppingListDatabase.TABLE_NAME_ARTICLE, ShoppingListDatabase.FIELD_NAME_ID + "=" + aID, null) > 0;
	}
	
	/* Create a new shoppinglist and return the rowid of new dataset */
	public long createShoppingList(String aName) {
		ContentValues values = new ContentValues();
		values.put(ShoppingListDatabase.FIELD_NAME_NAME, aName);
		
		return db.insert(ShoppingListDatabase.TABLE_NAME_SHOPPPINGLIST, null, values);
	} 
	
	/* update shoppinglist and return if succeed or not */
	public boolean  updateShoppingList(long aID, String aName) {
		ContentValues values = new ContentValues();
		values.put(ShoppingListDatabase.FIELD_NAME_NAME, aName);
		
		return db.update(ShoppingListDatabase.TABLE_NAME_SHOPPPINGLIST, values, ShoppingListDatabase.FIELD_NAME_ID + "=" + aID, null) > 0;
	} 	
}
