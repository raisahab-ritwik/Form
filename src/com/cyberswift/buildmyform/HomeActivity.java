package com.cyberswift.buildmyform;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cyberswift.buildmyform.Adapters.FormListAdapter;
import com.cyberswift.buildmyform.Constants.ConstantVO;
import com.cyberswift.buildmyform.Constants.User;
import com.cyberswift.buildmyform.Listeners.FormLoadAsyncTaskListener;
import com.cyberswift.buildmyform.Tasks.FormLoadAsyncTask;
import com.cyberswift.buildmyform.VO.FormStatus;

public class HomeActivity extends BaseActivity implements
		FormLoadAsyncTaskListener, OnItemClickListener {

	private Context context;
	private TextView tv_approved_forms;
	private Animation upAnim, downAnim;
	private ListView lv_forms_list;
	private RelativeLayout optionLayout;
	private User user;
	private ArrayList<FormStatus> formlistDATA;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_home);
		context = HomeActivity.this;
		initview();

	}

	private void initview() {

		tv_approved_forms = (TextView) findViewById(R.id.tv_approved_forms);

		optionLayout = (RelativeLayout) findViewById(R.id.optionsLayout);

		upAnim = AnimationUtils.loadAnimation(HomeActivity.this,
				R.anim.slide_up_noalpha);
		downAnim = AnimationUtils.loadAnimation(HomeActivity.this,
				R.anim.slide_down_noalpha);

		lv_forms_list = (ListView) findViewById(R.id.lv_forms_list);
		lv_forms_list.setOnItemClickListener(this);

		fetchDataFromIntent();

	}

	private void fetchDataFromIntent() {

		user = (User) getIntent().getExtras().get("LOGGED_IN_USER");
		Log.i("HomeActivity", user.baseUrl);

		if (user.baseUrl != null)
			fetchFormdata();

	}

	private void fetchFormdata() {

		if (user.baseUrl != null) {

			FormLoadAsyncTask formLoadAsyncTask = new FormLoadAsyncTask(
					HomeActivity.this, user);
			formLoadAsyncTask.listener = this;
			formLoadAsyncTask.execute();
		}
	}

	@Override
	public void getFormsListData(ArrayList<FormStatus> formlistDATA) {

		if (formlistDATA.size() > 0) {

			for (int i = 0; i < formlistDATA.size(); i++) {

				Log.i("Name",
						"name:: " + formlistDATA.get(i).formName.toString());

			}
			tv_approved_forms.setText("You have " + (formlistDATA.size())
					+ " forms approved");
			FormListAdapter adapter = new FormListAdapter(context, formlistDATA);
			lv_forms_list.setAdapter(adapter);
			this.formlistDATA = formlistDATA;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Bundle formDATAfromLIST = new Bundle();
		String formId = formlistDATA.get(position).id.toString();
		String formUrl = user.baseUrl + "/account/getHtmlform.aspx?id="
				+ formId + "";
		formDATAfromLIST.putString("FORM_URL", formUrl);
		startActivity(new Intent(context, FormActivity.class).putExtra(
				"FORM_DATA", formDATAfromLIST));

	}

	public void onOptionsButtonClicked(View v) {
		if (optionLayout.getVisibility() == View.INVISIBLE) {
			optionLayout.setVisibility(View.VISIBLE);
			optionLayout.startAnimation(upAnim);
		} else if (optionLayout.getVisibility() == View.VISIBLE) {
			optionLayout.startAnimation(downAnim);
			optionLayout.setVisibility(View.INVISIBLE);
		}

	}

	public void onLogoutButtonClicked(View v) {
		optionLayout.startAnimation(downAnim);
		optionLayout.setVisibility(View.INVISIBLE);
		Log.i("USERNAME", "UserName:: " + ConstantVO.username);
		Log.i("USERNAME", "UserName:: " + ConstantVO.password);
		if (ConstantVO.username != null) {

			try {

				ConstantVO.username = null;
				ConstantVO.password = null;

				ConstantVO.saveUserDataToPrefrences(ConstantVO.USER_NAME,
						HomeActivity.this, "");
				ConstantVO.saveUserDataToPrefrences(ConstantVO.PASSWORD,
						HomeActivity.this, "");

			} catch (Exception e1) {

				e1.printStackTrace();
			}

			finish();

		} else {

			try {

				ConstantVO.username = null;
				ConstantVO.password = null;

				ConstantVO.saveUserDataToPrefrences(ConstantVO.USER_NAME,
						HomeActivity.this, "");
				ConstantVO.saveUserDataToPrefrences(ConstantVO.PASSWORD,
						HomeActivity.this, "");

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			finish();
		}

	}

	public void onRefreshButtonClicked(View v) {

		optionLayout.startAnimation(downAnim);
		optionLayout.setVisibility(View.INVISIBLE);
		fetchFormdata();
	}

	public void onChangePasswordClicked(View v) {

		optionLayout.startAnimation(downAnim);
		optionLayout.setVisibility(View.INVISIBLE);
	}

	public void onCancelbuttonClicked(View v) {

		optionLayout.startAnimation(downAnim);
		optionLayout.setVisibility(View.INVISIBLE);
	}

}
