package com.wbh.loewe.shoppinglist.cursoradapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wbh.loewe.shoppinglist.R;
import com.wbh.loewe.shoppinglist.database.ShoppingListDatabase;
import com.wbh.loewe.shoppinglist.listitem.UseListChildListItem;

public class UseShoppingListCursorAdapter extends CustomCursorAdapter {
	
	public UseShoppingListCursorAdapter(Context aContext, int aLayout, Cursor aCursor, String[] aFrom, int aTo[], RowClickListener aOnRowClick) {
		super(aContext, aLayout, aCursor, aFrom, aTo, aOnRowClick);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {	
		
		View lView = super.getView(position, convertView, parent);
		
		mCursor.moveToPosition(position);
		//Log.w(ListCursorAdapter.class.getName(), "getView "+ mCursor.getCount() +" "+ mCursor.getColumnCount() +" "+ mCursor.getColumnCount());
		int lColIdx = mCursor.getColumnIndex("ITEMID");
		int lID = mCursor.getInt(lColIdx);
		lColIdx = mCursor.getColumnIndex(ShoppingListDatabase.FIELD_NAME_NAME);
    	String lName = mCursor.getString(lColIdx);
    	lColIdx = mCursor.getColumnIndex(ShoppingListDatabase.FIELD_NAME_ID);
    	int lArticleID = mCursor.getInt(lColIdx);
		
		UseListChildListItem lListItem = (UseListChildListItem)mItems.get(lID);
		if (lListItem == null) {
			lListItem = new UseListChildListItem();
			lListItem.setID(lID);
			lListItem.setArticleID(lArticleID);
			lListItem.setName(lName);
			mItems.put(lID, lListItem);
		} 
		lView.setTag(lListItem);
		
		if (lListItem != null) {
			lListItem.setTxtArticle((TextView)lView.findViewById(R.id.txt_article));
			lListItem.setTxtQuantity((TextView)lView.findViewById(R.id.edittxt_menge));
			lListItem.setTxtQuantityName((TextView)lView.findViewById(R.id.txt_einheit));
			
			// wenn der wert <> -1 ist, dann wurde er bereits vom benutzer gesetzt, bzw. initial geladen
			// es kann dieser wert verwendet werden
			boolean lSelected = false;
			if (lListItem.getSelectedInt() != -1) {
				lSelected = lListItem.getSelected();
			} else {
				if (mCursor.getInt(mCursor.getColumnIndex(ShoppingListDatabase.FIELD_NAME_SELECTED)) == 1) {
					lSelected = true;
				} else {
					lSelected = false;
				}
			}
			lListItem.setSelected(lSelected);
			// Prüfen ob dieses Item markiert wurde, wenn ja, dann markiert darstellen
			setSelectedViewState(lView, lListItem.getID());
		}
		
		lView.setOnClickListener(RowClickListener);
		
		return lView;
	}
	
	private OnClickListener RowClickListener = new OnClickListener() {
    	public void onClick(View aView) {
    		UseListChildListItem lItem = (UseListChildListItem)aView.getTag();
    		mRowClickListener.OnRowClick(aView, lItem, 0);
    	}
    };
    
 // Durchstreichen oder nicht
 	private void setSelectedViewState(View aView, int aKey) {
 			
 		UseListChildListItem lListItem = (UseListChildListItem)mItems.get(aKey);
 		Boolean lSelected = false;
 		if (lListItem != null) {
 			lSelected = lListItem.getSelected();
 		}
 		
 		setTextOfView(lListItem.getTxtArticle(), lSelected);
 		setTextOfView(lListItem.getTxtQuantity(), lSelected);
 		setTextOfView(lListItem.getTxtQuantityName(), lSelected);
 	}
 	
 	// Text einer View entsprechend dem Status setzen
 	private void setTextOfView(TextView aView, boolean aSelected) {
 		if (aView != null) {
 			if (aSelected) {
 				aView.setTextColor(mContext.getResources().getColor(R.color.row_strikedout_text));
 				aView.setPaintFlags(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG | Paint.STRIKE_THRU_TEXT_FLAG);
 			} else {
 				aView.setTextColor(mContext.getResources().getColor(R.color.row_unselected_text));
 				aView.setPaintFlags(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);
 			}
 		} else {
 			Log.e("UseShoppingListCursorAdapter.setTextOfView", "aView is not assigned");
 		}
 	}
 	
 	public Boolean setSelectedItem(View aView, int aItemID) {
		// Beim Klick auf ein Item muss dieses in eine interne Liste aufgenommen werden
		// So kann festgestellt werden ob dieses selektiert ist
		// Ist es bereits selektiert, dann wird die Selektierung zurückgenommen
		// In getChildView wird auf diese Liste zurückgegriffen
		
		UseListChildListItem lListItem = (UseListChildListItem)mItems.get(aItemID);
		Boolean lSelected = false;
		if (lListItem != null) {
			lListItem.setSelected(!lListItem.getSelected());
			lSelected = lListItem.getSelected();
		}
		setSelectedViewState(aView, aItemID);
		return lSelected;
	}
 	
	public void resetChildItemList() {
		mItems.clear();
	}

}
