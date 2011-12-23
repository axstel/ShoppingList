package com.wbh.loewe.shoppinglist.listitem;

import android.text.Editable;
import android.text.TextWatcher;

import com.wbh.loewe.shoppinglist.ShoppingListApplication;


public class EditListChildListItem extends ChildListItem implements TextWatcher {

	private int mGroupPos;
	private int mChildPos;
	private float mQuantity;
	private ShoppingListApplication mShoppinglistapp;
	
	public EditListChildListItem() {
		super();
		this.mGroupPos = -1;
		this.mChildPos = -1;
		this.mShoppinglistapp = null;
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
	
	public void setQuantity(float aValue) {
		this.mQuantity = aValue; 
	}
	
	public float getQuantity() {
		return mQuantity;
	}
	
	public void setApp(ShoppingListApplication aApp) {
		this.mShoppinglistapp = aApp;
	}
	
	public void afterTextChanged(Editable s) {
		
		String lText = s.toString();
		float lQuantity = -1;
		if (lText.length() > 0) {
			try {
				lQuantity = Float.valueOf(lText);
			}
			catch (NumberFormatException e) {
				lQuantity = 0; 
			}
		}
		
		if ((lQuantity != -1) && (lQuantity != mQuantity)) {
			mQuantity = lQuantity;
			//Log.d("afterTextChanged", this.getID() +" "+ s.toString() +" new:"+ this.getQuantity() +" old:"+ this.getQuantityOld());
			mShoppinglistapp.getDBAdapter().updateArticleQuantity(this.getID(), this.getQuantity());
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
