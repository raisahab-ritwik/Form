package com.cyberswift.buildmyform.Tasks;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Window;

import com.cyberswift.buildmyform.R;
import com.cyberswift.buildmyform.Constants.ConstantVO;
import com.cyberswift.buildmyform.Listeners.FormDataSyncListener;
import com.cyberswift.buildmyform.Network.HttpPostHandler;

@SuppressLint({ "NewApi", "SimpleDateFormat" })
public class FormDataSyncTask extends AsyncTask<Void, Object, String> {

	Dialog progressDialogMG;
	Context context;
	public String responseString = "";
	public String postData;
	public static int respose_Code = 10;

	public static String responseFlag = "";

	private String formData;
	private String uniqueId;
	private String formId;
	private String userId;
	private String baseURL;

	private boolean isDataUpdate;
	private boolean isOfflineMode=false;

	public FormDataSyncListener Listener;

	
	public FormDataSyncTask(Context context, String FormData, String uniqueId,
			String formId, String userId, String baseUrl, Boolean isDataUpdate,Boolean isOfflineMode) {
		this.formData = FormData;
		this.uniqueId = uniqueId;
		this.formId = formId;
		this.userId = userId;
		this.baseURL = baseUrl;
		this.context = context;
		this.isDataUpdate = isDataUpdate;
		this.isOfflineMode=isOfflineMode;

	}

	@Override
	protected void onPreExecute() {

		progressDialogMG = new Dialog(context);
		progressDialogMG.requestWindowFeature(Window.FEATURE_NO_TITLE);
		progressDialogMG.getWindow ().setBackgroundDrawableResource(android.R.color.transparent);
		//RelativeLayout layout=createDialogScreen();
		progressDialogMG.setContentView(R.layout.custom_progress_dialog_small);
		progressDialogMG.setCancelable(false);
		
		
		progressDialogMG.show();

	}

	protected String doInBackground(Void... params) {

		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String URL;

		if (isDataUpdate == false) {

			System.out.println("Insert TAsk invoked!!");

			postData = "{\"form_xml_id\":\"" + formId + "\"\n,"
					+ "\n\"datetime_stamp\":\"" + date + "\"\n,"
					+ "\n\"unique_id\":\"" + uniqueId + "\"\n,"
					+ "\n\"version_id\":\"" + ConstantVO.version + "\"\n,"
					+ "\n\"data\":\"" + formData + "\"\n," + "\n\"user_id\":\""
					+ userId + "\"\n" + "}\n";
			URL = baseURL + "MobileAppService.svc/form_data";

			System.out.println("Insert URL:::" + URL);

		} else {

			System.out.println("Update task Invokded!!!!!!");
			postData = "{\"form_xml_id\":\"" + formId + "\",\"unique_id\":\""
					+ uniqueId + "\",\"version_id\":\"" + ConstantVO.version
					+ "\",\"data\":\"" + formData + "\",\"user_id\":\""
					+ userId + "\"}";

			URL = baseURL + "FDAS.svc/FDUpdateData";

			System.out.println("UpdateURL:::" + URL);

		}

		Log.i("Postdata", "PostData::::" + postData);

		HttpPostHandler httpPostHandler = new HttpPostHandler();
		responseString = httpPostHandler.connect(URL, postData, null);
		if (responseString != null) {

		}
		publishProgress(201);
		Log.i("responseString", "responseString" + responseString);

		return responseString;
	}

	@Override
	protected void onProgressUpdate(Object... values) {

		respose_Code = (Integer) values[0];
		System.out.println("responsecode::" + respose_Code);

		ConstantVO.resCode = respose_Code;
		ConstantVO.saveUserDataToPrefrencesInt(ConstantVO.RES, context,
				respose_Code);
		switch (respose_Code) {

		case 200:

			isDataUpdate = true;

			System.out.println("respose_Code" + isDataUpdate);

			break;
		case 201:

			isDataUpdate = true;

			System.out.println("respose_Code" + isDataUpdate);

			break;
		case 204:

			break;
		case 3:

			break;
		case 4:

			break;
		case 5:

			break;
		default:

			break;
		}

		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(String result) {

		if (progressDialogMG != null && progressDialogMG.isShowing()) {
			progressDialogMG.dismiss();
		}
		super.onPostExecute(result);
		// send callback status boolean
		Log.i("formdatasynctask", "isupdate:: "+isDataUpdate);
		
		if(isOfflineMode)
			
		Listener.formdataAsyncListenerCallback(isDataUpdate);
	}

}
