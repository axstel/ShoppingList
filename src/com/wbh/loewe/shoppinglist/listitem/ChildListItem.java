package com.wbh.loewe.shoppinglist.listitem;

import android.text.Editable;
import android.text.TextWatcher;


public class ChildListItem extends ListItem implements TextWatcher {

	private int mArticleID;
	private int mGroupPos;
	private int mChildPos;
	
	public ChildListItem() {
		super();
		this.mGroupPos = -1;
		this.mChildPos = -1;
	}

	public void setArticleID(int aID) {
		
		this.mArticleID = aID;
	}
	
	public int getArticleID() {
		
		return this.mArticleID;
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
	
	public void afterTextChanged(Editable s) {
		
	}

	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub
		
	}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}
}
