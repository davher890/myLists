package com.android.mylists;

import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListItemAdapter extends BaseAdapter {

	private Integer[] itemIds = null;
	private HashMap<Integer, String> mapItems;
	private Context context;

	public ListItemAdapter(Context context, HashMap<Integer, String> mapItems) {
		this.context = context;
		this.mapItems = mapItems;
		this.itemIds = mapItems.keySet().toArray(new Integer[mapItems.size()]);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.list_item, parent, false);
		}

		this.itemIds = mapItems.keySet().toArray(new Integer[mapItems.size()]);
		((TextView) convertView.findViewById(R.id.tvItem)).setText(mapItems
				.get(itemIds[position]));

		return convertView;
	}

	@Override
	public int getCount() {
		return mapItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mapItems.get(itemIds[position]);
	}

	@Override
	public long getItemId(int position) {
		return itemIds[position];
	}

}
