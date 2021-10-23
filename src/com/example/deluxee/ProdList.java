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
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class ProdList extends ActionBarActivity {
	protected ArrayAdapter<CharSequence> mAdapter;
	public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
EditText first,last,phone,user,pass,copass;
String result=null;
String line,username,password,u,p;
Spinner spinner,spinner2;
LinearLayout bra;
String loca;
InputStream is=null;
Button email_sign_in_button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prod_list);
		spinner = (Spinner) findViewById(R.id.spinner1);
		email_sign_in_button = (Button) findViewById(R.id.email_sign_in_button);

		bra=(LinearLayout)findViewById(R.id.bra);
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		this.mAdapter = ArrayAdapter.createFromResource(this, R.array.usertype,
                android.R.layout.simple_spinner_dropdown_item);
		 spinner.setAdapter(this.mAdapter);
		 this.mAdapter = ArrayAdapter.createFromResource(this, R.array.branch,
	                android.R.layout.simple_spinner_dropdown_item);
			 spinner2.setAdapter(this.mAdapter);
		 
		ActionBar actionBar = getSupportActionBar();
		first=(EditText)findViewById(R.id.fname);
		last=(EditText)findViewById(R.id.lname);
	phone=(EditText)findViewById(R.id.pnumber);
		user=(EditText)findViewById(R.id.uname);
		pass=(EditText)findViewById(R.id.password);
		copass=(EditText)findViewById(R.id.password2);
	    actionBar.setHomeButtonEnabled(true);
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#153221")));
	
	    if(android.os.Build.VERSION.SDK_INT>9){
			StrictMode.ThreadPolicy pol=new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(pol);
	 }
	}
	
   
	
	
public void SignUp(View v){
	String nam=first.getText().toString();
	String lnam=last.getText().toString();
	String pho=phone.getText().toString();
	String us=user.getText().toString();
	String psw=pass.getText().toString();
	String copas=copass.getText().toString();
	if(nam.equalsIgnoreCase("")||lnam.equalsIgnoreCase("")||pho.equalsIgnoreCase("")||us.equalsIgnoreCase("")||psw.equalsIgnoreCase("")){
		Toast.makeText(getApplicationContext(), "Please Fill all the fields !!",Toast.LENGTH_LONG).show();
	}else if(!psw.equalsIgnoreCase(copas)){
		Toast.makeText(getApplicationContext(), "Password did not match please try again!!"+psw+copas, Toast.LENGTH_LONG).show();
	}else{
    	String usertype=spinner.getSelectedItem().toString();
if(usertype.equalsIgnoreCase("Seller")){
	
	bra.setVisibility(View.VISIBLE);
	email_sign_in_button.setVisibility(View.GONE);
}else{
	
	 new AsyncLogin().execute();

}
	}
	}



public void reg(View v){
	String loca=spinner2.getSelectedItem().toString();

	 new AsyncLogin().execute();

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
        ProgressDialog pdLoading = new ProgressDialog(ProdList.this);
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
        	String nam=first.getText().toString();
        	String lnam=last.getText().toString();
        	String pho=phone.getText().toString();
        	String us=user.getText().toString();
        	String psw=pass.getText().toString();

        	String usertype=spinner.getSelectedItem().toString();
        	if(usertype.equalsIgnoreCase("Seller")){
            	 loca=spinner2.getSelectedItem().toString();

        	}else{
        		 loca="";
        	}

        	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        	nameValuePairs.add(new BasicNameValuePair("Rname",nam));
        	nameValuePairs.add(new BasicNameValuePair("lname",lnam));
        	nameValuePairs.add(new BasicNameValuePair("phone",pho));
        	nameValuePairs.add(new BasicNameValuePair("user",us));
        	nameValuePairs.add(new BasicNameValuePair("psw",psw));
        	nameValuePairs.add(new BasicNameValuePair("usr",usertype));
        	nameValuePairs.add(new BasicNameValuePair("loca",loca));

        	try
            	{
        		HttpClient httpclient = new DefaultHttpClient();
        	        HttpPost httppost = new HttpPost("http://192.168.43.173/add.php");
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
        	p=e.toString();
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
                
				return result; 


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();
         // Toast.makeText(getApplicationContext(), result+"false", Toast.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
            if(result.equalsIgnoreCase("true"))
            {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
               
*/
            	Toast.makeText(getApplicationContext(), "User Successfully registered", Toast.LENGTH_LONG).show();
            	onBackPressed();
            }else if(result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

            } 
            else if(result.equalsIgnoreCase("error")){

                // If username and password does not match display a error message
                Toast.makeText(getApplicationContext(), "OOPs! Something went wrong.\n Can not connect to server!!.", Toast.LENGTH_LONG).show();

            } else {
            	
                Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();

            }
        }

    }
}
