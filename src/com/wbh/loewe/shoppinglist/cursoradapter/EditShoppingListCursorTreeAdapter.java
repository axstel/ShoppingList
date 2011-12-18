package com.wbh.loewe.shoppinglist.cursoradapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.wbh.loewe.shoppinglist.QuantityTextWatcherSave;
import com.wbh.loewe.shoppinglist.R;
import com.wbh.loewe.shoppinglist.ShoppingListApplication;
import com.wbh.loewe.shoppinglist.listitem.ChildListItem;

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
		
		ChildListItem lListItem = (ChildListItem)lView.getTag();
		if (lListItem != null) {
			if (mChildCursor.moveToPosition(childPosition)) {
				EditText lEdit = (EditText)lView.findViewById(R.id.edittxt_menge);
		    	lListItem.setQuantityEdit(lEdit);
		    	TextView lLabel = (TextView)lView.findViewById(R.id.txt_einheit);
		    	lListItem.setQuantityLabel(lLabel);
		    	if (lEdit != null) {
			    	QuantityTextWatcherSave lTextWatcher = new QuantityTextWatcherSave(mListID, lListItem, mMainApp);
				    lEdit.addTextChangedListener(lTextWatcher);
			    }
				
			} else {
				Log.e("EditShoppingListCursorTreeAdapter.getChildView", "moveToPosition "+ childPosition +" failed");
			}
			
		} else {
			Log.e("EditShoppingListCursorTreeAdapter.getChildView", "Listitem not assigned");
		}
		
		return lView;
	}
}
