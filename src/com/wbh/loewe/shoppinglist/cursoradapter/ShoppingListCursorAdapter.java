package com.wbh.loewe.shoppinglist.cursoradapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wbh.loewe.shoppinglist.R;
import com.wbh.loewe.shoppinglist.database.ShoppingListDatabase;
import com.wbh.loewe.shoppinglist.listitem.ListItem;

public class ShoppingListCursorAdapter extends CustomCursorAdapter {
	
	public ShoppingListCursorAdapter(Context aContext, int aLayout, Cursor aCursor, String[] aFrom, int aTo[], RowClickListener aOnRowClick) {
		super(aContext, aLayout, aCursor, aFrom, aTo, aOnRowClick);
	}

	static class ViewHolder {
		protected TextView mText;
		protected Button mEdit;
		protected Button mDelete;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {	
		
		View lView = super.getView(position, convertView, parent);
		
		mCursor.moveToPosition(position);
		//Log.w(ListCursorAdapter.class.getName(), "getView "+ mCursor.getCount() +" "+ mCursor.getColumnCount() +" "+ mCursor.getColumnCount());
		int lID = mCursor.getInt(mCursor.getColumnIndex(ShoppingListDatabase.FIELD_NAME_ID));
		ListItem lListItem;
		if (mItems.containsKey(lID)) {
			lListItem = (ListItem)mItems.get(lID); 
		} else {
			lListItem = new ListItem();
			mItems.put(lID, lListItem);
		}
		lListItem.setID(lID);
		lListItem.setName(mCursor.getString(mCursor.getColumnIndex(ShoppingListDatabase.FIELD_NAME_NAME)));
		
		final ViewHolder lViewHolder = (ViewHolder)lView.getTag();
		if (lViewHolder == null) {
			final ViewHolder lNewViewHolder = new ViewHolder();
			lNewViewHolder.mText = (TextView) lView.findViewById(R.id.labelname);
			if (lNewViewHolder.mText != null) {
				lNewViewHolder.mText.setTag(lListItem);
			}
			
			lNewViewHolder.mEdit = (Button) lView.findViewById(R.id.btn_edititem);
			if (lNewViewHolder.mEdit != null) {
				lNewViewHolder.mEdit.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						ListItem lItem = (ListItem) lNewViewHolder.mEdit.getTag();
					    mRowClickListener.OnRowClick(v, lItem, 1);
					}
					    	
				});
				lNewViewHolder.mEdit.setTag(lListItem);
			}
			
			lNewViewHolder.mDelete = (Button) lView.findViewById(R.id.btn_deleteitem);
			if (lNewViewHolder.mDelete != null) {
				lNewViewHolder.mDelete.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						ListItem lItem = (ListItem) lNewViewHolder.mDelete.getTag();
					    mRowClickListener.OnRowClick(v, lItem, 2);
					}
					    	
				});
				lNewViewHolder.mDelete.setTag(lListItem);
			}
			
			lView.setTag(lNewViewHolder);
		} else {
			if (((ViewHolder)lView.getTag()).mEdit != null) {
				((ViewHolder)lView.getTag()).mEdit.setTag(lListItem);
			};
		}

		ViewHolder lHolder = (ViewHolder)lView.getTag();
		lHolder.mText.setText(lListItem.getName());
		
		lView.setOnClickListener(RowClickListener);
		
		return lView;
	}
	
	private OnClickListener RowClickListener = new OnClickListener() {
    	public void onClick(View aView) {
    		ViewHolder lHolder = (ViewHolder)aView.getTag();
    		ListItem lItem = (ListItem)lHolder.mText.getTag();
    		mRowClickListener.OnRowClick(aView, lItem, 0);
    	}
    };

}
