package com.android.mylists;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class ListItemActivity extends ActionBarActivity {

	ListView itemListView;
	ArrayList<String> listItem;
	int list_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_item);

		Bundle b = getIntent().getExtras();

		if (b != null) {
			this.listItem = (ArrayList<String>) b
					.getStringArrayList("listItem");
		}
		list_id = b.getInt("id");

		itemListView = (ListView) findViewById(R.id.listViewItem);

		ListItemAdapter adapter = new ListItemAdapter(this, listItem);
		itemListView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.index, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void manageDataWs(String result) {

	}
}
