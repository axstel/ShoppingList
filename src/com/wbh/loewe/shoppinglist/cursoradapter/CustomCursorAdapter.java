package com.wbh.loewe.shoppinglist.cursoradapter;

import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter;

import com.wbh.loewe.shoppinglist.listitem.ListItem;

public class CustomCursorAdapter extends SimpleCursorAdapter {
	
	public interface RowClickListener {
        public void OnRowClick(View aView, ListItem aListItem, int aAction);
    }
	
	protected Context mContext;
	protected Cursor mCursor;
	protected HashMap<Integer, Object> mItems = new HashMap<Integer, Object>();
	protected RowClickListener mRowClickListener;
	

	public CustomCursorAdapter(Context aContext, int aLayout, Cursor aCursor, String[] aFrom, int aTo[], RowClickListener aOnRowClick) {
		super(aContext, aLayout, aCursor, aFrom, aTo);
		this.mContext = aContext;
		this.mCursor = aCursor;
		this.mRowClickListener = aOnRowClick;
	}

}
