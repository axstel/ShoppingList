package com.wbh.loewe.shoppinglist.listitem;



public class ListItem  {

	protected int mID;
	protected String mName;
		
	public ListItem(int aID, String aName) {
		this.mID = aID;
		this.mName = aName;
	}

	public void setID(int aID) {
		
		this.mID = aID;
	}
	
	public int getID() {
		
		return this.mID;
	}
	
	public void setName(String aName) {
		
		this.mName = aName;
	}
	
	public String getName() {
		
		return this.mName;
	}
	
}
