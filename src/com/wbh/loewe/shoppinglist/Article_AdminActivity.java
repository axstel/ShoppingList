package com.wbh.loewe.shoppinglist;

import android.database.Cursor;
import android.os.Bundle;

import com.wbh.loewe.shoppinglist.cursoradapter.ArticleAdminCursorTreeAdapter;
import com.wbh.loewe.shoppinglist.cursoradapter.CustomCursorTreeAdapter;
import com.wbh.loewe.shoppinglist.database.ShoppingListDatabase;


/**
 * Expandable lists backed by a Simple Map-based adapter
 */
public class Article_AdminActivity extends ExpandableArticleListActivity 
{
	private ArticleAdminCursorTreeAdapter mArticleAdminAdapter;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mGroupItemLayout = R.layout.artdb_gui_group_row;
    	mChildItemLayout = R.layout.artdb_gui_child_row;
    	mGroupFrom = new String[] {ShoppingListDatabase.FIELD_NAME_NAME};
    	mGroupTo = new int[] {R.id.txt_kategorie}; 
    	mChildFrom = new String[] {ShoppingListDatabase.FIELD_NAME_NAME}; 
    	mChildTo = new int[] {R.id.txt_article};
        
		fillData();
	}
	
	@Override
    protected CustomCursorTreeAdapter createAdapter() {
		Cursor lGroupCursor = mShoppinglistapp.getDBAdapter().fetchAllCategories();
		mArticleAdminAdapter = new ArticleAdminCursorTreeAdapter(
    								this, 
    								lGroupCursor, 
    								mGroupItemLayout, 
    								mGroupFrom, 
    								mGroupTo, 
    								mChildItemLayout, 
    								mChildFrom, 
    								mChildTo,
    								new OnGroupRowClickListener(),
    								new OnChildRowClickListener(),
    								mShoppinglistapp) {
										@Override
										protected Cursor getChildrenCursor(Cursor groupCursor) {
											// DB-Abfrage um die Kindelemente darzustellen
											int lGroupID = groupCursor.getInt(groupCursor.getColumnIndex(ShoppingListDatabase.FIELD_NAME_ID));
											Cursor lChildCursor = mShoppinglistapp.getDBAdapter().fetchAllArticlesOfCategory(lGroupID);
											startManagingCursor(lChildCursor);
											return lChildCursor;
										}
									};
    	return mArticleAdminAdapter;
    }
		
}
