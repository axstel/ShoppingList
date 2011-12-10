package com.wbh.loewe.shoppinglist.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ShoppingListDatabase{
	
	// Constants for tablenames and fields
	public static final String TABLE_NAME_SHOPPPINGLIST = "shoppinglist";
	public static final String TABLE_NAME_ARTICLE = "article";
	public static final String TABLE_NAME_CATEGORY = "category";
	public static final String TABLE_NAME_QUANTITYUNIT = "quantityunit";
	public static final String TABLE_NAME_SHOPPPINGLIST_ARTICLE = "shoppinglist_article";
	
	public static final String FIELD_NAME_ID = "_id";
	public static final String FIELD_NAME_NAME = "name";
	public static final String FIELD_NAME_IDQUANTITYUNIT = "id_quantityunit";
	public static final String FIELD_NAME_IDCATEGORY = "id_category";
	public static final String FIELD_NAME_IDSHOPPINGLIST = "id_shoppinglist";
	public static final String FIELD_NAME_IDARTICLE = "id_article";
	public static final String FIELD_NAME_QUANTITY = "quantity";
	
	// Database creation SQL statement
	private static final String SHOPPINGLIST_CREATE = "create table "+ TABLE_NAME_SHOPPPINGLIST
                                         			+ " ("+ FIELD_NAME_ID +" integer primary key autoincrement, "
			                                        + FIELD_NAME_NAME +" text not null); ";
	
    private static final String ARTICLE_CREATE = "create table "+ TABLE_NAME_ARTICLE
                                     	   	   + " ("+ FIELD_NAME_ID +" integer primary key autoincrement, "
			                                   + FIELD_NAME_NAME +" text not null, "
                                     		   + FIELD_NAME_IDQUANTITYUNIT +" integer not null, "
			                                   + FIELD_NAME_IDCATEGORY +" integer not null); ";
    
    private static final String CATEGORY_CREATE = "create table "+ TABLE_NAME_CATEGORY
                                     	        + " ("+ FIELD_NAME_ID +" integer primary key autoincrement, "
			                                    + FIELD_NAME_NAME +" text not null); ";
    
    private static final String QUANTITYUNIT_CREATE = "create table "+ TABLE_NAME_QUANTITYUNIT
											    	+ " ("+ FIELD_NAME_ID +" integer primary key autoincrement, "
												    + FIELD_NAME_NAME +" text not null); ";
    
	private static final String SHOPPINGLIST_ARTICLE_CREATE = "create table "+ TABLE_NAME_SHOPPPINGLIST_ARTICLE
												            + " ("+ FIELD_NAME_ID +" integer primary key autoincrement, "
												            + FIELD_NAME_IDSHOPPINGLIST +" integer not null, "
												            + FIELD_NAME_IDARTICLE +" integer not null, "
												            + FIELD_NAME_QUANTITY +" real); ";

	public static void onCreate(SQLiteDatabase database) {
		Log.w(ShoppingListDatabase.class.getName(), "Creating new database");
		
		database.execSQL(SHOPPINGLIST_CREATE);
		database.execSQL(ARTICLE_CREATE);
		database.execSQL(QUANTITYUNIT_CREATE);
		database.execSQL(CATEGORY_CREATE);
		database.execSQL(SHOPPINGLIST_ARTICLE_CREATE);
		
		ShoppingListDefaultData.insertDefaultData(database);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.w(ShoppingListDatabase.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		
		database.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_SHOPPPINGLIST);
		database.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_ARTICLE);
		database.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_CATEGORY);
		database.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_QUANTITYUNIT);
		database.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_SHOPPPINGLIST_ARTICLE);
		onCreate(database);
	}
}
