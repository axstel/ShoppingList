package com.wbh.loewe.shoppinglist;

//import de.GUI.dialog.GUI_DialogActivity;
//import de.GUI.dialog.R;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.wbh.loewe.shoppinglist.cursoradapter.ListCursorAdapter;
import com.wbh.loewe.shoppinglist.database.ShoppingListDatabase;
import com.wbh.loewe.shoppinglist.listitem.ListItem;

public class ShoppingListActivity extends ListActivity {

	static final int NEWLIST_DIALOG_ID = 0;
	protected EditText edt_newlist_name;
	protected Spinner edt_newlist_template;

	private Cursor mCursor;
	protected ShoppingListApplication mShoppinglistapp;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mShoppinglistapp = (ShoppingListApplication)getApplication();
        
        fillData();
                
        //---the button is wired to an event handler---
        Button btn;
        
        btn = (Button)findViewById(R.id.btn_new_list);
        btn.setOnClickListener(btnNewListListener);
        
        btn = (Button)findViewById(R.id.btn_article_admin);
        btn.setOnClickListener(btnArticleAdminListener);
        
        btn = (Button)findViewById(R.id.btn_closeapp);
        btn.setOnClickListener(btnCloseAppListener);
    }
    
    protected void fillData() {
    	mCursor = mShoppinglistapp.getDBAdapter().fetchAllDataSets(ShoppingListDatabase.TABLE_NAME_SHOPPPINGLIST);
    	startManagingCursor(mCursor);

    	String[] from = new String[] { ShoppingListDatabase.FIELD_NAME_NAME };
    	int[] to = new int[] { R.id.labelname };

    	// Now create an array adapter and set it to display using our row
    	ListCursorAdapter datasets = new ListCursorAdapter(this, R.layout.list_row, mCursor, from, to, new OnRowClickListener());
    	setListAdapter(datasets);
 
    	ListView list = this.getListView();
    	LinearLayout linear_emptylist = (LinearLayout)findViewById(R.id.linear_emptylist);
        if (mCursor.getCount() > 0) {
        	linear_emptylist.setVisibility(View.GONE);
        	list.setVisibility(View.VISIBLE);
        } else {
        	linear_emptylist.setVisibility(View.VISIBLE);
        	list.setVisibility(View.GONE);
        }
    }
 
    @Override
    protected Dialog onCreateDialog(int id) {
    	Dialog lDialog = null;
        switch(id) {
        	case NEWLIST_DIALOG_ID:
        		// set up Dialog
        		lDialog = new Dialog(ShoppingListActivity.this);
        		lDialog.setContentView(R.layout.neue_ek_2);
        		lDialog.setTitle("Neue Einkaufsliste");
        		lDialog.setCancelable(true);
        
        		edt_newlist_name = (EditText)lDialog.findViewById(R.id.txtfield_Name);
        		edt_newlist_template = (Spinner)lDialog.findViewById(R.id.spinnerVorlage);
    
        		Button btnOK = (Button)lDialog.findViewById(R.id.btnOK);
        		if (btnOK != null) {
        			btnOK.setOnClickListener(btn_NewList_OK);
        		}
    
        		Button btnCancel = (Button)lDialog.findViewById(R.id.btnCancel);
        		if (btnCancel != null) {
        			btnCancel.setOnClickListener(btn_NewList_Cancel);
        		}
        		break;
        	}
        return lDialog;
    }
   
    //---create an anonymous class to act as a button click listener---
    // NEUE EINKAUFSLISTE
    private OnClickListener btnNewListListener = new OnClickListener() {
    	public void onClick(View v) {
    		showDialog(NEWLIST_DIALOG_ID);
    		}
    };

    //---create an anonymous class to act as a button click listener---
    private OnClickListener btnArticleAdminListener = new OnClickListener() {
    	public void onClick(View v) {
    		showArticleAdminList();
    	}
    };
    
    //---create an anonymous class to act as a button click listener---
    private OnClickListener btnCloseAppListener = new OnClickListener() {
    	public void onClick(View v) {
            //Toast.makeText(getBaseContext(),
            // "DIALOGFELD 'Beenden' anzeigen!",
            // Toast.LENGTH_LONG).show();
            // System.exit(0); // Beendet die App
    
    
    		// Button 3 wird eigentlich zum beenden der APP benutzt.
    		// Zu Demonstrationszwecken werden durch Button 3 (Beenden) die Dialogfelder angezeigt
    		Intent intent2 = new Intent(ShoppingListActivity.this, GUI_DialogActivity.class);
    		startActivity(intent2);
    	}
    };
    
    //--- create an anonymous class to act as a button click listener ---
    private OnClickListener btn_NewList_OK = new OnClickListener() {
    	public void onClick(View v) {
    
    		if (edt_newlist_name != null) {
    			String lName = String.valueOf(edt_newlist_name.getText());
    			if (mShoppinglistapp.getDBAdapter().createShoppingList(lName) > -1) {
    				fillData();
    				if (mCursor.moveToPosition(mCursor.getCount() - 1)) {
    					int lColIdx = mCursor.getColumnIndex(ShoppingListDatabase.FIELD_NAME_ID);
    					showEditShoppingList(mCursor.getInt(lColIdx));
    				}
    			}
    		}
    		dismissDialog(NEWLIST_DIALOG_ID);
    	}
    };
    
    //--- create an anonymous class to act as a button click listener ---
    private OnClickListener btn_NewList_Cancel = new OnClickListener() {
    	public void onClick(View v) {
    		dismissDialog(NEWLIST_DIALOG_ID);
    	}
    };
   
    // Event wenn auf den Name oder den Button geklickt wird
    private class OnRowClickListener implements ListCursorAdapter.RowClickListener {
        public void OnRowClick(ListItem aListItem, int aAction) {
        	//Log.w(OnRowClickListener.class.getName(), "OnRowClick "+ aListItem.getID() +" "+ aListItem.getName() +" "+ aAction);
        	switch (aAction) {
        		case 0: showUseShoppingList(aListItem.getID()); break;
        		case 1: showEditShoppingList(aListItem.getID()); break;
        		case 2: deleteShoppingList(aListItem.getID()); break;
        	}
        }
    }
    
    private void showEditShoppingList(int aID) {
    	Intent lEditActivity = new Intent(ShoppingListActivity.this, Edit_ShoppingListActivity.class);
    	lEditActivity.putExtra("ID", aID);
    	lEditActivity.putExtra("LAYOUT", R.layout.gui_edit_ek);
    	startActivity(lEditActivity);
    }
    
    private void showArticleAdminList() {
    	Intent lEditActivity = new Intent(ShoppingListActivity.this, Article_AdminActivity.class);
    	lEditActivity.putExtra("LAYOUT", R.layout.gui_article_db);
    	startActivity(lEditActivity);
    }
    
    private void deleteShoppingList(int aID) {
    	mShoppinglistapp.getDBAdapter().deleteShoppingList(aID);
    	fillData();
    }
    
    private void showUseShoppingList(int aID) {
    	Intent lEditActivity = new Intent(ShoppingListActivity.this, Use_ShoppingListActivity.class);
    	lEditActivity.putExtra("ID", aID);
    	lEditActivity.putExtra("LAYOUT", R.layout.gui_show_ek_1);
    	startActivity(lEditActivity);
    }
}