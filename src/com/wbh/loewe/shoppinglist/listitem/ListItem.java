package com.wbh.loewe.shoppinglist.listitem;



public class ListItem  {

	protected int mID;
	protected String mName;
		
	public ListItem() {
		this.mID = -1;
		this.mName = "";
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
