package com.cyberswift.buildmyform.Adapters;

import java.util.ArrayList;

import com.cyberswift.buildmyform.R;
import com.cyberswift.buildmyform.VO.FormStatus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FormListAdapter extends BaseAdapter {

	Context context;
	LayoutInflater inflater;
	ArrayList<FormStatus> formlistDATA;
	private ViewHolder viewHolder = null;

	
	public FormListAdapter(Context context,ArrayList<FormStatus> formlistDATA ) {
		
		this.context=context;
		this.formlistDATA=formlistDATA;
		inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return formlistDATA.size();
	}

	@Override
	public Object getItem(int position) {
		return formlistDATA.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public class ViewHolder{
		public TextView formName;
		public ViewHolder(View row) {

			formName=(TextView) row.findViewById(R.id.tv_form_list_item);
			
		}
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		
		View row = convertView;
		if (row == null) {
			row = inflater.inflate(R.layout.form_list_row_item, null);
			viewHolder = new ViewHolder(row);
			row.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) row.getTag();
		}

//		BOROUGH_VO obj = (BOROUGH_VO) roundList.get(position);
		viewHolder.formName.setText(formlistDATA.get(position).formName);

		return row;
		
	}

}
