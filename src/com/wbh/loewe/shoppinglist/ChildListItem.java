package com.wbh.loewe.shoppinglist;


public class ChildListItem  {

	private int mID;
	private int mGroupPos;
	private int mChildPos;
		
	public ChildListItem(int aID, int aGroupPos, int aChildPos) {
		this.mID = aID;
		this.mGroupPos = aGroupPos;
		this.mChildPos = aChildPos;
	}
	
	public void setID(int aID) {
		
		this.mID = aID;
	}
	
	public int getID() {
		
		return this.mID;
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
