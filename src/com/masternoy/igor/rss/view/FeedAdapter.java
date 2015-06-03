package com.masternoy.igor.rss.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.SparseBooleanArray;
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
	private final List<Long> mSelectedItemsIds = new ArrayList<Long>();

	public FeedAdapter(Context context, List<Feed> articles) {
		super(context, R.layout.feed, articles);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Activity activity = (Activity) getContext();
		LayoutInflater inflater = activity.getLayoutInflater();

		View rowView = inflater.inflate(R.layout.feed, null);

		final Feed article = getItem(position);
		TextView textView = (TextView) rowView
				.findViewById(R.id.feed_title_text);
		textView.setText(article.getName());

		textView = (TextView) rowView
				.findViewById(R.id.feed_listing_smallprint);
		textView.setText(article.getUrl().toString());

		 final CheckBox checkBox = (CheckBox)
		 rowView.findViewById(R.id.feed_check_box);
		 
		 checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			 
			private final long id = article.getDbId();
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				selectView(id,isChecked);
			}
		});

		return rowView;

	}

	public int getSelectedCount() {
		return mSelectedItemsIds.size();
	}

	public List<Long> getSelectedIds() {
		return mSelectedItemsIds;
	}

	public void selectView(long position, boolean value) {
		if (value)
			mSelectedItemsIds.add(position);
		else
			mSelectedItemsIds.remove(position);
//		notifyDataSetChanged();
	}

	public void removeSelection() {
		mSelectedItemsIds.clear();
//		notifyDataSetChanged();
	}

}
