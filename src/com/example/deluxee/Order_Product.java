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
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
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

public class Order_Product extends ActionBarActivity {
	
	String user_id="",supplier_id,Pquantity;
	String result=null,result2=null;
	String line=null,username,password,u,p;
	InputStream is=null;
	LinearLayout lin2;
	EditText quantity;
	Button cart,order,Corder;
	TextView text1,pna,fname,contact,ordq,desc;
	JSONObject json = null;
ImageView img;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order__product);
		quantity=(EditText)findViewById(R.id.quantity);
		cart=(Button)findViewById(R.id.cart);
		order=(Button)findViewById(R.id.order);
		Corder=(Button)findViewById(R.id.Corder);
		text1=(TextView)findViewById(R.id.pname);
		pna=(TextView)findViewById(R.id.pna);
		desc=(TextView)findViewById(R.id.desc);
img=(ImageView)findViewById(R.id.img);
		fname=(TextView)findViewById(R.id.fname);
		contact=(TextView)findViewById(R.id.phn);
		ordq=(TextView)findViewById(R.id.ordq);

		lin2=(LinearLayout)findViewById(R.id.lin2);
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setHomeButtonEnabled(true);
	    actionBar.setDisplayHomeAsUpEnabled(true);
	  final  String item_id=getIntent().getExtras().getString("pid");
	    //Toast.makeText(getApplicationContext(), itemid, Toast.LENGTH_LONG).show();
	    actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#153221")));
	   String usr=getIntent().getExtras().getString("user");
	  
	    get_user_id();
	    getsupplier_id();
	    check_orderd();
		get_pro_info();
		get_order_info();
		get_user_info();
		get_img_path();
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
public void get_user_id(){
	final String unam=getIntent().getExtras().getString("unam");
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
            	//Toast.makeText(getApplicationContext(), user_id, Toast.LENGTH_LONG).show();
	}
        catch(Exception e)
    	{
		Log.e("Fail 2", e.toString());
		Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
	}     
       
        
	
	
}
public void order(View v){
	
//cart.setVisibility(View.GONE);
order.setVisibility(View.GONE);
	lin2.setVisibility(View.VISIBLE);
}


public void getsupplier_id(){
	String itemid=getIntent().getExtras().getString("pid");
	
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		 
		nameValuePairs.add(new BasicNameValuePair("itemid",itemid));
		
	    	
	    	try
	    	{
			HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://192.168.43.173/get_supplier_id.php");
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
	            	supplier_id=result;
		}
	        catch(Exception e)
	    	{
			Log.e("Fail 2", e.toString());
			Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
		}   
}

public void request( View v){
	int Pquantity=Integer.parseInt(quantity.getText().toString());
int old=Integer.parseInt(ordq.getText().toString());
if(Pquantity>old){
	Toast.makeText(getApplicationContext(), "Max Product quantity Exceded!!", Toast.LENGTH_LONG).show();
}else{
	new Asyncadd().execute();
}
}






