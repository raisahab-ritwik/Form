package com.cyberswift.buildmyform;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.cyberswift.buildmyform.Util.GPSTracker;
import com.cyberswift.buildmyform.Util.JavaScriptHandler;
import com.cyberswift.buildmyform.Util.Util;

public class FormActivity extends Activity {

	private WebView wv_bmf_form;
	private GPSTracker gps;
	private Context context;
	private Dialog mDialog;
	private String barcode_id;
	private int SCANNER_REQUEST_CODE = 34373;
	private static String formLoadUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_form);
		context = FormActivity.this;
		initView();
	}

	private void initView() {

		wv_bmf_form = (WebView) findViewById(R.id.wv_bmf_form);
		Bundle formDATAfromLIST = getIntent().getExtras()
				.getBundle("FORM_DATA");
		gps = new GPSTracker(FormActivity.this);
		gps.getLocation();

		wv_bmf_form.getSettings().setJavaScriptEnabled(true);
		wv_bmf_form.getSettings().setBuiltInZoomControls(true);
		wv_bmf_form.getSettings().setDomStorageEnabled(true);
		wv_bmf_form.requestFocus(View.FOCUS_DOWN);

		wv_bmf_form.setWebChromeClient(new WebChromeClient());

		// Interface to handle java-script call

		wv_bmf_form.addJavascriptInterface(new JavaScriptHandler(this),
				"MyHandler");

		wv_bmf_form.setWebViewClient(new myWebClient());
		formLoadUrl = formDATAfromLIST.getString("FORM_URL");
		wv_bmf_form.loadUrl(formLoadUrl);
	}

	public void javascriptCallFinished(String val) {

	}

	public class myWebClient extends WebViewClient {

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {

			super.onPageStarted(view, url, favicon);

			if (mDialog != null && mDialog.isShowing()) {

				mDialog.dismiss();

			}
			/************************************************************/
			mDialog = new Dialog(FormActivity.this);
			mDialog.setCancelable(false);
			mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			mDialog.getWindow().setBackgroundDrawableResource(
					android.R.color.transparent);
			mDialog.setContentView(R.layout.custom_progress_dialog_small);

			if (!mDialog.isShowing())
				mDialog.show();

		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {

			if (url.contains("http://www.google.co.in/")) {

				callJavaScriptFunctionAndGetResultBack(333, 444);
				callJavaScriptFunctionAndGetResultBackGeo();
				Toast.makeText(context, "Image Upload clicked",
						Toast.LENGTH_SHORT).show();
				startActivity(new Intent(context, TakePictureActivity.class));

			} else if (url.contains("http://www.google.com/")) {

				callJavaScriptFunctionAndGetResultBack(333, 444);
				callJavaScriptFunctionAndGetResultBackGeo();

				String[] urlArray = url.split("\\?");
				String value = urlArray[1];

				String[] valueArray = value.split("\\=");

				Toast.makeText(context, "Bar code scan clicked!!",
						Toast.LENGTH_SHORT).show();

				barcode_id = valueArray[1];

				/*
				 * Intent intent = new
				 * Intent("com.google.zxing.client.android.SCAN");
				 * intent.putExtra("SCAN_MODE", "SCAN_MODE");
				 * startActivityForResult(intent, SCANNER_REQUEST_CODE);
				 */
				Intent intent = new Intent(
						"com.google.zxing.client.android.SCAN");
				intent.putExtra("SCAN_MODE", "SCAN_MODE");
				startActivityForResult(intent, SCANNER_REQUEST_CODE);

			}
			return true;
		}

		@Override
		public void onPageFinished(WebView view, String url) {

			super.onPageFinished(view, url);
			view.clearCache(true);

			if (mDialog != null && mDialog.isShowing())

				mDialog.dismiss();

		}
	}

	public void callJavaScriptFunctionAndGetResultBackGeo() {

		wv_bmf_form
				.loadUrl("javascript:window.MyHandler.setResultGeo( isGeo() )");

	}

	public void callJavaScriptFunctionAndGetResultBack(int val1, int val2) {

		wv_bmf_form
				.loadUrl("javascript:window.MyHandler.setResult( fetchFormData() )");

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == SCANNER_REQUEST_CODE) {

			// Handle scan intent

			if (resultCode == Activity.RESULT_OK) {
				// Handle successful scan

				final String contents = data.getStringExtra("SCAN_RESULT");
				final String formatName = data
						.getStringExtra("SCAN_RESULT_FORMAT");
				byte[] rawBytes = data.getByteArrayExtra("SCAN_RESULT_BYTES");
				int intentOrientation = data.getIntExtra(
						"SCAN_RESULT_ORIENTATION", Integer.MIN_VALUE);
				Integer orientation = (intentOrientation == Integer.MIN_VALUE) ? null
						: intentOrientation;
				String errorCorrectionLevel = data
						.getStringExtra("SCAN_RESULT_ERROR_CORRECTION_LEVEL");

				Toast.makeText(getApplicationContext(),
						contents + "\n\n" + formatName, Toast.LENGTH_LONG)
						.show();

				wv_bmf_form.setWebViewClient(new WebViewClient() {

					@Override
					public void onPageFinished(WebView view, String url) {

						super.onPageFinished(view, url);

						wv_bmf_form.loadUrl("javascript:GiveBracodeValue('"
								+ barcode_id + "','" + formatName + ":: "
								+ contents + "')");
						wv_bmf_form.setWebViewClient(new myWebClient());

					}
				});

				// myWebView.setWebViewClient(new myWebClient());

				if (Util.isInternetAvailable(getApplicationContext())) {

					wv_bmf_form.loadUrl(formLoadUrl);

					// } else if
					// (!Util.isInternetAvailable(getApplicationContext())) {
					//
					// String data2 = loadfromdatabase(formId);
					// wv_bmf_form.loadDataWithBaseURL("file:///android_asset/",
					// data2, "text/html", "utf-8", null);
					//
					// }
					// // myWebView.loadUrl(formUrl);
				}
			}
		}
	}

	public void onSaveButtonClicked(View v) {

	}

}
