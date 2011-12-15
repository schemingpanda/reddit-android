/**
 *    Copyright 2011 Scheming Panda
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package org.schemingpanda.reddit;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchActivity extends ActivityWithOptionsMenu {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		
		Button searchButton = (Button)findViewById(R.id.searchBtn);
		final EditText searchBox = (EditText)findViewById(R.id.searchTextBox);
		
		searchButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
		    	String queryString = searchBox.getText().toString();
		    	String urlString = "http://www.reddit.com/reddits/search.json?q=" + queryString;
		    	URL url = null;
		    	try
		    	{
		    		url = new URL(urlString);
		    	}
		    	catch(MalformedURLException e)
		    	{
		    	}
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
		});
		
	}
}
