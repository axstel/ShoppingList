package com.wbh.loewe.shoppinglist;

import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.wbh.loewe.shoppinglist.database.ShoppingListDatabase;

public class ListCursorAdapter extends SimpleCursorAdapter {
	
	public interface RowClickListener {
        public void OnRowClick(ListItem aListItem, int aAction);
    }
	
	private Cursor mCursor;
	private HashMap<Integer, Object> mItems = new HashMap<Integer, Object>();
	private RowClickListener mRowClickListener;
	

	public ListCursorAdapter(Context aContext, int aLayout, Cursor aCursor, String[] aFrom, int aTo[], RowClickListener aOnRowClick) {
		super(aContext, aLayout, aCursor, aFrom, aTo);
		this.mCursor = aCursor;
		this.mRowClickListener = aOnRowClick;
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
			lListItem = new ListItem(lID, mCursor.getString(mCursor.getColumnIndex(ShoppingListDatabase.FIELD_NAME_NAME)));
			mItems.put(lID, lListItem);
		}
		
		final ViewHolder lViewHolder = (ViewHolder)lView.getTag();
		if (lViewHolder == null) {
			final ViewHolder lNewViewHolder = new ViewHolder();
			lNewViewHolder.mText = (TextView) lView.findViewById(R.id.labelname);
			if (lNewViewHolder.mText != null) {
				lNewViewHolder.mText.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						ListItem lItem = (ListItem) lNewViewHolder.mText.getTag();
					    lItem.setSelected(!lItem.getSelected());
					    //Log.w(ListCursorAdapter.class.getName(), "OnTextClick "+ lItem.getID() +" "+ lItem.getName());
					    mRowClickListener.OnRowClick(lItem, 0);
					}
				});
				lNewViewHolder.mText.setTag(lListItem);
			}
			
			lNewViewHolder.mEdit = (Button) lView.findViewById(R.id.btn_edititem);
			if (lNewViewHolder.mEdit != null) {
				lNewViewHolder.mEdit.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						ListItem lItem = (ListItem) lNewViewHolder.mEdit.getTag();
					    lItem.setSelected(!lItem.getSelected());
					    //Log.w(ListCursorAdapter.class.getName(), "OnEditClick "+ lItem.getID() +" "+ lItem.getName());
					    mRowClickListener.OnRowClick(lItem, 1);
					}
					    	
				});
				lNewViewHolder.mEdit.setTag(lListItem);
			}
			
			lNewViewHolder.mDelete = (Button) lView.findViewById(R.id.btn_deleteitem);
			if (lNewViewHolder.mDelete != null) {
				lNewViewHolder.mDelete.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						ListItem lItem = (ListItem) lNewViewHolder.mDelete.getTag();
					    lItem.setSelected(!lItem.getSelected());
					    //Log.w(ListCursorAdapter.class.getName(), "OnEditClick "+ lItem.getID() +" "+ lItem.getName());
					    mRowClickListener.OnRowClick(lItem, 2);
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
		
		return lView;
	}

}
