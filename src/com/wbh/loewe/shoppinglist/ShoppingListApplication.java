package com.wbh.loewe.shoppinglist;

import com.wbh.loewe.shoppinglist.database.ShoppingListDatabaseAdapter;

import android.app.Application;

public class ShoppingListApplication extends Application {
	
	private ShoppingListDatabaseAdapter mDBAdapter;
	
	@Override
    public void onCreate() {
        super.onCreate();
        mDBAdapter = new ShoppingListDatabaseAdapter(getApplicationContext());
        mDBAdapter.open();
    }

	@Override
	public void onTerminate() {
		if (mDBAdapter != null) {
			mDBAdapter.close();
		}
		super.onTerminate();
	}
	
	public ShoppingListDatabaseAdapter getDBAdapter() {
		return mDBAdapter;
	}
}
