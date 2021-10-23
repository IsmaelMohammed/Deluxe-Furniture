
package com.example.deluxee;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
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


import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Product_Display extends ActionBarActivity {
TextView text1,pname,Unit_Price,desc,contact;
JSONObject json = null;
LinearLayout linr;
EditText edt;
Button cart,order;
String result=null,result2=null,user_id=null,typ,operation;
String line=null,username,password,u,p,quant,oldq;
InputStream is=null;
String itemid;
ImageView img;
String location,seller_id;
String loca;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product__display);
		edt=(EditText)findViewById(R.id.edt);
		text1=(TextView)findViewById(R.id.text1);
		contact=(TextView)findViewById(R.id.contact);
		desc=(TextView)findViewById(R.id.desc);
img=(ImageView)findViewById(R.id.img);
		pname=(TextView)findViewById(R.id.pname);
		Unit_Price=(TextView)findViewById(R.id.Unit_Price);
		linr=(LinearLayout)findViewById(R.id.linr);
		cart=(Button)findViewById(R.id.cart);
		order=(Button)findViewById(R.id.order);
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setHomeButtonEnabled(true);
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#153221")));
		String Type=getIntent().getExtras().getString("user");
    	itemid=getIntent().getExtras().getString("pid");

		if(Type.equalsIgnoreCase("gust"))
{
	text1.setText("You are using as Guest Access!\n Please Login To Order This Item");
	cart.setVisibility(View.GONE);
	order.setVisibility(View.GONE);
}
else if(Type.equalsIgnoreCase("manager")||Type.equalsIgnoreCase("admin")){
get_user_id();
cart.setVisibility(View.GONE);
order.setVisibility(View.GONE);
}
else if(Type.equalsIgnoreCase("supl")){
	cart.setVisibility(View.GONE);
	order.setVisibility(View.GONE);
get_user_id();
}
else if(Type.equalsIgnoreCase("user")){
	cart.setVisibility(View.VISIBLE);
	order.setVisibility(View.VISIBLE);
get_user_id();
}
		
		get_product_info();
        //final String imgURL  = "http://192.168.43.173/images/0.png";
		get_img_path();
	}
	
	public void get_contact_person(String location){
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		 
		nameValuePairs.add(new BasicNameValuePair("loca",location));

	    	
	    	try
	    	{
			HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost   = new HttpPost("http://192.168.43.173/get_contact.php");
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
					Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

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
				contact.setText(json.getString("FirstName")+" "+json.getString("Phonenumber")+"\n");
				seller_id=json.getString("id");
			}catch(JSONException e ){
				
			}
		
	}
	
	
	public void get_img_path(){
		String pid=getIntent().getExtras().getString("pid");
		
			
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			 
			nameValuePairs.add(new BasicNameValuePair("pid",pid));
		    	
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
	public void get_product_info(){
		
		String pid=getIntent().getExtras().getString("pid");
		String Type=getIntent().getExtras().getString("user");
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		 
		nameValuePairs.add(new BasicNameValuePair("pro_id",pid));
		nameValuePairs.add(new BasicNameValuePair("typ",Type));

	    	
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
				pname.setText(json.getString("Product_Name"));
				Unit_Price.setText(json.getString("Unit_Price"));
				desc.setText(json.getString("Description"));
				contact.setText(json.getString("Product_Location"));
				 oldq=json.getString("Quantity");
				//Toast.makeText(getApplicationContext(), "Location "+location,Toast.LENGTH_LONG).show();
				
				
				 
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       	 
	       
		
	

	//Toast.makeText(getApplicationContext(), usr, Toast.LENGTH_LONG).show();
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
	            	get_product_info();

		}
	        catch(Exception e)
	    	{
			Log.e("Fail 2", e.toString());
			Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
		}     
	       
	    
		
		
	}
	public void order(View v){
		order.setVisibility(View.GONE);
		cart.setVisibility(View.GONE);
		linr.setVisibility(View.VISIBLE);
		 loca=contact.getText().toString();
		//get_contact_person(loca);
		
		
    }
	public void order2(View v){
		quant=edt.getText().toString();
		operation="order";
		int edtt=Integer.parseInt(edt.getText().toString());
		int old=Integer.parseInt(oldq);
		if(edtt>old){
			Toast.makeText(getApplicationContext(), "Max product quantity Exceded !!", Toast.LENGTH_LONG).show();
		}else{
		new Asyncadd().execute();	
	}
	}
	
	private class Asyncadd extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(Product_Display.this);
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
        	
        	
        	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        	nameValuePairs.add(new BasicNameValuePair("itemid",itemid));
        	nameValuePairs.add(new BasicNameValuePair("userid",user_id));
        	nameValuePairs.add(new BasicNameValuePair("seller_id",loca));
        	nameValuePairs.add(new BasicNameValuePair("quantity",quant));
        	nameValuePairs.add(new BasicNameValuePair("operation",operation));
        	
        	 
            	try
            	{
        		HttpClient httpclient = new DefaultHttpClient();
        	        HttpPost httppost = new HttpPost("http://192.168.43.173/order.php");
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
         Toast.makeText(getApplicationContext(), result2, Toast.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
            if(result2.equalsIgnoreCase("true"))
            {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
               
*/String message="";
            	if(operation.equalsIgnoreCase("order")){
            		message="Item Successfuly Orderd !!";
            	}else if (operation.equalsIgnoreCase("cart")){
            		message="Item add to your cart list!";
            	}
            	Toast.makeText(getApplicationContext(),message , Toast.LENGTH_LONG).show();
            	  onBackPressed();
            }else if(result2.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                Toast.makeText(getApplicationContext(), "Failed to Order Product!", Toast.LENGTH_LONG).show();

            } 
            else if(result2.equalsIgnoreCase("error")){

                // If username and password does not match display a error message
                Toast.makeText(getApplicationContext(), "OOPs! Something went wrong.\n Can not connect to server!!.", Toast.LENGTH_LONG).show();

            } 
        }

    }
	
	public void cart(View v){
		operation="cart";
		new Asyncadd().execute();
		//Toast.makeText(getApplicationContext(), "CART", Toast.LENGTH_LONG).show();
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product__display, menu);
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
