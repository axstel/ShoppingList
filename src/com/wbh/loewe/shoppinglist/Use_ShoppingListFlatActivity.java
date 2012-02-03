package com.wbh.loewe.shoppinglist;

import android.app.Dialog;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.wbh.loewe.shoppinglist.cursoradapter.UseShoppingListCursorAdapter;
import com.wbh.loewe.shoppinglist.database.ShoppingListDatabase;
import com.wbh.loewe.shoppinglist.listitem.ListItem;
import com.wbh.loewe.shoppinglist.listitem.UseListChildListItem;


/**
 * Expandable lists backed by a Simple Map-based adapter
 */
public class Use_ShoppingListFlatActivity extends ListActivity 
{
	private Cursor mCursor;
	protected ShoppingListApplication mShoppinglistapp;
	private int mListID;
	private UseShoppingListCursorAdapter mUseShoppingListAdapter;
	private boolean mShowOnlyOpenArticles;
	private Button mBtnCategory;
	private Button mBtnArticle;
	private Button mBtnResetSelection;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.gui_use_shoppinglist_flat);
        mShoppinglistapp = (ShoppingListApplication)getApplication();
        
    	this.mShowOnlyOpenArticles = false;
         
        Bundle lExtras = getIntent().getExtras();
		if (lExtras != null) {
			mListID = lExtras.getInt("ID");
			fillData();
		}
		
		//---the button is wired to an event handler---
        mBtnCategory = (Button)findViewById(R.id.btnShowCategories);
        mBtnCategory.setOnClickListener(OnBtnCategoryClick);
        
        mBtnArticle = (Button)findViewById(R.id.btnUnSelectedArticles);
        mBtnArticle.setOnClickListener(OnBtnArticleClick);
        
        mBtnResetSelection = (Button)findViewById(R.id.btnReset);
        mBtnResetSelection.setOnClickListener(OnBtnResetSelectionClick);
	}
    
	protected void fillData() {
		// -1 bedeutet das die Kategorie ignoriert wird
		if (mShowOnlyOpenArticles) {
			mCursor = mShoppinglistapp.getDBAdapter().fetchAllArticlesOfCategoryInList(mListID, -1, false);
		} else {
			mCursor = mShoppinglistapp.getDBAdapter().fetchAllArticlesOfCategoryInList(mListID, -1, true);
		}
    	startManagingCursor(mCursor);

    	String[] from = new String[] {ShoppingListDatabase.FIELD_NAME_NAME, ShoppingListDatabase.FIELD_NAME_QUANTITY, "QUANTITYUNITNAME"}; 
    	int[] to = new int[] {R.id.txt_article, R.id.edittxt_menge, R.id.txt_einheit};
    	
    	// Now create an array adapter and set it to display using our row
    	mUseShoppingListAdapter = new UseShoppingListCursorAdapter(this, R.layout.gui_use_shoppinglist_articlerow, mCursor, from, to, new OnRowClickListener());
    	
    	setListAdapter(mUseShoppingListAdapter);
    }
	
	// Event wenn auf eine Zeile geklickt wird
    private class OnRowClickListener implements UseShoppingListCursorAdapter.RowClickListener {
        public void OnRowClick(View aView, ListItem aListItem, int aAction) {
        	//Log.w(OnRowClickListener.class.getName(), "OnRowClick "+ aListItem.getID() +" "+ aListItem.getName() +" "+ aAction);
        	if (aListItem != null) {
    			mUseShoppingListAdapter.setSelectedItem(aView, aListItem.getID());
    			mShoppinglistapp.getDBAdapter().updateArticleSelected(aListItem.getID(), ((UseListChildListItem)aListItem).getSelected());
        	} else {
        		Log.e("Use_ShoppingListFlatActivity.OnChildRowClick", "aListItem is not assigned");
        	}
        }
    }
    
    private OnClickListener OnBtnCategoryClick = new OnClickListener()
    {
    	public void onClick(View v) {
    		finish(); // Activity schließen und zurück zum aufrufenden Screen
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
    
    public void refreshView() {
    	mUseShoppingListAdapter.resetChildItemList();
    	fillData();
    }
    
    @Override
    protected Dialog onCreateDialog(int id) {
    	Dialog lDialog = null;
        switch(id) {
        	case DialogConsts.RESETLIST_DIALOG_ID :
        	{
        		// set up Dialog
        		lDialog = new Dialog(Use_ShoppingListFlatActivity.this);
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
