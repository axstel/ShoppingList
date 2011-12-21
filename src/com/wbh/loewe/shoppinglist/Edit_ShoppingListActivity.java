package com.wbh.loewe.shoppinglist;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wbh.loewe.shoppinglist.cursoradapter.CustomCursorTreeAdapter;
import com.wbh.loewe.shoppinglist.cursoradapter.EditShoppingListCursorTreeAdapter;
import com.wbh.loewe.shoppinglist.database.ShoppingListDatabase;


/**
 * Expandable lists backed by a Simple Map-based adapter
 */
public class Edit_ShoppingListActivity extends ExpandableArticleListActivity 
{
	
	static final int ADDARTICLE_REQUESTCODE = 1;

	private int mListID;
	private EditShoppingListCursorTreeAdapter mEditShoppingListAdapter;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mGroupItemLayout = R.layout.screen4_editek_gui_group_row;
    	mChildItemLayout = R.layout.screen4_editek_gui_child_row;
    	mGroupFrom = new String[] {ShoppingListDatabase.FIELD_NAME_NAME};
    	mGroupTo = new int[] {R.id.txt_kategorie}; 
    	mChildFrom = new String[] {ShoppingListDatabase.FIELD_NAME_NAME, "QUANTITYUNITNAME"}; 
    	mChildTo = new int[] {R.id.txt_article, R.id.txt_einheit};
         
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
									mShoppinglistapp,
									mListID) {
    									@Override
    									protected Cursor getChildrenCursor(Cursor groupCursor) {
    										// DB-Abfrage um die Kindelemente darzustellen
    										int lGroupID = groupCursor.getInt(groupCursor.getColumnIndex(ShoppingListDatabase.FIELD_NAME_ID));
    										Cursor lChildCursor = mShoppinglistapp.getDBAdapter().fetchAllArticlesOfCategoryInList(mListID, lGroupID);
    										startManagingCursor(lChildCursor);
    										return lChildCursor;
    									}
    								};
    	return mEditShoppingListAdapter;
    }
    
	@Override
	public void fillData() {
		super.fillData();
		ExpandableListView list = this.getExpandableListView();
    	LinearLayout linear_emptylist = (LinearLayout)findViewById(R.id.linear_emptylist);
        if (mEditShoppingListAdapter.getCursor().getCount() > 0) {
        	linear_emptylist.setVisibility(View.GONE);
        	list.setVisibility(View.VISIBLE);
        } else {
        	linear_emptylist.setVisibility(View.VISIBLE);
        	list.setVisibility(View.GONE);
        }
	}
	
    private OnClickListener btnAddArticleListener = new OnClickListener()
    {
    	public void onClick(View v) {
    		Intent lAddArticleActivity = new Intent(Edit_ShoppingListActivity.this, Add_Article_ListActivity.class);
    		lAddArticleActivity.putExtra("LISTID", mListID);
    		lAddArticleActivity.putExtra("LAYOUT", R.layout.gui_addarticle);
    		startActivityForResult(lAddArticleActivity, ADDARTICLE_REQUESTCODE);
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
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADDARTICLE_REQUESTCODE) {
            if (resultCode == RESULT_OK) {
            	mEditShoppingListAdapter.resetChildItemList();
            	fillData();
            }
        }
    }

}
