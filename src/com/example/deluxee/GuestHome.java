package com.example.deluxee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class GuestHome extends ActionBarActivity implements FetchDataListener, OnItemClickListener  {
	int mPosition = -1;	
	String mTitle = "";
	
	// Array of strings storing country names
    String[] mlabel ;
	
    // Array of integers points to images stored in /res/drawable-ldpi/
    int[] menu = new int[]{
                R.drawable.homw,
                R.drawable.cartlist,
                R.drawable.myorder,
                R.drawable.appr,

                R.drawable.changepass, 
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
	ListView li;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guest_home);
    	getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#153221")));
    	 li=(ListView)findViewById(R.id.list);
		// Getting an array of country names
		mlabel = getResources().getStringArray(R.array.GustMenu);
		
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
            	//highlightSelectedCountry();  
            	supportInvalidateOptionsMenu();       
            }

            /** Called when a drawer is opened */
            public void onDrawerOpened(View drawerView) {            	
               // getSupportActionBar().setTitle("Deluxe Furniture");  
               
            	supportInvalidateOptionsMenu();                
            }
        };
       
        // Setting event listener for the drawer
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
        // ItemClick event handler for the drawer items
        mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				
				// Increment hit count of the drawer list item
				switch (position) {
                case 0:
                    Toast.makeText(getBaseContext(), "Home", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                	Intent list=new Intent(GuestHome.this,Cart_list.class);
                	String uname=getIntent().getExtras().getString("uname");
                 	 
                 	 String pswe=getIntent().getExtras().getString("psw");
                 	list.putExtra("usr", "Customer");
              	list.putExtra("uname", uname);
                   list.putExtra("psw", pswe);
                   
                	startActivity(list);
                    break;
                
                case 2:
                	String usname=getIntent().getExtras().getString("uname");
                 	 
                 	 String pswer=getIntent().getExtras().getString("psw");
               	Intent ord=new Intent(GuestHome.this,Orderd_Prod_list.class);
              	 ord.putExtra("uname", usname);
                   ord.putExtra("psw", pswer);
                   ord.putExtra("usr", "Customer");
              	startActivity(ord);
                    break;
case 3:
	String unam=getIntent().getExtras().getString("uname");
 	 
 	 String psw=getIntent().getExtras().getString("psw");
	Intent aproved=new Intent(GuestHome.this,Approved_Product.class);
	aproved.putExtra("uname", unam);
	aproved.putExtra("typ", "user");

	aproved.putExtra("psw", psw);
	startActivity(aproved);
                	break;
                
                case 4:
                	String chunam=getIntent().getExtras().getString("uname");
                	 
                	 String chpas=getIntent().getExtras().getString("psw");
              	Intent change=new Intent(GuestHome.this,Change_Password.class);
             	 change.putExtra("uname", chunam);
                  change.putExtra("psw", chpas);
                  startActivity(change);
                	
                    break;
                case 5:
                	moveTaskToBack(true);
                	android.os.Process.killProcess(android.os.Process.myPid());
                	System.exit(1);
                	break;
                case 6:
                	
                	break;
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
			Intent hom=new Intent(GuestHome.this,MainActivity.class);
			startActivity(hom);
			break;
		}
		if (mDrawerToggle.onOptionsItemSelected(item)) {
	          return true;
	    }
		return super.onOptionsItemSelected(item);
	}	
			
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
		final String unam=getIntent().getExtras().getString("uname");
		final String psw=getIntent().getExtras().getString("psw");
		try{
		View curr = parent.getChildAt((int) id);
		TextView c = (TextView)curr.findViewById(R.id.titleTxt);
		TextView c2 = (TextView)curr.findViewById(R.id.titleTxt2);
		TextView c3 = (TextView)curr.findViewById(R.id.titleTxt6);
		String Pid=c3.getText().toString();
		 fromm=c.getText().toString();
		
		 ID=c2.getText().toString();
		 Intent disp=new Intent(GuestHome.this,Product_Display.class);
		 disp.putExtra("user", "user");
		 disp.putExtra("uname", unam);
		 disp.putExtra("psw", psw);
		 disp.putExtra("pid", Pid);
		 
		 startActivity(disp);
		Toast.makeText(getApplicationContext(), fromm , Toast.LENGTH_LONG).show();
		}
		catch(Exception e){
			Toast.makeText(getApplicationContext(),fromm, Toast.LENGTH_LONG).show();
		}
		
	}
	
	

	
	// Highlight the selected country : 0 to 4

}