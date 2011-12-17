package com.wbh.loewe.shoppinglist.listitem;

import android.widget.EditText;


public class ChildListItem extends ListItem {

	private int mGroupPos;
	private int mChildPos;
	private int mQuantity;
	private EditText mQuantityEdit;
		
	public ChildListItem(int aID, String aName, int aGroupPos, int aChildPos) {
		super(aID, aName);
		this.mGroupPos = aGroupPos;
		this.mChildPos = aChildPos;
		this.mQuantity = 0;
		this.mQuantityEdit = null;
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
	
	public void setQuantity(int aQuantity) {
		
		this.mQuantity = aQuantity;
	}
	
	public int getQuantity() {
		
		return this.mQuantity;
	}
	
	public void setQuantityEdit(EditText aEdit) {
		
		this.mQuantityEdit = aEdit;
	}
	
	public EditText getQuantityEdit() {
		
		return this.mQuantityEdit;
	}
}
