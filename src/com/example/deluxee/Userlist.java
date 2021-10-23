package com.example.deluxee;

import java.util.List;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Userlist extends ActionBarActivity implements FetchDataListener, OnItemClickListener {
ListView li;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userlist);
		li=(ListView)findViewById(R.id.Ulist);
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setHomeButtonEnabled(true);
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#153221")));
	    li.setOnItemClickListener(this);
	    Get_Users();
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
	private void Get_Users() {
		// show progress dialog
		 
		String url = "http://192.168.43.173/getusers.php";
		Fetchusers task = new Fetchusers(this);
		task.execute(url);
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
			 Intent userinfo =new Intent(Userlist.this,Userinfo.class);
			userinfo.putExtra("userid", userid);
			 startActivity(userinfo);
			
			//Toast.makeText(getApplicationContext(), Pid, Toast.LENGTH_LONG).show();
			}
			catch(Exception e){
				//Toast.makeText(getApplicationContext(),fromm, Toast.LENGTH_LONG).show();
			}
	}
	
}
