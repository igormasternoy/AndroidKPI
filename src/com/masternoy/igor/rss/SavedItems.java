package com.masternoy.igor.rss;

import java.util.List;

import com.masternoy.igor.rss.db.RssAdapter;
import com.masternoy.igor.rss.db.FeedRepository;
import com.masternoy.igor.rss.entities.Article;
import com.masternoy.igor.rss.entities.Feed;
import com.masternoy.igor.rss.view.FeedAdapter;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class SavedItems extends Activity {
	
	 FragmentTransaction fTrans;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saved_items);
		refreshFeedsView();
		setUpButtons();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.saved_items, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		Intent intent;
		if (id == R.layout.activity_categories) {
			intent = new Intent(this, Categories.class);
			startActivity(intent);
			return true;

		} else if (id == R.layout.activity_main) {
			intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void setUpButtons() {
		Button addFeedButton = (Button) this.findViewById(R.id.addFeedBtn);
		Button removeFeedButton = (Button) this.findViewById(R.id.removeFeedBtn);
		
		addFeedButton.setClickable(true);
		addFeedButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SavedItems.this, AddFeed.class);
				startActivity(intent);
			}
		});
		
		removeFeedButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	private void refreshFeedsView() {
		FeedRepository dba = new FeedRepository(this);
		dba.openToRead();
		List<Feed> fetchedArticle = dba.getBlogListing();
		dba.close();
		ListView feedsView = (ListView) this.findViewById(R.id.feedsListView);
		FeedAdapter adapter = new FeedAdapter(this, fetchedArticle);
		feedsView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
}
