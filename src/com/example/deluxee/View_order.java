package com.example.deluxee;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class View_order extends ActionBarActivity {
	String usr,Supplier_ID;
	Button accept,reject;
	String date,proid;
	TextView orddat,ordq,txv,namm;
	 String str = "";
	 Button block;
	JSONObject json = null;
	String result=null;
	 String fname;
	String line=null,username,password,u,p;
	String user="",user_id;
	InputStream is=null;
	ImageView img;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_order);
		orddat=(TextView)findViewById(R.id.orddat);
		ordq=(TextView)findViewById(R.id.ordq);
		img=(ImageView)findViewById(R.id.img);
		txv=(TextView)findViewById(R.id.txv);
		accept=(Button)findViewById(R.id.accept);
		reject=(Button)findViewById(R.id.reject);
		namm=(TextView)findViewById(R.id.namm);
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setHomeButtonEnabled(true);
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#153221")));
		 final String type=getIntent().getExtras().getString("usr");
if(type.equalsIgnoreCase("Manager")||type.equalsIgnoreCase("Customer")){
	
accept.setVisibility(View.GONE);
reject.setVisibility(View.GONE);
}
		 get_data();
		
	}

	public void get_img_path(String x){
		
			
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			 
			nameValuePairs.add(new BasicNameValuePair("pid",x));
		    	
		    	try
		    	{
				HttpClient httpclient = new DefaultHttpClient();
			        HttpPost httppost = new HttpPost("http://192.168.43.173/get_img_path.php");
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
		            	
		        		new DownLoadImageTask(img).execute(result);

		            	//Toast.makeText(getApplicationContext(), user_id, Toast.LENGTH_LONG).show();
			}
		        catch(Exception e)
		    	{
				Log.e("Fail 2", e.toString());
				Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
			}     
		       
		        
			
			
		
		
		
	}
	
	public void get_user(String y){
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("user_id",y));
			
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
		        	 namm.setText(json.getString("FirstName")+" "+json.getString("LastName")+": "+json.getString("Phonenumber"));
		        	 get_pro_info(proid);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}
	
public void get_pro_info(String x){
	
	 final String type=getIntent().getExtras().getString("usr");
	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		 
		nameValuePairs.add(new BasicNameValuePair("pro_id",x));
		nameValuePairs.add(new BasicNameValuePair("typ",type));
	    	
	    	try
	    	{
			HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://192.168.43.173/get_pro_info.php");
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
		        	 txv.setText(json.getString("Product_Name"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	 
	     
	
	
}
	public void get_data(){
		
		 final String type=getIntent().getExtras().getString("usr");
		 final String ProductId=getIntent().getExtras().getString("orderid");
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		 
		nameValuePairs.add(new BasicNameValuePair("order_id",ProductId));
		nameValuePairs.add(new BasicNameValuePair("typ",type));

	    	
	    	try
	    	{
			HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://192.168.43.173/get_order_info.php");
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
	           Log.e("pass 2", "connection success ");
		}
	        catch(Exception e)
	    	{
			Log.e("Fail 2", e.toString());
			Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
		}     
	       
	        try{
	        	  String utype=getIntent().getExtras().getString("usr");
		        	
	        	  JSONArray jArray = new JSONArray(result);
	        	 json = jArray.getJSONObject(0);
	        	if(utype.equalsIgnoreCase("Manager")){
	        		 Supplier_ID=json.getString("Supplier_ID");	
	        	}
	        	 orddat.setText(json.getString("Date"));
	        	 ordq.setText(json.getString("Quantity"));
	        	 if(utype.equalsIgnoreCase("seller")){
	        		 proid=json.getString("Item_ID");
		        	 user=json.getString("user_id");
		        	 }else{
	        	 proid=json.getString("Product_ID");
	        	 user=json.getString("User_ID");}
	        	
	        	// get_pro_info(proid);
	        	 get_user(user);
	        	 get_img_path(proid);
	        	// user=json.getString("User_ID");
	        	
	        	
	        	 //u Toast.makeText(getApplicationContext(), json.getString("User_ID"), Toast.LENGTH_LONG).show();
	        	
	        	
	        	

	        	 
	        	 
	        	 
	        	 } catch ( JSONException e) {
	 	 			//Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();

	        	 e.printStackTrace(); 
	        	 }
		
	}
	
	
public void accept(View v){
	String oldq=ordq.getText().toString();
	
	 final String type=getIntent().getExtras().getString("usr");

	 final String ProductId=getIntent().getExtras().getString("orderid");

	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
 	 
	nameValuePairs.add(new BasicNameValuePair("pro_id",ProductId));
	nameValuePairs.add(new BasicNameValuePair("typ",type));
	nameValuePairs.add(new BasicNameValuePair("pid",proid));
	nameValuePairs.add(new BasicNameValuePair("ordq",oldq));
	nameValuePairs.add(new BasicNameValuePair("uid",user));

	
	 
    	try
    	{
		HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://192.168.43.173/approve.php");
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
            	Toast.makeText(getApplicationContext(), "Product Order Successfuly Approved!!", Toast.LENGTH_LONG).show();
            	View_order.this.finish();
            	}else{
            	Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();	
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

public void reject(View v){
	 final String type=getIntent().getExtras().getString("usr");

	 final String ProductId=getIntent().getExtras().getString("orderid");


	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
 	 
	nameValuePairs.add(new BasicNameValuePair("pro_id",ProductId));
	nameValuePairs.add(new BasicNameValuePair("typ",type));

	
	 
    	try
    	{
		HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://192.168.43.173/Reject.php");
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
            	Toast.makeText(getApplicationContext(), "Product Order Rejected!!", Toast.LENGTH_LONG).show();
            	View_order.this.finish();
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_order, menu);
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
	
	private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap>{
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }

	
}
