package com.wbh.loewe.shoppinglist;

//import de.GUI.dialog.GUI_DialogActivity;
//import de.GUI.dialog.R;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;

public class ShoppingListActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
                
        //---the button is wired to an event handler---
        Button btn1 = (Button)findViewById(R.id.button1);
        btn1.setOnClickListener(btnListener1);
        
        Button btn2 = (Button)findViewById(R.id.button2);
        btn2.setOnClickListener(btnListener2);
        
        Button btn3 = (Button)findViewById(R.id.button3);
        btn3.setOnClickListener(btnListener3);
        
   

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