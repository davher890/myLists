package com.android.mylists;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class IndexActivity extends ActionBarActivity implements OnClickListener {

	EditText etListName;
	EditText etListPwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);

		etListName = (EditText) findViewById(R.id.etListName);
		etListPwd = (EditText) findViewById(R.id.etListPwd);

		findViewById(R.id.btNew).setOnClickListener(this);
		findViewById(R.id.btJoin).setOnClickListener(this);
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

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.btNew) {
			Intent intent = new Intent(this, NewListActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.btJoin) {
			HashMap<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("name", etListName.getText().toString());
			paramMap.put("password", etListPwd.getText().toString());

			GetWSData get = new GetWSData("myLists/getItemList.php/", this,
					paramMap);
			get.execute();
		}
	}

	public void manageDataWs(String result) {

		result = result.substring(result.indexOf('['));

		JSONArray arrayItems = null;
		HashMap<Integer, String> mapItems = new HashMap<>();
		try {
			arrayItems = new JSONArray(result);

			if (!arrayItems.isNull(0)) {
				int list_id = ((JSONObject) arrayItems.getJSONObject(0))
						.getInt("id");

				for (int i = 1; i < arrayItems.length(); i++) {
					JSONObject obj = arrayItems.getJSONObject(i);
					mapItems.put(obj.getInt("item_id"),
							obj.getString("content"));
				}

				Intent i = new Intent(this, ListItemActivity.class);
				i.putExtra("list_id", list_id);
				i.putExtra("map_items", mapItems);
				startActivity(i);
			} else {
				Toast.makeText(this, "The list does not exist",
						Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
