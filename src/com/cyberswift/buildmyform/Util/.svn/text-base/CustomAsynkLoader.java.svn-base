package com.cyberswift.buildmyform.Util;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.cyberswift.buildmyform.R;

/**
 * Custom Progress Dialog
 * @author raisahab.ritwik
 * 
 */
public class CustomAsynkLoader extends Dialog{

	Dialog mDialog;
	Context context;
	
	public CustomAsynkLoader(Context context){
		super(context);
		this.context=context;

		mDialog=new Dialog(context);
		mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mDialog.getWindow ().setBackgroundDrawableResource(android.R.color.transparent);
		//RelativeLayout layout=createDialogScreen();
		mDialog.setContentView(R.layout.custom_progress_dialog_small);
		mDialog.setCancelable(false);
	}
	
	public void ShowDialog(){
			mDialog.show();
	}
	
	public void DismissDialog(){
			mDialog.dismiss();
	}
	
	public boolean isDialogShowing(){
		if(mDialog!=null&&mDialog.isShowing())
			return true;
		else return false;
	}
	
	
	
}
