package com.wbh.loewe.shoppinglist.listitem;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;


public class UseListChildListItem extends ChildListItem implements TextWatcher {

	private int mGroupPos;
	private int mChildPos;
	private int mSelected;
	private TextView mTxtArticle;
	private TextView mTxtQuantity;
	private TextView mTxtQuantityName;
	
	public UseListChildListItem() {
		super();
		this.mGroupPos = -1;
		this.mChildPos = -1;
		this.mSelected = -1;
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
		if (aSelected) {
			this.mSelected = 1;
		} else {
			this.mSelected = 0;
		}
	}
	
	public boolean getSelected() {
		if (this.mSelected == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public int getSelectedInt() {
		return this.mSelected;
	}
	
	public void setTxtArticle(TextView aView) {
		this.mTxtArticle = aView;
	}
	
	public TextView getTxtArticle() {
		return this.mTxtArticle;
	}
	
	public void setTxtQuantity(TextView aView) {
		this.mTxtQuantity = aView;
	}
	
	public TextView getTxtQuantity() {
		return this.mTxtQuantity;
	}
	
	public void setTxtQuantityName(TextView aView) {
		this.mTxtQuantityName = aView;
	}
	
	public TextView getTxtQuantityName() {
		return this.mTxtQuantityName;
	}
	
	public void afterTextChanged(Editable s) {
		// nothing todo
	}

	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub
		
	}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}
}
