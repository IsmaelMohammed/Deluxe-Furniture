package com.example.deluxee;


import java.io.BufferedReader;
import java.io.IOException;
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
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class AddProduct extends ActionBarActivity {
	protected ArrayAdapter<CharSequence> mAdapter;
	EditText pnam,quantit,price,loc,desc;
	Spinner spinner;
	String result=null,user_id;
	Button sel;
	String line,username,password,u,p;
	ImageView img;
	InputStream is=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_product);
        // spinner = (Spinner) findViewById(R.id.spinner1);
       // this.mAdapter = ArrayAdapter.createFromResource(this, R.array.Location,
         //       android.R.layout.simple_spinner_dropdown_item);

        /*
         * Attach the mLocalAdapter to the spinner.
         */

      //  spinner.setAdapter(this.mAdapter);
        pnam=(EditText)findViewById(R.id.pname);
      
        img=(ImageView)findViewById(R.id.img);
        quantit=(EditText)findViewById(R.id.pnumber);
        price=(EditText)findViewById(R.id.price);
        desc=(EditText)findViewById(R.id.desc);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#262021")));
	
	}
	

	

	 public void add(View v){
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
		            	new Asyncadd().execute();
			}
		        catch(Exception e)
		    	{
				Log.e("Fail 2", e.toString());
				Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
			}     
		       
		        
			
			
		}
	 
	 
	 
	 
	

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_product, menu);
		return true;
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
	private class Asyncadd extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(AddProduct.this);
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
        	String pname=pnam.getText().toString();
        	String quan=quantit.getText().toString();
        	String loca = desc.getText().toString();
        	String uprice=price.getText().toString();
        	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        	nameValuePairs.add(new BasicNameValuePair("Pname",pname));
        	nameValuePairs.add(new BasicNameValuePair("quan",quan));
        	nameValuePairs.add(new BasicNameValuePair("loca",loca));
        	nameValuePairs.add(new BasicNameValuePair("upric",uprice));
        	nameValuePairs.add(new BasicNameValuePair("uid",user_id));
        	 
            	try
            	{
        		HttpClient httpclient = new DefaultHttpClient();
        	        HttpPost httppost = new HttpPost("http://192.168.43.173/addproduct.php");
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
                    	//Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        	        Log.e("pass 2", "connection success ");
        	}
                catch(Exception e)
            	{
        		Log.e("Fail 2", e.toString());
        		//Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        	}
                
				return result; 


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();
         //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
            if(result.equalsIgnoreCase("true"))
            {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
               
*/final String unam=getIntent().getExtras().getString("uname");
final String psw=getIntent().getExtras().getString("psw");
            	Toast.makeText(getApplicationContext(), "Product Successfully registered \n Please Upload Pictures", Toast.LENGTH_LONG).show();
            	Intent test=new Intent(AddProduct.this,Image_Upload.class);
                startActivity(test);
            }else if(result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                Toast.makeText(getApplicationContext(), "Product Not Registered Please check again!", Toast.LENGTH_LONG).show();

            } 
            else if(result.equalsIgnoreCase("error")){

                // If username and password does not match display a error message
                Toast.makeText(getApplicationContext(), "OOPs! Something went wrong.\n Can not connect to server!!.", Toast.LENGTH_LONG).show();

            } 
        }

    }
}
