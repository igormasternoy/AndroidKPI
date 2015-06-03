package com.masternoy.igor.rss.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.masternoy.igor.rss.R;
import com.masternoy.igor.rss.entities.Article;
import com.masternoy.igor.rss.utils.DateUtils;

public class ArticleAdapter extends ArrayAdapter<Article> {


	public ArticleAdapter(Context context, List<Article> articles) {
		super(context, R.layout.article, articles);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Activity activity = (Activity) getContext();
		LayoutInflater inflater = activity.getLayoutInflater();

		View rowView = inflater.inflate(R.layout.article, null);
		Article article = getItem(position);
		

		TextView textView = (TextView) rowView.findViewById(R.id.article_title_text);
		textView.setText(article.getTitle());
		
		TextView dateView = (TextView) rowView.findViewById(R.id.article_listing_smallprint);
		String pubDate = article.getPubDate();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		Date pDate;
		try {
			pDate = df.parse(pubDate);
			pubDate = "published " + DateUtils.getDateDifference(pDate) + " by " + article.getAuthor();
		} catch (ParseException e) {
			Log.e("DATE PARSING", "Error parsing date..");
			pubDate = "published by " + article.getAuthor();
		}
		dateView.setText(pubDate);

		
		if (!article.isRead()){
			LinearLayout row = (LinearLayout) rowView.findViewById(R.id.article_row_layout);
			row.setBackgroundColor(Color.WHITE);
			textView.setTypeface(Typeface.DEFAULT_BOLD);
		}
		return rowView;

	} 
}