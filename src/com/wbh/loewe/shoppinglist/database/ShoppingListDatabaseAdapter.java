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
	
	/* delete shoppinglist and return if succeed or not */
	public boolean  deleteShoppingList(long aID) {
		db.delete(ShoppingListDatabase.TABLE_NAME_SHOPPPINGLIST_ARTICLE, ShoppingListDatabase.FIELD_NAME_IDSHOPPINGLIST + "=" + aID, null);
		return db.delete(ShoppingListDatabase.TABLE_NAME_SHOPPPINGLIST, ShoppingListDatabase.FIELD_NAME_ID + "=" + aID, null) > 0;
	}
	
	/* Return a Cursor over the list of all categories of an shoppinglist */
	public Cursor fetchAllCategoriesOfList(int aListID) {
		String lQuery = " SELECT DISTINCT c.* FROM "+ ShoppingListDatabase.TABLE_NAME_CATEGORY +" c "+
						" LEFT JOIN "+ ShoppingListDatabase.TABLE_NAME_ARTICLE +" a ON c."+ ShoppingListDatabase.FIELD_NAME_ID +" = a."+ ShoppingListDatabase.FIELD_NAME_IDCATEGORY +
						" LEFT JOIN "+ ShoppingListDatabase.TABLE_NAME_SHOPPPINGLIST_ARTICLE +" sa ON a."+ ShoppingListDatabase.FIELD_NAME_ID +" = sa."+ ShoppingListDatabase.FIELD_NAME_IDARTICLE +
						" WHERE sa."+ ShoppingListDatabase.FIELD_NAME_IDSHOPPINGLIST +" = "+ aListID;
		//Log.w(ShoppingListDatabaseAdapter.class.getName(), lQuery);
		return db.rawQuery(lQuery, new String [] {});
	}
	
	/* Return a Cursor over the list of all articles of an category in a shoppinglist */
	public Cursor fetchAllArticlesOfCategoryInList(int aListID, int aCategoryID) {
		String lQuery = " SELECT DISTINCT a.*, sa."+ ShoppingListDatabase.FIELD_NAME_QUANTITY +" AS "+ ShoppingListDatabase.FIELD_NAME_QUANTITY +" FROM "+ ShoppingListDatabase.TABLE_NAME_ARTICLE +" a "+
						" LEFT JOIN "+ ShoppingListDatabase.TABLE_NAME_SHOPPPINGLIST_ARTICLE +" sa ON a."+ ShoppingListDatabase.FIELD_NAME_ID +" = sa."+ ShoppingListDatabase.FIELD_NAME_IDARTICLE +
						" WHERE sa."+ ShoppingListDatabase.FIELD_NAME_IDSHOPPINGLIST +" = "+ aListID +
						" AND a."+ ShoppingListDatabase.FIELD_NAME_IDCATEGORY +" = "+ aCategoryID;
		//Log.w(ShoppingListDatabaseAdapter.class.getName(), lQuery);
		return db.rawQuery(lQuery, new String [] {});
	}
	
	/* Return a Cursor over the list of all categories of articles in database */
	public Cursor fetchAllCategories() {
		String lQuery = " SELECT DISTINCT c.* FROM "+ ShoppingListDatabase.TABLE_NAME_CATEGORY +" c "+
						" LEFT JOIN "+ ShoppingListDatabase.TABLE_NAME_ARTICLE +" a ON c."+ ShoppingListDatabase.FIELD_NAME_ID +" = a."+ ShoppingListDatabase.FIELD_NAME_IDCATEGORY +
						" LEFT JOIN "+ ShoppingListDatabase.TABLE_NAME_SHOPPPINGLIST_ARTICLE +" sa ON a."+ ShoppingListDatabase.FIELD_NAME_ID +" = sa."+ ShoppingListDatabase.FIELD_NAME_IDARTICLE;
		//Log.w(ShoppingListDatabaseAdapter.class.getName(), lQuery);
		return db.rawQuery(lQuery, new String [] {});
	}
	
	/* Return a Cursor over the list of all articles of an category in database */
	public Cursor fetchAllArticlesOfCategory(int aCategoryID) {
		String lQuery = " SELECT DISTINCT a.* FROM "+ ShoppingListDatabase.TABLE_NAME_ARTICLE +" a "+
						" LEFT JOIN "+ ShoppingListDatabase.TABLE_NAME_SHOPPPINGLIST_ARTICLE +" sa ON a."+ ShoppingListDatabase.FIELD_NAME_ID +" = sa."+ ShoppingListDatabase.FIELD_NAME_IDARTICLE +
						" WHERE a."+ ShoppingListDatabase.FIELD_NAME_IDCATEGORY +" = "+ aCategoryID;
		//Log.w(ShoppingListDatabaseAdapter.class.getName(), lQuery);
		return db.rawQuery(lQuery, new String [] {});
	}
	
	/* Add a new article to shoppinglist, return the rowid of new dataset*/
	public long addArticleToShoppingList(int aListID, int aArticleID, int aQuantity) {
		ContentValues values = new ContentValues();
		values.put(ShoppingListDatabase.FIELD_NAME_IDSHOPPINGLIST, aListID);
		values.put(ShoppingListDatabase.FIELD_NAME_IDARTICLE, aArticleID);
		values.put(ShoppingListDatabase.FIELD_NAME_QUANTITY, aQuantity);
		
		return db.insert(ShoppingListDatabase.TABLE_NAME_SHOPPPINGLIST_ARTICLE, null, values);
	} 
	
	/* update article and return if succeed or not */
	public boolean  updateArticleQuantity(int aListID, int aArticleID, int aQuantity) {
		ContentValues lValues = new ContentValues();
		lValues.put(ShoppingListDatabase.FIELD_NAME_QUANTITY, aQuantity);
		return db.update(ShoppingListDatabase.TABLE_NAME_SHOPPPINGLIST_ARTICLE, lValues, ShoppingListDatabase.FIELD_NAME_IDSHOPPINGLIST + "=" + aListID +" AND "+ ShoppingListDatabase.FIELD_NAME_IDARTICLE + "=" + aArticleID, null) > 0;
	} 
}
