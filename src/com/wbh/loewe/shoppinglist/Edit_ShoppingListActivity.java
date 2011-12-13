package com.wbh.loewe.shoppinglist;

import android.database.Cursor;
import android.os.Bundle;


/**
 * Expandable lists backed by a Simple Map-based adapter
 */
public class Edit_ShoppingListActivity extends ExpandableArticleListActivity 
{
	private int mListID;
	
	@Override
    public void onCreate(Bundle savedInstanceState, int aLayout) {
        super.onCreate(savedInstanceState, aLayout);
        
        Bundle lExtras = getIntent().getExtras();
		if (lExtras != null) {
			mListID = lExtras.getInt("ID");
		}
	}
	
	protected Cursor getGroupCursor() {
    	return mShoppinglistapp.getDBAdapter().fetchAllCategoriesOfList(mListID);
    }
    
    protected Cursor getChildCursor(int aGroupID) {
    	return mShoppinglistapp.getDBAdapter().fetchAllArticlesOfCategoryInList(mListID, aGroupID);
    }	
}
