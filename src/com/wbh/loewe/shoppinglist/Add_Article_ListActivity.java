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

import com.wbh.loewe.shoppinglist.cursoradapter.AddArticleCursorTreeAdapter;
import com.wbh.loewe.shoppinglist.cursoradapter.CustomCursorTreeAdapter;
import com.wbh.loewe.shoppinglist.database.ShoppingListDatabase;
import com.wbh.loewe.shoppinglist.listitem.AddArticleChildListItem;
import com.wbh.loewe.shoppinglist.listitem.ChildListItem;


/**
 * Expandable lists backed by a Simple Map-based adapter
 */
public class Add_Article_ListActivity extends ExpandableArticleListActivity 
{
	private int mListID;
	private AddArticleCursorTreeAdapter mAddArticleAdapter;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mGroupItemLayout = R.layout.screen5_add_art_gui_group_row;
    	mChildItemLayout = R.layout.screen5_add_art_gui_child_row;
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
    
    @Override
    protected CustomCursorTreeAdapter createAdapter() {
    	Cursor lGroupCursor = mShoppinglistapp.getDBAdapter().fetchAllCategories();
    	mAddArticleAdapter = new AddArticleCursorTreeAdapter(
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
    										Cursor lChildCursor = mShoppinglistapp.getDBAdapter().fetchAllArticlesOfCategory(lGroupID);
    										startManagingCursor(lChildCursor);
    										return lChildCursor;
    									}
    								};
    	return mAddArticleAdapter;
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
    		Vector<AddArticleChildListItem> lItems = mAddArticleAdapter.getSelectedItems();
    		for (int i = 0; i < lItems.size(); i++) {
    			AddArticleChildListItem lItem = lItems.get(i);
    			mShoppinglistapp.getDBAdapter().addArticleToShoppingList(mListID, lItem.getID(), lItem.getQuantity());
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
    protected void OnChildRowClick(View aView, ChildListItem aListItem) {
    	if (aListItem != null) {
    		mAddArticleAdapter.setSelectedItem(aView, aListItem.getGroupPos(), aListItem.getChildPos());
    	} else {
    		Log.e("Add_Article_ListActivity.OnChildRowClick", "aListItem is not assigned");
    	}
    }
}
