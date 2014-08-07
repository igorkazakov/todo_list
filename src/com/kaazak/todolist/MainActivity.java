 package com.kaazak.todolist;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends Activity
{

	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private ActionBarDrawerToggle drawerToggle;
	private CharSequence drawerTitle;
	private CharSequence title;
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		title = drawerTitle = getTitle();
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
		drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
		drawerList = (ListView)findViewById(R.id.list_slidermenu);
		
		navDrawerItems = new ArrayList<NavDrawerItem>();
		
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
		
		navMenuIcons.recycle();
		
		adapter = new NavDrawerListAdapter(getApplicationContext(), 
				navDrawerItems);
		drawerList.setAdapter(adapter);
		drawerList.setOnItemClickListener(new SlideMenuClickListener());
		
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_drawer,
				R.string.app_name,
				R.string.app_name)
		{
			public void onDrawerClosed(View view)
			{
				getActionBar().setTitle(title);
				invalidateOptionsMenu();
			}
			
			public void onDrawerOpened(View view)
			{
				getActionBar().setTitle(drawerTitle);
				invalidateOptionsMenu();
			}
		};
		
		drawerLayout.setDrawerListener(drawerToggle);
		
		if(savedInstanceState == null)
		{
			displayView(0);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
        case R.id.action_settings:
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}
	
	public void setTitle(CharSequence title)
	{
		this.title = title;
		getActionBar().setTitle(title);
	}
	
	protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }
 
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        drawerToggle.onConfigurationChanged(newConfig);
    }
    
    public class SlideMenuClickListener implements ListView.OnItemClickListener
    {

    	@Override
    	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
    	{
    		displayView(position);
    	}
    }
    
    public void displayView(int position)
	{
		switch(position)
		{
			case 0:
				Intent intent = new Intent(this, NoteActivity.class);
				startActivity(intent);
				break;
			case 1:
				//fragment = new reminderFragment();
				break;
			case 2:
				//fragment = new informationFragment();
				break;
			case 3:
				//fragment = new settingsFragment();
				break;
		}
		
			drawerList.setItemChecked(position, true);
			drawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			drawerLayout.closeDrawer(drawerList);
		
	}
}
