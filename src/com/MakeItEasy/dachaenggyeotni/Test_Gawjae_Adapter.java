package com.MakeItEasy.dachaenggyeotni;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Test_Gawjae_Adapter extends ArrayAdapter<mData> {
	Context context;
	static int layoutid;
	ArrayList<mData> mDataArr;
	LayoutInflater inflater;

	Test_Alarm_status_DBHelper alarm_DBHelper;
	Gawjae_DBHelper mDBHelper;
	SQLiteDatabase db;
	
	public Test_Gawjae_Adapter(Context _context, ArrayList<mData> _mDataArr) {
		super(_context, 0, _mDataArr);
		context = _context;

		this.mDataArr = _mDataArr;
		inflater = (LayoutInflater) _context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}






	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDataArr.size();
	}

	@Override
	public mData getItem(int position) {
		// TODO Auto-generated method stub
		return mDataArr.get(position);
	}

	@Override
	public long getItemId(int ID) {
		// TODO Auto-generated method stub
		return ID;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final int pos = position;
		View v = null;
		final Context context = parent.getContext();
		TextView text = null;
		TextView text2 = null;
		Button btn = null;
		CustomHolder holder = null;
		mDBHelper = new Gawjae_DBHelper(context);
		alarm_DBHelper = new Test_Alarm_status_DBHelper(context);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.test_list_item, parent, false);
		} else {
			v = convertView;
		}
		final mData data = this.getItem(position);
		if (data != null) {
			TextView textview = (TextView) convertView
					.findViewById(R.id.test_list_textView1);
			TextView textview2 = (TextView) convertView
					.findViewById(R.id.test_list_textView2);
			Button button = (Button) convertView
					.findViewById(R.id.test_list_item_btn);
			textview.setText(data.get_name());
			textview2.setText(data.get_story());
			button.setText("ªË¡¶");
			button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mDataArr.remove(pos);
					db = mDBHelper.getWritableDatabase();
					Cursor c = db.rawQuery("SELECT * FROM gawjae;", null);
					c.moveToPosition(pos);
					int id = c.getInt(c.getColumnIndex("_id"));
					db.execSQL("DELETE FROM gawjae WHERE _id = "+id+";");
					
					notifyDataSetChanged();
					db.close();
					mDBHelper.close();
					
					db = alarm_DBHelper.getWritableDatabase();
					db.rawQuery("DELETE FROM alarm_data WHERE _pos = "+pos+";", null);

					db.close();
					alarm_DBHelper.close();
				}
			});
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					final int time = pos+1;
					
					int position = pos;		
					Intent intent = new Intent(context,Test_Gawjae_item.class);
					intent.putExtra("now_time", time);
					intent.putExtra("position", position);
					context.startActivity(intent);
				}
			});
		}
		/*
		 * convertView.setOnLongClickListener(new OnLongClickListener() {
		 * 
		 * @Override public boolean onLongClick(View v) { Intent intent = new
		 * Intent(context.getApplicationContext(),Gawjae_item.class);
		 * context.startActivity(intent); return true; } });
		 */
		return convertView;
	}

	public void add(Context context, String _gawjae_name,
			String _gawjae_story) {
		// TODO Auto-generated method stub
		mData _msg = new mData(context, _gawjae_name,
				_gawjae_story);
		mDataArr.add(_msg);
	}

	public void remove(int _position) {
		// TODO Auto-generated method stub
		mDataArr.remove(_position);
	}

}

class CustomHolder {
	// TODO Auto-generated method stub
	TextView m_TextView;
	TextView m_TextView2;
	Button m_Btn;
}