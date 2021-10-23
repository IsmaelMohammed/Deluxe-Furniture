package com.example.deluxee;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Cart_list_view extends ActionBarActivity {
TextView pname,Unit_Price,contact,desc;
JSONObject json = null;

String result=null,result2=null,user_id=null,typ,operation,pro_id;
String line=null,username,password,u,p,quant;
InputStream is=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart_list_view);
		pname=(TextView)findViewById(R.id.pname);
		Unit_Price=(TextView)findViewById(R.id.Unit_Price);
		contact=(TextView)findViewById(R.id.contact);
		desc=(TextView)findViewById(R.id.desc);

		ActionBar actionBar = getSupportActionBar();
	    actionBar.setHomeButtonEnabled(true);
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    
	    actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#153221")));
		final String pnam=getIntent().getExtras().getString("name");

		pname.setText(pnam);
		get_pro_id();
		
	}
public void get_pro_id(){
	String pid=getIntent().getExtras().getString("orderid");
	
		
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		 
		nameValuePairs.add(new BasicNameValuePair("pid",pid));
	    	
	    	try
	    	{
			HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://192.168.43.173/get_pro_id.php");
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
	            	pro_id=result;
	            	get_product_info(pro_id);
	            	//Toast.makeText(getApplicationContext(), user_id, Toast.LENGTH_LONG).show();
		}
	        catch(Exception e)
	    	{
			Log.e("Fail 2", e.toString());
			Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
		}     
	       
	        
		
		
	
	
	
}
public void get_product_info(String y){
	
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		 
		nameValuePairs.add(new BasicNameValuePair("pro_id",y));
		nameValuePairs.add(new BasicNameValuePair("typ","user"));

	    	
	    	try
	    	{
			HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost   = new HttpPost("http://192.168.43.173/get_pro_info.php");
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
	       		    sb.append(line + "\n");
	           	}
	            	is.close();
	            	result = sb.toString();
	            	//Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
		        Log.e("pass 2", "connection success ");
		}
	        catch(Exception e)
	    	{
			Log.e("Fail 2", e.toString());
			Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
		}     
	        JSONArray jArray;
			try {
				jArray = new JSONArray(result);
				json = jArray.getJSONObject(0);
				String loc=json.getString("Product_Location");
				String descc=json.getString("Description");
				String prc=json.getString("Unit_Price");

				Unit_Price.setText(prc);
				desc.setText(descc);
				contact.setText(loc);
				 
				Toast.makeText(getApplicationContext(), "Location "+loc,Toast.LENGTH_LONG).show();
				
				
				 
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       	 
	       
		
	

	//Toast.makeText(getApplicationContext(), usr, Toast.LENGTH_LONG).show();
}

public void ccart(View v){
	String pid=getIntent().getExtras().getString("orderid");


		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	 	 
		nameValuePairs.add(new BasicNameValuePair("pro_id",pid));

		
		 
	    	try
	    	{
			HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://192.168.43.173/cancelcart.php");
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
	            	Toast.makeText(getApplicationContext(), "CART Canceled!!", Toast.LENGTH_LONG).show();
	            	Cart_list_view.this.finish();
	            	}else{
		           
	            	}
		        Log.e("pass 2", "connection success ");
		}
	        catch(Exception e)
	    	{
			Log.e("Fail 2", e.toString());
			//Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
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
}
