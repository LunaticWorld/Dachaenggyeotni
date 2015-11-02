package com.MakeItEasy.dachaenggyeotni;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

class Junbimul_CursorAdapter extends CursorAdapter {
	final static String KEY_ID = "_id";
	final static String KEY_JUNBIMUL_GAWMOK = "junbimul_gawmok";
	final static String KEY_JUNBIMUL_INFO = "junbimul_info";
	final static String KEY_YEAR = "junbimul_year";
	final static String TABLE_NAME = "mytable";

	@SuppressWarnings("deprecation")
	public Junbimul_CursorAdapter(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TextView tvName = (TextView) view.findViewById(R.id.db_text_gamok);
		TextView tvAge = (TextView) view.findViewById(R.id.db_text_junbimul);
		TextView tvYear = (TextView) view.findViewById(R.id.db_text_year);

		String Junbimul_gawmok = cursor.getString(cursor
				.getColumnIndex(KEY_JUNBIMUL_GAWMOK));

		String Junbimul_info = cursor.getString(cursor
				.getColumnIndex(KEY_JUNBIMUL_INFO));

		String Junbimul_year = cursor
				.getString(cursor.getColumnIndex(KEY_YEAR));

		Log.d("스트링 확인", Junbimul_gawmok + ", " + Junbimul_info + ","
				+ Junbimul_year);

		tvName.setText(Junbimul_gawmok);
		tvAge.setText(Junbimul_info);
		tvYear.setText(Junbimul_year);

	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.junbimul_item, parent, false);

		return v;
	}

}