package com.masternoy.igor.rss;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.masternoy.igor.rss.db.FeedRepository;
import com.masternoy.igor.rss.entities.Feed;
import com.masternoy.igor.rss.service.RssService;

public class MainActivity extends Activity {
	private static final String BLOG_URL = "http://blog.nerdability.com/feeds/posts/default";

	private RssService rssService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		refreshListView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// TODO [imasternoy] Move navigation somewhere
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		Intent intent;
		if (id == R.layout.activity_categories) {
			intent = new Intent(this, Categories.class);
			startActivity(intent);
			return true;
		} else if (id == R.layout.activity_saved_items) {
			intent = new Intent(this, SavedItems.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void refreshListView() {
		ListView lw = (ListView) findViewById(R.id.mainListView);
		rssService = new RssService(lw, this);

		FeedRepository feedRepository = new FeedRepository(this);
		feedRepository.openToRead();
		List<Feed> feeds = feedRepository.getBlogListing();
		feedRepository.close();

		List<String> getUrls = new ArrayList<String>();

		for (Feed feed : feeds) {
			// TODO [imasternoy] It's 22:20 And I'm Lazy
			// refactor to use list of URLs directly
			getUrls.add(feed.getUrl().toString());
		}
		rssService.execute(getUrls.toArray(new String[feeds.size()]));
	}
}
