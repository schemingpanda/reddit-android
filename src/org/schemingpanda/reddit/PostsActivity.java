package org.schemingpanda.reddit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PostsActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.posts);
		ListView postsListView = (ListView)findViewById(R.id.postsListView);

		final String[] titles = getIntent().getStringArrayExtra("titles");
		final String[] thumbnails = getIntent().getStringArrayExtra("thumbnails");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles);
		postsListView.setAdapter(adapter);
		postsListView.setOnItemClickListener(new ListView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(getApplicationContext(), ViewImageActivity.class);
				intent.putExtra("imageURL", thumbnails[arg2]);
				startActivity(intent);
			}
		});
	}
}
