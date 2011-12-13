package com.wbh.loewe.shoppinglist;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.Toast;

import com.wbh.loewe.shoppinglist.database.ShoppingListDatabase;


/**
 * Expandable lists backed by a Simple Map-based adapter
 */
public class Article_AdminActivity extends ExpandableArticleListActivity 
{
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mGroupItemLayout = R.layout.artdb_gui_group_row;
    	mChildItemLayout = R.layout.artdb_gui_child_row;
    	mGroupFrom = new String[] {ShoppingListDatabase.FIELD_NAME_NAME};
    	mGroupTo = new int[] {R.id.txt_kategorie}; 
    	mChildFrom = new String[] {ShoppingListDatabase.FIELD_NAME_NAME}; 
    	mChildTo = new int[] {R.id.txt_article};
    	
    	getExpandableListView().setOnGroupClickListener(gl);
        
		fillData();
	}
	
	private OnGroupClickListener gl = new OnGroupClickListener() {
		public boolean onGroupClick(ExpandableListView arg0, View arg1, int arg2, long arg3) {
			Toast.makeText(getBaseContext(), "Function not implemented yet!", Toast.LENGTH_LONG).show();
			return false;
		}
    };   
	
	protected Cursor getGroupCursor() {
    	return mShoppinglistapp.getDBAdapter().fetchAllCategories();
    }
    
    protected Cursor getChildCursor(int aGroupID) {
    	return mShoppinglistapp.getDBAdapter().fetchAllArticlesOfCategory(aGroupID);
    }	
}
