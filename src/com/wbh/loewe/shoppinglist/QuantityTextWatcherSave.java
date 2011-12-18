package com.wbh.loewe.shoppinglist;

import android.text.Editable;

import com.wbh.loewe.shoppinglist.listitem.ChildListItem;

public class QuantityTextWatcherSave extends QuantityTextWatcher {

	private ShoppingListApplication mShoppinglistapp;
	private int mListID;
	
	public QuantityTextWatcherSave(int aListID, ChildListItem aListItem, ShoppingListApplication aApp) {
		super(aListItem);
		this.mShoppinglistapp = aApp;
		this.mListID = aListID;
	}
	
	@Override
	public void afterTextChanged(Editable s) {
		super.afterTextChanged(s);
		mShoppinglistapp.getDBAdapter().updateArticleQuantity(mListID, mListItem.getID(), mListItem.getQuantity());		
	}
}
