package com.example.deluxee;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class UsersAdapter extends ArrayAdapter<meslis>{
	private List<meslis> items;

	public UsersAdapter(Context context, List<meslis> items) {
		super(context, R.layout.app_custom_list, items);
		this.items = items;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if(v == null) {
			LayoutInflater li = LayoutInflater.from(getContext());
			v = li.inflate(R.layout.onli, null);           
		}

		meslis app = items.get(position);

		if(app != null) {
			TextView titleText = (TextView)v.findViewById(R.id.titleTxt);
			TextView titlepr = (TextView)v.findViewById(R.id.titleTxt2);
			TextView titleid = (TextView)v.findViewById(R.id.titleTxt6);
			ImageView img = (ImageView)v.findViewById(R.id.titleTxt9);
			
			
			if(titleText != null){
				titleText.setText(app.getTitle());
				titlepr.setText(app.getId());
				titleid.setText(""+app.getPrice());
				
			} 


		}

		return v;
	}
}