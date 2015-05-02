package com.cyberswift.buildmyform;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RegistrationActivity extends Activity implements OnClickListener {

	Button btn_admin_registration, btn_user_registration;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_registration);
		context = RegistrationActivity.this;
		btn_admin_registration = (Button) findViewById(R.id.btn_registeras_admin);
		btn_user_registration = (Button) findViewById(R.id.btn_registeras_user);
		btn_admin_registration.setOnClickListener(this);
		btn_user_registration.setOnClickListener(this);
	}

	@Override
	public void onBackPressed() {

		super.onBackPressed();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_registeras_admin:
			startActivity(new Intent(context, RegistrationAdminActivity.class));
			finish();
			break;

		case R.id.btn_registeras_user:
			startActivity(new Intent(context, RegistrationUserActivity.class));
			finish();
			break;

		default:
			break;
		}

	}

}
