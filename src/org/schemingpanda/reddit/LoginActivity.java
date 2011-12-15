package org.schemingpanda.reddit;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * An {@link Activity} for logging in to Reddit
 * 
 * @author William Anthony Hunt
 */
public class LoginActivity extends ActivityWithOptionsMenu {
	
	private static final String UNSECURED_LOGIN_URL = "http://www.reddit.com/api/login/";
	private static final String USER_PARAM = "user";
	private static final String PASSWORD_PARAM = "passwd";
	private static final String API_TYPE_PARAM = "api_type";
	private static final String API_TYPE_VALUE = "json";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		Button loginButton = (Button)findViewById(R.id.loginButton);
		final EditText userNameText = (EditText) findViewById(R.id.usernameField);
		final EditText passwordText = (EditText) findViewById(R.id.passwordField);
		
		loginButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String username = userNameText.getText() == null ? "" : userNameText.getText().toString();
				String password = userNameText.getText() == null ? "" : passwordText.getText().toString();
				
				if(username == null || username.trim().length() == 0){
					//TODO: some error handling stuff
					System.err.println("Username null or empty.");
					return;
				}
				if(password == null || password.trim().length() == 0){
					//TODO: some error handling stuff
					System.err.println("Password null or empty.");
					return;
				}
				
				System.out.println("User and password are good.");
				TextView loginText = (TextView) getWindow().findViewById(R.id.loginMessage);
				
				try{
					HttpClient client = new DefaultHttpClient();
					List<NameValuePair> postParams = new ArrayList<NameValuePair>(3);
					postParams.add(new BasicNameValuePair(API_TYPE_PARAM, API_TYPE_VALUE));
					postParams.add(new BasicNameValuePair(USER_PARAM, username));
					postParams.add(new BasicNameValuePair(PASSWORD_PARAM, password));
					System.out.println("Params" + postParams);
					HttpPost post = new HttpPost();
					
					post.setEntity(new UrlEncodedFormEntity(postParams));
					post.setURI(new URI(UNSECURED_LOGIN_URL + username));
					HttpResponse resp = client.execute(post);
					int statusCode = resp.getStatusLine().getStatusCode();
					if(statusCode == HttpStatus.SC_OK){
						System.out.println("Status code for login POST: " + statusCode);
						InputStream is = new BufferedInputStream(resp.getEntity().getContent());
				    	StringBuilder sb = new StringBuilder();
				    	int readByte = is.read();
				    	do
				    	{
				    		sb.append((char)readByte);
				    	}
				    	while((readByte = is.read()) != -1);
				    	String responseString = sb.toString();
				    	JSONObject jsonObject = new JSONObject(responseString);
				    	JSONArray errors = jsonObject.getJSONObject("json").getJSONArray("errors");
				    	if(errors.length() == 0){
				    		System.out.println("Successful login.");
				    		String modHash = jsonObject.getJSONObject("json").getJSONObject("data").getString("modhash");
				    		String cookie =  jsonObject.getJSONObject("json").getJSONObject("data").getString("cookie");
				    		System.out.println("modhash:" + modHash + ", cookie:" + cookie);
				    		UserState.loggedIn(username, modHash, cookie);
				    		
				    		//TODO: better way to do this? Start a new intent?
				    		getWindow().findViewById(R.id.loginButton).setVisibility(View.INVISIBLE);
				    		getWindow().findViewById(R.id.usernameField).setVisibility(View.INVISIBLE);
				    		getWindow().findViewById(R.id.passwordField).setVisibility(View.INVISIBLE);
				    		loginText.setText("Logged in, " + username);
				    	}
				    	else{
				    		System.err.println("Unsuccessful login. Errors: " + errors);
				    		loginText.setText("Hmmm...Reddit's saying \"no\", " + username + ". Try again.");
				    	}
					}
					else{
						System.err.println("Status code for login POST: " + statusCode);
						loginText.setText(R.string.server_error_message);
					}
				}
				catch (Exception e) {
					//TODO: some error handling stuff
					e.printStackTrace();
				}
			}
		});
		
	}

}
