package com.wbh.loewe.shoppinglist;


public class GroupListItem  {

	private int mPos;
	private Boolean mIsExpanded;
		
	public GroupListItem(int aPos, Boolean aIsExpanded) {
		this.mPos = aPos;
		this.mIsExpanded = aIsExpanded;
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
