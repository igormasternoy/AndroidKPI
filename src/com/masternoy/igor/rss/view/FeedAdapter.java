package com.masternoy.igor.rss.view;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.masternoy.igor.rss.R;
import com.masternoy.igor.rss.entities.Feed;

public class FeedAdapter extends ArrayAdapter<Feed> {

	public FeedAdapter(Context context, List<Feed> articles) {
		super(context, R.layout.feed, articles);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Activity activity = (Activity) getContext();
		LayoutInflater inflater = activity.getLayoutInflater();

		View rowView = inflater.inflate(R.layout.feed, null);

		Feed article = getItem(position);
		TextView textView = (TextView) rowView
				.findViewById(R.id.feed_title_text);
		textView.setText(article.getName());

		// CheckBox checkBox = (CheckBox)
		// rowView.findViewById(R.id.feed_check_box);

		return rowView;

	}
}
