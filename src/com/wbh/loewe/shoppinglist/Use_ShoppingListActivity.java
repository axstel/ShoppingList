package com.wbh.loewe.shoppinglist;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

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
	private boolean mHideCategories;
	private Button mBtnCategory;
	private Button mBtnArticle;
	private Button mBtnResetSelection;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mGroupItemLayout = R.layout.screen6_showek_gui_group_row;
    	mChildItemLayout = R.layout.screen6_showek_gui_child_row;
    	mGroupFrom = new String[] {ShoppingListDatabase.FIELD_NAME_NAME};
    	mGroupTo = new int[] {R.id.txt_kategorie}; 
    	mChildFrom = new String[] {ShoppingListDatabase.FIELD_NAME_NAME, ShoppingListDatabase.FIELD_NAME_QUANTITY, "QUANTITYUNITNAME"}; 
    	mChildTo = new int[] {R.id.txt_article, R.id.edittxt_menge, R.id.txt_einheit};
    	this.mShowOnlyOpenArticles = false;
    	this.mHideCategories = false;
         
        Bundle lExtras = getIntent().getExtras();
		if (lExtras != null) {
			mListID = lExtras.getInt("ID");
			fillData();
		}
		
		//---the button is wired to an event handler---
        mBtnCategory = (Button)findViewById(R.id.btn_hideKat);
        mBtnCategory.setOnClickListener(OnBtnCategoryClick);
        
        mBtnArticle = (Button)findViewById(R.id.btn_offeneArtikel);
        mBtnArticle.setOnClickListener(OnBtnArticleClick);
        
        mBtnResetSelection = (Button)findViewById(R.id.btn_zuruecksetzen);
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
    											lChildCursor = mShoppinglistapp.getDBAdapter().fetchAllArticlesOfCategoryInListUnSelected(mListID, lGroupID);
    										} else {
    											lChildCursor = mShoppinglistapp.getDBAdapter().fetchAllArticlesOfCategoryInList(mListID, lGroupID);
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
			mUseShoppingListAdapter.setSelectedItem(aView, aListItem.getGroupPos(), aListItem.getChildPos());
			mShoppinglistapp.getDBAdapter().updateArticleSelected(mListID, aListItem.getID(), ((UseListChildListItem)aListItem).getSelected());
    	} else {
    		Log.e("Use_ShoppingListActivity.OnChildRowClick", "aListItem is not assigned");
    	}
    }
	
	private OnClickListener OnBtnCategoryClick = new OnClickListener()
    {
    	public void onClick(View v) {
    		mHideCategories = !mHideCategories;
    		if (mHideCategories) {
    			mBtnCategory.setText(R.string.btn_showKat);
    		} else {
    			mBtnCategory.setText(R.string.btn_hideKat);
    		}
    		Toast.makeText(getBaseContext(), "Function not implemented yet!", Toast.LENGTH_SHORT).show();
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
    		mShoppinglistapp.getDBAdapter().updateArticleUnSelected(mListID);
    		refreshView();
    	}
    };

}
