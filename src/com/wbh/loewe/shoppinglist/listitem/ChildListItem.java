package com.wbh.loewe.shoppinglist.listitem;

import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.wbh.loewe.shoppinglist.ShoppingListApplication;
import com.wbh.loewe.shoppinglist.cursoradapter.CustomCursorTreeAdapter;
import com.wbh.loewe.shoppinglist.database.ShoppingListDatabase;


public class ChildListItem extends ListItem implements TextWatcher {

	private int mGroupPos;
	private int mChildPos;
	private EditText mQuantityEdit;
	private TextView mQuantityLabel;
	private int mQuantity;
	private ShoppingListApplication mShoppinglistapp;
	private int mListID;
	private CustomCursorTreeAdapter mAdapter;
	
	public ChildListItem(int aID, String aName, int aGroupPos, int aChildPos, int aListID, ShoppingListApplication aApp) {
		super(aID, aName);
		this.mGroupPos = aGroupPos;
		this.mChildPos = aChildPos;
		this.mQuantityEdit = null;
		this.mShoppinglistapp = aApp;
		this.mListID = aListID;
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
	
	public void setQuantityEdit(EditText aEdit) {
		
		this.mQuantityEdit = aEdit;
		mQuantity = getQuantity();
	}
	
	public EditText getQuantityEdit() {
		
		return this.mQuantityEdit;
	}
	
	public void setQuantityLabel(TextView aText) {
		
		this.mQuantityLabel = aText;
	}
	
	public TextView getQuantityLabel() {
		
		return this.mQuantityLabel;
	}
	
	public int getQuantity() {
		int lQuantity = 0;
		if (this.mQuantityEdit != null) {
			try {
				String lText = this.mQuantityEdit.getText().toString();
				lQuantity = Integer.valueOf(lText);
			} catch (NumberFormatException e) {
				lQuantity = 0;
			}
		}
		return lQuantity;
	}
	
	public int getQuantityOld() {
		return mQuantity;
	}
	
	public void setListID(int aListID) {
		this.mListID = aListID;
	}
	
	public void setAdapter(CustomCursorTreeAdapter aAdapter) {
		this.mAdapter = aAdapter;
	}
	
	public void afterTextChanged(Editable s) {
		if (this.getQuantity() != this.getQuantityOld()) {
			//Log.d("afterTextChanged", this.getID() +" "+ s.toString() +" new:"+ this.getQuantity() +" old:"+ this.getQuantityOld());
			mShoppinglistapp.getDBAdapter().updateArticleQuantity(mListID, this.getID(), this.getQuantity());
		}
		Cursor lGroupCursor = mAdapter.getGroup(this.getGroupPos());
		int lGroupID = lGroupCursor.getInt(lGroupCursor.getColumnIndex(ShoppingListDatabase.FIELD_NAME_ID));
		Cursor lChildCursor = mShoppinglistapp.getDBAdapter().fetchAllArticlesOfCategoryInList(mListID, lGroupID);
		mAdapter.setChildrenCursor(this.getGroupPos(), lChildCursor);
	}

	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}
}
