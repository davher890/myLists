package com.android.mylists;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListItemAdapter extends ArrayAdapter<String> {

	private ArrayList<String> itemsList = null;

	public ListItemAdapter(Context context, ArrayList<String> itemsList) {
		super(context, 0, itemsList);
		this.itemsList = itemsList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.list_item, parent, false);
		}

		((TextView) convertView.findViewById(R.id.tvItem)).setText(itemsList
				.get(position));

		return convertView;
	}

}
