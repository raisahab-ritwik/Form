package com.cyberswift.buildmyform.Network;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Base64;
import android.util.Log;

@SuppressWarnings("unused")
public class Mypost {

	static String path;
	private String request = null;
	private String responsData = null;
	private boolean connecting;
	private boolean isTimeout = false;
	private String responseTypeString = null;
	private int rc;
	private Bitmap bmp=null;
	private Context context;
	private String category="";
	HttpURLConnection conn = null;
	
	String boundary = "---------------------------14737809831466499882746641449";
	DataOutputStream dos = null;  
	static int CONNECTIONTIMEOUT = 10000;
	 static String fullContent;

	@SuppressWarnings("static-access")
	public Mypost(Bitmap bmp,Context context,String path,String category) {
		// TODO Auto-generated constructor stub
		this.bmp=bmp;
		this.context=context;
		this.path=path;
		this.category=category;
	} 
	@SuppressWarnings("static-access")
	public Mypost(Context context,String path,String category)	{
		// TODO Auto-generated constructor stub
		this.context=context;
		this.path=path;
		this.category=category;
	}

	public String executeMultipartPost_NoImage() throws Exception {
		
		System.out.println("No Image POst called!!");
		String serverResponseMessage = null;
		StringBuilder s=new StringBuilder();
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			Bitmap bm = bmp;
			bm.compress(CompressFormat.PNG, 75, bos);
			byte[] imageData = bos.toByteArray();
			String encodedImage=Base64.encodeToString(imageData, 1000);
			System.out.println("encoded image"+encodedImage);
			byte[] encodedImages=Base64.encode(imageData, 1);

			URL url = new URL(path);

			String body = "--" + boundary + "\r\n" + "Content-Disposition: form-data; name=\"userfile\"; filename=\"image.jpg\"\r\n";
			body += "Content-Type: application/octet-stream\r\n\r\n";
			String fullContent = body + encodedImage + "\r\n--" + boundary + "--\r\n";
			// Open a HTTP  connection to  the URL
			conn = (HttpURLConnection) url.openConnection(); 
			conn.setDoInput(true); // Allow Inputs
			conn.setDoOutput(true); // Allow Outputs
			conn.setUseCaches(false); // Don't use a Cached Copy
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("ENCTYPE", "multipart/form-data");
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			conn.setRequestProperty("Connection", "close");
			conn.connect();
			// conn.getOutputStream().write(imageData);
			dos = new DataOutputStream(conn.getOutputStream());
			dos.writeBytes(boundary); 

			if (dos != null) {
				if (imageData.length> 0) {
					//dos.writeChars(fullContent);
					dos.writeBytes(fullContent);
					//dos.write(imageData);
				}
				dos.flush();
				dos.close();
				dos = null;      
			}
			int serverResponseCode = conn.getResponseCode();
			serverResponseMessage = conn.getResponseMessage();

			System.out.println("Response: " + serverResponseMessage+" "+serverResponseCode);
			
		} catch (Exception e) {
			// handle exception here
			Log.e(e.getClass().getName(), e.getMessage());
			s = new StringBuilder("Network connection failure.\nPlease check your network connection.\nYou can save this data to local storage.");
		}
		return serverResponseMessage;
	}
	public String executeMultipartPost_Image() throws Exception {

		StringBuilder s=new StringBuilder();
		try {	
			Bitmap bm = bmp;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
			byte[] outputByteArray = baos.toByteArray();	
			String base64EncodedString = Base64.encodeToString(outputByteArray, Base64.DEFAULT);
			
			String body = "--" + boundary + "\r\n" + "Content-Disposition: form-data; name=\"userfile\"; filename=\"image.jpg\"\r\n";
			 body += "Content-Type: application/octet-stream\r\n\r\n"; 
			fullContent = body + base64EncodedString + "\r\n--" + boundary + "--\r\n";
			
			 Log.i("Path", "IMage upload Url:: "+path);
			 
			String response=postMethodWay(path, fullContent);
			
			Log.i("Response", "Response ::: "+response);
			s.append(response);
		
		} catch (Exception e) {
			
			// handle exception here
			
			Log.i("Exp", "Exception at post");
			e.printStackTrace();
			s = new StringBuilder("Network connection failure.\nPlease check your network connection.\nYou can save this data to local storage.");
		}
		Log.i("response", "String builder response::: "+s.toString());
		return s.toString();
	} 

	public String getResponseData()
	{

		if(responsData != null){
			return responsData;
		}
		return null;	
	}

	public boolean isTimeout(){

		return isTimeout;	
	}


	public String getResponseTypeString(){

		if(responseTypeString != null){
			return responseTypeString;
		}
		return null;	
	}
	
	///////////////////////////////////
	public String getResponseCodeString() {

		if (rc != -1) {
			return String.valueOf(rc);
		}
		return null;
	}

	///////////////////////////////////////
	public String getStringResponseData(){

		if(responsData != null){
			return responsData;
		}
		return null;	
	}
	
	public static String postMethodWay(String hostName,
			String data) throws ClientProtocolException, IOException {
		
		String boundary = "---------------------------14737809831466499882746641449";
		HttpPost httppost = new HttpPost(hostName);
		httppost.setHeader("Connection", "Keep-Alive");
		httppost.setHeader("Content-type", "multipart/form-data; boundary=\"" + boundary + "\"");
		HttpParams httpParameters = new BasicHttpParams();
		HttpProtocolParams.setContentCharset(httpParameters, HTTP.UTF_8);
		HttpProtocolParams.setHttpElementCharset(httpParameters, HTTP.UTF_8);
		httpParameters.setParameter("http.socket.timeout", CONNECTIONTIMEOUT);
		String response_str = "";
		HttpClient hc=new DefaultHttpClient();
		try {
			httppost.setEntity(new StringEntity(fullContent));
			HttpResponse resp = hc.execute(httppost);
			HttpEntity ent = resp.getEntity();
			response_str = EntityUtils.toString(ent);
			Log.i("tag", "response ::: "+response_str);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("exception", "Exception Occured!!!");
		}    
		
		Log.i("response", "Response str"+response_str);
		return response_str;
	}


}
