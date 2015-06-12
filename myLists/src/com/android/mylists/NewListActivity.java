package com.android.mylists;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewListActivity extends ActionBarActivity implements
		OnClickListener {

	EditText etNewListName;
	EditText etNewListPwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_list);

		etNewListName = (EditText) findViewById(R.id.etNewListName);
		etNewListPwd = (EditText) findViewById(R.id.etNewListPwd);

		((Button) findViewById(R.id.btCreateList)).setOnClickListener(this);
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

		if (v.getId() == R.id.btCreateList) {
			HashMap<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("name", etNewListName.getText().toString());
			paramMap.put("password", etNewListPwd.getText().toString());

			GetWSData get = new GetWSData("myLists/addList.php/", this,
					paramMap);
			get.execute();
		}
	}

	public void manageDataWs(String result) {

		result = result.substring(result.indexOf('['));

		JSONObject obj;
		try {
			obj = new JSONObject(result);
			obj.getString("error");
			Toast.makeText(this, "Some error happened!", Toast.LENGTH_SHORT)
					.show();
		} catch (JSONException e) {
			Toast.makeText(this, "Save completed!", Toast.LENGTH_SHORT).show();
		}

	}
}
