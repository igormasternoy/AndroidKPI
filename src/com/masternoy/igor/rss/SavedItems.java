package com.masternoy.igor.rss;

import java.util.List;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;

import com.masternoy.igor.rss.db.FeedRepository;
import com.masternoy.igor.rss.entities.Feed;
import com.masternoy.igor.rss.view.FeedAdapter;

public class SavedItems extends Activity {

	FragmentTransaction fTrans;
	private FeedAdapter listViewAdapter;

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
		Button removeFeedButton = (Button) this
				.findViewById(R.id.removeFeedBtn);

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
				List<Long> selected = listViewAdapter.getSelectedIds();
				FeedRepository feedRepository = new FeedRepository(
						SavedItems.this);
				feedRepository.openToWrite();
				// Captures all selected ids with a loop
				if (selected != null && selected.size() > 0) {
					for (Long dbId : selected) {
						feedRepository.delete(dbId);
					}
				}
				feedRepository.close();
				refreshFeedsView();
			}
		});
	}

	private void refreshFeedsView() {
		FeedRepository dba = new FeedRepository(this);
		dba.openToRead();
		List<Feed> fetchedArticle = dba.getBlogListing();
		dba.close();
		ListView feedsView = (ListView) this.findViewById(R.id.feedsListView);
		listViewAdapter = new FeedAdapter(this, fetchedArticle);
		feedsView.setAdapter(listViewAdapter);
		listViewAdapter.notifyDataSetChanged();
	}
}
