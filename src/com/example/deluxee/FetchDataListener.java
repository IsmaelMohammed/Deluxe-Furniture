package com.example.deluxee;

import java.util.List;

public interface FetchDataListener {
	public void onFetchComplete(List<meslis> dataa);
	public void onFetchFailure(String msgg);
}