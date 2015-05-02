package com.cyberswift.buildmyform.Tasks;

import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.cyberswift.buildmyform.Constants.User;
import com.cyberswift.buildmyform.Listeners.FormLoadAsyncTaskListener;
import com.cyberswift.buildmyform.Util.CustomAsynkLoader;
import com.cyberswift.buildmyform.Util.Util;
import com.cyberswift.buildmyform.VO.FormStatus;

public class FormLoadAsyncTask extends AsyncTask<Void, Void, String> {

	Context context;
	CustomAsynkLoader mDialog;
	String baseURL;
	ArrayList<FormStatus> formlistDATA;
	private URL url;
	private Document doc;
	private DocumentBuilder db;
	User user;
	private int nodelength = 0;
	public String strFormName, strFormId;
	private String formUrl;
	private static final String TAG = "FormLoadAsyncTask";
	public FormLoadAsyncTaskListener listener;

	public FormLoadAsyncTask(Context context, User user) {

		this.context = context;
		this.user = user;
		mDialog = new CustomAsynkLoader(context);
		this.baseURL = baseURL;
		formlistDATA = new ArrayList<FormStatus>();

	}

	@Override
	protected void onPreExecute() {

		super.onPreExecute();

		if (!mDialog.isDialogShowing()) {

			mDialog.ShowDialog();
		}
	}

	@Override
	protected String doInBackground(Void... params) {

		if (Util.isInternetAvailable(context)) {
			try {
				if (user.baseUrl != null)

					parseXML();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.i("xml", "Exception!!XML parsernot called");

			}
		}

		return null;
	}

	@Override
	protected void onPostExecute(String result) {

		super.onPostExecute(result);

		if (mDialog.isDialogShowing()) {

			mDialog.DismissDialog();
			
		}
		if(formlistDATA.size()>0)
		listener.getFormsListData(formlistDATA);
	}

	public void parseXML() throws TransformerException {

		String urlForm = user.baseUrl + "MobileAppService.svc/GetForms?orgId="
				+ user.organizationId + "&$select=form_xml_id,form_name";
		try {

			url = new URL(urlForm);

		} catch (MalformedURLException e) {

			e.printStackTrace();
		}

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			db = dbf.newDocumentBuilder();

		} catch (ParserConfigurationException e) {

			e.printStackTrace();
		}

		// Download the XML file

		try {
			doc = db.parse(new InputSource(url.openStream()));
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			StringWriter sw = new StringWriter();
			t.transform(new DOMSource(doc), new StreamResult(sw));

		} catch (SAXException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		doc.getDocumentElement().normalize();

		// Locate the Tag Name
		if (doc != null) {

			NodeList m = doc.getElementsByTagName("entry");
			int count = m.getLength();
			nodelength = count;

			for (int i = 0; i < count; i++) {

				Element e1 = (Element) m.item(i);
				NodeList n1 = e1.getElementsByTagName("d:form_xml_id");
				if (n1.getLength() > 0) {

					Element name = (Element) n1.item(0);
					strFormId = name.getFirstChild().getNodeValue();

				}

				NodeList n5 = e1.getElementsByTagName("d:form_name");
				if (n5.getLength() > 0) {
					Element id = (Element) n5.item(0);
					strFormName = id.getFirstChild().getNodeValue();

				}
				NodeList n6 = e1.getElementsByTagName("id");
				if (n6.getLength() > 0) {
					Element id = (Element) n6.item(0);
					formUrl = id.getFirstChild().getNodeValue();

				}

				FormStatus formStatus = new FormStatus();
				formStatus.formName = strFormName;
				formStatus.id = strFormId;
				formlistDATA.add(formStatus);

			}
		}
		// if (Util.isInternetAvailable(context)) {
		// try {
		//
		// fetchformdata();
		//
		// } catch (ClientProtocolException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
	}
}
