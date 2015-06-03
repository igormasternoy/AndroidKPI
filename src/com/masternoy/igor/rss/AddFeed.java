package com.masternoy.igor.rss;

import java.net.MalformedURLException;
import java.net.URL;

import com.masternoy.igor.rss.db.FeedRepository;
import com.masternoy.igor.rss.entities.Feed;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddFeed extends Activity {
	EditText feedName;
	EditText feedUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_feed);
		configureForm();
		configureButtons();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.add_feed, menu);
		return true;
	}

	private void configureForm() {
		feedName = (EditText) this.findViewById(R.id.feedName);
		feedUrl = (EditText) this.findViewById(R.id.feedUrl);
	}

	private void configureButtons() {
		Button okBtn = (Button) this.findViewById(R.id.add_feed_ok_btn);
		okBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FeedRepository feedRepository = new FeedRepository(AddFeed.this);
				feedRepository.openToWrite();
				Feed feed = new Feed();
				feed.setName(feedName.getText().toString());
				try {
					feed.setUrl(new URL(feedUrl.getText().toString()));
					feedRepository.insertBlogListing(feed);
				} catch (MalformedURLException e) {
					// TODO [imasternoy] Show smth to the user
					Log.e("Shit happens", "Failed to save feed", e);
				}
				feedRepository.close();
				Intent intent = new Intent(AddFeed.this, SavedItems.class);
				startActivity(intent);

			}
		});

		Button cancelBtn = (Button) this.findViewById(R.id.add_feed_cancel_btn);
		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AddFeed.this, SavedItems.class);
				startActivity(intent);
			}
		});
	}

}
