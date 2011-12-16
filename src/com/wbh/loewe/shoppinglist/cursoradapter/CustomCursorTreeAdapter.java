package com.wbh.loewe.shoppinglist.cursoradapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.SimpleCursorTreeAdapter;

import com.wbh.loewe.shoppinglist.ChildListItem;
import com.wbh.loewe.shoppinglist.GroupListItem;
import com.wbh.loewe.shoppinglist.database.ShoppingListDatabase;

public class CustomCursorTreeAdapter extends SimpleCursorTreeAdapter {

	public interface GroupRowClickListener {
        public void OnClick(View aView, GroupListItem aListItem);
    }
	
	public interface ChildRowClickListener {
        public void OnClick(View aView, ChildListItem aListItem);
    }
	
	protected GroupRowClickListener mGroupRowClickListener;
	protected ChildRowClickListener mChildRowClickListener;
	protected Context mContext;
	protected Cursor mGroupCursor;
	protected Cursor mChildCursor;
	
	public CustomCursorTreeAdapter(Context context, Cursor cursor,
			int groupLayout, String[] groupFrom, int[] groupTo,
			int childLayout, String[] childFrom, int[] childTo,
			GroupRowClickListener aGroupClick, ChildRowClickListener aChildClick) {
		super(context, cursor, groupLayout, groupFrom, groupTo, childLayout, childFrom, childTo);
		mContext = context;
		mGroupCursor = cursor;
		mChildCursor = null;
		mGroupRowClickListener = aGroupClick;
		mChildRowClickListener = aChildClick;
	}
	
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
    	View lView = super.getGroupView(groupPosition, isExpanded, convertView, parent);
    	lView.setOnClickListener(btnGroupViewClickListListener);
    	
    	int lColIDx = mGroupCursor.getColumnIndex(ShoppingListDatabase.FIELD_NAME_ID);  
    	int lID = mGroupCursor.getInt(lColIDx);
    	if (lView.getTag() == null) {
    		GroupListItem lListItem = new GroupListItem(lID, groupPosition, isExpanded);
    		lView.setTag(lListItem);
    	} else {
    		GroupListItem lListItem = (GroupListItem)lView.getTag();
    		lListItem.setID(lID);
    		lListItem.setPos(groupPosition);
    		lListItem.setIsExpanded(isExpanded);
    	}
    	return lView;
    }
    
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		View lView = super.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
		lView.setOnClickListener(btnChildViewClickListListener);
		
		// Wenn auf den Datensatz nicht gesprungen werden kann, dann bauch man hier nicht weiter machen
		if (mChildCursor.moveToPosition(childPosition)) {
			int lColIDx = mChildCursor.getColumnIndex(ShoppingListDatabase.FIELD_NAME_ID);  
			int lID = mChildCursor.getInt(lColIDx);
			if (lView.getTag() == null) {
				ChildListItem lListItem = new ChildListItem(lID, groupPosition, childPosition);
				lView.setTag(lListItem);
			} else {
				ChildListItem lListItem = (ChildListItem)lView.getTag();
				lListItem.setID(lID);
				lListItem.setGroupPos(groupPosition);
				lListItem.setChildPos(childPosition);
			}
		} else {
			Log.e("CustomCursorTreeAdapter.getChildView", "moveToPosition "+ childPosition +" failed");
		}
		return lView;
	}
	
	private OnClickListener btnGroupViewClickListListener = new OnClickListener()
    {
    	public void onClick(View aView) {
    		if (mGroupRowClickListener != null) {
    			GroupListItem lListItem = null;
    			if (aView.getTag() != null) {
    				lListItem = (GroupListItem)aView.getTag();
    			} else {
    				Log.e("CustomCursorTreeAdapter.btnGroupViewClickListListener", "aView.getTag() is not assigned");
    			}
    			mGroupRowClickListener.OnClick(aView, lListItem);
    		} else {
    			Log.e("CustomCursorTreeAdapter.btnGroupViewClickListListener", "mGroupRowClickListener is not assigned");
    		}
    	}
    };
    
    private OnClickListener btnChildViewClickListListener = new OnClickListener()
    {
    	public void onClick(View aView) {
    		if (mChildRowClickListener != null) {
    			ChildListItem lListItem = null;
    			if (aView.getTag() != null) {
    				lListItem = (ChildListItem)aView.getTag();
    			} else {
    				Log.e("CustomCursorTreeAdapter.btnChildViewClickListListener", "aView.getTag() is not assigned");
    			}
    			mChildRowClickListener.OnClick(aView, lListItem);
    		} else {
    			Log.e("CustomCursorTreeAdapter.btnChildViewClickListListener", "mChildRowClickListener is not assigned");
    		}
    	}
    };

	public Cursor getGroupCursor() {
		return mGroupCursor;
	}
	
	public Cursor getChildCursor() {
		return mChildCursor;
	}

	@Override
	protected Cursor getChildrenCursor(Cursor groupCursor) {
		// must be implemented in subclass 
		return null;
	}

}
