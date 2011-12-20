package com.wbh.loewe.shoppinglist.cursoradapter;

import android.content.Context;
import android.database.Cursor;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.wbh.loewe.shoppinglist.R;
import com.wbh.loewe.shoppinglist.ShoppingListApplication;
import com.wbh.loewe.shoppinglist.database.ShoppingListDatabase;
import com.wbh.loewe.shoppinglist.listitem.ChildListItem;
import com.wbh.loewe.shoppinglist.listitem.EditListChildListItem;

public class EditShoppingListCursorTreeAdapter extends CustomCursorTreeAdapter {
	
	private int mListID;
	
	public EditShoppingListCursorTreeAdapter(Context context, Cursor cursor,
			int groupLayout, String[] groupFrom, int[] groupTo,
			int childLayout, String[] childFrom, int[] childTo,
			GroupRowClickListener aGroupClick, ChildRowClickListener aChildClick,
			ShoppingListApplication aApp, int aListID) {
		super(context, cursor, groupLayout, groupFrom, groupTo, childLayout, childFrom,
				childTo, aGroupClick, aChildClick, aApp);
		this.mListID = aListID;
	}
	
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		View lView = super.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
		
		EditListChildListItem lListItem = (EditListChildListItem)lView.getTag();
		if (lListItem != null) {
			Cursor lChildCursor = getChild(groupPosition, childPosition);
			if (lChildCursor != null) {
				EditText lEdit = (EditText)lView.findViewById(R.id.edittxt_menge);
				
				// Prüfen ob dem Editfeld bereits einmal ein Textwatcher zugewiesen wurde, wenn ja,
		    	// dann wieder entfernen und neu zuweisen
		    	TextWatcher lTextWatcher = (TextWatcher)lEdit.getTag();
		    	if (lTextWatcher != null) {
		    		lEdit.removeTextChangedListener(lTextWatcher);
		    	}
		    	
		    	lListItem.setListID(mListID);
		    	lListItem.setApp(mMainApp);
		    	
				// wenn der wert <> -1 ist, dann wurde er bereits vom benutzer gesetzt, bis initial geladen
		    	// es kann dieser wert verwendet werden
		    	float lQuantity = 0;
		    	if (lListItem.getQuantity() != -1) {
		    		lQuantity = lListItem.getQuantity();
		    	} else {
		    		lQuantity = lChildCursor.getFloat(lChildCursor.getColumnIndex(ShoppingListDatabase.FIELD_NAME_QUANTITY));
		    	}
		    	lEdit.setText(String.valueOf(lQuantity));
		    	lListItem.setQuantity(lQuantity);
		    	
		    	// Listener neu zuweisen
		    	lEdit.addTextChangedListener(lListItem);
		    	lEdit.setTag(lListItem);
			} else {
				Log.e("EditShoppingListCursorTreeAdapter.getChildView", "getChild "+ groupPosition +";"+ childPosition +" failed");
			}
			
		} else {
			Log.e("EditShoppingListCursorTreeAdapter.getChildView", "Listitem not assigned");
		}
		
		return lView;
	}
	
	@Override
	protected ChildListItem getNewListItem() {
		return new EditListChildListItem();
	}
}
