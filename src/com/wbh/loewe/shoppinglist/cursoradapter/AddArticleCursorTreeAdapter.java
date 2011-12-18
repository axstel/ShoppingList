package com.wbh.loewe.shoppinglist.cursoradapter;

import java.util.Vector;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.wbh.loewe.shoppinglist.R;
import com.wbh.loewe.shoppinglist.ShoppingListApplication;
import com.wbh.loewe.shoppinglist.listitem.AddArticleChildListItem;
import com.wbh.loewe.shoppinglist.listitem.ChildListItem;

public class AddArticleCursorTreeAdapter extends CustomCursorTreeAdapter {
	
	public AddArticleCursorTreeAdapter(Context context, Cursor cursor,
			int groupLayout, String[] groupFrom, int[] groupTo,
			int childLayout, String[] childFrom, int[] childTo,
			GroupRowClickListener aGroupClick, ChildRowClickListener aChildClick,
			ShoppingListApplication aApp) {
		super(context, cursor, groupLayout, groupFrom, groupTo, childLayout, childFrom,
				childTo, aGroupClick, aChildClick, aApp);
		// TODO Auto-generated constructor stub
	}
	
	public Boolean setSelectedItem(View aView, int aGroupPos, int aChildPos) {
		// Beim Klick auf ein Item muss dieses in eine interne Liste aufgenommen werden
		// So kann festgestellt werden ob dieses selektiert ist
		// Ist es bereits selektiert, dann wird die Selektierung zurückgenommen
		// In getChildView wird auf diese Liste zurückgegriffen
		String lKey = aGroupPos +"_"+ aChildPos;
		
		AddArticleChildListItem lListItem = (AddArticleChildListItem)mChildListItems.get(lKey);
		Boolean lSelected = false;
		if (lListItem != null) {
			lListItem.setSelected(!lListItem.getSelected());
			lSelected = lListItem.getSelected();
		}
		setSelectedViewState(aView, lKey);
		return lSelected;
	}
	
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
    	View lView = super.getGroupView(groupPosition, isExpanded, convertView, parent);
    	return lView;
    }
    
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		View lView = super.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
		
		// Menge
		AddArticleChildListItem lListItem = (AddArticleChildListItem)lView.getTag();
		if (lListItem != null) {
			EditText lEdit = (EditText)lView.findViewById(R.id.edittxt_menge);
			lEdit.setText(String.valueOf(lListItem.getQuantity()));
		} else {
			Log.e("AddArticleCursorTreeAdapter.getChildView", "Listitem not assigned");
		}
		
    	// Prüfen ob dieses Item markiert wurde, wenn ja, dann markiert darstellen
    	String lKey = groupPosition +"_"+ childPosition;
    	setSelectedViewState(lView, lKey);
		return lView;
	}
	
	
	// Setzt die Farbe der View entsprechend dem SelectState, wird nur intern verwendet in getChildView
	// und setSelectedItem
	// Wenn noch spezielle Controls angepasst werden müssen, dann muss das Event zugewiesen sein
	private void setSelectedViewState(View aView, String aKey) {
		
		AddArticleChildListItem lListItem = (AddArticleChildListItem)mChildListItems.get(aKey);
		Boolean lSelected = false;
		if (lListItem != null) {
			lSelected = lListItem.getSelected();
		}
		TextView ltxt_Article = (TextView)aView.findViewById(R.id.txt_article);
		if (lSelected) {
			aView.setBackgroundColor(mContext.getResources().getColor(R.color.row_selected_background));
			
			if (ltxt_Article != null) {
				ltxt_Article.setTextColor(mContext.getResources().getColor(R.color.row_selected_text));
			} else {
				Log.e("Add_Article_ListActivity.OnSetSelectedView", "txt_article not found");
			}
		} else {
			aView.setBackgroundColor(mContext.getResources().getColor(R.color.row_unselected_background));
			
			if (ltxt_Article != null) {
				ltxt_Article.setTextColor(mContext.getResources().getColor(R.color.row_unselected_text));
			} else {
				Log.e("Add_Article_ListActivity.OnSetSelectedView", "txt_article not found");
			}
		}
	}
	
	@Override
	protected ChildListItem getNewListItem() {
		return new AddArticleChildListItem();
	}
	
	public Vector<AddArticleChildListItem> getSelectedItems() {
		Vector<AddArticleChildListItem> lSelected = new Vector<AddArticleChildListItem>();
		for (ChildListItem lChildListItem : mChildListItems.values()) {
			AddArticleChildListItem lListItem = (AddArticleChildListItem)lChildListItem;
			if (lListItem.getSelected()) {
				lSelected.add(lListItem);
			}
		}
		return lSelected;
	}
}
