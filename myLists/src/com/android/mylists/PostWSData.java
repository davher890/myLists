package com.android.mylists;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class PostWSData extends AsyncTask<String, Context, String> {

	private static final String HTTP_WWW_BARLATERRAZA_COM = "http://www.barlaterraza.com/";
	private ProgressDialog dialog;
	private Context c = null;
	private String url;
	private List<NameValuePair> nvp = null;

	public PostWSData(String url, Context c, List<NameValuePair> nvp) {

		this.url = new StringBuffer(HTTP_WWW_BARLATERRAZA_COM).append(url)
				.append("?").toString();
		this.nvp = nvp;
		this.c = (Context) c;
	}

	protected String doInBackground(String... params) {

		// Create a new HttpClient and Get Header
		String result = null;

		try {
			HttpClient httpclient = new DefaultHttpClient();

			HttpPost httpPost = new HttpPost(url);

			//httpPost.setHeader("Accept", "application/json");
			//httpPost.setHeader("Content-Type", "application/json");
			//httpPost.setHeader("Accept-Charset", "utf-8");

			httpPost.setEntity(new UrlEncodedFormEntity(nvp));
			HttpResponse response = httpclient.execute(httpPost);
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
