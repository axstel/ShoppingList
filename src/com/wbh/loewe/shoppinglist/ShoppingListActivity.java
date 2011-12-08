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
import android.widget.LinearLayout;
import android.widget.ListView;

import com.wbh.loewe.shoppinglist.database.ShoppingListDatabase;

public class ShoppingListActivity extends ListActivity {
	
	private Cursor cursor;
	protected ShoppingListApplication shoppinglistapp;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        shoppinglistapp = (ShoppingListApplication)getApplication();
        
        fillData();
                
        //---the button is wired to an event handler---
        Button btn1 = (Button)findViewById(R.id.button1);
        btn1.setOnClickListener(btnListener1);
        
        Button btn2 = (Button)findViewById(R.id.button2);
        btn2.setOnClickListener(btnListener2);
        
        Button btn3 = (Button)findViewById(R.id.button3);
        btn3.setOnClickListener(btnListener3);
    }
    
    protected void fillData() {
    	cursor = shoppinglistapp.getDBAdapter().fetchAllDataSets(ShoppingListDatabase.TABLE_NAME_SHOPPPINGLIST);
 		startManagingCursor(cursor);

 	    String[] from = new String[] { ShoppingListDatabase.FIELD_NAME_ID, ShoppingListDatabase.FIELD_NAME_NAME };
 		int[] to = new int[] { R.id.labelid, R.id.labelname };

 		// Now create an array adapter and set it to display using our row
 		ListCursorAdapter datasets = new ListCursorAdapter(this, R.layout.list_row, cursor, from, to);
 		setListAdapter(datasets);
 		
 		ListView list = this.getListView();
 		LinearLayout linear_emptylist = (LinearLayout)findViewById(R.id.linear_emptylist);
        if (cursor.getCount() > 0) {
        	linear_emptylist.setVisibility(View.GONE);
        	list.setVisibility(View.VISIBLE);
        } else {
        	linear_emptylist.setVisibility(View.VISIBLE);
        	list.setVisibility(View.GONE);
        }
 	}
 
   
    //---create an anonymous class to act as a button click listener---
    // NEUE EINKAUFSLISTE
    private OnClickListener btnListener1 = new OnClickListener()
    {
    	public void onClick(View v)
        {                        
            // set up Dialog
    		Dialog dialog = new Dialog(ShoppingListActivity.this);
    		dialog.setContentView(R.layout.neue_ek_2);
    		dialog.setTitle("Neue Einkaufsliste");
    		dialog.setCancelable(true);
    		
    		// set up Text
    		dialog.show();
    		
    		
		}

    };

    
    
    //---create an anonymous class to act as a button click listener---
    private OnClickListener btnListener2 = new OnClickListener()
    {
    	public void onClick(View v)
        {                        
            /*Toast.makeText(getBaseContext(), 
                    "DIALOGFELD 'Artikel verwalten' starten!", 
                    Toast.LENGTH_LONG).show();*/	
            Intent intent = new Intent(ShoppingListActivity.this, GUI_ExpandActivity.class);	
            startActivity(intent);																
		}

    };
    
    //---create an anonymous class to act as a button click listener---
    private OnClickListener btnListener3 = new OnClickListener()
    {
    	public void onClick(View v)
        {                        
            //Toast.makeText(getBaseContext(), 
            //        "DIALOGFELD 'Beenden' anzeigen!", 
            //        Toast.LENGTH_LONG).show();	
            //	System.exit(0);	// Beendet die App
    		
    		
    		// Button 3 wird eigentlich zum beenden der APP benutzt.
    		// Zu Demonstrationszwecken werden durch Button 3 (Beenden) die Dialogfelder angezeigt
    		Intent intent2 = new Intent(ShoppingListActivity.this, GUI_DialogActivity.class);
    		startActivity(intent2);
		}

    };
    
   
}