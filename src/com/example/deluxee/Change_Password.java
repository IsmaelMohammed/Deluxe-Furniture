package com.example.deluxee;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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


import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Change_Password extends ActionBarActivity {
	String result=null,result2=null,user_id=null,typ,operation;
	String line=null,username,password,u,p,quant;
	InputStream is=null;
	EditText oldpsw,newpsw,renewpsw;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change__password);
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setHomeButtonEnabled(true);
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#153221")));
	oldpsw=(EditText)findViewById(R.id.oldpassword);
	newpsw=(EditText)findViewById(R.id.newpassword);
	renewpsw=(EditText)findViewById(R.id.reppassword);

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
	            	
		}
	        catch(Exception e)
	    	{
			Log.e("Fail 2", e.toString());
			Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
		}     
	       
	    
		
		
	}
	
	public void change(View v){
		final String psw=getIntent().getExtras().getString("psw");

	String old=oldpsw.getText().toString();
	String newp=newpsw.getText().toString();
	String repnewpsw=renewpsw.getText().toString();
	if(!psw.equalsIgnoreCase(old)){
		
		Toast.makeText(getApplicationContext(), "Old Passwrod is incorrect!! \n Please Try Again !!" , Toast.LENGTH_LONG).show();
	}else if(!newp.equalsIgnoreCase(repnewpsw)){
		Toast.makeText(getApplicationContext(), "Confirmed password did not match!! \n Please Try Again !!", Toast.LENGTH_LONG).show();

	}else{
		new Asyncadd().execute();
	}
	
	}
	
	private class Asyncadd extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(Change_Password.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }
        @Override
        protected String doInBackground(String... params) {
        	String newp=newpsw.getText().toString();
        	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        	
        	nameValuePairs.add(new BasicNameValuePair("userid",user_id));
        	nameValuePairs.add(new BasicNameValuePair("psw",newp));

            	try
            	{
        		HttpClient httpclient = new DefaultHttpClient();
        	        HttpPost httppost = new HttpPost("http://192.168.43.173/change_psw.php");
        	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
        	        HttpResponse response = httpclient.execute(httppost); 
        	        HttpEntity entity = response.getEntity();
        	        is = entity.getContent();
        	        
        	}
                catch(Exception e)
        	{
        	    	//Toast.makeText(getApplicationContext(), "Can not Connect To The Server Please Check Your Connection",
        			//Toast.LENGTH_LONG).show();
        	result2="error";
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
                    	result2 = sb.toString();
                    	//Toast.makeText(getApplicationContext(), result2, Toast.LENGTH_LONG).show();
        	        Log.e("pass 2", "connection success ");
        	}
                catch(Exception e)
            	{
        		Log.e("Fail 2", e.toString());
        		//Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        	}
                
				return result2; 


        }

        @Override
        protected void onPostExecute(String result2) {

            //this method will be running on UI thread

            pdLoading.dismiss();
          //Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
            if(result2.equalsIgnoreCase("true"))
            {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
               
*/
            	Toast.makeText(getApplicationContext(), "Passsword Successfully Changed !!", Toast.LENGTH_LONG).show();
            	  onBackPressed();
            }else if(result2.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                Toast.makeText(getApplicationContext(), "Failed to Change Password!", Toast.LENGTH_LONG).show();

            } 
            else if(result2.equalsIgnoreCase("error")){

                // If username and password does not match display a error message
                Toast.makeText(getApplicationContext(), "OOPs! Something went wrong.\n Can not connect to server!!.", Toast.LENGTH_LONG).show();

            } 
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
