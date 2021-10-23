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
import android.app.Notification;
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
import android.support.v7.app.ActionBar;
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

public class Manager extends ActionBarActivity implements FetchDataListener, OnItemClickListener  {
	int mPosition = -1;	
	String mTitle = "";
	 String line=null,username,password,u,p,user_id;
	    InputStream is=null;
	    String result=null;
	// Array of strings storing country names
    String[] mlabel ;
	
    // Array of integers points to images stored in /res/drawable-ldpi/
    int[] menu = new int[]{
            R.drawable.homw,
            R.drawable.newp,
            R.drawable.appr,
         //   R.drawable.creaacco,
            R.drawable.sold,
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
	final private String label = "label";	
	final private String COUNT = "count";
	ListView li;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manager);
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setHomeButtonEnabled(true);
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#153221")));
    	 li=(ListView)findViewById(R.id.list);
		// Getting an array of country names
    	 final String unam=getIntent().getExtras().getString("uname");
    	 
    	 final String psw=getIntent().getExtras().getString("psw");
		mlabel = getResources().getStringArray(R.array.Manager);
		
		// Title of the activity
		mTitle = (String)getTitle();
		
		// Getting a reference to the drawer listview
		mDrawerList = (ListView) findViewById(R.id.drawer_list);
		
		// Getting a reference to the sidebar drawer ( Title + ListView )
		mDrawer = ( LinearLayout) findViewById(R.id.drawer);
		
		// Each row in the list stores country name, count and flag
        mList = new ArrayList<HashMap<String,String>>();

        //268621
        for(int i=0;i<7;i++){
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
                	String unam=getIntent().getExtras().getString("uname");
               	 
               	 String psw=getIntent().getExtras().getString("psw");
                	Intent list=new Intent(Manager.this,Disp_supplier_Product.class);
                	 list.putExtra("uname", unam);
                     list.putExtra("psw", psw);
                	startActivity(list);
                    break;
                
                case 2:
                	String uname=getIntent().getExtras().getString("uname");
                  	 
                  	 String pswe=getIntent().getExtras().getString("psw");
                	Intent aproved=new Intent(Manager.this,Approved_Product.class);
                	aproved.putExtra("uname", uname);
                	aproved.putExtra("typ", "manager");

                	aproved.putExtra("psw", pswe);
               	startActivity(aproved);
                    break;
                case 3:
                	String soldnam=getIntent().getExtras().getString("uname");
                  	 
                  	 String soldpsas=getIntent().getExtras().getString("psw");
                	Intent soldo=new Intent(Manager.this,Sold_out_product.class);
               	 soldo.putExtra("uname", soldnam);
                  	 soldo.putExtra("operation", "soldout");
                  	soldo.putExtra("typ", "manager");
                    soldo.putExtra("psw", soldpsas);
                    startActivity(soldo);
                    break;
                
                
                
                case 4:
                	String unamm=getIntent().getExtras().getString("uname");
                  	 
                  	 String pswm=getIntent().getExtras().getString("psw");
                	Intent ord=new Intent(Manager.this,Orderd_Prod_list.class);
               	 ord.putExtra("uname", unamm);
                    ord.putExtra("psw", pswm);
                    ord.putExtra("usr", "Manager");
               	startActivity(ord);
                	break;
                case 5:
                	String chunam=getIntent().getExtras().getString("uname");
                 	 
                 	 String chpas=getIntent().getExtras().getString("psw");
               	Intent change=new Intent(Manager.this,Change_Password.class);
              	 change.putExtra("uname", chunam);
                   change.putExtra("psw", chpas);
                   startActivity(change);
                	break;
                case 6:
                	
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
				*/
				// Closing the drawer
				mDrawerLayout.closeDrawer(mDrawer);		
			}
		});
        
        
        // Enabling Up navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);     
        
        getSupportActionBar().setDisplayShowHomeEnabled(true);        

        // Setting the adapter to the listView
        get_user_id();
        check_update();
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
			Intent hom=new Intent(Manager.this,MainActivity.class);
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
	            	Toast.makeText(getApplicationContext(), user_id, Toast.LENGTH_LONG).show();
		}
	        catch(Exception e)
	    	{
			Log.e("Fail 2", e.toString());
			Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
		}     
	       
	        
		
		
	}
	
@SuppressWarnings("deprecation")
public void check_update(){
	
	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
  	 
	nameValuePairs.add(new BasicNameValuePair("user_id",user_id));
	
	 
    	try
    	{
		HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://192.168.43.173/check_new_updt.php");
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
            	         .setContentTitle("Order Accepted")
            	         .setContentText("Your Product Request has been accepted !!");

            	      Intent notificationIntent = new Intent(this, MainActivity.class);
            	      PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
            	         PendingIntent.FLAG_UPDATE_CURRENT);
            	      builder.setContentIntent(contentIntent);

            	      // Add as notification
            	      NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            	      manager.notify(0, builder.build());
          	Vibrator v =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
          	v.vibrate(2000);
            	}
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
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		String fromm="",ID;
		try{
		View curr = parent.getChildAt((int) id);
		TextView c = (TextView)curr.findViewById(R.id.titleTxt);
		TextView c2 = (TextView)curr.findViewById(R.id.titleTxt2);
		TextView c3 = (TextView)curr.findViewById(R.id.titleTxt6);

		String usr=getIntent().getExtras().getString("uname");
     	 String Pid=c3.getText().toString();
     	 String pss=getIntent().getExtras().getString("psw"); 
		fromm=c.getText().toString();
		
		 ID=c2.getText().toString();
		 Intent disp=new Intent(Manager.this,Product_Display.class);
		 disp.putExtra("user", "manager");
		 disp.putExtra("pid", Pid);
		 disp.putExtra("uname", usr);
		 disp.putExtra("psw", pss);
		 
		 
		 startActivity(disp);
		//Toast.makeText(getApplicationContext(), fromm , Toast.LENGTH_LONG).show();
		}
		catch(Exception e){
			Toast.makeText(getApplicationContext(),fromm, Toast.LENGTH_LONG).show();
		}
		
	}
	
	

	
	// Highlight the selected country : 0 to 4

}