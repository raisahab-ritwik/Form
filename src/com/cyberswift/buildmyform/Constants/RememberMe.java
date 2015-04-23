package com.cyberswift.buildmyform.Constants;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;

@SuppressWarnings("unused")
public class RememberMe {
	private String mobile = "";
	private String pin = "";
	String FILENAME = "rembin2";

	public void saveFile(String mobile, String pin, Context context) {

		
		FileOutputStream outStream = null;
		try {
			// Write to SD Card

			String data = mobile + "," + pin;
			if (mobile.trim().equalsIgnoreCase(""))
				data = "";
			// File dir = new
			// File(Environment.getExternalStorageDirectory(),"momark");
			// if(!dir.exists())
			// dir.mkdir();
			// Context context=

			FileOutputStream fos = context.openFileOutput(FILENAME,
					Context.MODE_PRIVATE);
			fos.write(data.getBytes());
			fos.close();

			// File f = new File(FILENAME);
			// outStream = new FileOutputStream(f);
			// outStream.write(data.getBytes());
			// /outStream.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void readFile(Context context) {

		try {
			// Write to SD Card
			String data = getEmail() + "," + getPin();
			// File dir = new
			// File(Environment.getExternalStorageDirectory(),"momark");

			// if(!dir.exists())
			// dir.mkdir();
			// File f = new File(FILENAME);
			FileInputStream f = context.openFileInput(FILENAME);
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(f));
				String lineString = new String(br.readLine());
				// System.out.println(lineString);
				if (lineString.length() > 0) {
					setEmail(lineString.substring(0, lineString.indexOf(",")));
					setPin(lineString.substring(lineString.indexOf(",") + 1,
							lineString.length()));
				}
			} catch (IOException e) {
				// You'll need to add proper error handling here
				e.printStackTrace();
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setEmail(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the mobile
	 */
	public String getEmail() {
		return mobile;
	}

	/**
	 * @param pin
	 *            the pin to set
	 */
	public void setPin(String pin) {
		this.pin = pin;
	}

	/**
	 * @return the pin
	 */
	public String getPin() {
		return pin;
	}
}