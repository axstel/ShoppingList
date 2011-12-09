package com.wbh.loewe.shoppinglist;

import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.wbh.loewe.shoppinglist.database.ShoppingListDatabase;

public class ListCursorAdapter extends SimpleCursorAdapter {
	
	private Context mContext;
	private Cursor mCursor;
	private HashMap<Integer, Object> mItems = new HashMap<Integer, Object>();

	public ListCursorAdapter(Context aContext, int aLayout, Cursor aCursor, String[] aFrom, int aTo[]) {
		super(aContext, aLayout, aCursor, aFrom, aTo);
		this.mContext = aContext;
		this.mCursor = aCursor;
	}

	static class ViewHolder {
		protected TextView mID;
		protected TextView mText;
		protected Button mEdit;
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
			lNewViewHolder.mID = (TextView) lView.findViewById(R.id.labelid);
			lNewViewHolder.mText = (TextView) lView.findViewById(R.id.labelname);
			lNewViewHolder.mEdit = (Button) lView.findViewById(R.id.btn_edititem);
			lNewViewHolder.mEdit.setOnClickListener(new OnClickListener() {
					    public void onClick(View v) {
					    	ListItem lItem = (ListItem) lNewViewHolder.mEdit.getTag();
					    	lItem.setSelected(!lItem.getSelected());
					    	Log.w(ListCursorAdapter.class.getName(), "OnEditClick "+ lItem.getID() +" "+ lItem.getName());
					    }
					});
			lView.setTag(lNewViewHolder);
			lNewViewHolder.mEdit.setTag(lListItem);
		} else {
			((ViewHolder)lView.getTag()).mEdit.setTag(lListItem);
		}

		ViewHolder lHolder = (ViewHolder)lView.getTag();
		lHolder.mID.setText(Integer.toString(lListItem.getID()));
		lHolder.mText.setText(lListItem.getName());
		
		return lView;
	}
	
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Log.w(ListCursorAdapter.class.getName(), "onListItemClick");
	}

}
