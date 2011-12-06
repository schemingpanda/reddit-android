package org.schemingpanda.reddit;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class ViewImageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_image);
		ImageView image = (ImageView)findViewById(R.id.viewImage);
		String imageURL = getIntent().getStringExtra("imageURL");
		try
		{
			Drawable d = Drawable.createFromStream((InputStream)new URL(imageURL).getContent(), "src");
			image.setImageDrawable(d);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}
