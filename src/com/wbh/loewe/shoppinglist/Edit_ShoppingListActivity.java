package com.wbh.loewe.shoppinglist;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.wbh.loewe.shoppinglist.cursoradapter.CustomCursorTreeAdapter;
import com.wbh.loewe.shoppinglist.cursoradapter.EditShoppingListCursorTreeAdapter;
import com.wbh.loewe.shoppinglist.database.ShoppingListDatabase;


/**
 * Expandable lists backed by a Simple Map-based adapter
 */
public class Edit_ShoppingListActivity extends ExpandableArticleListActivity 
{
	private int mListID;
	private EditShoppingListCursorTreeAdapter mEditShoppingListAdapter;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mGroupItemLayout = R.layout.screen4_editek_gui_group_row;
    	mChildItemLayout = R.layout.screen4_editek_gui_child_row;
    	mGroupFrom = new String[] {ShoppingListDatabase.FIELD_NAME_NAME};
    	mGroupTo = new int[] {R.id.txt_kategorie}; 
    	mChildFrom = new String[] {ShoppingListDatabase.FIELD_NAME_NAME}; 
    	mChildTo = new int[] {R.id.txt_article}; 
         
        Bundle lExtras = getIntent().getExtras();
		if (lExtras != null) {
			mListID = lExtras.getInt("ID");
			fillData();
		}
		
		//---the button is wired to an event handler---
        Button btn;
        
        btn = (Button)findViewById(R.id.btn_addArt);
        btn.setOnClickListener(btnAddArticleListener);
        
        btn = (Button)findViewById(R.id.btn_editName);
        btn.setOnClickListener(btnEditNameListener);
        
        btn = (Button)findViewById(R.id.btn_emptyList);
        btn.setOnClickListener(btnEmptyListListener);
	}
	
	@Override
    protected CustomCursorTreeAdapter createAdapter() {
		Cursor lGroupCursor = mShoppinglistapp.getDBAdapter().fetchAllCategoriesOfList(mListID);
    	mEditShoppingListAdapter = new EditShoppingListCursorTreeAdapter(
    								this, 
    								lGroupCursor, 
    								mGroupItemLayout, 
    								mGroupFrom, 
    								mGroupTo, 
    								mChildItemLayout, 
    								mChildFrom, 
    								mChildTo,
    								new OnGroupRowClickListener(),
    								new OnChildRowClickListener(),
    								mShoppinglistapp) {
    	    
    	     							@Override
    	     							protected Cursor getChildrenCursor(Cursor groupCursor) {
    	     								// DB-Abfrage um die Kindelemente darzustellen
    	     								int lGroupID = groupCursor.getInt(groupCursor.getColumnIndex(ShoppingListDatabase.FIELD_NAME_ID));
    	     								mChildCursor = mShoppinglistapp.getDBAdapter().fetchAllArticlesOfCategoryInList(mListID, lGroupID);
    	     								startManagingCursor(mChildCursor);
    	     								return mChildCursor;
    	     							}
    	     						};
    	return mEditShoppingListAdapter;
    }
    
    private OnClickListener btnAddArticleListener = new OnClickListener()
    {
    	public void onClick(View v) {
    		Intent lAddArticleActivity = new Intent(Edit_ShoppingListActivity.this, Add_Article_ListActivity.class);
    		lAddArticleActivity.putExtra("LISTID", mListID);
    		lAddArticleActivity.putExtra("LAYOUT", R.layout.gui_addarticle);
    		startActivity(lAddArticleActivity);
    	}
    };
    
    private OnClickListener btnEditNameListener = new OnClickListener()
    {
    	public void onClick(View v) {
    		// TODO
    		Toast.makeText(getBaseContext(), "Function not implemented yet!", Toast.LENGTH_LONG).show();
    		
    	}
    };
    
    private OnClickListener btnEmptyListListener = new OnClickListener()
    {
    	public void onClick(View v) {
    		// TODO
    		Toast.makeText(getBaseContext(), "Function not implemented yet!", Toast.LENGTH_LONG).show();
    	}
    };
}
