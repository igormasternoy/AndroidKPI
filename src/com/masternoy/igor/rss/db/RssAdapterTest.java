package com.masternoy.igor.rss.db;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import android.test.AndroidTestCase;

import com.masternoy.igor.rss.entities.Feed;

public class RssAdapterTest extends AndroidTestCase {

	FeedRepository rssAdapter;

	public void preamble() {
		rssAdapter = new FeedRepository(getContext());
		rssAdapter.openToRead();
		rssAdapter.sqLiteHelper.onUpgrade(rssAdapter.sqLiteDatabase, 0, 0);
		List<Feed> feeds = rssAdapter.getBlogListing();
		rssAdapter.close();
		if (feeds.size() > 0) {
			rssAdapter.openToWrite();
			for (Feed feed : feeds) {
				rssAdapter.delete(feed.getDbId());
			}
			rssAdapter.close();
		}
	}

	@Test
	public void testWithClearDB() {
		preamble();
		assertNotNull(rssAdapter);
		rssAdapter.openToRead();
		List<Feed> feeds = rssAdapter.getBlogListing();
		rssAdapter.close();
		assertNotNull(feeds);
		assertTrue(feeds.size() == 0);
	}

	@Test
	public void testInsertSelect() {
		preamble();
		this.rssAdapter = new FeedRepository(getContext());
		assertNotNull(rssAdapter);
		Feed feed = createDummyFeed();
		rssAdapter.openToWrite();
		Long savedId = rssAdapter.insertBlogListing(feed);
		rssAdapter.close();
		assertNotNull(savedId);

		rssAdapter.openToRead();
		List<Feed> dbFeeds = rssAdapter.getBlogListing();
		rssAdapter.close();
		assertNotNull(dbFeeds);
		assertEquals(1, dbFeeds.size());
		feed.setDbId(savedId);
		assertEquals(dbFeeds.get(0), feed);
	}

	private Feed createDummyFeed() {
		Feed feed = new Feed();
		feed.setName("FeedName");
		try {
			feed.setUrl(new URL("https://google.com"));
		} catch (MalformedURLException e) {
			// Should never happen
		}
		return feed;

	}

}
