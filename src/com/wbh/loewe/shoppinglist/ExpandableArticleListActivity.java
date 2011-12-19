package com.wbh.loewe.shoppinglist;

import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.wbh.loewe.shoppinglist.cursoradapter.CustomCursorTreeAdapter;
import com.wbh.loewe.shoppinglist.listitem.ChildListItem;
import com.wbh.loewe.shoppinglist.listitem.GroupListItem;


/**
 * Expandable lists backed by a Simple Map-based adapter
 */
public class ExpandableArticleListActivity extends ExpandableListActivity 
{

	private ExpandableListView mListView;
	protected CustomCursorTreeAdapter mAdapter;
	protected ShoppingListApplication mShoppinglistapp;
	protected int mGroupItemLayout;
	protected int mChildItemLayout;
	protected String[] mGroupFrom;
	protected int[] mGroupTo;
	protected String[] mChildFrom;
	protected int[] mChildTo;
	
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        int lLayout = 0;
        Bundle lExtras = getIntent().getExtras();
		if (lExtras != null) {
			lLayout = lExtras.getInt("LAYOUT");
		}
        
        setContentView(lLayout);
        
        mShoppinglistapp = (ShoppingListApplication)getApplication();
        mListView = getExpandableListView();
	}
    
    public void fillData() {
    	mAdapter = createAdapter(); 
    	startManagingCursor(mAdapter.getCursor());
    	
    	//Der ExpandableListView den Adapter zuweisen
    	mListView.setAdapter(mAdapter);
    }
    
    protected CustomCursorTreeAdapter createAdapter() {
    	// Muss in subklasse �berschrieben werden
    	return null;
    }
    
    // Event wenn auf eine Kategorie klickt
    protected class OnGroupRowClickListener implements CustomCursorTreeAdapter.GroupRowClickListener {
		public void OnClick(View aView, GroupListItem aListItem) {
			if (aListItem != null) {
				if (aListItem.getIsExpanded()) {
					mListView.collapseGroup(aListItem.getPos());
					aListItem.setIsExpanded(false);
				} else {
					mListView.expandGroup(aListItem.getPos());
					aListItem.setIsExpanded(true);
				}
			}	
			OnGroupRowClick(aView, aListItem);
		}
    }
    
    // Event wenn auf eine Kategorie klickt
    protected class OnChildRowClickListener implements CustomCursorTreeAdapter.ChildRowClickListener {
		public void OnClick(View aView, ChildListItem aListItem) {
			OnChildRowClick(aView, aListItem);
		}
    }
    
    protected void OnGroupRowClick(View aView, GroupListItem aListItem) {
    	// in abgeleiteter klasse �berschreiben
    }
    
    protected void OnChildRowClick(View aView, ChildListItem aListItem) {
    	// in abgeleiteter klasse �berschreiben
    }
}
