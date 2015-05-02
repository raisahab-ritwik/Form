package com.cyberswift.buildmyform.Util;

import com.cyberswift.buildmyform.FormActivity;

public class JavaScriptHandler {

	FormActivity parentActivity;

	public JavaScriptHandler(FormActivity activity) {
		parentActivity = activity;
	}

	public void setResult(String val) {

		this.parentActivity.javascriptCallFinished(val);
	}

	public void setResultGeo(String val) {

		this.parentActivity.javascriptCallFinished(val);
	}

}
