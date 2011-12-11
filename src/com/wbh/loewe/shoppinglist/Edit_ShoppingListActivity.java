package com.wbh.loewe.shoppinglist;

import com.wbh.loewe.shoppinglist.database.ShoppingListDatabase;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.SimpleCursorTreeAdapter;


/**
 * Expandable lists backed by a Simple Map-based adapter
 */
public class Edit_ShoppingListActivity extends ExpandableListActivity 
{
	
	private Cursor mGroupCursor;
	private Cursor mChildCursor;
	private ExpandableListView mListView;
	private SimpleCursorTreeAdapter mAdapter;
	private	ShoppingListApplication mShoppinglistapp;
	private int mListID;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mShoppinglistapp = (ShoppingListApplication)getApplication();
        mListView = getExpandableListView();
        
        Bundle lExtras = getIntent().getExtras();
		if (lExtras != null) {
			mListID = lExtras.getInt("ID");
			fillData();
		}
	}
    
    private void fillData() {
    	mGroupCursor = mShoppinglistapp.getDBAdapter().fetchAllCategoriesOfList(mListID);
    	startManagingCursor(mGroupCursor);
    	       
    	// Adapter für ExpandableList erstellen
    	mAdapter = new SimpleCursorTreeAdapter(
    			this, 
    			mGroupCursor, 
    	        android.R.layout.simple_expandable_list_item_1, 
    	        new String[] {ShoppingListDatabase.FIELD_NAME_NAME}, 
    	        new int[] {android.R.id.text1}, 
    	        android.R.layout.simple_expandable_list_item_2, 
    	        new String[] {ShoppingListDatabase.FIELD_NAME_NAME}, 
    	        new int[] {android.R.id.text1}) {
    	          
    	        @Override
    	        protected Cursor getChildrenCursor(Cursor groupCursor) {
    	        	// DB-Abfrage um die Kindelemente darzustellen
    	            mChildCursor = mShoppinglistapp.getDBAdapter().fetchAllArticlesOfCategory(mListID, mGroupCursor.getInt(mGroupCursor.getColumnIndex(ShoppingListDatabase.FIELD_NAME_ID)));
    	            startManagingCursor(mChildCursor);
    	            return mChildCursor;
    	          }
    	        };
    	        
    	        // Der ExpandableListView den Adapter zuweisen
    	        mListView.setAdapter(mAdapter);
    }

	
}
