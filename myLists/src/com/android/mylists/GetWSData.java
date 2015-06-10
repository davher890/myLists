package com.android.mylists;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class GetWSData extends AsyncTask<String, Context, String> {

	private static final String HTTP_WWW_BARLATERRAZA_COM = "http://www.barlaterraza.com/";
	private ProgressDialog dialog;
	private Context c = null;
	private String url;
	private HashMap<String, String> mapParams = null;

	public GetWSData(String url, Context c, HashMap<String, String> mapParams) {

		this.url = new StringBuffer(HTTP_WWW_BARLATERRAZA_COM).append(url)
				.append("?").toString();
		this.mapParams = mapParams;
		this.c = (Context) c;
	}

	protected String doInBackground(String... params) {

		// Create a new HttpClient and Get Header
		String result = null;

		try {
			HttpClient httpclient = new DefaultHttpClient();

			if (mapParams != null) {
				StringBuffer urlParams = new StringBuffer(url);
				for (String name : mapParams.keySet()) {
					urlParams.append(name).append("=").append('\'')
							.append(mapParams.get(name)).append('\'')
							.append("&");
				}
				url = urlParams.toString();
			}
			HttpGet httpget = new HttpGet(url);

			httpget.setHeader("Accept", "application/json");
			httpget.setHeader("Content-Type", "application/json");
			httpget.setHeader("Accept-Charset", "utf-8");

			HttpResponse response = httpclient.execute(httpget);
			result = EntityUtils.toString(response.getEntity());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	protected void onPreExecute() {
		dialog = new ProgressDialog(c);
		dialog.setTitle(c.getResources().getString(R.string.app_name));
		dialog.setMessage("Descargando Datos...");
		dialog.setCancelable(true);
		dialog.setIndeterminate(true);
		dialog.show();
	}

	protected void onPostExecute(String result) {
		try {
			Method method = c.getClass()
					.getMethod("manageDataWs", String.class);
			method.invoke(c, result);
		} catch (IllegalAccessException | NoSuchMethodException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		dialog.cancel();
	}
}
