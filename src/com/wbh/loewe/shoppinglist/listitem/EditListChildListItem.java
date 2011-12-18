package com.wbh.loewe.shoppinglist.listitem;

import android.text.Editable;
import android.text.TextWatcher;

import com.wbh.loewe.shoppinglist.ShoppingListApplication;
import com.wbh.loewe.shoppinglist.cursoradapter.CustomCursorTreeAdapter;


public class EditListChildListItem extends ChildListItem implements TextWatcher {

	private int mGroupPos;
	private int mChildPos;
	private int mQuantity;
	private ShoppingListApplication mShoppinglistapp;
	private int mListID;
	
	public EditListChildListItem() {
		super();
		this.mGroupPos = -1;
		this.mChildPos = -1;
		this.mShoppinglistapp = null;
		this.mListID = -1;
		this.mQuantity = -1;
	}

	public void setGroupPos(int aPos) {
		
		this.mGroupPos = aPos;
	}
	
	public int getGroupPos() {
		
		return this.mGroupPos;
	}
	
	public void setChildPos(int aPos) {
		
		this.mChildPos = aPos;
	}
	
	public int getChildPos() {
		
		return this.mChildPos;
	}
	
	public void setQuantity(int aValue) {
		this.mQuantity = aValue; 
	}
	
	public int getQuantity() {
		return mQuantity;
	}
	
	public void setListID(int aListID) {
		this.mListID = aListID;
	}
	
	public void setApp(ShoppingListApplication aApp) {
		this.mShoppinglistapp = aApp;
	}
	
	public void afterTextChanged(Editable s) {
		
		String lText = s.toString();
		int lQuantity = -1;
		if (lText.length() > 0) {
			try {
				lQuantity = Integer.valueOf(lText);
			}
			catch (NumberFormatException e) {
				lQuantity = 0; 
			}
		}
		
		
		if ((lQuantity != -1) && (lQuantity != mQuantity)) {
			mQuantity = lQuantity;
			//Log.d("afterTextChanged", this.getID() +" "+ s.toString() +" new:"+ this.getQuantity() +" old:"+ this.getQuantityOld());
			mShoppinglistapp.getDBAdapter().updateArticleQuantity(mListID, this.getID(), this.getQuantity());
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
