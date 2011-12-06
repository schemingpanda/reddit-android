package org.schemingpanda.reddit;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SearchResultsActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_results);
		final String[] displayNames = getIntent().getStringArrayExtra("displayNames");
		final String[] titles = getIntent().getStringArrayExtra("titles");
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, displayNames);
		ListView resultsListView = (ListView)findViewById(R.id.searchResultsListView);
		resultsListView.setAdapter(adapter);
		resultsListView.setOnItemClickListener(new ListView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
		    	try
		    	{
		    		URL url = new URL("http://www.reddit.com/r/" + displayNames[arg2] + "/.json");
			    	HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			    	InputStream is = new BufferedInputStream(connection.getInputStream());
			    	StringBuilder sb = new StringBuilder();
			    	int readByte = is.read();
			    	do
			    	{
			    		sb.append((char)readByte);
			    	}
			    	while((readByte = is.read()) != -1);
			    	JSONObject jsonObject = (JSONObject)new JSONTokener(sb.toString()).nextValue();
			    	JSONObject data = jsonObject.getJSONObject("data");
			    	JSONArray array = data.getJSONArray("children");

		    		Intent intent = new Intent(getApplicationContext(), PostsActivity.class);
		    		
		    		String[] thumbnails = new String[array.length()];
		    		String[] titles = new String[array.length()];
		    		
			    	for(int i = 0; i < array.length(); i++)
			    	{
			    		JSONObject reddit = (JSONObject)array.get(i);
			    		JSONObject redditData = reddit.getJSONObject("data");
			    		String thumbnailURL = redditData.getString("thumbnail");
			    		thumbnails[i] = thumbnailURL;
			    		String title = redditData.getString("title");
			    		titles[i] = title;
			    	}
			    	
			    	intent.putExtra("titles", titles);
			    	intent.putExtra("thumbnails", thumbnails);

		    		startActivity(intent);
		    	}
		    	catch(IOException e)
		    	{
		    		System.out.println(e);
		    	}
		    	catch(JSONException e)
		    	{
		    		System.out.println(e);
		    	}
				System.out.println(displayNames[arg2]);
			}
		});
	}
	
	private void something(URL url)
	{
    	try
    	{
	    	HttpURLConnection connection = (HttpURLConnection)url.openConnection();
	    	InputStream is = new BufferedInputStream(connection.getInputStream());
	    	StringBuilder sb = new StringBuilder();
	    	int readByte = is.read();
	    	do
	    	{
	    		sb.append((char)readByte);
	    	}
	    	while((readByte = is.read()) != -1);
	    	JSONObject jsonObject = (JSONObject)new JSONTokener(sb.toString()).nextValue();
	    	JSONObject data = jsonObject.getJSONObject("data");
	    	JSONArray array = data.getJSONArray("children");

    		Intent intent = new Intent(getApplicationContext(), SearchResultsActivity.class);
    		
    		String[] displayNames = new String[array.length()];
    		String[] titles = new String[array.length()];
    		
	    	for(int i = 0; i < array.length(); i++)
	    	{
	    		JSONObject reddit = (JSONObject)array.get(i);
	    		JSONObject redditData = reddit.getJSONObject("data");
	    		String displayName = redditData.getString("display_name");
	    		String title = redditData.getString("title");
	    		displayNames[i] = displayName;
	    		titles[i] = title;
	    	}
	    	
	    	intent.putExtra("displayNames", displayNames);
	    	intent.putExtra("titles", titles);

    		startActivity(intent);
    	}
    	catch(IOException e)
    	{
    		System.out.println(e);
    	}
    	catch(JSONException e)
    	{
    		System.out.println(e);
    	}
	}

}
