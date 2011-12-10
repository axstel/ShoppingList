package com.wbh.loewe.shoppinglist;


public class ListItem  {

	private int mID;
	private String mName;
	private boolean mSelected;
		
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
	
	public void setSelected(boolean aSelected) {
		
		this.mSelected = aSelected;
	}
	
	public boolean getSelected() {
		
		return this.mSelected;
	}
}
