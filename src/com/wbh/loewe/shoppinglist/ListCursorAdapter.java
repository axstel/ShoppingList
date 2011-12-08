package com.wbh.loewe.shoppinglist;

import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
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
			
		View view = null;
		if (convertView == null) {
			LayoutInflater inflator = LayoutInflater.from(mContext);
			view = inflator.inflate(R.layout.list_row, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.mID = (TextView) view.findViewById(R.id.labelid);
			viewHolder.mText = (TextView) view.findViewById(R.id.labelname);
			viewHolder.mEdit = (Button) view.findViewById(R.id.btn_edititem);
			viewHolder.mEdit
					.setOnClickListener(new OnClickListener() {
					    public void onClick(View v) {
					    	ListItem lItem = (ListItem) viewHolder.mEdit.getTag();
					    	lItem.setSelected(!lItem.getSelected());
					    	Log.w(ListCursorAdapter.class.getName(), "OnEditClick "+ lItem.getID() +" "+ lItem.getName());
					    }
					});
			view.setTag(viewHolder);
			viewHolder.mEdit.setTag(lListItem);
		} else {
			view = convertView;
			((ViewHolder) view.getTag()).mEdit.setTag(lListItem);
		}
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.mID.setText(Integer.toString(lListItem.getID()));
		holder.mText.setText(lListItem.getName());
		
		return view;
	}
}
