package com.wbh.loewe.shoppinglist;

import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Expandable lists backed by a Simple Map-based adapter
 */
public class GUI_ExpandActivity extends ExpandableListActivity 
{
    private static final String NAME = "NAME";
    private static final String IS_EVEN = "IS_EVEN";
    private String[] Kategorien = {"Gemüse", 
    		"Kräuter", 
    		"Gewürze", 
    		"Obst", 
    		"Konserven", 
    		"Molkereiprodukte", 
    		"Lebensmittel", 
    		"Verpackungen", 
    		"Reinigungsmittel", 
    		"Hygieneartikel", 
    		"Getränke"};
    private String[] Artikel = {"Artikel 1", "Artikel 2", "Artikel 3"};
    
    
    private ExpandableListAdapter mAdapter;
    
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        //setContentView();
       
        List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
        List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
        
        for (int i = 0; i < 11; i++)
        {
        	Map<String, String> curGroupMap = new HashMap<String, String>();
        	groupData.add(curGroupMap);
        	curGroupMap.put(NAME, Kategorien[i]);
        	
        	List<Map<String, String>> children = new ArrayList<Map<String, String>>();
        	
        	for (int j = 0; j < 3; j++)
            {
            	Map<String, String> curChildMap = new HashMap<String, String>();
            	children.add(curChildMap);
            	curChildMap.put(NAME,  Artikel[j]);  
            	
            }
                childData.add(children);
        }
        
        
        // Set up our adapter
        mAdapter = new SimpleExpandableListAdapter(
                this,
                groupData,
                android.R.layout.simple_expandable_list_item_1,
                new String[] { NAME, IS_EVEN },
                new int[] { android.R.id.text1, android.R.id.text2 },
                childData,
                android.R.layout.simple_expandable_list_item_2,
                new String[] { NAME, IS_EVEN },
                new int[] { android.R.id.text1, android.R.id.text2 }
                );
        setListAdapter(mAdapter);
	}

	
}
