package com.cyberswift.buildmyform.Tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.cyberswift.buildmyform.Constants.ConstantVO;
import com.cyberswift.buildmyform.Constants.Consts;
import com.cyberswift.buildmyform.Constants.User;
import com.cyberswift.buildmyform.Listeners.LoginListener;
import com.cyberswift.buildmyform.Network.HttpGetHandler;
import com.cyberswift.buildmyform.Util.CustomAsynkLoader;
import com.cyberswift.buildmyform.Util.Util;
import com.cyberswift.buildmyform.Util.Validation;
import com.cyberswift.buildmyform.VO.ResponseLogin;

public class LoginAsyncTask extends AsyncTask<Void, Void, String> {

	private String userID;
	
	private String orgID;
	
	private String is_valid;
	
	private String resPassword;
	
	private String email;
	
	CustomAsynkLoader mDialog;
	
	User obj;
	
	String email_id, password;

	Context context;

	public LoginListener login_listener;

	public LoginAsyncTask(Context context, String email_id, String password) {

		mDialog = new CustomAsynkLoader(context);
		
		this.context = context;
		
		this.email_id = email_id;
		
		this.password = password;
		
		obj=new User();

	}

	@Override
	protected void onPreExecute() {
		
		super.onPreExecute();

		if (!mDialog.isDialogShowing()) {
			
			mDialog.ShowDialog();
		
		}

	}

	@Override
	protected String doInBackground(Void... params) {
		
		String jsonLayer = null;

		if (Util.isInternetAvailable(context)) {

			try {

				obj = Util.getBaseurl(context);// Base U r l call
				
				if (obj.baseUrl.equalsIgnoreCase(Consts.login_no_redirection_address)) {

					obj.login_status = Consts.login_not_responding;

				} else {

					if (sendRequestForAuthenticationLogin() == true) {

						if (email_id.length() > 0 && password.length() > 0) {

							ConstantVO.username = email_id;
							
							ConstantVO.password = password;

							sendRequestForLogin();

						} else {
							
							// enter valid details
							
							// showDialog("Alert","Please enter valid Details",false);
							
							obj.login_status = Consts.login_invalid_credentials;
						
						}

					}

					else {

						// no network connection
						
						// showDialog("Alert","Unable to get network connections",false);
						
						obj.login_status = Consts.login_unable_to_get_network_access;
					
					}

				}

			}

			catch (Exception e) {
				
				e.printStackTrace();
				
				// showDialog("Alert","Unable to get network connections",false);
				
				obj.login_status = Consts.login_unable_to_get_network_access;
			
			}
		} else {

			Log.i("error", "no network Connections");
			
			// showDialog("Alert","Unable to get network connections",false);
			
			obj.login_status = Consts.login_unable_to_get_network_access;
		
		}

		return jsonLayer;
	}

	@Override
	protected void onPostExecute(String result) {
		
		super.onPostExecute(result);
		
		login_listener.logincallback(obj);

		if (mDialog.isDialogShowing()) {
			
			mDialog.DismissDialog();

		}

	}

	public boolean sendRequestForAuthenticationLogin() {

		obj = Util.getBaseurl(context);

		if (obj.baseUrl.equalsIgnoreCase(Consts.login_no_redirection_address)) {

			return false;
			
		} else {
			
			String urlAuthentication = obj.baseUrl + "Account/Login.aspx?user=" + obj.userId + "&pass=" + obj.password + "";
			
			new HttpGetHandler().connect(urlAuthentication);
			
			return true;
			
		}

	}

	public void sendRequestForLogin() {

		String urlLogin123 = obj.baseUrl + "MobileAppService.svc/users?$filter=email_id eq" + " '" + email_id + "'";
		
		Log.i("Login", "Login url is :::   " + urlLogin123);

		String urlLogin = ConstantVO .stringByAddingPercentEscapesUsingEncoding(urlLogin123);
		
		Log.i("tag", "response login:: " + urlLogin);

		ResponseLogin obj = new ResponseLogin(urlLogin);
		
		Log.i("LoginAsyncTask", "Login Status:: ");

		boolean status_login_req = obj.fetchXML();

		if (status_login_req) {
			
			while (obj.parsingComplete);

			orgID = obj.getOrganization_id();
			
			is_valid = obj.getIs_valid();
			
			resPassword = obj.getPassword();
			
			userID = obj.getUser_id();
			
			email = obj.getEmail_id();

			this.obj.organizationId = orgID;
			
			this.obj.userId = userID;
			
			this.obj.email = email;

			@SuppressWarnings("static-access")
			String passwordMd5 = new Validation().md5(password);

			Log.i("tag", "orgID " + orgID);
			
			Log.i("tag", "baseUrl " + this.obj.baseUrl);
			
			Log.i("tag", "userID " + userID);
			
			Log.i("tag", "email " + email);
			
			Log.i("tag", "is_valid " + is_valid);

			if (is_valid.equals("true") && passwordMd5.equals(resPassword)) {
				
				// success
				Log.i("tag", "Success 1 " + orgID);
				
				this.obj.login_status = Consts.login_success;

			} else if (is_valid.equals("d:is_valid")|| !passwordMd5.equals(resPassword)) {
				
				// invalid user id
				Log.i("tag", "wrong password!!");
				
				this.obj.login_status = Consts.login_invaliduser;
			
			} else if (is_valid.equals("false")) {

				// showDialog("Alert","Invalid Login", false);
				Log.i("tag", "false login");
				
				this.obj.login_status = Consts.login_invalid;
			
			} else {
				
				// success
				Log.i("tag", "Success 2");
				
				this.obj.login_status = Consts.login_success;
			}
		} else if (!status_login_req) {
			
			this.obj.login_status = Consts.login_not_responding;
		
		}
	}

}
