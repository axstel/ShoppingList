package com.wbh.loewe.shoppinglist;

import android.database.Cursor;


/**
 * Expandable lists backed by a Simple Map-based adapter
 */
public class Article_AdminActivity extends ExpandableArticleListActivity 
{
	protected Cursor getGroupCursor() {
    	return mShoppinglistapp.getDBAdapter().fetchAllCategories();
    }
    
    protected Cursor getChildCursor(int aGroupID) {
    	return mShoppinglistapp.getDBAdapter().fetchAllArticlesOfCategory(aGroupID);
    }	
}
