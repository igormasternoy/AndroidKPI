package com.masternoy.igor.rss;

import com.masternoy.igor.rss.service.RssService;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class MainActivity extends Activity {
	private static final String BLOG_URL = "http://blog.nerdability.com/feeds/posts/default";

	private RssService rssService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initListView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void initListView() {
		ListView lw = (ListView) findViewById(R.id.mainListView);
		lw.setOnScrollListener(new OnScrollListener() {

			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// Log.d(LOG_TAG, "scrollState = " + scrollState);
			}

			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				Log.d("LOG", "scroll: firstVisibleItem = " + firstVisibleItem
						+ ", visibleItemCount" + visibleItemCount
						+ ", totalItemCount" + totalItemCount);
			}
		});
		lw.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				return false;
			}
		});
		refreshList(lw);
	}

	private void refreshList(ListView lw) {
		rssService = new RssService(lw, this);
		rssService.execute(BLOG_URL);
	}
}
