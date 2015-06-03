package com.masternoy.igor.rss.entities;

import java.io.Serializable;
import java.net.URL;

public class Feed implements Serializable {

	public static final String KEY = "FEED";

	private static final long serialVersionUID = 1L;

	private String name;
	private URL url;
	private long dbId;
	private boolean checked;

	public String getName() {
		return name;
	}
	
	

	public boolean isChecked() {
		return checked;
	}



	public void setChecked(boolean checked) {
		this.checked = checked;
	}



	public void setName(String name) {
		this.name = name;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public long getDbId() {
		return dbId;
	}
	
	public void setDbId(long dbId) {
		this.dbId = dbId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (dbId ^ (dbId >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Feed other = (Feed) obj;
		if (dbId != other.dbId)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Feed [name=" + name + ", url=" + url + ", dbId=" + dbId + "]";
	}



}
