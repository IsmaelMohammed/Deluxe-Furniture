package com.example.deluxee;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Approved_Product extends ActionBarActivity implements FetchDataListener, OnItemClickListener {
	ListView li;
	String result=null,result2=null,user_id=null,typ;
	String line=null,username,password,u,p;
	InputStream is=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orderd__prod_list);
		li=(ListView)findViewById(R.id.list);
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setHomeButtonEnabled(true);
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    
	    actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#153221")));
	    li.setOnItemClickListener(this);
	    
	    get_user_id();
	}


	
	public void get_user_id(){
		final String unam=getIntent().getExtras().getString("uname");
		final String psw=getIntent().getExtras().getString("psw");
		final String typ=getIntent().getExtras().getString("typ");

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
	            	if(typ.equalsIgnoreCase("user")){
		            	Get_product2(user_id);

	            	}else{
	            	Get_product(user_id);
	            	}
		}
	        catch(Exception e)
	    	{
			Log.e("Fail 2", e.toString());
			Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
		}     
	       
	        
		
		
	}
	private void Get_product( String id ) {
		// show progress dialog
		
		  
			 
			 String url = "http://192.168.43.173/approved.php";
				FetchOrderdList task = new FetchOrderdList(this);
				task.execute(url,user_id , "");
		
	}
	private void Get_product2( String id ) {
		// show progress dialog
		
		  
			 
			 String url = "http://192.168.43.173/approved_gust.php";
				FetchOrderdList task = new FetchOrderdList(this);
				task.execute(url,user_id , "");
		
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

	@Override
	public void onFetchComplete(List<meslis> dataa) {
		// TODO Auto-generated method stub
		ApplicationAdapter adapter = new ApplicationAdapter(this, dataa);
		li.setAdapter(adapter);
	}

	@Override
	public void onFetchFailure(String msgg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		final String utyp=getIntent().getExtras().getString("typ");

		try{
		View curr = arg0.getChildAt((int) arg3);
		TextView c = (TextView)curr.findViewById(R.id.titleTxt);
		TextView c3 = (TextView)curr.findViewById(R.id.titleTxt6);
		TextView c2 = (TextView)curr.findViewById(R.id.titleTxt2);
		String pname = c.getText().toString();
		String price=c2.getText().toString();
		String id=c3.getText().toString();
		if(utyp.equalsIgnoreCase("user")){Toast.makeText(getApplicationContext(), "Approved", Toast.LENGTH_LONG).show();}
		else{
		 Intent disp=new Intent(Approved_Product.this,Assign_Product.class);
			

		 disp.putExtra("orderid", id);
		 disp.putExtra("usr", typ);
		 
		 startActivity(disp);
		}
		}
		catch(Exception e){
			Toast.makeText(getApplicationContext(),"", Toast.LENGTH_LONG).show();
		}
		
	}
}
