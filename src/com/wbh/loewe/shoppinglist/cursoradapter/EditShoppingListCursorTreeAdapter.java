package com.wbh.loewe.shoppinglist.cursoradapter;

import java.util.Iterator;
import java.util.Vector;

import android.content.Context;
import android.database.Cursor;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.wbh.loewe.shoppinglist.R;
import com.wbh.loewe.shoppinglist.ShoppingListApplication;
import com.wbh.loewe.shoppinglist.database.ShoppingListDatabase;
import com.wbh.loewe.shoppinglist.listitem.ChildListItem;
import com.wbh.loewe.shoppinglist.listitem.EditListChildListItem;
import com.wbh.loewe.shoppinglist.listitem.GroupListItem;

public class EditShoppingListCursorTreeAdapter extends CustomCursorTreeAdapter {
	
	private int mListID;
	
	public EditShoppingListCursorTreeAdapter(Context context, Cursor cursor,
			int groupLayout, String[] groupFrom, int[] groupTo,
			int childLayout, String[] childFrom, int[] childTo,
			GroupRowClickListener aGroupClick, ChildRowClickListener aChildClick,
			RowActionClickListener aActionClick,  ShoppingListApplication aApp, int aListID) {
		super(context, cursor, groupLayout, groupFrom, groupTo, childLayout, childFrom,
				childTo, aGroupClick, aChildClick, aActionClick, aApp);
		this.mListID = aListID;
	}
	
	@Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		View lView = super.getGroupView(groupPosition, isExpanded, convertView, parent);
		
		GroupListItem lListItem = (GroupListItem)lView.getTag();
		
		if (lListItem != null) {
			final ImageButton lBtnDelete = (ImageButton)lView.findViewById(R.id.btn_del);
			if (lBtnDelete != null) {
				lBtnDelete.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						if (mRowActionLickListener != null) {
							GroupListItem lItem = (GroupListItem)lBtnDelete.getTag();
							mRowActionLickListener.OnRowClick(lItem, 2);
						}
					}
				});
			} else {
				Log.e("EditShoppingListCursorTreeAdapter.getGroupView", "btn_del not assigned");
			}
			lBtnDelete.setTag(lListItem);
		} else {
			Log.e("EditShoppingListCursorTreeAdapter.getGroupView", "Listitem not assigned");
		}
		
		return lView;
	}
	
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		View lView = super.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
		
		EditListChildListItem lListItem = (EditListChildListItem)lView.getTag();
		if (lListItem != null) {
			
			lListItem.setListID(mListID);
	    	lListItem.setApp(mMainApp);
	    	
	    	final ImageButton lBtnDelete = (ImageButton)lView.findViewById(R.id.btn_del);
			if (lBtnDelete != null) {
				lBtnDelete.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						if (mRowActionLickListener != null) {
							EditListChildListItem lItem = (EditListChildListItem)lBtnDelete.getTag();
							mRowActionLickListener.OnRowClick(lItem, 1);
						}
					}
					    	
				});
				lBtnDelete.setTag(lListItem);
			} else {
				Log.e("EditShoppingListCursorTreeAdapter.getChildView", "btn_del not assigned");
			}
	    	
			Cursor lChildCursor = getChild(groupPosition, childPosition);
			if (lChildCursor != null) {
				EditText lEdit = (EditText)lView.findViewById(R.id.edittxt_menge);
				
				if (lEdit != null) {
					// Prüfen ob dem Editfeld bereits einmal ein Textwatcher zugewiesen wurde, wenn ja,
					// dann wieder entfernen und neu zuweisen
					TextWatcher lTextWatcher = (TextWatcher)lEdit.getTag();
					if (lTextWatcher != null) {
						lEdit.removeTextChangedListener(lTextWatcher);
					}
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
					Log.e("EditShoppingListCursorTreeAdapter.getChildView", "edittxt_menge not found");
				}
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
	
	public void removeGroup(GroupListItem aListItem) {
        Log.v("Adapter", "Removing group "+ aListItem.getPos());
        
        Vector<Integer> lDelIDs = new Vector<Integer>();
        // erstmal die zu löschenden IDs ermitteln
        Iterator<Integer> lIterator = mChildListItems.keySet().iterator();
        while(lIterator.hasNext()) {
        	ChildListItem lChildItem = (ChildListItem)mChildListItems.get(lIterator.next());
        	if (lChildItem.getGroupPos() == aListItem.getPos()) {
        		lDelIDs.add(lChildItem.getID());
        	}
        }
        // Jetzt die Items aus der Hashmap löschen
        for (int i = 0; i < lDelIDs.size(); i++) {
        	mChildListItems.remove(lDelIDs.get(i));
        }
        notifyDataSetChanged();
    } 

    public void removeChild(ChildListItem aListItem, boolean aDoNotify) {
        Log.v("Adapter", "Removing child "+ aListItem.getChildPos() +" in group "+ aListItem.getGroupPos());
        mChildListItems.remove(aListItem.getID());
        if (aDoNotify) {
        	notifyDataSetChanged();
        }
    }
}
