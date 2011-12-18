package com.wbh.loewe.shoppinglist.listitem;

import android.widget.EditText;
import android.widget.TextView;


public class ChildListItem extends ListItem {

	private int mGroupPos;
	private int mChildPos;
	private EditText mQuantityEdit;
	private TextView mQuantityLabel;
		
	public ChildListItem(int aID, String aName, int aGroupPos, int aChildPos) {
		super(aID, aName);
		this.mGroupPos = aGroupPos;
		this.mChildPos = aChildPos;
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
	
	public void setQuantityEdit(EditText aEdit) {
		
		this.mQuantityEdit = aEdit;
	}
	
	public EditText getQuantityEdit() {
		
		return this.mQuantityEdit;
	}
	
	public void setQuantityLabel(TextView aText) {
		
		this.mQuantityLabel = aText;
	}
	
	public TextView getQuantityLabel() {
		
		return this.mQuantityLabel;
	}
	
	public int getQuantity() {
		int lQuantity = 0;
		if (this.mQuantityEdit != null) {
			try {
				String lText = this.mQuantityEdit.getText().toString();
				lQuantity = Integer.valueOf(lText);
			} catch (NumberFormatException e) {
				lQuantity = 0;
			}
		}
		return lQuantity;
	}
}
