package com.example.deluxee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements FetchDataListener, OnItemClickListener  {
	int mPosition = -1;	
	String mTitle = "";
	ListView li;
	// Array of strings storing country names
    String[] mlabel ;
    
    // Array of integers points to images stored in /res/drawable-ldpi/
    int[] menu = new int[]{
                R.drawable.homw,
                R.drawable.login,
                R.drawable.creaacco,
                R.drawable.about,
                R.drawable.share,
               
               R.drawable.exit,
    };

    // Array of strings to initial counts
    String[] mCount = new String[]{
        "", "", "", "", "", 
        "", "", "", "", "" };
	
	private DrawerLayout mDrawerLayout;	
	private ListView mDrawerList;	
	private ActionBarDrawerToggle mDrawerToggle;	
	private LinearLayout mDrawer ;	
	private List<HashMap<String,String>> mList ;	
	private SimpleAdapter mAdapter;	
	final private String menuu = "menu";	
	final private String label = "label";	
	final private String COUNT = "count";
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
    	//getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#384921")));
    	 li=(ListView)findViewById(R.id.list);
		// Getting an array of country names
		mlabel = getResources().getStringArray(R.array.Menu);
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setHomeButtonEnabled(true);
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#153221")));
		// Title of the activity
		mTitle = (String)getTitle();
		
		// Getting a reference to the drawer listview
		mDrawerList = (ListView) findViewById(R.id.drawer_list);
		
		// Getting a reference to the sidebar drawer ( Title + ListView )
		mDrawer = ( LinearLayout) findViewById(R.id.drawer);
		
		// Each row in the list stores country name, count and flag
        mList = new ArrayList<HashMap<String,String>>();

        //268621
        for(int i=0;i<6;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put(menuu, mlabel[i]);
            hm.put(COUNT, mCount[i]);
            hm.put(label, Integer.toString(menu[i]) );
            mList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = { label,menuu,COUNT };

        // Ids of views in listview_layout
        int[] to = { R.id.icon , R.id.icontext  };

        // Instantiating an adapter to store each items
        // R.layout.drawer_layout defines the layout of each item
        mAdapter = new SimpleAdapter(this, mList, R.layout.drawer_layout, from, to);
        
        // Getting reference to DrawerLayout
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);        
        
        // Creating a ToggleButton for NavigationDrawer with drawer event listener
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer , R.string.drawer_open,R.string.drawer_close){
        	
        	 /** Called when drawer is closed */
            public void onDrawerClosed(View view) {               
            	 
            	supportInvalidateOptionsMenu();       
            }

            /** Called when a drawer is opened */
            public void onDrawerOpened(View drawerView) {            	
                getSupportActionBar().setTitle("Deluxe Furniture");  
               
            	supportInvalidateOptionsMenu();                
            }
        };
       
        // Setting event listener for the drawer
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
        // ItemClick event handler for the drawer items
        mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@TargetApi(Build.VERSION_CODES.GINGERBREAD)
			@SuppressLint("NewApi")
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				
				// Increment hit count of the drawer list item
				switch (position) {
				case 0:
                    Toast.makeText(getBaseContext(), "Home", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                	Intent i=new Intent(MainActivity.this, Login.class);
    				startActivity(i);
                    break;
                
                case 2:
                	Intent i2=new Intent(MainActivity.this, Account.class);
    				startActivity(i2);
                    break;
                
                
                case 3:
                    
                	Intent ii=new Intent(MainActivity.this, ALogin.class);
    				startActivity(ii);
                    break;
                case 4:
                	Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND); 
                	sharingIntent.setType("text/plain");
                	String shareBody = "Share Deluxe App";
                	sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share This App");
                	sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                	startActivity(Intent.createChooser(sharingIntent, "Share via"));
                    break;
                    
                case 5:
                	moveTaskToBack(true);
                	android.os.Process.killProcess(android.os.Process.myPid());
                	System.exit(1);
                	break;
            }
				/*incrementHitCount(position);			
				 
				if(position < 5) { // Show fragment for countries : 0 to 4				
					showFragment(position);
				}else{ // Show message box for countries : 5 to 9				
					Toast.makeText(getApplicationContext(), mCountries[position], Toast.LENGTH_LONG).show();
				}
				/*incrementHitCount(position);			
				 
				if(position < 5) { // Show fragment for countries : 0 to 4				
					showFragment(position);
				}else{ // Show message box for countries : 5 to 9				
					Toast.makeText(getApplicationContext(), mCountries[position], Toast.LENGTH_LONG).show();
				}
				*/
				// Closing the drawer
				mDrawerLayout.closeDrawer(mDrawer);		
			}
		});
        if(android.os.Build.VERSION.SDK_INT>9){
			StrictMode.ThreadPolicy pol=new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(pol);
	 }
        
        // Enabling Up navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);     
        
        getSupportActionBar().setDisplayShowHomeEnabled(true);        

        // Setting the adapter to the listView
        mDrawerList.setAdapter(mAdapter);  
        li.setOnItemClickListener(this);
        Get_product();

	}
	

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.action_settings:
			Intent hom=new Intent(MainActivity.this,MainActivity.class);
			startActivity(hom);
			break;
		}
		if (mDrawerToggle.onOptionsItemSelected(item)) {
	          return true;
	    }
		return super.onOptionsItemSelected(item);
	}	
			

	
	private void Get_product() {
		// show progress dialog
		 
		String url = "http://192.168.43.173/getpro.php";
		FetchProduct task = new FetchProduct(this);
		task.execute(url);
	}


	@Override
	public void onFetchComplete(List<meslis> dataa) {
		// TODO Auto-generated method stub
		ApplicationAdapter adapter = new ApplicationAdapter(this, dataa);
		li.setAdapter(adapter);
	}


	@Override
	public void onFetchFailure(String msgg) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		String fromm="",ID;
		try{
		View curr = parent.getChildAt((int) id);
		TextView c = (TextView)curr.findViewById(R.id.titleTxt);
		TextView c2 = (TextView)curr.findViewById(R.id.titleTxt2);
		TextView c3 = (TextView)curr.findViewById(R.id.titleTxt6);
		 fromm=c.getText().toString();
		String pid=c3.getText().toString();
		 ID=c2.getText().toString();
		 Intent disp=new Intent(MainActivity.this,Product_Display.class);
		 disp.putExtra("user", "gust");
		 disp.putExtra("pid", pid);
		 startActivity(disp);
		//Toast.makeText(getApplicationContext(), pid+ID+fromm , Toast.LENGTH_LONG).show();
		}
		catch(Exception e){
			Toast.makeText(getApplicationContext(),fromm, Toast.LENGTH_LONG).show();
		}
		
	}
	
	

	
	// Highlight the selected country : 0 to 4

}