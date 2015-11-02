package com.MakeItEasy.dachaenggyeotni;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Test_Gawjae_Activity extends Activity {
	final static String KEY_ID = "_id";
	final static String TEST_TABLE_GAWJAE = "test_gawjae";
	final static String TEST_KEY_GAWJAE_GAWMOK = "test_gawjae_gamok";
	final static String TEST_KEY_GAWJAE_INFO = "test_gawjae_info";
	
	final static String TABLE_GAWJAE = "gawjae";
    final static String KEY_GAWJAE_GAWMOK = "gawjae_gawmok";
    final static String KEY_GAWJAE_INFO = "gawjae_info";
    final static String querySelectAll = String.format("SELECT * FROM %s",
			TABLE_GAWJAE);
	ListView list;
	Button del_btn;
	TextView text, text2;
	static ArrayList<mData> mDataArr;
	Test_Gawjae_Adapter mAdapter;


	Gawjae_DBHelper mDBHelper;
	SQLiteDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_gawjae_list);
		list = (ListView)findViewById(R.id.test_listview);
		
		
		mDataArr = new ArrayList<mData>();
		
		mAdapter = new Test_Gawjae_Adapter(getApplicationContext(), mDataArr);
		
		list.setAdapter(mAdapter);
		mDBHelper = new Gawjae_DBHelper(getApplicationContext());
		
		db = mDBHelper.getReadableDatabase();
		
		Cursor c = db.rawQuery("SELECT * FROM gawjae;", null);
		c.moveToFirst();
		while(!c.isAfterLast()){
			String gawmok = c.getString(c.getColumnIndex(KEY_GAWJAE_GAWMOK));
			String info = c.getString(c.getColumnIndex(KEY_GAWJAE_INFO));
			mAdapter.add(getApplicationContext(), gawmok, info);
			c.moveToNext();
		}
		c.close();
		mDBHelper.close();
		
	}


}
