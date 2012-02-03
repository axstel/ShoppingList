package com.wbh.loewe.shoppinglist;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.wbh.loewe.shoppinglist.cursoradapter.CustomCursorTreeAdapter;
import com.wbh.loewe.shoppinglist.cursoradapter.UseShoppingListCursorTreeAdapter;
import com.wbh.loewe.shoppinglist.database.ShoppingListDatabase;
import com.wbh.loewe.shoppinglist.listitem.ChildListItem;
import com.wbh.loewe.shoppinglist.listitem.UseListChildListItem;


/**
 * Expandable lists backed by a Simple Map-based adapter
 */
public class Use_ShoppingListActivity extends ExpandableArticleListActivity 
{
	
	private int mListID;
	private UseShoppingListCursorTreeAdapter mUseShoppingListAdapter;
	private boolean mShowOnlyOpenArticles;
	private Button mBtnCategory;
	private Button mBtnArticle;
	private Button mBtnResetSelection;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mGroupItemLayout = R.layout.gui_use_shoppinglist_categoryrow;
    	mChildItemLayout = R.layout.gui_use_shoppinglist_articlerow;
    	mGroupFrom = new String[] {ShoppingListDatabase.FIELD_NAME_NAME};
    	mGroupTo = new int[] {R.id.txt_kategorie}; 
    	mChildFrom = new String[] {ShoppingListDatabase.FIELD_NAME_NAME, ShoppingListDatabase.FIELD_NAME_QUANTITY, "QUANTITYUNITNAME"}; 
    	mChildTo = new int[] {R.id.txt_article, R.id.edittxt_menge, R.id.txt_einheit};
    	this.mShowOnlyOpenArticles = false;
         
        Bundle lExtras = getIntent().getExtras();
		if (lExtras != null) {
			mListID = lExtras.getInt("ID");
			fillData();
		}
		
		//---the button is wired to an event handler---
        mBtnCategory = (Button)findViewById(R.id.btnHideCategory);
        mBtnCategory.setOnClickListener(OnBtnCategoryClick);
        
        mBtnArticle = (Button)findViewById(R.id.btnUnSelectedArticles);
        mBtnArticle.setOnClickListener(OnBtnArticleClick);
        
        mBtnResetSelection = (Button)findViewById(R.id.btnReset);
        mBtnResetSelection.setOnClickListener(OnBtnResetSelectionClick);
	}
	
	@Override
    protected CustomCursorTreeAdapter createAdapter() {
		Cursor lGroupCursor;
		if (mShowOnlyOpenArticles) {
			lGroupCursor = mShoppinglistapp.getDBAdapter().fetchAllCategoriesOfListUnSelected(mListID);
		} else {
			lGroupCursor = mShoppinglistapp.getDBAdapter().fetchAllCategoriesOfList(mListID);
		}
		mUseShoppingListAdapter = new UseShoppingListCursorTreeAdapter(
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
    										Cursor lChildCursor;
    										if (mShowOnlyOpenArticles) {
    											lChildCursor = mShoppinglistapp.getDBAdapter().fetchAllArticlesOfCategoryInList(mListID, lGroupID, false);
    										} else {
    											lChildCursor = mShoppinglistapp.getDBAdapter().fetchAllArticlesOfCategoryInList(mListID, lGroupID, true);
    										}
    										startManagingCursor(lChildCursor);
    										return lChildCursor;
    									}
    								};
		
    	return mUseShoppingListAdapter;
    }
    
	@Override
	public void fillData() {
		super.fillData();
		ExpandableListView list = this.getExpandableListView();
    	LinearLayout linear_emptylist = (LinearLayout)findViewById(R.id.linear_emptylist);
        if (mUseShoppingListAdapter.getCursor().getCount() > 0) {
        	linear_emptylist.setVisibility(View.GONE);
        	list.setVisibility(View.VISIBLE);
        } else {
        	linear_emptylist.setVisibility(View.VISIBLE);
        	list.setVisibility(View.GONE);
        }
	}
	
	@Override
	protected void OnChildRowClick(View aView, ChildListItem aListItem) {
		if (aListItem != null) {
			mUseShoppingListAdapter.setSelectedItem(aView, aListItem.getID());
			mShoppinglistapp.getDBAdapter().updateArticleSelected(aListItem.getID(), ((UseListChildListItem)aListItem).getSelected());
    	} else {
    		Log.e("Use_ShoppingListActivity.OnChildRowClick", "aListItem is not assigned");
    	}
    }
	
	private OnClickListener OnBtnCategoryClick = new OnClickListener()
    {
    	public void onClick(View v) {
    		Intent lFlatActivity = new Intent(Use_ShoppingListActivity.this, Use_ShoppingListFlatActivity.class);
    		lFlatActivity.putExtra("ID", mListID);
    	    startActivity(lFlatActivity);
    	}
    };
    
    private OnClickListener OnBtnArticleClick = new OnClickListener()
    {
    	public void onClick(View v) {
    		mShowOnlyOpenArticles = !mShowOnlyOpenArticles;
    		if (mShowOnlyOpenArticles) {
    			mBtnArticle.setText(R.string.btn_alleArt);
    		} else {
    			mBtnArticle.setText(R.string.btn_offeneArtikel);
    		}
    		refreshView();
    	}
    };
    
    private OnClickListener OnBtnResetSelectionClick = new OnClickListener()
    {
    	public void onClick(View v) {
    		showDialog(DialogConsts.RESETLIST_DIALOG_ID);
    	}
    };
    
    @Override
    protected Dialog onCreateDialog(int id) {
    	Dialog lDialog = null;
        switch(id) {
        	case DialogConsts.RESETLIST_DIALOG_ID :
        	{
        		// set up Dialog
        		lDialog = new Dialog(Use_ShoppingListActivity.this);
        		lDialog.setContentView(R.layout.ek_zuruecksetzen_2);
        		lDialog.setTitle("Einkaufsliste zurücksetzen");
        		lDialog.setCancelable(true);
        
        		Button btnOK = (Button)lDialog.findViewById(R.id.btn_erledigt_OK);
        		if (btnOK != null) {
        			btnOK.setOnClickListener(btnResetOK);
        		}
    
        		Button btnCancel = (Button)lDialog.findViewById(R.id.btn_erledigt_Cancel);
        		if (btnCancel != null) {
        			btnCancel.setOnClickListener(btnResetCancel);
        		}
        		break;
        	}
        }
        return lDialog;
    }
    
    //--- create an anonymous class to act as a button click listener ---
    private OnClickListener btnResetOK = new OnClickListener() {
    	public void onClick(View aView) {
    		mShoppinglistapp.getDBAdapter().updateArticleUnSelected(mListID);
    		refreshView();
    		removeDialog(DialogConsts.RESETLIST_DIALOG_ID);
    	}
    };
    
    //--- create an anonymous class to act as a button click listener ---
    private OnClickListener btnResetCancel = new OnClickListener() {
    	public void onClick(View v) {
    		removeDialog(DialogConsts.RESETLIST_DIALOG_ID);
    	}
    };

}
