package com.wbh.loewe.shoppinglist;

import android.database.Cursor;
import android.os.Bundle;


/**
 * Expandable lists backed by a Simple Map-based adapter
 */
public class Article_AdminActivity extends ExpandableArticleListActivity 
{
	@Override
    public void onCreate(Bundle savedInstanceState, int aLayout) {
        super.onCreate(savedInstanceState, aLayout);
		fillData();
	}
	
	protected Cursor getGroupCursor() {
    	return mShoppinglistapp.getDBAdapter().fetchAllCategories();
    }
    
    protected Cursor getChildCursor(int aGroupID) {
    	return mShoppinglistapp.getDBAdapter().fetchAllArticlesOfCategory(aGroupID);
    }	
}
