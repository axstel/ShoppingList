package com.wbh.loewe.shoppinglist.database;

import android.database.sqlite.SQLiteDatabase;

public class ShoppingListDefaultData {

	private static final String quantityunits[] = 
		{"Becher",
		 "Beutel",
		 "Bund",
		 "Dose",
		 "Flasche",
		 "kg",
		 "Liter",
		 "Packung",
		 "Stück"};
	
	private static final String categories[] = 
		{"Gemüse",
		 "Getränke",
		 "Gewürze",
		 "Hygieneartikel",
		 "Konserven",
		 "Kräuter",
		 "Lebensmittel",
		 "Molkerei",
		 "Obst",
		 "Reinigungsmittel",
		 "Verpackungen"};
		
	private static final String articles[][] = 
		{{"Artischocken", "kg", "Gemüse"},
	     {"Aubergine", "kg", "Gemüse"},
	     {"Avocado", "kg", "Gemüse"},
	     {"Blumenkohl", "kg", "Gemüse"},
	     {"Bohnen", "kg", "Gemüse"},
	     {"Broccoli", "kg", "Gemüse"},
	     {"Champignons - braun", "kg", "Gemüse"},
	     {"Champignons - hell", "kg", "Gemüse"},
	     {"Charlotten", "kg", "Gemüse"},
	     {"Chicoree", "kg", "Gemüse"}};
	
	static private void insertQuantityunits(SQLiteDatabase aDatabase) {
		for (int i = 0; i <= quantityunits.length - 1; ++i) {
			aDatabase.execSQL(" insert into "+ ShoppingListDatabase.TABLE_NAME_QUANTITYUNIT +" ("+ ShoppingListDatabase.FIELD_NAME_NAME +") values('"+ quantityunits[i] +"'); ");
		}
	};
	
	static private void insertCategories(SQLiteDatabase aDatabase) {
		for (int i = 0; i <= categories.length - 1; ++i) {
			aDatabase.execSQL(" insert into "+ ShoppingListDatabase.TABLE_NAME_CATEGORY + " ("+ ShoppingListDatabase.FIELD_NAME_NAME +") values('"+ categories[i] +"'); ");
		}
	}
	
	static private void insertArticles(SQLiteDatabase aDatabase) {
		for (int i = 0; i <= articles.length - 1; ++i) {
			//Log.w(ShoppingListDatabase.class.getName(), articles[i][0] +";"+ articles[i][1] +";"+ articles[i][2]);
			aDatabase.execSQL(" insert into "+ ShoppingListDatabase.TABLE_NAME_ARTICLE + " ("+ ShoppingListDatabase.FIELD_NAME_NAME +", "+ ShoppingListDatabase.FIELD_NAME_IDQUANTITYUNIT +", "+ ShoppingListDatabase.FIELD_NAME_IDCATEGORY +") "+
			                  " select '"+ articles[i][0] +"', qu."+ ShoppingListDatabase.FIELD_NAME_ID +", c."+ ShoppingListDatabase.FIELD_NAME_ID +" from "+ ShoppingListDatabase.TABLE_NAME_QUANTITYUNIT +" qu cross join "+ ShoppingListDatabase.TABLE_NAME_CATEGORY +" c "+
			                  " where qu."+ ShoppingListDatabase.FIELD_NAME_NAME +" = '"+ articles[i][1] +"' and c."+ ShoppingListDatabase.FIELD_NAME_NAME +" = '"+ articles[i][2] +"' ");
		};
	};
	
	static public void insertDefaultData(SQLiteDatabase aDatabase) {
		// the order is important
		insertQuantityunits(aDatabase);
		insertCategories(aDatabase);
		insertArticles(aDatabase);
	}
	
}
