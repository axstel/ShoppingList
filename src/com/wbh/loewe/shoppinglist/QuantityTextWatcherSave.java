package com.wbh.loewe.shoppinglist;

import android.content.ContentValues;
import android.text.Editable;

import com.wbh.loewe.shoppinglist.database.ShoppingListDatabase;
import com.wbh.loewe.shoppinglist.listitem.ChildListItem;

public class QuantityTextWatcherSave extends QuantityTextWatcher {

	private ShoppingListApplication mShoppinglistapp;
	
	public QuantityTextWatcherSave(ChildListItem aListItem, ShoppingListApplication aApp) {
		super(aListItem);
		this.mShoppinglistapp = aApp;
	}
	
	@Override
	public void afterTextChanged(Editable s) {
		super.afterTextChanged(s);
		mShoppinglistapp.getDBAdapter().updateArticleQuantity(mListItem.getID(), mListItem.getQuantity());		
	}
}
