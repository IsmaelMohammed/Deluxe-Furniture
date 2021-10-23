package com.example.deluxee;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;



import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class Login extends ActionBarActivity {
	 public static final int CONNECTION_TIMEOUT=10000;
	    public static final int READ_TIMEOUT=15000;
	    private EditText etEmail;
	    private EditText etPassword;
	    String email;
	    String line=null,username,password,u,p;
	    TextView block;
	    InputStream is=null;
	    String result=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		etEmail=(EditText)findViewById(R.id.email);
		block=(TextView)findViewById(R.id.block);
		etPassword=(EditText)findViewById(R.id.password);
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setHomeButtonEnabled(true);
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#153221")));
	
	    if(android.os.Build.VERSION.SDK_INT>9){
			StrictMode.ThreadPolicy pol=new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(pol);
	 }}


public void login(View v){
	 email = etEmail.getText().toString();
    password = etPassword.getText().toString();

        // Initialize  AsyncLogin() class with email and password
        new AsyncLogin().execute(email,password);
    
  
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
	
	private class AsyncLogin extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(Login.this);
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
       	 
        	nameValuePairs.add(new BasicNameValuePair("username",params[0]));
        	nameValuePairs.add(new BasicNameValuePair("password",params[1]));
        	 
            	try
            	{
        		HttpClient httpclient = new DefaultHttpClient();
        	        HttpPost httppost = new HttpPost("http://192.168.43.173/check.php");
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
        	//Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

            pdLoading.dismiss();
          if(result.equalsIgnoreCase("trueadmin1"))
            {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
               
*/
        	  block.setVisibility(View.GONE);
            	//Toast.makeText(getApplicationContext(), "Success Admin Login ", Toast.LENGTH_LONG).show();
Intent home=new Intent(Login.this,Home.class);
home.putExtra("uname", email);
home.putExtra("psw", password);
startActivity(home);
            }
            else if(result.equalsIgnoreCase("trueCustomer1"))
            {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
               
               
*/
          	  block.setVisibility(View.GONE);

            	Intent i=new Intent(Login.this,GuestHome.class);
            	
          i.putExtra("uname", email);
            i.putExtra("psw", password);
            	startActivity(i);
            	//Toast.makeText(getApplicationContext(), " Guest User Account", Toast.LENGTH_LONG).show();
            }
            else if(result.equalsIgnoreCase("trueManager1"))
            {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
               
               
*/
          // Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
          	  block.setVisibility(View.GONE);
          
            	Intent i=new Intent(Login.this,Manager.class);
            	
            i.putExtra("uname", email);
            i.putExtra("psw", password);
            	startActivity(i);
 }
            else if(result.equalsIgnoreCase("trueSupplier1"))
            {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
               
               
*/
           //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
          	  block.setVisibility(View.GONE);
    
            	Intent i=new Intent(Login.this,Supplier.class);
            	
            i.putExtra("uname", email);
            i.putExtra("psw", password);
            	startActivity(i);
 }

            else if(result.equalsIgnoreCase("trueSeller1"))
            {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
               
               
*/
           //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
          	  block.setVisibility(View.GONE);
      
            	Intent i=new Intent(Login.this,Seller.class);
            	
            i.putExtra("uname", email);
            i.putExtra("psw", password);
            	startActivity(i);
 }
            
            else if(result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
          	 // block.setVisibility(View.GONE);
          	block.setText("Invalid username or password");
                Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_LONG).show();

            } 
            else if(result.equalsIgnoreCase("error")){

                // If username and password does not match display a error message
          	  block.setText("OOPs! Something went wrong.\n Can not connect to server!!");

                Toast.makeText(getApplicationContext(), "OOPs! Something went wrong.\n Can not connect to server!!.", Toast.LENGTH_LONG).show();

            }else if(result.endsWith("0")){
        	  block.setVisibility(View.VISIBLE);
              block.setText("This user account has been blocked\n           by system administrator");

          } 
        }

    }
}
