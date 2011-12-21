package com.wbh.loewe.shoppinglist.cursoradapter;

import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.SimpleCursorTreeAdapter;

import com.wbh.loewe.shoppinglist.ShoppingListApplication;
import com.wbh.loewe.shoppinglist.database.ShoppingListDatabase;
import com.wbh.loewe.shoppinglist.listitem.ChildListItem;
import com.wbh.loewe.shoppinglist.listitem.GroupListItem;

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
	protected ShoppingListApplication mMainApp;
	protected HashMap<String, ChildListItem> mChildListItems = new HashMap<String, ChildListItem>();
	
	public CustomCursorTreeAdapter(Context context, Cursor cursor,
			int groupLayout, String[] groupFrom, int[] groupTo,
			int childLayout, String[] childFrom, int[] childTo,
			GroupRowClickListener aGroupClick, ChildRowClickListener aChildClick,
			ShoppingListApplication aApp) {
		super(context, cursor, groupLayout, groupFrom, groupTo, childLayout, childFrom, childTo);
		mContext = context;
		mGroupRowClickListener = aGroupClick;
		mChildRowClickListener = aChildClick;
		mMainApp = aApp;
	}
	
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
    	View lView = super.getGroupView(groupPosition, isExpanded, convertView, parent);
    	lView.setOnClickListener(btnGroupViewClickListListener);
    	
    	Cursor lGroupCursor = getGroup(groupPosition);
    	if (lGroupCursor != null) {
    		int lColIdx = -1;
    		lColIdx = lGroupCursor.getColumnIndex(ShoppingListDatabase.FIELD_NAME_ID);  
    		int lID = lGroupCursor.getInt(lColIdx);
    		lColIdx = lGroupCursor.getColumnIndex(ShoppingListDatabase.FIELD_NAME_NAME);
    		String lName = lGroupCursor.getString(lColIdx);
    		if (lView.getTag() == null) {
    			GroupListItem lListItem = new GroupListItem(lID, lName, groupPosition, isExpanded);
    			lView.setTag(lListItem);
    		} else {
    			GroupListItem lListItem = (GroupListItem)lView.getTag();
    			lListItem.setID(lID);
    			lListItem.setPos(groupPosition);
    			lListItem.setIsExpanded(isExpanded);
    		}
    	} else {
    		Log.e("CustomCursorTreeAdapter.getGroupView", "getGroup "+ groupPosition +" failed");
    	}
    	return lView;
    }
    
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		View lView = super.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
		lView.setOnClickListener(btnChildViewClickListListener);
		
		Cursor lChildCursor = getChild(groupPosition, childPosition);
		// Wenn auf den Datensatz nicht gesprungen werden kann, dann bauch man hier nicht weiter machen
		if (lChildCursor != null) {
			
	    	ChildListItem lListItem = mChildListItems.get(groupPosition +"_"+ childPosition);
			if (lListItem == null) {
				int lColIdx = -1;
		    	lColIdx = lChildCursor.getColumnIndex(ShoppingListDatabase.FIELD_NAME_ID);  
		    	int lID = lChildCursor.getInt(lColIdx);
		    	lColIdx = lChildCursor.getColumnIndex(ShoppingListDatabase.FIELD_NAME_NAME);
		    	String lName = lChildCursor.getString(lColIdx);
				
				lListItem = getNewListItem();
				lListItem.setID(lID);
				lListItem.setName(lName);
				lListItem.setGroupPos(groupPosition);
				lListItem.setChildPos(childPosition);
				mChildListItems.put(groupPosition +"_"+ childPosition, lListItem);
				lView.setTag(lListItem);
			} else {
				lView.setTag(lListItem);
			}
			
		} else {
			Log.e("CustomCursorTreeAdapter.getChildView", "getChild "+ groupPosition +","+ childPosition +" failed");
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


	@Override
	protected Cursor getChildrenCursor(Cursor groupCursor) {
		// must be implemented in subclass 
		return null;
	}
	
	protected ChildListItem getNewListItem() {
		return new ChildListItem();
	}
	
	public void resetChildItemList() {
		mChildListItems.clear();
	}

}
