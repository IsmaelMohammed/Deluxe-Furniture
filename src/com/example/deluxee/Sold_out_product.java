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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Sold_out_product extends ActionBarActivity implements FetchDataListener, OnItemClickListener {
ListView li;
String result=null,result2=null,branch=null,typ,operation;
String line=null,username,password,u,p,quant;
InputStream is=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sold_out_product);
		li=(ListView)findViewById(R.id.Ulist);
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setHomeButtonEnabled(true);
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#153221")));
	    li.setOnItemClickListener(this);
		final String operation=getIntent().getExtras().getString("operation");
		final String typ=getIntent().getExtras().getString("typ");

	    if(operation.equalsIgnoreCase("soldout")){
            getSupportActionBar().setTitle("Sold Out Products");  

        	}else{
                getSupportActionBar().setTitle("Sold Products");  

        	}
	    if(typ.equalsIgnoreCase("seller")){
	    get_branch();
	    }
	    if(typ.equalsIgnoreCase("manager")){
	    	
		    get_sold_out();
		    }
	    }


	public void get_branch(){
		final String unam=getIntent().getExtras().getString("uname");
		final String psw=getIntent().getExtras().getString("psw");
		final String operation=getIntent().getExtras().getString("operation");

		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		 
		nameValuePairs.add(new BasicNameValuePair("username",unam));
		nameValuePairs.add(new BasicNameValuePair("password",psw));
	    	
	    	try
	    	{
			HttpClient httpclient = new DefaultHttpClient();
		        HttpPost httppost = new HttpPost("http://192.168.43.173/get_branch.php");
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
	            	branch=result;
	            	if(operation.equalsIgnoreCase("soldout")){
	            	Get_soldout(branch);
	            	}else{
	            		Get_sold(branch);
	            	}
		}
	        catch(Exception e)
	    	{
			Log.e("Fail 2", e.toString());
			Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
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
	private void Get_soldout(String x) {
		// show progress dialog
		 
		String url = "http://192.168.43.173/soldout.php";
		Fetch_Sold_OUT task = new Fetch_Sold_OUT(this);
		task.execute(url,x,"soldout");
	}
	private void get_sold_out() {
		// show progress dialog
		 
		String url = "http://192.168.43.173/soldoutpro.php";
		FetchProduct task = new FetchProduct(this);
		task.execute(url);
	}
	private void Get_sold(String x) {
		// show progress dialog
		 
		String url = "http://192.168.43.173/sold.php";
		Fetch_Sold_OUT task = new Fetch_Sold_OUT(this);
		task.execute(url,x,"sold");
	}

	@Override
	public void onFetchComplete(List<meslis> dataa) {
		// TODO Auto-generated method stub
		UsersAdapter adapter = new UsersAdapter(this, dataa);
		li.setAdapter(adapter);
	}


	@Override
	public void onFetchFailure(String msgg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		//Toast.makeText(getApplicationContext(), "ss", Toast.LENGTH_LONG).show();
		try{
			View curr = arg0.getChildAt((int) arg3);
			TextView c = (TextView)curr.findViewById(R.id.titleTxt);
			TextView c2 = (TextView)curr.findViewById(R.id.titleTxt2);
			TextView c3 = (TextView)curr.findViewById(R.id.titleTxt6);
			String userid=c2.getText().toString();
			 //Intent userinfo =new Intent(Sold_out_product.this,Userinfo.class);
			//userinfo.putExtra("userid", userid);
			 //startActivity(userinfo);
			
			//Toast.makeText(getApplicationContext(), Pid, Toast.LENGTH_LONG).show();
			}
			catch(Exception e){
				//Toast.makeText(getApplicationContext(),fromm, Toast.LENGTH_LONG).show();
			}
	}
	
}
