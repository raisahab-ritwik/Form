package com.cyberswift.buildmyform.Util;

/**
 * 
 * @author Ritwik Rai
 * 
 */

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.Log;

import com.cyberswift.buildmyform.Constants.ConstantVO;
import com.cyberswift.buildmyform.Constants.Consts;
import com.cyberswift.buildmyform.Constants.User;
import com.cyberswift.buildmyform.VO.ResponseVO;

@SuppressLint("SimpleDateFormat") public class Util {
	static int CONNECTIONTIMEOUT = 10000;

	public static boolean isInternetAvailable(Context context) {

		ConnectivityManager conManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo i = conManager.getActiveNetworkInfo();
		if ((i == null) || (!i.isConnected()) || (!i.isAvailable())) {

			return false;
		}
		return true;
	}

	public static String postMethodWay(String hostName,
			HashMap<String, String> map) {

		HttpPost postMethod = new HttpPost(hostName);

		HttpParams httpParameters = new BasicHttpParams();
		HttpProtocolParams.setContentCharset(httpParameters, HTTP.UTF_8);
		HttpProtocolParams.setHttpElementCharset(httpParameters, HTTP.UTF_8);
		httpParameters.setParameter("http.socket.timeout", CONNECTIONTIMEOUT);
		String response_str = "";
		try {
			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			for (String key : map.keySet())
				postParameters.add(new BasicNameValuePair(key, map.get(key)));
			HttpClient hc = new DefaultHttpClient(httpParameters);
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters, HTTP.UTF_8);
			postMethod.setEntity(formEntity);
			response_str = EntityUtils.toString(hc.execute(postMethod)
					.getEntity());
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response_str;
	}

	public static User getBaseurl(Context context) {

		Log.i("Util", "get base url called");
		User object = new User();
		String[] stringURL;
		String baseUrl, userId = null, password;
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		String loginUrl = Consts.get_baseURL
				+ new ConstantVO(context).getDeviceToken() + "&vid=1.0&buster="
				+ date + "";
		
		Log.i("tag", "Login URL IS:: "+loginUrl);

		ResponseVO obj = new ResponseVO(loginUrl);
		
		boolean status_login_req = obj.fetchXML();
		
		if (status_login_req) {
			while (obj.parsingComplete)
				;

			String responseURl = obj.getString();
			
			byte[] tmp2 = Base64.decode(responseURl, 0);

			String val2 = null;
			try {
				val2 = new String(tmp2, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stringURL = val2.split(Pattern.quote("|*|"));

			baseUrl = stringURL[0].toString().trim();
			userId = stringURL[1].toString().trim();
			password = stringURL[2].toString().trim();

			object.baseUrl = baseUrl;
			object.userId = userId;
			object.password = password;

			ConstantVO.saveUserDataToPrefrences("userId", context, userId);
			ConstantVO.saveUserDataToPrefrences(ConstantVO.BASEURL, context,baseUrl);
		} else if (!status_login_req) {
			object.baseUrl = Consts.login_no_redirection_address;
		}

		return object;

	}
}
