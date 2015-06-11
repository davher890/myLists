package com.android.mylists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ListItemActivity extends ActionBarActivity {

	ListView itemListView;
	HashMap<Integer, String> mapItems;
	ArrayList<String> newAdded;
	ArrayList<Integer> delItem;
	int list_id;
	ListItemAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_item);

		Intent b = getIntent();

		if (b != null) {
			mapItems = (HashMap<Integer, String>) b
					.getSerializableExtra("map_items");
		}
		list_id = b.getIntExtra("list_id", -1);

		itemListView = (ListView) findViewById(R.id.listViewItem);

		adapter = new ListItemAdapter(this, mapItems);
		itemListView.setAdapter(adapter);

		newAdded = new ArrayList<String>();
		delItem = new ArrayList<Integer>();

		itemListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {

				long itemId = adapter.getItemId(position);
				if (view.isSelected()) {
					delItem.add((int) itemId);
					view.setSelected(false);
				} else {
					delItem.add((int) itemId);
					view.setSelected(true);
				}
				return false;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_item, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_add) {
			final Dialog dialog = new Dialog(this);
			dialog.setContentView(R.layout.add_item_dialog);
			dialog.setTitle("New list item");

			Button dialogButton = (Button) dialog.findViewById(R.id.btOkDialog);
			// if button is clicked, close the custom dialog
			dialogButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					final EditText text = (EditText) dialog
							.findViewById(R.id.etItemContentDialog);
					String itemContent = text.getText().toString();
					newAdded.add(itemContent);
					mapItems.put(-1 * newAdded.size(), itemContent);
					adapter.notifyDataSetChanged();
					dialog.dismiss();
				}
			});
			dialog.show();
		}

		if (id == R.id.action_save) {

			List<NameValuePair> nvp = new ArrayList<NameValuePair>();
			nvp.add(new BasicNameValuePair("list_id", String.valueOf(list_id)));
			if (newAdded != null && newAdded.size() > 0) {
				for (String value : newAdded) {
					nvp.add(new BasicNameValuePair("items_add[]", value));
				}
			}
			if (delItem != null && delItem.size() > 0) {
				for (long value : delItem) {
					nvp.add(new BasicNameValuePair("items_del[]", String
							.valueOf(value)));
				}
			}
			if (nvp.size() > 1) {
				PostWSData postWSData = new PostWSData(
						"myLists/saveItemList.php/", ListItemActivity.this, nvp);
				postWSData.execute();
			}
		}
		return super.onOptionsItemSelected(item);
	}

	public void manageDataWs(String result) {

		JSONObject obj;
		try {
			obj = new JSONObject(result);
			obj.getString("error");
			Toast.makeText(this, "Some error happened!", Toast.LENGTH_SHORT)
					.show();
		} catch (JSONException e) {
			Toast.makeText(this, "Save completed!", Toast.LENGTH_SHORT).show();
			if (result.equals("null")) {
				for (int i = 0; i < delItem.size(); i++) {
					mapItems.remove(delItem.get(i));
				}
				delItem.clear();
				newAdded.clear();
				adapter.notifyDataSetChanged();
			}
		}

	}
}
