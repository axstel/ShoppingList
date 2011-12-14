package com.wbh.loewe.shoppinglist;

import java.util.HashMap;
import java.util.Vector;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorTreeAdapter;

public class CustomCursorTreeAdapter extends SimpleCursorTreeAdapter {

	private HashMap<String, Integer> mSelectedItems = new HashMap<String, Integer>();
	
	public CustomCursorTreeAdapter(Context context, Cursor cursor,
			int groupLayout, String[] groupFrom, int[] groupTo,
			int childLayout, String[] childFrom, int[] childTo) {
		super(context, cursor, groupLayout, groupFrom, groupTo, childLayout, childFrom,
				childTo);
		// TODO Auto-generated constructor stub
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
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		View lView = super.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
		// Prüfen ob dieses Item markiert wurde, wenn ja, dann markiert darstellen
		String lKey = groupPosition +"_"+ childPosition;
		setSelectedViewState(lView, lKey);
		
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
	

}
