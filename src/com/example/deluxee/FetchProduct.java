package com.example.deluxee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.widget.Toast;

public class FetchProduct extends AsyncTask<String, Void, String>{
	private final FetchDataListener listener;
	private String msg;

	public FetchProduct(FetchDataListener listener) {
		this.listener = listener;
	}

	@Override
	protected String doInBackground(String... params) {
		if(params == null) return null;
		String url = params[0];
		//ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		try {
			// create http connection
			HttpClient client = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			//httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,HTTP.UTF_8));
			 
			// connect
			HttpResponse response = client.execute(httppost);

			// get response
			HttpEntity entity = response.getEntity();

			if(entity == null) {
				msg = "No response from server";
				//Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
				return null;
			}

			// get response content and convert it to json string
			InputStream is = entity.getContent();
			return streamToString(is);
		}
		catch(IOException e){
			msg = e.toString();

		}

		return null;
	}

	@Override
	protected void onPostExecute(String sJson) {
		if(sJson == null) {
			if(listener != null) listener.onFetchFailure(msg);
			return;
		}       

		try {
			// convert json string to json array
			JSONArray aJson = new JSONArray(sJson);
			// create apps list
			List<meslis> apps = new ArrayList<meslis>();

			for(int i=0; i<aJson.length(); i++) {
				JSONObject json = aJson.getJSONObject(i);
				meslis app = new meslis();
				app.setTitle(json.getString("Product_Name"));
				app.setId(json.getString("Product_ID"));
				app.setPrice(json.getString("Unit_Price"));


				// add the app to apps list
				apps.add(app);
		
			}

			//notify the activity that fetch data has been complete
			if(listener != null) listener.onFetchComplete(apps);
		} catch (JSONException e) {
			msg = "Invalid response";
			if(listener != null) listener.onFetchFailure(msg);
			return;
		}       
	}

	public String streamToString(final InputStream is) throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;

		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		}
		catch (IOException e) {
			throw e;
		}
		finally {          
			try {
				is.close();
			}
			catch (IOException e) {
				throw e;
			}
		}

		return sb.toString();
	}
}