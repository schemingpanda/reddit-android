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

import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * An {@link Activity} for viewing images associated with a post
 *
 */
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
