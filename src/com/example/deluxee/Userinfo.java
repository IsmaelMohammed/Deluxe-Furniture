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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Userinfo extends ActionBarActivity {
	String usr;
	TextView Fname,uname,psw,typ,status,phn;
	 String str = "";
	 Button block;
	JSONObject json = null;
	String result=null;
	 String fname;
	String line=null,username,password,u,p;
	InputStream is=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userinfo);
		Fname=(TextView)findViewById(R.id.fname);
		phn=(TextView)findViewById(R.id.phn);
		block=(Button)findViewById(R.id.block);
		uname=(TextView)findViewById(R.id.uname);
		psw=(TextView)findViewById(R.id.psw);
		typ=(TextView)findViewById(R.id.typ);
		status=(TextView)findViewById(R.id.status);
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setHomeButtonEnabled(true);
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#153221")));
		    usr=getIntent().getExtras().getString("userid");
		   get_user_info();
	}
	
	public void get_user_info(){

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			 
			nameValuePairs.add(new BasicNameValuePair("user_id",usr));

		    	
		    	try
		    	{
				HttpClient httpclient = new DefaultHttpClient();
			        HttpPost httppost = new HttpPost("http://192.168.43.173/get_user_info.php");
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
		       
		        try{
		        	 JSONArray jArray = new JSONArray(result);
		        	 json = jArray.getJSONObject(0);
		        	 Fname.setText(json.getString("FirstName")+" "+json.getString("LastName"));
		        	 uname.setText(json.getString("u_name"));
		        	 psw.setText(json.getString("password"));
		        	 typ.setText(json.getString("user_type"));
		        	 phn.setText(json.getString("Phonenumber"));
		        	 if(json.getString("account_status").equalsIgnoreCase("1")){
		        		 status.setTextColor(Color.parseColor("#00FF00"));
		        		 status.setText("ACTIVE");
		        	 } else{
		        		 block.setText("Un Block");
		        		 status.setTextColor(Color.parseColor("#ff0000"));
		        		 status.setText("BLOCKED");
		        	 }
		        	 
		        	 
		        	 } catch ( JSONException e) {
		        	 e.printStackTrace(); 
		        	 }
			
		

		//Toast.makeText(getApplicationContext(), usr, Toast.LENGTH_LONG).show();
	}
public void block(View v) throws JSONException{
	String message="";
	String status=json.getString("account_status");
	if(json.getString("account_status").equalsIgnoreCase("1")){
	message="Are you sure you want to block this user ?";
	}else{
		message="Are you sure you want to Unblock this user ?";	
	}
	
	AlertDialog.Builder al =  new AlertDialog.Builder(this);
    al. setMessage(message);
     al.setPositiveButton("No", new OnClickListener() {                     
         @Override
         public void onClick(DialogInterface dialog, int which) {
         Toast.makeText(getApplicationContext(), "Operation Canceled", Toast.LENGTH_LONG).show();	
         }
     });
     al.setNegativeButton("Yes", new OnClickListener() {                     
         @Override
         public void onClick(DialogInterface dialog, int which) {
       try {
		perform_task();
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
         }
     }).
    show();
    
}
public void perform_task() throws JSONException{
	String update_status="";

	String Num=json.getString("account_status");
	if(json.getString("account_status").equalsIgnoreCase("1")){
		update_status="0";
		
	}else{
		update_status="1";
		
	}
	
	String userid=getIntent().getExtras().getString("userid");
ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

	nameValuePairs.add(new BasicNameValuePair("user_id",userid));
	nameValuePairs.add(new BasicNameValuePair("status",update_status));
    	try
    	{
		HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://192.168.43.173/User_operation.php");
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
	        HttpResponse response = httpclient.execute(httppost); 
	        HttpEntity entity = response.getEntity();
	        is = entity.getContent();
	     
	    	
	        
	}
        catch(Exception e)
	{
	    	Toast.makeText(getApplicationContext(), "Can not Connect To The Server Please Check Your Connection",
			Toast.LENGTH_LONG).show();
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
            	//Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
	        Log.e("pass 2", "connection success ");
	}
        catch(Exception e)
    	{
		Log.e("Fail 2", e.toString());
		Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
	}
        
		if(result.equalsIgnoreCase("true")){
			String final_message="";

			if(json.getString("account_status").equalsIgnoreCase("1")){
				
				final_message="Account Successfuly BLOCKED!";
			}else{
			
				final_message="Account Successfuly UN-BLOCKED!";
			}
			Toast.makeText(getApplicationContext(),final_message, Toast.LENGTH_LONG).show();
			Userinfo.this.finish();
		}else{
			Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
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
