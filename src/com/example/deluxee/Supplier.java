package com.example.deluxee;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
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

public class Supplier extends ActionBarActivity implements FetchDataListener, OnItemClickListener  {
	int mPosition = -1;	
	String mTitle = "";
	String result=null,result2=null,user_id;
	String line=null,username,password,u,p;
	InputStream is=null;
	// Array of strings storing country names
    String[] mlabel ;
	
    // Array of integers points to images stored in /res/drawable-ldpi/
    int[] menu = new int[]{
            R.drawable.homw,
            R.drawable.addnewpro,
            R.drawable.myorder,
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
	private String unam;
	private String psw;	
	final private String label = "label";	
	final private String COUNT = "count";
	ListView li;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_supplier);
    	getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#384921")));
    	 li=(ListView)findViewById(R.id.list);
		// Getting an array of country names
		mlabel = getResources().getStringArray(R.array.supplier);
		
		// Title of the activity
		mTitle = (String)getTitle();
		
		// Getting a reference to the drawer listview
		mDrawerList = (ListView) findViewById(R.id.drawer_list);
		
		// Getting a reference to the sidebar drawer ( Title + ListView )
		mDrawer = ( LinearLayout) findViewById(R.id.drawer);
		
		// Each row in the list stores country name, count and flag
        mList = new ArrayList<HashMap<String,String>>();

        //268621
        for(int i=0;i<5;i++){
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
                	
                	Intent i=new Intent(Supplier.this,AddProduct.class);
                	i.putExtra("uname", unam);
                    i.putExtra("psw", psw);
                	startActivity(i);
                    break;
                
                case 2:
                	
                	Intent ord=new Intent(Supplier.this,Orderd_Prod_list.class);
                	ord.putExtra("uname", unam);
                    ord.putExtra("psw", psw);
                    ord.putExtra("usr", "Supplier");
                	startActivity(ord);
                	break;
                
                
                case 3:
                     
              	Intent change=new Intent(Supplier.this,Change_Password.class);
             	 change.putExtra("uname", unam);
                  change.putExtra("psw", psw);
                  startActivity(change);
                    break;
                case 4:
                	moveTaskToBack(true);
                	android.os.Process.killProcess(android.os.Process.myPid());
                	System.exit(1);
                	break;
                case 5:
                	
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
     unam=getIntent().getExtras().getString("uname");
		psw=getIntent().getExtras().getString("psw");
        mDrawerList.setAdapter(mAdapter);  
        li.setOnItemClickListener(this);
        Get_product();
        get_user_id();
	}
	
	public void get_user_id(){
		final String unam=getIntent().getExtras().getString("uname");
		final String psw=getIntent().getExtras().getString("psw");
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		 
		nameValuePairs.add(new BasicNameValuePair("username",unam));
		nameValuePairs.add(new BasicNameValuePair("password",psw));
	    	
	    	try
	    	{
			HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://192.168.43.173/get_id.php");
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
		        HttpResponse response = httpclient.execute(httppost); 
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();
		}
	        catch(Exception e)
		{
		    	Toast.makeText(getApplicationContext(), "Can not Connect To The Server Please Check Your Connection",
				Toast.LENGTH_LONG).show();
		}     
	        
	        try
	        {
	         	BufferedReader reader = new BufferedReader
					(new InputStreamReader(is));
	            	StringBuilder sb = new StringBuilder();
	            	while ((line = reader.readLine()) != null)
			{
	       		    sb.append(line);
	           	}
	            	is.close();
	            	result = sb.toString();
	            	user_id=result;
	            	check_update(user_id);
		}
	        catch(Exception e)
	    	{
			Log.e("Fail 2", e.toString());
			Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
		}     
	       
	        
		
		
	}
	
	
	@SuppressWarnings("deprecation")
	public void check_update(String x ){
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	  	 
		nameValuePairs.add(new BasicNameValuePair("user_id",x));
		
		 
	    	try
	    	{
			HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://192.168.43.116/check_new_order.php");
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
		        HttpResponse response = httpclient.execute(httppost); 
		        HttpEntity entity = response.getEntity();
		        is = entity.getContent();
		        
		}
	        catch(Exception e)
		{
		    	//Toast.makeText(getApplicationContext(), "Can not Connect To The Server Please Check Your Connection",
				//Toast.LENGTH_LONG).show();
		result="error";
		}     
	        
	        try
	        {
	         	BufferedReader reader = new BufferedReader
					(new InputStreamReader(is));
	            	StringBuilder sb = new StringBuilder();
	            	while ((line = reader.readLine()) != null)
			{
	       		    sb.append(line);
	           	}
	            	is.close();
	            	result = sb.toString();
	            	if(result.equalsIgnoreCase("1")){
	            	NotificationCompat.Builder builder =
	            	         new NotificationCompat.Builder(this)
	            	         .setSmallIcon(R.drawable.deluxx)
	            	         .setContentTitle("New Furniture Order")
	            	         .setContentText("Dear User You have new order from DeluxeFurniture Please Review!!");

	            	      Intent notificationIntent = new Intent(this, Orderd_Prod_list.class);
	            	      notificationIntent.putExtra("uname", unam);
	            	      notificationIntent.putExtra("psw", psw);
	            	      notificationIntent.putExtra("usr", "Supplier");
	            	      PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
	            	         PendingIntent.FLAG_UPDATE_CURRENT);
	            	      builder.setContentIntent(contentIntent);

	            	      // Add as notification
	            	      NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	            	      manager.notify(0, builder.build());
	            	
	            	      Vibrator v =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	                    	v.vibrate(2000);	            	}
		        Log.e("pass 2", "connection success ");
		}
	        catch(Exception e)
	    	{
			Log.e("Fail 2", e.toString());
			//Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
		}
	        
			//return result; 

		
		
		
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
			Intent hom=new Intent(Supplier.this,MainActivity.class);
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
		 
		String url = "http://192.168.43.173/getsuppro.php";
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
		 String Pid=c3.getText().toString();
		 ID=c2.getText().toString();
		 Intent disp=new Intent(Supplier.this,Product_Display.class);
		 disp.putExtra("user", "supl");
		 disp.putExtra("pid", Pid);
		 startActivity(disp);
		}
		catch(Exception e){
			Toast.makeText(getApplicationContext(),fromm, Toast.LENGTH_LONG).show();
		}
		
	}
	
	

	
	// Highlight the selected country : 0 to 4

}