package com.android.mylists;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class IndexActivity extends ActionBarActivity implements OnClickListener {

	EditText etListName;
	EditText etListPwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);

		etListName = (EditText) findViewById(R.id.etListName);
		etListPwd = (EditText) findViewById(R.id.etListPwd);
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

			GetWSData get = new GetWSData("joinList.php/", this, paramMap);
			get.execute();
		}
	}

	public void manageDataWs(String result) {

	}
}
