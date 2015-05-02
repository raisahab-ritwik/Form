package com.cyberswift.buildmyform.Constants;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;
import java.util.Vector;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

public class ConstantVO {

	public static Vector<Object> voVector;
	
	private Context context = null;
	
	private static final String USER_PREF = "USER_PREF";
	
	private static final String LOGIN_PREF = "loginPrefs";
	
	private static Editor editor;
	
	public final static String URL = "url";
	
	public final static String RES = "";

	public final static String BASEURL = "baseurl";
	
	public final static String POSTDATA = "postdata";
	
	protected String urlString = null;
	
	protected XmlPullParserFactory xmlFactoryObject;
	
	public static final String USER_NAME = "USER_NAME";
	
	public static final String PASSWORD = "PASSWORD";
	
	public static String username;
	
	public static String password;
	
	public static String baseURl;
	
	public static final String UID = "UUID";
	
	public static String iurl;
	
	public static int resCode;
	
	public static String version = "1.0";
	
	public static final String NetworkError = "Oops!Unable to get network Connections";

	boolean status = false;

	public ConstantVO(Context context) {

		this.context = context;
		
	}

	public boolean isOnline() {
		
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			
			return true;
		}
		
		return false;
	}

	public ConstantVO() {

	}

	public String getDeviceToken() {
		
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		
		return telephonyManager.getDeviceId();

	}

	public static void updateUserData(Context context) {
		iurl = getUserDataFromPrefrences(ConstantVO.URL, context);
		username = getUserDataFromPrefrences(ConstantVO.USER_NAME, context);
		password = getUserDataFromPrefrences(ConstantVO.PASSWORD, context);

	}

	public static String stringByAddingPercentEscapesUsingEncoding(
			String input, String charset) throws UnsupportedEncodingException {
		byte[] bytes = input.getBytes(charset);
		StringBuilder sb = new StringBuilder(bytes.length);
		for (int i = 0; i < bytes.length; ++i) {
			int cp = bytes[i] < 0 ? bytes[i] + 256 : bytes[i];
			if (cp <= 0x20
					|| cp >= 0x7F
					|| (cp == 0x22 || cp == 0x25 || cp == 0x3C || cp == 0x3E
							|| cp == 0x20 || cp == 0x5B || cp == 0x5C
							|| cp == 0x5D || cp == 0x5E || cp == 0x60
							|| cp == 0x7b || cp == 0x7c || cp == 0x7d)) {
				sb.append(String.format("%%%02X", cp));
			} else {
				sb.append((char) cp);
			}
		}
		return sb.toString();
	}

	public static String stringByAddingPercentEscapesUsingEncoding(String input) {
		try {
			return stringByAddingPercentEscapesUsingEncoding(input, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(
					"Java platforms are required to support UTF-8");
			// will never happen
		}

	}

	public String getRandomUiid() {

		UUID uuid = UUID.randomUUID();
		String randomUUIDString = uuid.toString();

		System.out.println("Random UUID String = " + randomUUIDString);

		System.out.println("UUID version       = " + uuid.version());
		System.out.println("UUID variant       = " + uuid.variant());
		return randomUUIDString;
	}

	public static String getUserDataFromPrefrences(String key, Context context) {
		
		SharedPreferences pref = context.getSharedPreferences(USER_PREF, 0);
		
		return pref.getString(key, "");
		
	}

	public static void saveUserDataToPrefrences(String key, Context context,String value) {
		
		SharedPreferences pref = context.getSharedPreferences(USER_PREF, 0);
		
		editor = pref.edit();
		
		editor.putString(key, value);
		
		editor.commit();
	}

	public static String getUserDataFromPrefrencess(String key, Context context) {
		
		SharedPreferences pref = context.getSharedPreferences(LOGIN_PREF, 0);
		
		return pref.getString(key, "");
		
	}

	public static void saveUserDataToPrefrencess(String key, Context context,String value) {
		
		SharedPreferences pref = context.getSharedPreferences(LOGIN_PREF, 0);
		
		editor = pref.edit();
		
		editor.putString(key, value);
		
		editor.commit();
	}

	public static void saveUserDataToPrefrencesInt(String key, Context context,int value) {
		
		SharedPreferences pref = context.getSharedPreferences(USER_PREF, 0);
		
		editor = pref.edit();
		
		editor.putInt(key, value);
		
		editor.commit();
	}

	public static int getUserDataFromPrefrencesInt(String key, Context context) {
		
		SharedPreferences pref = context.getSharedPreferences(USER_PREF, 0);
		
		return pref.getInt(key, -10);
	}

	public Boolean fetchXML() {

		try {
			
			URL url = new URL(urlString.trim());
			
			Log.i("ConstantVo", "url string ::" + urlString);
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setReadTimeout(10000 /* milliseconds */);
			
			conn.setConnectTimeout(15000 /* milliseconds */);
			
			conn.setRequestMethod("GET");
			
			conn.setDoInput(true);
			
			conn.connect();
			
			Log.i("tag", "Response code:: "+conn.getResponseCode());
			Log.i("tag", "Response code:: "+conn.getResponseMessage());
			
			InputStream stream = conn.getInputStream();
			
			xmlFactoryObject = XmlPullParserFactory.newInstance();
			
			XmlPullParser myparser = xmlFactoryObject.newPullParser();

			myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			
			myparser.setInput(stream, null);
			
			parseXMLAndStoreIt(myparser);
			
			stream.close();
			
			conn.disconnect();
			
			status = true;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			Log.i("ConstantVO", "Response Message :: "+e.getLocalizedMessage());
			
			status = false;
			
		}
		Log.i("ConstatntVO", "Login status::" + status);
		
		return status;
	}

	public void parseXMLAndStoreIt(XmlPullParser xmlPullParser) {

	}

	@SuppressLint("SimpleDateFormat")
	public String getCurrentDate() {

		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		return date;
		
	}
}
