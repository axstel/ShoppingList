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
import android.widget.Toast;

import com.wbh.loewe.shoppinglist.cursoradapter.CustomCursorTreeAdapter;
import com.wbh.loewe.shoppinglist.cursoradapter.EditShoppingListCursorTreeAdapter;
import com.wbh.loewe.shoppinglist.database.ShoppingListDatabase;
import com.wbh.loewe.shoppinglist.listitem.ChildListItem;
import com.wbh.loewe.shoppinglist.listitem.GroupListItem;
import com.wbh.loewe.shoppinglist.listitem.ListItem;


/**
 * Expandable lists backed by a Simple Map-based adapter
 */
public class Edit_ShoppingListActivity extends ExpandableArticleListActivity 
{
	static final int ADDARTICLE_REQUESTCODE = 1;

	private int mListID;
	private EditShoppingListCursorTreeAdapter mEditShoppingListAdapter;
	private ChildListItem mDelChildListItem;
	private GroupListItem mDelGroupListItem;
	
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
									new OnRowActionClickListener(),
									mShoppinglistapp) {
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
		checkEmptyList();
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
    		showDialog(DialogConsts.EMPTYLIST_DIALOG_ID);
    	}
    };
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADDARTICLE_REQUESTCODE) {
            if (resultCode == RESULT_OK) {
            	refreshView();
            }
        }
    }
    
    @Override
    protected Dialog onCreateDialog(int id) {
    	Dialog lDialog = null;
        switch(id) {
        	case DialogConsts.EMPTYLIST_DIALOG_ID:
        	{
        		// set up Dialog
        		lDialog = new Dialog(Edit_ShoppingListActivity.this);
        		lDialog.setContentView(R.layout.empty_list_2);
        		lDialog.setTitle("Einkaufsliste leeren");
        		lDialog.setCancelable(true);
        
        		Button btnOK = (Button)lDialog.findViewById(R.id.btn_emptyList_OK);
        		if (btnOK != null) {
        			btnOK.setOnClickListener(btn_EmptyList_OK);
        		}
    
        		Button btnCancel = (Button)lDialog.findViewById(R.id.btn_emptyList_Cancel);
        		if (btnCancel != null) {
        			btnCancel.setOnClickListener(btn_EmptyList_Cancel);
        		}
        		break;
        	}
        	case DialogConsts.DELETECATEGORY_DIALOG_ID:
        	{
        		// set up Dialog
        		lDialog = new Dialog(Edit_ShoppingListActivity.this);
        		lDialog.setContentView(R.layout.del_kat_2);
        		lDialog.setTitle("Kategorie löschen");
        		lDialog.setCancelable(true);
        
        		Button btnOK = (Button)lDialog.findViewById(R.id.btn_del_kat_OK);
        		if (btnOK != null) {
        			btnOK.setOnClickListener(btn_DelCategory_OK);
        		}
    
        		Button btnCancel = (Button)lDialog.findViewById(R.id.btn_del_kat_Cancel);
        		if (btnCancel != null) {
        			btnCancel.setOnClickListener(btn_DelCategory_Cancel);
        		}
        		break;
        	}
        	case DialogConsts.DELETEARTICLE_DIALOG_ID:
        	{
        		// set up Dialog
        		lDialog = new Dialog(Edit_ShoppingListActivity.this);
        		lDialog.setContentView(R.layout.del_art_2);
        		lDialog.setTitle("Artikel löschen");
        		lDialog.setCancelable(true);
        
        		Button btnOK = (Button)lDialog.findViewById(R.id.btnOK);
        		if (btnOK != null) {
        			btnOK.setOnClickListener(btn_DelArticle_OK);
        		}
    
        		Button btnCancel = (Button)lDialog.findViewById(R.id.btnCancel);
        		if (btnCancel != null) {
        			btnCancel.setOnClickListener(btn_DelArticle_Cancel);
        		}
        		break;
        	}
        }
        
        return lDialog;
    }
    
    //--- create an anonymous class to act as a button click listener ---
    private OnClickListener btn_EmptyList_OK = new OnClickListener() {
    	public void onClick(View v) {
    		mShoppinglistapp.getDBAdapter().removeAllArticlesFromList(mListID);
    		refreshView();
    		removeDialog(DialogConsts.EMPTYLIST_DIALOG_ID);
    	}
    };
    
    //--- create an anonymous class to act as a button click listener ---
    private OnClickListener btn_EmptyList_Cancel = new OnClickListener() {
    	public void onClick(View v) {
    		removeDialog(DialogConsts.EMPTYLIST_DIALOG_ID);
    	}
    };
    
    //--- create an anonymous class to act as a button click listener ---
    private OnClickListener btn_DelCategory_OK = new OnClickListener() {
    	public void onClick(View v) {
    		if (mDelGroupListItem != null) {
    			mShoppinglistapp.getDBAdapter().deleteCategoryFromShoppingList(mListID, mDelGroupListItem.getID());
    			removeGroupFromExpandableListView(mDelGroupListItem);
    			mDelChildListItem = null;
    			mDelGroupListItem = null;
    		} else {
    			Log.e("Edit_ShoppingListActivity.btn_DelCategory_OK", "mDelGroupListItem not assigned");
    		}
    		removeDialog(DialogConsts.DELETECATEGORY_DIALOG_ID);
    	}
    };
    
    //--- create an anonymous class to act as a button click listener ---
    private OnClickListener btn_DelCategory_Cancel = new OnClickListener() {
    	public void onClick(View v) {
    		removeDialog(DialogConsts.DELETECATEGORY_DIALOG_ID);
    	}
    };
    
    //--- create an anonymous class to act as a button click listener ---
    private OnClickListener btn_DelArticle_OK = new OnClickListener() {
    	public void onClick(View aView) {
    		if (mDelChildListItem != null) {
    			mShoppinglistapp.getDBAdapter().deleteArticleFromShoppingList(mDelChildListItem.getID());
    			mEditShoppingListAdapter.removeChild(mDelChildListItem, true);
    			if (!childItemsExists(mDelChildListItem.getGroupPos())) {
    				GroupListItem lGroupListItem = (GroupListItem)aView.getTag();
    				removeGroupFromExpandableListView(lGroupListItem);
    			}
    			mDelGroupListItem = null;
    			mDelChildListItem = null;
    		} else {
    			Log.e("Edit_ShoppingListActivity.btn_DelArticle_OK", "mDelChildListItem not assigned");
    		}
    		removeDialog(DialogConsts.DELETEARTICLE_DIALOG_ID);
    	}
    };
    
    //--- create an anonymous class to act as a button click listener ---
    private OnClickListener btn_DelArticle_Cancel = new OnClickListener() {
    	public void onClick(View v) {
    		removeDialog(DialogConsts.DELETEARTICLE_DIALOG_ID);
    	}
    };
    
    // Event wenn auf Buttons geklickt wird
    private class OnRowActionClickListener implements CustomCursorTreeAdapter.RowActionClickListener {
		public void OnRowClick(ListItem aListItem, int aAction) {
			switch (aAction) {
				case 1: {
					mDelChildListItem = (ChildListItem)aListItem;
					mDelGroupListItem = null;
					showDialog(DialogConsts.DELETEARTICLE_DIALOG_ID); break;
				}
				case 2: {
					mDelChildListItem = null;
					mDelGroupListItem = (GroupListItem)aListItem;
					showDialog(DialogConsts.DELETECATEGORY_DIALOG_ID); break;
				}
			}
		}
    }
   
    private boolean childItemsExists(int aGroupPos) {
    	return true;
    }
    
    private void removeGroupFromExpandableListView(GroupListItem aListItem) {
    	Cursor lGroupCursor = mShoppinglistapp.getDBAdapter().fetchAllCategoriesOfList(mListID);
		mEditShoppingListAdapter.setGroupCursor(lGroupCursor);
		mEditShoppingListAdapter.removeGroup(aListItem);
		checkEmptyList();
    }
    
    private void checkEmptyList() {
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
}
