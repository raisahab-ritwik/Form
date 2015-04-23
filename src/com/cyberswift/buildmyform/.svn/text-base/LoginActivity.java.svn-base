package com.cyberswift.buildmyform;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.cyberswift.buildmyform.Constants.ConstantVO;
import com.cyberswift.buildmyform.Constants.Consts;
import com.cyberswift.buildmyform.Constants.RememberMe;
import com.cyberswift.buildmyform.Constants.User;
import com.cyberswift.buildmyform.Listeners.LoginListener;
import com.cyberswift.buildmyform.Tasks.LoginAsyncTask;

public class LoginActivity extends BaseActivity implements LoginListener,OnClickListener{
	
	private Button btn_signIn,btn_register,btn_help,btn_info;
	private EditText et_email,et_userPassword;
	private Button saveLoginbutton;
	
	private LoginAsyncTask loginAsynTask = null;
	Context context;
	RememberMe rememberMe;
	private static boolean checbox_selcection_count=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.layout_login);
		
		context=LoginActivity.this;
		
		btn_signIn=(Button) findViewById(R.id.btn_signIn);
		btn_register=(Button) findViewById(R.id.btn_register);
		btn_help=(Button) findViewById(R.id.btn_help);
		btn_info=(Button) findViewById(R.id.btn_info);
		
		et_email=(EditText) findViewById(R.id.et_email);
		et_userPassword=(EditText) findViewById(R.id.et_password);
		
		saveLoginbutton=(Button) findViewById(R.id.chckbox_remember_me);
		
		rememberMe = new RememberMe();
		rememberMe.readFile(getApplicationContext());
		
		if (!rememberMe.getEmail().equalsIgnoreCase("")) {

			et_email.setText(rememberMe.getEmail());
			et_userPassword.setText(rememberMe.getPin());
			checbox_selcection_count=true;
			saveLoginbutton.setBackgroundResource(R.drawable.chkbox_selected);
			

			if (!checbox_selcection_count) {

				et_email.setText("");
				et_userPassword.setText("");
			}
		}
		
		btn_signIn.setOnClickListener(this);
		
		saveLoginbutton.setOnClickListener(this);
		
		btn_register.setOnClickListener(this);
		
		btn_help.setOnClickListener(this);
		
		btn_info.setOnClickListener(this);
	}

	@Override
	public void logincallback(User object) {
		
		Log.i("LoginActivity", "login status:: " + object.login_status);

		if (object.login_status.equalsIgnoreCase(Consts.login_success)) {

			// On Successful Login
			ConstantVO.username = et_email.getText().toString();
			ConstantVO.password = et_userPassword.getText().toString();

			if (checbox_selcection_count) {
				
				rememberMe.saveFile(ConstantVO.username,ConstantVO.password.toString(),context);

			} else if (!checbox_selcection_count) {
				
				rememberMe.saveFile("", "", getApplicationContext());

			}
			
			Intent i = new Intent(LoginActivity.this, HomeActivity.class);
			i.putExtra("orgID", object.organizationId);
			i.putExtra("baseURL", object.baseUrl);
			i.putExtra("userID", object.userId);
			i.putExtra("email", object.email);
			startActivity(i);
			finish();
		
		} else if (object.login_status.equalsIgnoreCase(Consts.login_not_responding)) {
			
			showLoginDialog(context.getResources().getString(R.string.title_alert),context.getResources().getString(R.string.login_not_responding) , LoginActivity.this);
		
		} else if (object.login_status.equalsIgnoreCase(Consts.login_no_redirection_address)) {
			
			showLoginDialog(context.getResources().getString(R.string.title_alert),context.getResources().getString(R.string.login_no_redirection_address) , LoginActivity.this);
		}
		
		else if(object.login_status.equalsIgnoreCase(Consts.login_invaliduser)){
			
			showLoginDialog(context.getResources().getString(R.string.title_alert),context.getResources().getString(R.string.login_invaliduser) , LoginActivity.this);
			
		}
		
		else if(object.login_status.equalsIgnoreCase(Consts.login_invalid_credentials)){
			
			showLoginDialog(context.getResources().getString(R.string.title_alert),context.getResources().getString(R.string.login_invalid_credentials) , LoginActivity.this);
		
		}
		
		else if(object.login_status.equalsIgnoreCase(Consts.login_unable_to_get_network_access)){
			
			showLoginDialog(context.getResources().getString(R.string.title_alert),context.getResources().getString(R.string.login_unable_to_get_network_access) , LoginActivity.this);
		
		}
		else if(object.login_status.equalsIgnoreCase(Consts.login_invalid)){
			
			showLoginDialog(context.getResources().getString(R.string.title_alert),context.getResources().getString(R.string.login_invalid) , LoginActivity.this);
			
		}
		
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		
		case R.id.btn_signIn:

			loginAsynTask = new LoginAsyncTask(LoginActivity.this,et_email.getText().toString().trim(), et_userPassword.getText().toString().trim());
			loginAsynTask.login_listener = LoginActivity.this;
			loginAsynTask.execute();

			break;

		case R.id.chckbox_remember_me:


			if(checbox_selcection_count){
				checbox_selcection_count=false;
				saveLoginbutton.setBackgroundResource(R.drawable.chkbox_normal);
			}

			else if(!checbox_selcection_count){
				checbox_selcection_count=true;
				saveLoginbutton.setBackgroundResource(R.drawable.chkbox_selected);
			}

			break;

		case R.id.btn_register:
			
			startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
			finish();
			break;
			
		case R.id.btn_help:

			startActivity(new Intent(LoginActivity.this, HelpActivity.class));
			finish();
			break;

		case R.id.btn_info:
			
			startActivity(new Intent(LoginActivity.this, InfoActivity.class));
			finish();
			break;
			
		default:
			
			break;
		}

	}

}
