package com.masternoy.igor.rss.db;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.masternoy.igor.rss.entities.Feed;

public class FeedRepository {

	public static final String KEY_ROWID = BaseColumns._ID;
	public static final String KEY_NAME = "name";
	public static final String KEY_URL = "url";

	private static final String DATABASE_NAME = "blogposts";
	private static final String DATABASE_TABLE = "blogpostFeed";
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE_LIST_TABLE = "create table if not exists "
			+ DATABASE_TABLE + " (" + KEY_ROWID
			+ " integer primary key autoincrement, " + KEY_NAME
			+ " text not null, " + KEY_URL + " text not null);";

	private SQLiteHelper sqLiteHelper;
	private SQLiteDatabase sqLiteDatabase;

	public FeedRepository(Context c) {
		sqLiteHelper = new SQLiteHelper(c, DATABASE_NAME, null,
				DATABASE_VERSION);
		openToWrite();
		sqLiteDatabase.execSQL(DATABASE_CREATE_LIST_TABLE);
		close();
	}

	public FeedRepository openToRead() throws android.database.SQLException {
		sqLiteDatabase = sqLiteHelper.getWritableDatabase();
		return this;
	}

	public FeedRepository openToWrite() throws android.database.SQLException {
		sqLiteDatabase = sqLiteHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		sqLiteHelper.close();
	}

	public class SQLiteHelper extends SQLiteOpenHelper {
		public SQLiteHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE_LIST_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
	}

	public long insertBlogListing(Feed feed) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_NAME, feed.getName());
		initialValues.put(KEY_URL, feed.getUrl().toString());
		return sqLiteDatabase.insert(DATABASE_TABLE, null, initialValues);
	}

	public List<Feed> getBlogListing() {
		try {
			Cursor mCursor = sqLiteDatabase.query(true, DATABASE_TABLE,
					new String[] { KEY_ROWID, KEY_NAME, KEY_URL }, null, null,
					null, null, null, null);
			List<Feed> feeds = new ArrayList<Feed>();
			if (mCursor != null && mCursor.getCount() > 0) {
				while (mCursor.moveToNext()) {
					Feed a = new Feed();
					a.setDbId(mCursor.getLong(mCursor.getColumnIndex(KEY_ROWID)));
					a.setName(mCursor.getString(mCursor
							.getColumnIndex(KEY_NAME)));
					a.setUrl(new URL(mCursor.getString(mCursor
							.getColumnIndex(KEY_URL))));
					feeds.add(a);
				}
				return feeds;
			}
		} catch (Exception e) {
			Log.e("Something happens", "Failed to get feeds", e);
		}
		return Collections.emptyList();
	}

}