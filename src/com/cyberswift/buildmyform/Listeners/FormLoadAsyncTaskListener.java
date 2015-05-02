package com.cyberswift.buildmyform.Listeners;

import java.util.ArrayList;

import com.cyberswift.buildmyform.VO.FormStatus;

public interface FormLoadAsyncTaskListener {
	
	void getFormsListData(ArrayList<FormStatus> formlistDATA);

}
