package com.wbh.loewe.shoppinglist;

import com.wbh.loewe.shoppinglist.database.ShoppingListDatabase;

import android.database.Cursor;
import android.os.Bundle;


/**
 * Expandable lists backed by a Simple Map-based adapter
 */
public class Article_AdminActivity extends ExpandableArticleListActivity 
{
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mGroupItemLayout = android.R.layout.simple_expandable_list_item_1;
    	mChildItemLayout = android.R.layout.simple_expandable_list_item_2;
    	mGroupFrom = new String[] {ShoppingListDatabase.FIELD_NAME_NAME};
    	mGroupTo = new int[] {android.R.id.text1}; 
    	mChildFrom = new String[] {ShoppingListDatabase.FIELD_NAME_NAME}; 
    	mChildTo = new int[] {android.R.id.text1};
        
		fillData();
	}
	
	protected Cursor getGroupCursor() {
    	return mShoppinglistapp.getDBAdapter().fetchAllCategories();
    }
    
    protected Cursor getChildCursor(int aGroupID) {
    	return mShoppinglistapp.getDBAdapter().fetchAllArticlesOfCategory(aGroupID);
    }	
}
