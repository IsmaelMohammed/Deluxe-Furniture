package com.example.deluxee;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Orderd_Prod_list extends ActionBarActivity implements FetchDataListener, OnItemClickListener {
	ListView li;
	String result=null,result2=null,user_id=null,typ;
	String line=null,username,password,u,p;
	InputStream is=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orderd__prod_list);
		li=(ListView)findViewById(R.id.list);
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setHomeButtonEnabled(true);
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#153221")));
	    li.setOnItemClickListener(this);
	    typ=getIntent().getExtras().getString("usr");
	   
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
	            	if(typ.equalsIgnoreCase("seller")){
	            		Get_branch();
	            	}else{
	            	 Get_product(user_id,typ);
	            	}
		}
	        catch(Exception e)
	    	{
			Log.e("Fail 2", e.toString());
			Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
		}   
	        
	       
	        
		
		
	}
	
	
	public void Get_branch(){
		
		final String unam=getIntent().getExtras().getString("uname");
		final String psw=getIntent().getExtras().getString("psw");
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		 
		nameValuePairs.add(new BasicNameValuePair("username",unam));
		nameValuePairs.add(new BasicNameValuePair("password",psw));

	    	
	    	try
	    	{
			HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://192.168.43.173/get_branch.php");
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
	            	String branch=result;
            		
	            	get_product(branch);
	        }
	            	
	        catch(Exception e)
	    	{
			Log.e("Fail 2", e.toString());
			Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
		}   
	}
	private void get_product(String branch){
		String url = "http://192.168.43.173/custemer_order2.php";
		FetchOrderdList task = new FetchOrderdList(this);
		task.execute(url,branch , typ);
	}
	private void Get_product( String id , String typ) {
		// show progress dialog
		
		 if(typ.equalsIgnoreCase("seller")){
			
		String url = "http://192.168.43.116/custemer_order.php";
		FetchOrderdList task = new FetchOrderdList(this);
		task.execute(url,id , typ);
		 }else if(typ.equalsIgnoreCase("Customer")){
				
				String url = "http://192.168.43.173/custemer_order.php";
				FetchOrderdList task = new FetchOrderdList(this);
				task.execute(url,id , typ);
				 }  else {
			 
			 String url = "http://192.168.43.173/suplier.php";
				FetchOrderdList task = new FetchOrderdList(this);
				task.execute(url,id , typ); 
		 }
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem)
	{       
	    switch (menuItem.getItemId()) {
	        case android.R.id.home:
	            onBackPressed();
	            return true;
	        default:
	            return super.onOptionsItemSelected(menuItem);
	    }
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
		try{
		View curr = arg0.getChildAt((int) arg3);
		TextView c = (TextView)curr.findViewById(R.id.titleTxt);
		TextView c3 = (TextView)curr.findViewById(R.id.titleTxt6);
		TextView c2 = (TextView)curr.findViewById(R.id.titleTxt2);
		String pname = c.getText().toString();
		String price=c2.getText().toString();
		String id=c3.getText().toString();
		 Intent disp=new Intent(Orderd_Prod_list.this,View_order.class);
			
	 disp.putExtra("orderid", id);
		 disp.putExtra("usr", typ);
		 
		 startActivity(disp);
		
		}
		catch(Exception e){
			Toast.makeText(getApplicationContext(),"", Toast.LENGTH_LONG).show();
		}
	}
}