private class Asyncadd extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(Order_Product.this);
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
        	
        	String itemid=getIntent().getExtras().getString("pid");
        	Pquantity=quantity.getText().toString();
        	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        	nameValuePairs.add(new BasicNameValuePair("itemid",itemid));
        	nameValuePairs.add(new BasicNameValuePair("userid",user_id));
        	nameValuePairs.add(new BasicNameValuePair("supplierid",supplier_id));
        	nameValuePairs.add(new BasicNameValuePair("quantity",Pquantity));
        	
            	try
            	{
        		HttpClient httpclient = new DefaultHttpClient();
        	        HttpPost httppost = new HttpPost("http://192.168.43.173/Neworder.php");
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
               
*/
            	Toast.makeText(getApplicationContext(), "Order Successfully sent to supplier", Toast.LENGTH_LONG).show();
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

public void get_pro_info(){
	String itemid=getIntent().getExtras().getString("pid");
	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	 
	nameValuePairs.add(new BasicNameValuePair("pro_id",itemid));
	nameValuePairs.add(new BasicNameValuePair("typ","x"));

    	
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
       
        try{
        	  String utype=getIntent().getExtras().getString("usr");
	        	
        	  JSONArray jArray = new JSONArray(result);
        	 json = jArray.getJSONObject(0);
        	 
String Pronam=json.getString("Product_Name");
String quan=json.getString("Quantity");
String des=json.getString("Description");

        	 pna.setText(Pronam);
        	 ordq.setText(quan);
        	 desc.setText(des);
        	// user=json.getString("User_ID");
        	
        	
        	 //u Toast.makeText(getApplicationContext(), json.getString("User_ID"), Toast.LENGTH_LONG).show();
        	
        	
        	

        	 
        	 
        	 
        	 } catch ( JSONException e) {
 	 			//Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();

        	 e.printStackTrace(); 
        	 }
	
}

public void get_order_info(){
	
	String itemid=getIntent().getExtras().getString("pid");
	//Toast.makeText(getApplicationContext(), "user_id"+user_id+"supllier_id"+supplier_id+"Item_id"+itemid, Toast.LENGTH_LONG).show();
	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
  	 
	nameValuePairs.add(new BasicNameValuePair("user_id",user_id));
	nameValuePairs.add(new BasicNameValuePair("supplier_id",supplier_id));
	nameValuePairs.add(new BasicNameValuePair("item_id",itemid));
	 
    	try
    	{
		HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://192.168.43.173/check_order_info.php");
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
      	 
String Ord_quan=json.getString("Quantity");



//ordq.setText(Ord_quan);


      	 
      	 
      	 
      	 } catch ( JSONException e) {
	 			//Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();

      	 e.printStackTrace(); 
      	 }
	
        
}

public void get_user_info(){
	
	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	nameValuePairs.add(new BasicNameValuePair("user_id",supplier_id));
		
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
	        	String First_nam=json.getString("FirstName");
	        	String Last_nam=json.getString("LastName");
	        	String phon=json.getString("Phonenumber");
	        	
	        	
	        	fname.setText(First_nam+" "+ Last_nam);
	        	contact.setText(phon);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
}

public void check_orderd(){
	//Toast.makeText(getApplicationContext(), "cheking", Toast.LENGTH_LONG).show();
	String itemid=getIntent().getExtras().getString("pid");
	//Toast.makeText(getApplicationContext(), "user_id"+user_id+"supllier_id"+supplier_id+"Item_id"+itemid, Toast.LENGTH_LONG).show();
	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
  	 
	nameValuePairs.add(new BasicNameValuePair("user_id",user_id));
	nameValuePairs.add(new BasicNameValuePair("supplier_id",supplier_id));
	nameValuePairs.add(new BasicNameValuePair("item_id",itemid));
	 
    	try
    	{
		HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://192.168.43.173/check_order.php");
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
           Log.e("pass 2", "connection success ");
	}
        catch(Exception e)
    	{
		Log.e("Fail 2", e.toString());
		Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
	}
        
		if(result.equalsIgnoreCase("true")){
			text1.setText("You already orderd this product");
			order.setVisibility(View.GONE);
			//cart.setVisibility(View.GONE);
			Corder.setVisibility(View.VISIBLE);
		}else{
			Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
		}

}

public void Cancelorder(View v){
	ImageView image = new ImageView(this);
	image.setImageResource(R.drawable.deluxx);
	AlertDialog.Builder al =  new AlertDialog.Builder(this);
	       al. setMessage("Are you sure you want to cancel this order");
	        al.setPositiveButton("No", new OnClickListener() {                     
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	            Toast.makeText(getApplicationContext(), "Order not canceled", Toast.LENGTH_LONG).show();	
	            }
	        });
	        al.setNegativeButton("Yes", new OnClickListener() {                     
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	           remove_order();
	            }
	        }).
	       show();
	       
	        
}
	
	
	
public void remove_order(){
	
	String itemid=getIntent().getExtras().getString("pid");
	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
  	 
	nameValuePairs.add(new BasicNameValuePair("user_id",user_id));
	nameValuePairs.add(new BasicNameValuePair("supplier_id",supplier_id));
	nameValuePairs.add(new BasicNameValuePair("item_id",itemid));
	 
    	try
    	{
		HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://192.168.43.173/remove_order.php");
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
           Log.e("pass 2", "connection success ");
	}
        catch(Exception e)
    	{
		Log.e("Fail 2", e.toString());
		Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
	}
        
		if(result.equalsIgnoreCase("true")){
			Toast.makeText(getApplicationContext(),"Order Successfully Removed", Toast.LENGTH_LONG).show();
			Order_Product.this.finish();
		}else{
			Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
		}
}


public void cart(View v){
	//Toast.makeText(getApplicationContext(), "cart", Toast.LENGTH_LONG).show();
}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.order__product, menu);
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
