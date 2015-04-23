package com.cyberswift.buildmyform.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.util.Log;
/**
 * 
 * @author Ravish.Gupta
 *it is used to post request to server using URL.
 */

public class HttpPostHandler {

	public String stringHttpResp = "";
	
	public static String responsecode="";
	public HttpPostHandler(String url, String jsonString, String header) {
		stringHttpResp = connect(url, jsonString, header);

	}
	public HttpPostHandler() {

	}
	
	@SuppressWarnings("unused")
	public String connect(String url, String jsonString, String header) {
		String result = null;
		int errorCode = 0;
		  HttpParams httpParameters = new BasicHttpParams();
// Set the timeout in milliseconds until a connection is established.
// The default value is zero, that means the timeout is not used.
        int timeoutConnection = 30000;
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
// Set the default socket timeout (SO_TIMEOUT)
// in milliseconds which is the timeout for waiting for data.
        int timeoutSocket = 30000;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		errorCode = 1;
        HttpClient httpclient = new DefaultHttpClient(httpParameters);

        // Prepare a request object
		Log.i("url", url);
		HttpPost httpPost = new HttpPost(url);

		errorCode = 2;
		StringEntity se = null;
		try {
			errorCode = 3;

			httpPost.setHeader("Content-type", "application/json");
			if (header != null) {
				httpPost.setHeader("apiKey", header);

			}
			se = new StringEntity(jsonString);
			httpPost.setEntity(se);
			errorCode = 4;
			errorCode = 5;
		} catch (UnsupportedEncodingException ue) {
			errorCode = 6;
		}
		errorCode = 7;

		errorCode = 8;

		// Execute the request
		HttpResponse response;
		try {

			response = httpclient.execute(httpPost);
			int responce_code = response.getStatusLine().getStatusCode();
		    responsecode=String.valueOf(responce_code);
            System.out.println(responce_code);
            Log.v("responce_code",""+responce_code);
			errorCode = 9;
			// Examine the response status

			errorCode = 10;
			if (responce_code == 200||responce_code==201) {
				// Get hold of the response entity
				HttpEntity entity = response.getEntity();
				// If the response does not enclose an entity, there is no need
				// to worry about connection release
				errorCode = 11;
				if (entity != null) {

					// A Simple JSON Response Read
					InputStream instream = entity.getContent();
					errorCode = 12;
					result = convertStreamToString(instream);

					// now you have the string representation of the HTML
					// request
					errorCode = 13;
					instream.close();

				}

				errorCode = 14;
			} 
			
			else {
				// responce code not ok
				return null;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}


	
	private static String convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the
		 * BufferedReader.readLine() method. We iterate until the BufferedReader
		 * return null which means there's no more data to read. Each line will
		 * appended to a StringBuilder and returned as String.
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
