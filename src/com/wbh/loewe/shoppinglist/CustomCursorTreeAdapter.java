package com.wbh.loewe.shoppinglist;

import java.util.HashMap;
import java.util.Vector;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.SimpleCursorTreeAdapter;

public class CustomCursorTreeAdapter extends SimpleCursorTreeAdapter {

	public interface GroupRowClickListener {
        public void OnClick(View aView, GroupListItem aListItem);
    }
	
	public interface ChildRowClickListener {
        public void OnClick(View aView, ChildListItem aListItem);
    }
	
	private HashMap<String, Integer> mSelectedItems = new HashMap<String, Integer>();
	private GroupRowClickListener mGroupRowClickListener;
	private ChildRowClickListener mChildRowClickListener;
	
	public CustomCursorTreeAdapter(Context context, Cursor cursor,
			int groupLayout, String[] groupFrom, int[] groupTo,
			int childLayout, String[] childFrom, int[] childTo,
			GroupRowClickListener aGroupClick, ChildRowClickListener aChildClick) {
		super(context, cursor, groupLayout, groupFrom, groupTo, childLayout, childFrom,
				childTo);
		mGroupRowClickListener = aGroupClick;
		mChildRowClickListener = aChildClick;
	}

	@Override
	protected Cursor getChildrenCursor(Cursor groupCursor) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setSelectedItem(View aView, int aGroupPos, int aChildPos, int aID) {
		// Beim Klick auf ein Item muss dieses in eine interne Liste aufgenommen werden
		// So kann festgestellt werden ob dieses selektiert ist
		// Ist es bereits selektiert, dann wird die Selektierung zurückgenommen
		// In getChildView wird auf diese Liste zurückgegriffen
		String lKey = aGroupPos +"_"+ aChildPos;
		if (mSelectedItems.containsKey(lKey)) {
			mSelectedItems.remove(lKey);
		} else {
			mSelectedItems.put(lKey, aID);
		}
		setSelectedViewState(aView, lKey);
	}
	
	private static final int BLACK = 0x000000;
    private static final int RED = 0xffbc0000;

	
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
    	View lView = super.getGroupView(groupPosition, isExpanded, convertView, parent);
    	lView.setOnClickListener(btnGroupViewClickListListener);
    	if (lView.getTag() == null) {
    		GroupListItem lListItem = new GroupListItem(groupPosition, isExpanded);
    		lView.setTag(lListItem);
    	} else {
    		GroupListItem lListItem = (GroupListItem)lView.getTag();
    		lListItem.setPos(groupPosition);
    		lListItem.setIsExpanded(isExpanded);
    	}
    	return lView;
    }
    
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		View lView = super.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
		// Prüfen ob dieses Item markiert wurde, wenn ja, dann markiert darstellen
		String lKey = groupPosition +"_"+ childPosition;
		setSelectedViewState(lView, lKey);
		lView.setOnClickListener(btnChildViewClickListListener);
		if (lView.getTag() == null) {
    		ChildListItem lListItem = new ChildListItem(groupPosition, childPosition);
    		lView.setTag(lListItem);
    	} else {
    		ChildListItem lListItem = (ChildListItem)lView.getTag();
    		lListItem.setGroupPos(groupPosition);
    		lListItem.setChildPos(childPosition);
    	}
		return lView;
	}
	
	private void setSelectedViewState(View aView, String aKey) {
		if (mSelectedItems.containsKey(aKey)) {
			aView.setBackgroundColor(RED);
		} else {
			aView.setBackgroundColor(BLACK);
		}
	}
	
	public Vector<Integer> getSelectedIDs() {
		Vector<Integer> lSelected = new Vector<Integer>();
		for (int lID : mSelectedItems.values()) {
			lSelected.add(lID);
		}
		return lSelected;
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

}
