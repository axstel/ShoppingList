package com.wbh.loewe.shoppinglist;

import android.text.Editable;
import android.text.TextWatcher;

import com.wbh.loewe.shoppinglist.listitem.ChildListItem;

public class QuantityTextWatcher implements TextWatcher {
	
	protected ChildListItem mListItem;
	
	public QuantityTextWatcher(ChildListItem aListItem) {
		this.mListItem = aListItem;
	}
	
	public void afterTextChanged(Editable s) {
		try {
			mListItem.setQuantity(Integer.valueOf(s.toString()));
		} catch (NumberFormatException e) {
			mListItem.setQuantity(0);
		}
	}

	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}
}
