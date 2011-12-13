package com.wbh.loewe.shoppinglist;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
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
        
        mGroupItemLayout = android.R.layout.simple_expandable_list_item_1;
    	mChildItemLayout = android.R.layout.simple_expandable_list_item_2;
    	mGroupFrom = new String[] {ShoppingListDatabase.FIELD_NAME_NAME};
    	mGroupTo = new int[] {android.R.id.text1}; 
    	mChildFrom = new String[] {ShoppingListDatabase.FIELD_NAME_NAME}; 
    	mChildTo = new int[] {android.R.id.text1};
        
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
        
        getExpandableListView().setChoiceMode(ExpandableListView.CHOICE_MODE_MULTIPLE);
        getExpandableListView().setItemsCanFocus(false);

    	
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
    		// TODO
    		Toast.makeText(getBaseContext(), "Function not implemented yet!", Toast.LENGTH_LONG).show();
    		
    	}
    };
    
    public boolean onGroupClick (ExpandableListView parent, View v, int groupPosition, long id) {
    	Toast.makeText(getBaseContext(), "onGroupClick"+ groupPosition +" "+ id, Toast.LENGTH_LONG).show();
    	return true;
    }
    
    private static final int WHITE = 0xffffffff;
    private static final int RED   = 0xffbc0000;
    
    @Override
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
    	Toast.makeText(getBaseContext(), "onChildClick "+ groupPosition +" "+ childPosition +" "+ id, Toast.LENGTH_LONG).show();
    	//setSelectedChild(groupPosition, childPosition, true);
    	if(((TextView)v).isSelected() == false){
            
            ((TextView)v).setSelected(true);
            ((TextView)v).setTextColor(RED);
       }
       else {
           ((TextView)v).setSelected(false);
           ((TextView)v).setTextColor(WHITE);
       }
    	return true;
    }
}
