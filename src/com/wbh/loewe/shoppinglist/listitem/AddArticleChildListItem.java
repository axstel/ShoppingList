package com.wbh.loewe.shoppinglist.listitem;

import android.text.Editable;
import android.text.TextWatcher;


public class AddArticleChildListItem extends ChildListItem implements TextWatcher {

	private int mGroupPos;
	private int mChildPos;
	private int mQuantity;
	private boolean mSelected;
	
	public AddArticleChildListItem() {
		super();
		this.mGroupPos = -1;
		this.mChildPos = -1;
		this.mSelected = false;
		this.mQuantity = 1;
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
	
	public void setSelected(boolean aSelected) {
		this.mSelected = aSelected;
	}
	
	public boolean getSelected() {
		return this.mSelected;
	}
	
	public void setQuantity(int aValue) {
		this.mQuantity = aValue;
	}
	
	public int getQuantity() {
		return this.mQuantity;
	}
	
	public void afterTextChanged(Editable s) {
		
	}

	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub
		
	}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}
}
