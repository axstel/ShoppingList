package com.wbh.loewe.shoppinglist;


public class ChildListItem  {

	private int mGroupPos;
	private int mChildPos;
		
	public ChildListItem(int aGroupPos, int aChildPos) {
		this.mGroupPos = aGroupPos;
		this.mChildPos = aChildPos;
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
}
