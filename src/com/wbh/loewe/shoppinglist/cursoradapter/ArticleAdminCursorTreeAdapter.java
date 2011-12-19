package com.wbh.loewe.shoppinglist.cursoradapter;

import android.content.Context;
import android.database.Cursor;

import com.wbh.loewe.shoppinglist.ShoppingListApplication;

public class ArticleAdminCursorTreeAdapter extends CustomCursorTreeAdapter {
	
	public ArticleAdminCursorTreeAdapter(Context context, Cursor cursor,
			int groupLayout, String[] groupFrom, int[] groupTo,
			int childLayout, String[] childFrom, int[] childTo,
			GroupRowClickListener aGroupClick, ChildRowClickListener aChildClick,
			ShoppingListApplication aApp) {
		super(context, cursor, groupLayout, groupFrom, groupTo, childLayout, childFrom,
				childTo, aGroupClick, aChildClick, aApp);
		// TODO Auto-generated constructor stub
	}
}
