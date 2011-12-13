package com.wbh.loewe.shoppinglist;



import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
//import android.widget.Toast;


public class GUI_DialogActivity extends Activity {
	

	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialoge);       
               
        
        // Button NEUE EINKAUFSLISTE
        //---the button is wired to an event handler---
        Button btn4 = (Button)findViewById(R.id.btn_neueEK);
        btn4.setOnClickListener(btnKlickListener4);
        
        // Button EINKAUFSLISTE LÖSCHEN
        //Button btn5 = (Button)findViewById(R.id.btn_delEK);
        //btn5.setOnClickListener(btnKlickListener5);
        
        // Button KATEGORIE BEARBEITEN
        //Button btn6 = (Button)findViewById(R.id.btn_editKat);
        //btn6.setOnClickListener(btnKlickListener6);
        
        // Button KATEGORIE LÖSCHEN
        //Button btn7 = (Button)findViewById(R.id.btn_delKat);
        //btn7.setOnClickListener(btnKlickListener7);
        
        // Button NEUE KATEGORIE
        Button btn8 = (Button)findViewById(R.id.btn_newKat);
        btn8.setOnClickListener(btnKlickListener8);
        
        // Button ARTIKEL BEARBEITEN
        Button btn9 = (Button)findViewById(R.id.btn_editArt);
        btn9.setOnClickListener(btnKlickListener9);
        
        // Button ARTIKEL LÖSCHEN
        Button btn10 = (Button)findViewById(R.id.btn_delArt);
        btn10.setOnClickListener(btnKlickListener10);
        
        // Button NEUER ARTIKEL
        Button btn11 = (Button)findViewById(R.id.btn_newArt);
        btn11.setOnClickListener(btnKlickListener11);
        
        // Button NAME ÄNDERN
        Button btn12 = (Button)findViewById(R.id.btn_editName);
        btn12.setOnClickListener(btnKlickListener12);

        // Button EINKAUFSLISTE LEEREN
        Button btn13 = (Button)findViewById(R.id.btn_emptyList);
        btn13.setOnClickListener(btnKlickListener13);
        
        // Button EINKAUFSLISTE ZURÜCKSETZEN
        Button btn14 = (Button)findViewById(R.id.btn_EK_zuruecksetzen);
        btn14.setOnClickListener(btnKlickListener14);

        /* Noch nicht in main.xml verknüpft da nicht scrollbar
        // Button EINKAUFSLISTE ERLEDIGT
        Button btn12 = (Button)findViewById(R.id.btn_EK_erledigt);
        btn12.setOnClickListener(btnKlickListener12);*/

       
    }
    
    //---create an anonymous class to act as a button click listener---
    // NEUE EINKAUFSLISTE
    private OnClickListener btnKlickListener4 = new OnClickListener()
    {
    	public void onClick(View v)
        {                        
            // set up Dialog
    		Dialog dialog = new Dialog(GUI_DialogActivity.this);
    		dialog.setContentView(R.layout.neue_ek_2);
    		dialog.setTitle("Neue Einkaufsliste");
    		dialog.setCancelable(true);
    		
    		// set up Text
    		dialog.show();
    		
		}

    };
    
    //---create an anonymous class to act as a button click listener---
    // EINKAUFSLISTE LÖSCHEN
    /*private OnClickListener btnKlickListener5 = new OnClickListener()
    {
    	public void onClick(View v)
        {                        
            // set up Dialog
    		Dialog dialog = new Dialog(GUI_DialogActivity.this);
    		dialog.setContentView(R.layout.del_ek_2);
    		dialog.setTitle("Einkaufsliste löschen");
    		dialog.setCancelable(true);
    		
    		// set up Text
    		dialog.show();
		}

    };*/
    
    //---create an anonymous class to act as a button click listener---
    // KATEGORIE BEARBEITEN
    /*private OnClickListener btnKlickListener6 = new OnClickListener()
    {
    	public void onClick(View v)
        {                        
            // set up Dialog
    		Dialog dialog = new Dialog(GUI_DialogActivity.this);
    		dialog.setContentView(R.layout.edit_kat_2);
    		dialog.setTitle("Kategorie bearbeiten");
    		dialog.setCancelable(true);
    		
    		// set up Text
    		dialog.show();
		}

    };*/
    
    //---create an anonymous class to act as a button click listener---
    // KATEGORIE LÖSCHEN
    /*private OnClickListener btnKlickListener7 = new OnClickListener()
    {
    	public void onClick(View v)
        {                        
            // set up Dialog
    		Dialog dialog = new Dialog(GUI_DialogActivity.this);
    		dialog.setContentView(R.layout.del_kat_2);
    		dialog.setTitle("Kategorie löschen");
    		dialog.setCancelable(true);
    		
    		// set up Text
    		dialog.show();
		}

    };*/
    
    //---create an anonymous class to act as a button click listener---
    // NEUE KATEGORIE
    private OnClickListener btnKlickListener8 = new OnClickListener()
    {
    	public void onClick(View v)
        {                        
            // set up Dialog
    		Dialog dialog = new Dialog(GUI_DialogActivity.this);
    		dialog.setContentView(R.layout.new_kat_2);
    		dialog.setTitle("Neue Kategorie");
    		dialog.setCancelable(true);
    		
    		// set up Text
    		dialog.show();
		}
    	
    
    };

	//---create an anonymous class to act as a button click listener---
    // ARTIKEL BEARBEITEN
    private OnClickListener btnKlickListener9 = new OnClickListener()
    {
    	public void onClick(View v)
        {                        
            // set up Dialog
    		Dialog dialog = new Dialog(GUI_DialogActivity.this);
    		dialog.setContentView(R.layout.edit_art_2);
    		dialog.setTitle("Artikel bearbeiten");
    		dialog.setCancelable(true);
    		
    		// set up Text
    		dialog.show();
    		   		
		}
    

    };
    

	//---create an anonymous class to act as a button click listener---
    // ARTIKEL LÖSCHEN
    private OnClickListener btnKlickListener10 = new OnClickListener()
    {
    	public void onClick(View v)
        {                        
            // set up Dialog
    		Dialog dialog = new Dialog(GUI_DialogActivity.this);
    		dialog.setContentView(R.layout.del_art_2);
    		dialog.setTitle("Artikel löschen");
    		dialog.setCancelable(true);
    		
    		// set up Text
    		dialog.show();
		}

    };
    
	//---create an anonymous class to act as a button click listener---
    // NEUER ARTIKEL
    private OnClickListener btnKlickListener11 = new OnClickListener()
    {
    	public void onClick(View v)
        {                        
            // set up Dialog
    		Dialog dialog = new Dialog(GUI_DialogActivity.this);
    		dialog.setContentView(R.layout.new_art_2);
    		dialog.setTitle("Neuer Artikel");
    		dialog.setCancelable(true);
    		
    		// set up Text
    		dialog.show();
		}

    };
    
	//---create an anonymous class to act as a button click listener---
    // NEUER ARTIKEL
    private OnClickListener btnKlickListener12 = new OnClickListener()
    {
    	public void onClick(View v)
        {                        
            // set up Dialog
    		Dialog dialog = new Dialog(GUI_DialogActivity.this);
    		dialog.setContentView(R.layout.edit_name_2);
    		dialog.setTitle("Name ändern");
    		dialog.setCancelable(true);
    		
    		// set up Text
    		dialog.show();
		}

    };

	//---create an anonymous class to act as a button click listener---
    // EINKAUFSLISTE LEEREN
    private OnClickListener btnKlickListener13 = new OnClickListener()
    {
    	public void onClick(View v)
        {                        
            // set up Dialog
    		Dialog dialog = new Dialog(GUI_DialogActivity.this);
    		dialog.setContentView(R.layout.empty_list_2);
    		dialog.setTitle("Einkaufsliste leeren");
    		dialog.setCancelable(true);
    		
    		// set up Text
    		dialog.show();
		}

    };

	//---create an anonymous class to act as a button click listener---
    // EINKAUFSLISTE ZURÜCKSETZEN
    private OnClickListener btnKlickListener14 = new OnClickListener()
    {
    	public void onClick(View v)
        {                        
            // set up Dialog
    		Dialog dialog = new Dialog(GUI_DialogActivity.this);
    		dialog.setContentView(R.layout.ek_zuruecksetzen_2);
    		dialog.setTitle("Einkaufsliste zurücksetzen");
    		dialog.setCancelable(true);
    		
    		// set up Text
    		dialog.show();
		}

    };
    
	/*	Noch nicht in main.xml verknüpft da nicht scrollbar
    //---create an anonymous class to act as a button click listener---
    // EINKAUFSLISTE ERLEDIGT
    private OnClickListener btnKlickListener12 = new OnClickListener()
    {
    	public void onClick(View v)
        {                        
            // set up Dialog
    		Dialog dialog = new Dialog(GUI_DialogActivity.this);
    		dialog.setContentView(R.layout.ek_erledigt_2);
    		dialog.setTitle("Erledigte Einkaufsliste");
    		dialog.setCancelable(true);
    		
    		// set up Text
    		dialog.show();
		}

    };*/

    
}