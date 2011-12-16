package com.wbh.loewe.shoppinglist;


public class GroupListItem  {

	private int mID;
	private int mPos;
	private Boolean mIsExpanded;
		
	public GroupListItem(int aID, int aPos, Boolean aIsExpanded) {
		this.mID = aID;
		this.mPos = aPos;
		this.mIsExpanded = aIsExpanded;
	}

	public void setID(int aID) {
		
		this.mID = aID;
	}
	
	public int getID() {
		
		return this.mID;
	}
	
	public void setPos(int aPos) {
		
		this.mPos = aPos;
	}
	
	public int getPos() {
		
		return this.mPos;
	}
	
	public void setIsExpanded(Boolean aIsExpanded) {
		
		this.mIsExpanded = aIsExpanded;
	}
	
	public Boolean getIsExpanded() {
		
		return this.mIsExpanded;
	}
}
