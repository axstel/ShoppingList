package com.wbh.loewe.shoppinglist.cursoradapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wbh.loewe.shoppinglist.R;
import com.wbh.loewe.shoppinglist.ShoppingListApplication;
import com.wbh.loewe.shoppinglist.database.ShoppingListDatabase;
import com.wbh.loewe.shoppinglist.listitem.ChildListItem;
import com.wbh.loewe.shoppinglist.listitem.UseListChildListItem;

public class UseShoppingListCursorTreeAdapter extends CustomCursorTreeAdapter {
	
	public UseShoppingListCursorTreeAdapter(Context context, Cursor cursor,
			int groupLayout, String[] groupFrom, int[] groupTo,
			int childLayout, String[] childFrom, int[] childTo,
			GroupRowClickListener aGroupClick, ChildRowClickListener aChildClick,
			ShoppingListApplication aApp) {
		super(context, cursor, groupLayout, groupFrom, groupTo, childLayout, childFrom,
				childTo, aGroupClick, aChildClick, aApp);
	}
	
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		View lView = super.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
		
		UseListChildListItem lListItem = (UseListChildListItem)lView.getTag();
		if (lListItem != null) {
			lListItem.setTxtArticle((TextView)lView.findViewById(R.id.txt_article));
			lListItem.setTxtQuantity((TextView)lView.findViewById(R.id.edittxt_menge));
			lListItem.setTxtQuantityName((TextView)lView.findViewById(R.id.txt_einheit));
			
			Cursor lChildCursor = getChild(groupPosition, childPosition);
			if (lChildCursor != null) {
				// wenn der wert <> -1 ist, dann wurde er bereits vom benutzer gesetzt, bzw. initial geladen
				// es kann dieser wert verwendet werden
				boolean lSelected = false;
				if (lListItem.getSelectedInt() != -1) {
					lSelected = lListItem.getSelected();
				} else {
					if (lChildCursor.getInt(lChildCursor.getColumnIndex(ShoppingListDatabase.FIELD_NAME_SELECTED)) == 1) {
						lSelected = true;
					} else {
						lSelected = false;
					}
				}
				lListItem.setSelected(lSelected);
			} else {
				Log.e("UseShoppingListCursorTreeAdapter.getChildView", "getChild "+ groupPosition +";"+ childPosition +" failed");
			}
			
			// Pr�fen ob dieses Item markiert wurde, wenn ja, dann markiert darstellen
			String lKey = groupPosition +"_"+ childPosition;
			setSelectedViewState(lView, lKey);
		} else {
			Log.e("UseShoppingListCursorTreeAdapter.getChildView", "Listitem not assigned");
		}
		return lView;
	}
	
	@Override
	protected ChildListItem getNewListItem() {
		return new UseListChildListItem();
	}
	
	public Boolean setSelectedItem(View aView, int aGroupPos, int aChildPos) {
		// Beim Klick auf ein Item muss dieses in eine interne Liste aufgenommen werden
		// So kann festgestellt werden ob dieses selektiert ist
		// Ist es bereits selektiert, dann wird die Selektierung zur�ckgenommen
		// In getChildView wird auf diese Liste zur�ckgegriffen
		String lKey = aGroupPos +"_"+ aChildPos;
		
		UseListChildListItem lListItem = (UseListChildListItem)mChildListItems.get(lKey);
		Boolean lSelected = false;
		if (lListItem != null) {
			lListItem.setSelected(!lListItem.getSelected());
			lSelected = lListItem.getSelected();
		}
		setSelectedViewState(aView, lKey);
		return lSelected;
	}
	
	// Durchstreichen oder nicht
	private void setSelectedViewState(View aView, String aKey) {
			
		UseListChildListItem lListItem = (UseListChildListItem)mChildListItems.get(aKey);
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
				aView.setTextColor(mContext.getResources().getColor(R.color.row_selected_text));
				aView.setPaintFlags(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG | Paint.STRIKE_THRU_TEXT_FLAG);
			} else {
				aView.setTextColor(mContext.getResources().getColor(R.color.row_unselected_text));
				aView.setPaintFlags(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);
			}
		} else {
			Log.e("UseShoppingListCursorTreeAdapter.setTextOfView", "aView is not assigned");
		}
	}
	
}
