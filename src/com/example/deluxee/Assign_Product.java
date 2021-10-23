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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Assign_Product extends ActionBarActivity {
	Spinner spinner;
	String result=null;
	String line=null;
	InputStream is=null;
	JSONObject json = null;
	TextView Unit_Price,quantity,pname;
	EditText pric,quanti;
	String pro_id,description;
	ImageView img;
	protected ArrayAdapter<CharSequence> mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_assign__product);
		pric=(EditText)findViewById(R.id.pric);
		quanti=(EditText)findViewById(R.id.quan);
		img=(ImageView)findViewById(R.id.img);
		Unit_Price=(TextView)findViewById(R.id.Unit_Price);
		quantity=(TextView)findViewById(R.id.quantity);
		pname=(TextView)findViewById(R.id.pname);
	     ActionBar actionBar = getSupportActionBar();
	        actionBar.setHomeButtonEnabled(true);
		    actionBar.setDisplayHomeAsUpEnabled(true);
		    actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#153221")));
			final String id=getIntent().getExtras().getString("orderid");
spinner = (Spinner) findViewById(R.id.spinner1);
		this.mAdapter = ArrayAdapter.createFromResource(this, R.array.branch,
                android.R.layout.simple_spinner_dropdown_item);
		 spinner.setAdapter(this.mAdapter);
		 get_data();
	}


	
	public void get_data(){

		 final String ProductId=getIntent().getExtras().getString("orderid");
		String type="Manager";
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
	        	  
	        	 JSONArray jArray = new JSONArray(result);
	        	 json = jArray.getJSONObject(0);
	        	  pro_id=json.getString("Product_ID");
	        	  Toast.makeText(getApplicationContext(), pro_id, Toast.LENGTH_LONG).show();
	        	  quantity.setText(json.getString("Quantity"));
	        	  get_pro_info(pro_id);
	        	 } catch ( JSONException e) {
	 	 			//Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();

	        	 e.printStackTrace(); 
	        	 }
	        
		
	}
	
	public void get_pro_info(String x){
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		 
		nameValuePairs.add(new BasicNameValuePair("pro_id",x));
		nameValuePairs.add(new BasicNameValuePair("typ","manager"));
		
	    	
	    	try
	    	{
			HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://192.168.43.173/get_sup_pro_info.php");
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
	        	  
	        	 JSONArray jArray = new JSONArray(result);
	        	 json = jArray.getJSONObject(0);
	        	
	        	 Unit_Price.setText(json.getString("Unit_Price"));
	        	 pname.setText(json.getString("Product_Name")); 
	        	 description=json.getString("Description");
	        	 } catch ( JSONException e) {
	 	 			Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();

	        	 e.printStackTrace(); 
	        	 }	
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.assign__product, menu);
		return true;
	}
public void asign(View v){
	int new_quan=Integer.parseInt(quanti.getText().toString());
	int old_quan=Integer.parseInt(quantity.getText().toString());
	if(new_quan>old_quan){
		Toast.makeText(getApplicationContext(), "Max Product Quantity Exceed!!", Toast.LENGTH_LONG).show();

	}else{
		
		String pnam=pname.getText().toString();
    	String quan=quanti.getText().toString();
    	String loca =spinner.getSelectedItem().toString();
    	String uprice=pric.getText().toString();
		 final String orderId=getIntent().getExtras().getString("orderid");
			//Toast.makeText(getApplicationContext(), pnam+quan+loca+uprice+orderId, Toast.LENGTH_LONG).show();

		new Asyncadd().execute();	
	}
	
	
}

private class Asyncadd extends AsyncTask<String, String, String>
{
    ProgressDialog pdLoading = new ProgressDialog(Assign_Product.this);
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
    	String pnam=pname.getText().toString();
    	String quan=quanti.getText().toString();
    	String loca =spinner.getSelectedItem().toString();
    	String uprice=pric.getText().toString();
    	
		 final String orderId=getIntent().getExtras().getString("orderid");

    	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    	nameValuePairs.add(new BasicNameValuePair("orderId",orderId));
    	nameValuePairs.add(new BasicNameValuePair("Pname",pnam));
    	nameValuePairs.add(new BasicNameValuePair("quan",quan));
    	nameValuePairs.add(new BasicNameValuePair("loca",loca));
    	nameValuePairs.add(new BasicNameValuePair("upric",uprice));
    	nameValuePairs.add(new BasicNameValuePair("descri",description));
    	nameValuePairs.add(new BasicNameValuePair("prid",pro_id));
    	 
        	try
        	{
    		HttpClient httpclient = new DefaultHttpClient();
    	        HttpPost httppost = new HttpPost("http://192.168.43.173/add_delux_product.php");
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

protected void onPostExecute(String result) {

    //this method will be running on UI thread

    pdLoading.dismiss();
 //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
    //Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
    if(result.equalsIgnoreCase("true"))
    {
        /* Here launching another activity when login successful. If you persist login state
        use sharedPreferences of Android. and logout button to clear sharedPreferences.
       
*/
    	Toast.makeText(getApplicationContext(), "Product Successfully registered Assigned", Toast.LENGTH_LONG).show();
    	Assign_Product.this.finish();
    	
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

