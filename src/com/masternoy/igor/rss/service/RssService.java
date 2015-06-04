package com.masternoy.igor.rss.service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.masternoy.igor.rss.db.RssAdapter;
import com.masternoy.igor.rss.entities.Article;
import com.masternoy.igor.rss.view.ArticleAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

public class RssService extends AsyncTask<String, Void, List<Article>> {

	private ProgressDialog progress;
	private Context context;
	private ListView listView;
	private Activity activity;

	public RssService(ListView listView, Activity activity) {
		this.context = activity;
		this.activity = activity;
		this.listView = listView;
		this.progress = new ProgressDialog(context);
		this.progress.setMessage("Loading...");
	}

	protected void onPreExecute() {
		Log.e("ASYNC", "PRE EXECUTE");
		progress.show();
	}

	protected void onPostExecute(final List<Article> articles) {
		Log.e("ASYNC", "POST EXECUTE");
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				for (Article a : articles) {
					Log.d("DB", "Searching DB for GUID: " + a.getGuid());
					RssAdapter dba = new RssAdapter(context);
					dba.openToRead();
					Article fetchedArticle = dba.getBlogListing(a.getGuid());
					dba.close();
					if (fetchedArticle == null) {
						Log.d("DB",
								"Found entry for first time: " + a.getTitle());
						dba = new RssAdapter(context);
						dba.openToWrite();
						dba.insertBlogListing(a.getGuid());
						dba.close();
					} else {
						a.setDbId(fetchedArticle.getDbId());
						a.setOffline(fetchedArticle.isOffline());
						a.setRead(fetchedArticle.isRead());
					}
				}
				ArticleAdapter adapter = new ArticleAdapter(context, articles);
				listView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			}
		});
		progress.dismiss();
	}

	@Override
	protected List<Article> doInBackground(String... urls) {
		URL url = null;
		List<Article> articleList = new ArrayList<Article>();
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();

			for (String feed : urls) {
				url = new URL(feed);
				RSSReader rssReader = new RSSReader();
				xr.setContentHandler(rssReader);
				xr.parse(new InputSource(url.openStream()));
				articleList.addAll(rssReader.getArticleList());
			}
			Log.e("ASYNC", "PARSING FINISHED");
			return articleList;

		} catch (IOException e) {
			Log.e("RSS Handler IO", e.getMessage() + " >> " + e.toString());
		} catch (SAXException e) {
			Log.e("RSS Handler SAX", e.toString());
		} catch (ParserConfigurationException e) {
			Log.e("RSS Handler Parser Config", e.toString());
		}
		return articleList;

	}
}