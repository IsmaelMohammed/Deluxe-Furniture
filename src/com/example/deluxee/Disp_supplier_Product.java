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

public class Disp_supplier_Product extends ActionBarActivity implements FetchDataListener, OnItemClickListener {
ListView li;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_disp_supplier__product);
		li=(ListView)findViewById(R.id.list);
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setHomeButtonEnabled(true);
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#153221")));
	    li.setOnItemClickListener(this);
	    Get_product();
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
	private void Get_product() {
		// show progress dialog
		 
		String url = "http://192.168.43.173/getsuppro.php";
		FetchProduct task = new FetchProduct(this);
		task.execute(url);
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
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		String fromm="",ID;
		try{
		View curr = parent.getChildAt((int) id);
		TextView c = (TextView)curr.findViewById(R.id.titleTxt);
		TextView c2 = (TextView)curr.findViewById(R.id.titleTxt2);
		TextView c3 = (TextView)curr.findViewById(R.id.titleTxt6);
		String Pid=c3.getText().toString();
		 fromm=c.getText().toString();
		 ID=c2.getText().toString();
		 final String unam=getIntent().getExtras().getString("uname");
		 final String psw=getIntent().getExtras().getString("psw");
		 Intent disp=new Intent(Disp_supplier_Product.this,Order_Product.class);
		 disp.putExtra("unam", unam);
         disp.putExtra("psw", psw);
         disp.putExtra("pid", Pid);
         disp.putExtra("user", "manager");
		 startActivity(disp);
		//Toast.makeText(getApplicationContext(), Pid+ID+fromm , Toast.LENGTH_LONG).show();
		}
		catch(Exception e){
			//Toast.makeText(getApplicationContext(),fromm, Toast.LENGTH_LONG).show();
		}
		
	}

}
