package com.wbh.loewe.shoppinglist;

import java.util.Vector;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.wbh.loewe.shoppinglist.database.ShoppingListDatabase;


/**
 * Expandable lists backed by a Simple Map-based adapter
 */
public class Add_Article_ListActivity extends ExpandableArticleListActivity 
{
	private int mListID;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mGroupItemLayout = R.layout.artdb_gui_group_row;
    	mChildItemLayout = R.layout.artdb_gui_child_row;
    	mGroupFrom = new String[] {ShoppingListDatabase.FIELD_NAME_NAME};
    	mGroupTo = new int[] {R.id.txt_kategorie}; 
    	mChildFrom = new String[] {ShoppingListDatabase.FIELD_NAME_NAME}; 
    	mChildTo = new int[] {R.id.txt_article};
        
    	Bundle lExtras = getIntent().getExtras();
		if (lExtras != null) {
			mListID = lExtras.getInt("LISTID");
		}
		
		//---the button is wired to an event handler---
        Button btn;
        
        btn = (Button)findViewById(R.id.btn_addArt);
        btn.setOnClickListener(btnAddArticleListener);
        
        btn = (Button)findViewById(R.id.btn_newKat);
        btn.setOnClickListener(btnNewCategoryListener);
        
        btn = (Button)findViewById(R.id.btn_save);
        btn.setOnClickListener(btnSaveListener);

		fillData();
	}
	
	protected Cursor getGroupCursor() {
    	return mShoppinglistapp.getDBAdapter().fetchAllCategories();
    }
    
    protected Cursor getChildCursor(int aGroupID) {
    	return mShoppinglistapp.getDBAdapter().fetchAllArticlesOfCategory(aGroupID);
    }	
    
    private OnClickListener btnAddArticleListener = new OnClickListener()
    {
    	public void onClick(View v) {
    		// TODO
    		Toast.makeText(getBaseContext(), "Function not implemented yet!", Toast.LENGTH_LONG).show();
    		
    	}
    };
    
    private OnClickListener btnNewCategoryListener = new OnClickListener()
    {
    	public void onClick(View v) {
    		// TODO
    		Toast.makeText(getBaseContext(), "Function not implemented yet!", Toast.LENGTH_LONG).show();
    		
    	}
    };
    
    private OnClickListener btnSaveListener = new OnClickListener()
    {
    	public void onClick(View v) {
    		Vector<Integer> lIDs = mAdapter.getSelectedIDs();
    		for (int i = 0; i < lIDs.size(); i++) {
    			mShoppinglistapp.getDBAdapter().addArticleToShoppingList(mListID, lIDs.get(i), 0);
    		}
    		finish();
    	}
    };
    
    public boolean onGroupClick (ExpandableListView parent, View v, int groupPosition, long id) {
    	// TODO alle Artikel markieren
    	//Toast.makeText(getBaseContext(), "onGroupClick"+ groupPosition +" "+ id, Toast.LENGTH_LONG).show();
    	return true;
    }
    
    @Override
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
    	//Toast.makeText(getBaseContext(), "onChildClick "+ groupPosition +" "+ childPosition +" "+ id, Toast.LENGTH_LONG).show();
    	if (mChildCursor.moveToPosition(childPosition)) {
    		int lColIdx = mChildCursor.getColumnIndex(ShoppingListDatabase.FIELD_NAME_ID);
    		mAdapter.setSelectedItem(v, groupPosition, childPosition, mChildCursor.getInt(lColIdx));
    	} else {
    		Log.w("Add_Article_ListActivity.onChildClick", "moveToPosition "+ childPosition +" failed");
    	}
    	return true;
    }
}
