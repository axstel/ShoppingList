package com.wbh.loewe.shoppinglist.cursoradapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.wbh.loewe.shoppinglist.QuantityTextWatcherSave;
import com.wbh.loewe.shoppinglist.R;
import com.wbh.loewe.shoppinglist.ShoppingListApplication;
import com.wbh.loewe.shoppinglist.database.ShoppingListDatabase;
import com.wbh.loewe.shoppinglist.listitem.ChildListItem;

public class EditShoppingListCursorTreeAdapter extends CustomCursorTreeAdapter {
	
	public EditShoppingListCursorTreeAdapter(Context context, Cursor cursor,
			int groupLayout, String[] groupFrom, int[] groupTo,
			int childLayout, String[] childFrom, int[] childTo,
			GroupRowClickListener aGroupClick, ChildRowClickListener aChildClick,
			ShoppingListApplication aApp) {
		super(context, cursor, groupLayout, groupFrom, groupTo, childLayout, childFrom,
				childTo, aGroupClick, aChildClick, aApp);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		View lView = super.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
		
		ChildListItem lListItem = (ChildListItem)lView.getTag();
		if (lListItem != null) {
			if (mChildCursor.moveToPosition(childPosition)) {
				int lColIdx = mChildCursor.getColumnIndex(ShoppingListDatabase.FIELD_NAME_QUANTITY);
				// warum auch immer
				if (lColIdx == -1) {
					lColIdx = 4;
				}
		    	int lQuantity = mChildCursor.getInt(lColIdx);
		    	EditText lEdit = (EditText)lView.findViewById(R.id.edittxt_menge);
		    	lListItem.setQuantityEdit(lEdit);
		    	lListItem.setQuantity(lQuantity);
		    	if (lEdit != null) {
			    	lEdit.setText(String.valueOf(lQuantity));
			    	QuantityTextWatcherSave lTextWatcher = new QuantityTextWatcherSave(lListItem, mMainApp);
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
