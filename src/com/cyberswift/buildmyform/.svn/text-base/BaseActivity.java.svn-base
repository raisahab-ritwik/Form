package com.cyberswift.buildmyform;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.Window;

import com.cyberswift.buildmyform.Constants.ConstantVO;
import com.cyberswift.buildmyform.Constants.Consts;
import com.cyberswift.buildmyform.VO.ResponseVO;



@SuppressLint({ "SimpleDateFormat", "HandlerLeak" })
public class BaseActivity extends Activity {
	protected Document params;
	protected String[] stringURL;
	protected String baseUrl = "";
	protected String userId = "";
	protected String password = "";
	protected String error;


	public void showDialog(String title, String alert, boolean finishActivity) {
		if (((Activity) this).isFinishing() == false) {
			final boolean finish = finishActivity;
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
			alertDialog.setTitle(title);
			alertDialog.setIcon(R.drawable.icon_error);
			alertDialog.setMessage(alert);
			alertDialog.setCancelable(false);

			alertDialog.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							if (finish == true)
								finish();
						}
					});
			alertDialog.show();
		}
	}

protected class MyBackgroundTask extends AsyncTask<String, Object, Boolean> {
		Dialog mDialog;
	
		Context context;
		public MyBackgroundTask(Context context) {
			
			this.context = context;
			mDialog = new Dialog(context);
			mDialog.setCancelable(false);
			mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			mDialog.getWindow ().setBackgroundDrawableResource(android.R.color.transparent);
			mDialog.setContentView(R.layout.custom_progress_dialog_small);
			
		}

		@Override
		protected void onPreExecute() {
			
			// initialize views like progress dialog.
		
			
			
			if(!mDialog.isShowing())
				mDialog.show();
		
			//waitSpinner = ProgressDialog.show(context, "Please Wait ...","Loading ...", true);

		}

		@Override
		protected Boolean doInBackground(String... xml) {
			getInBackground(xml[0]);
			return true;
		}

	

		@Override
		public void onPostExecute(Boolean success) {
			/*if (!((Activity) BaseActivity.this).isFinishing())
				waitSpinner.cancel();*/
			if(mDialog!=null&&mDialog.isShowing())
				
				mDialog.dismiss();
			
			if (error != null && !error.equalsIgnoreCase("")) {
				showDialog("",error,true);
				error = "";
			} else
				try {
					loadUI();
				} catch (Exception e) {
					showDialog("","Oops! Something went wrong",true);
					e.printStackTrace();
				}
			// update UI with the result
		}
	}

	// update UI with the result
	public void loadUI() throws Exception{
		// override it load ui
	}
	
/**
 * Get in Background method for callback to base activity which does the Work 
 * that is needed to be Done in the Do-in-background 
 * Override and use
 * **/
	public void getInBackground(String string) {
	
		
	}
	
	
/**
 *  to check whether connectivity is available or not
 * **/
	public boolean isOnline() {
		
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	protected NodeList getNodeListByTagName(String tagName) {
		NodeList m = null;
		if (tagName != null && tagName.length() > 0) {
			m = params.getElementsByTagName(tagName);
		}
		return m;
	}

	protected String getTagValue(String tagName) {
		NodeList m0 = getNodeListByTagName(tagName);
		if (m0 != null && m0.getLength() > 0) {
			Element e1 = (Element) m0.item(0);
			if (e1 != null)
				return e1.getFirstChild() != null ? e1.getFirstChild()
						.getNodeValue() : "";
		}
		return "";
	}

	public String getValue(Element item, String str) {
		NodeList n = item.getElementsByTagName(str);
		return this.getElementValue(n.item(0));
	}

	public final String getElementValue(Node elem) {
		Node child;
		if (elem != null) {
			if (elem.hasChildNodes()) {
				for (child = elem.getFirstChild(); child != null; child = child
						.getNextSibling()) {
					if (child.getNodeType() == Node.TEXT_NODE) {
						return child.getNodeValue();
					}
				}
			}
		}
		return "";
	}

	/**
	 * Gets the base url from GetMainURL
	 * **/
	public String[] getBaseUrl() {
		
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		
		String loginUrl = Consts.get_baseURL + new ConstantVO(BaseActivity.this).getDeviceToken() + "&vid=1.0&buster=" + date + "";	
		
		ResponseVO obj = new ResponseVO(loginUrl);
		obj.fetchXML();
		while (obj.parsingComplete);
		
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

		ConstantVO.saveUserDataToPrefrences("userId", BaseActivity.this, userId);
		ConstantVO.saveUserDataToPrefrences(ConstantVO.BASEURL,BaseActivity.this, baseUrl);
		
		return stringURL;
	}
	
	/** Alert Dialog for unsuccessful login
	 * **/
	public void showLoginDialog(String title,String message,Context context){
		
		AlertDialog.Builder alert=new AlertDialog.Builder(context);
		alert.setTitle(title);
		alert.setMessage(message);
		alert.setIcon(R.drawable.icon_error);
		alert.setPositiveButton(context.getResources().getString(R.string.crash_dialog_ok_toast), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				dialog.cancel();
			}
		});
		alert.show();
	}
}
