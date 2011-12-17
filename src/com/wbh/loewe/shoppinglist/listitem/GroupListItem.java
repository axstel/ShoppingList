package com.wbh.loewe.shoppinglist.listitem;


public class GroupListItem  extends ListItem {

	private int mPos;
	private Boolean mIsExpanded;
		
	public GroupListItem(int aID, String aName, int aPos, Boolean aIsExpanded) {
		super(aID, aName);
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
