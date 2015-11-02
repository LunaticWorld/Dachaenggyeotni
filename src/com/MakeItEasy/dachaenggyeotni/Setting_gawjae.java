package com.MakeItEasy.dachaenggyeotni;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Setting_gawjae extends Activity {

	Gawjae_DBHelper mHelper;
	SQLiteDatabase db;
	Test_Alarm_status_DBHelper test_Helper;
	SQLiteDatabase test_db;
	Cursor cursor;

	Test_Gawjae_Adapter mAdapter;
	ArrayList<mData> mDataArr;

	final static String KEY_ID = "_id";
	final static String KEY_JUNBIMUL_GAWMOK = "junbimul_gawmok";
	final static String KEY_JUNBIMUL_INFO = "junbimul_info";
	final static String TABLE_NAME = "mytable";
	final static String TABLE_GAWJAE = "gawjae";
	final static String KEY_GAWJAE_GAWMOK = "gawjae_gawmok";
	final static String KEY_GAWJAE_INFO = "gawjae_info";

	final static String TEST_TABLE_GAWJAE = "test_gawjae";
	final static String TEST_KEY_GAWJAE_GAWMOK = "test_gawjae_gamok";
	final static String TEST_KEY_GAWJAE_INFO = "test_gawjae_info";

	final static String querySelectAll = String.format("SELECT * FROM %s",
			TABLE_GAWJAE);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_gawjae);

		mHelper = new Gawjae_DBHelper(getApplicationContext());
		db = mHelper.getWritableDatabase();

		/*
		 * mDataArr = new ArrayList<mData>(); mAdapter = new
		 * Test_Gawjae_Adapter(getApplicationContext(), mDataArr);
		 */
		/*
		 * test_Helper = new
		 * Test_Alarm_status_DBHelper(getApplicationContext()); db =
		 * test_Helper.getWritableDatabase();
		 */

		Button gawmoklok = (Button) findViewById(R.id.gawmoklok);

		gawmoklok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						Test_Gawjae_Activity.class);

				startActivity(intent);
				finish();
			}
		});

		Button Cancel1 = (Button) findViewById(R.id.Cancel1);

		Cancel1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}

	public void mOnClick1(View v) {

		Builder dlg = new AlertDialog.Builder(Setting_gawjae.this);
		dlg.setMessage("과제를 추가하시겠습니까?")
				.setPositiveButton("예", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

						EditText eName = (EditText) findViewById(R.id.gawmok_name02);
						EditText eAge = (EditText) findViewById(R.id.gawjae_Input);

						String Gawjae_gawmok = eName.getText().toString();

						String Gawjae_info = eAge.getText().toString();

						if (Gawjae_gawmok.length() == 0
								&& Gawjae_info.length() == 0) {
							// 공백일 때 처리할 내용
							Toast.makeText(getApplicationContext(),
									"내용을 입력해주세요!", Toast.LENGTH_SHORT).show();
							return;
						}

						// 문자열은 ''로 감싸야 한다.
						String query = String.format(
								"INSERT INTO %s VALUES ( null, '%s', '%s' );",
								TABLE_GAWJAE, Gawjae_gawmok, Gawjae_info);
						Test_Gawjae_item.pos_move = Test_Gawjae_item.pos_move + 1;
						db.execSQL(query);

						eName.setText("");
						eAge.setText("");

						// 저장 버튼 누른 후 키보드 안보이게 하기
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(eAge.getWindowToken(), 0);

						Toast.makeText(getApplicationContext(),
								"준비물이 추가되었습니다.", Toast.LENGTH_SHORT).show();
					}
				})
				.setNegativeButton("아니요",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

							}
						}).show();

		/*
		 * test_db.execSQL(
		 * "INSERT INTO alarm_data VALUES ( null ,null ,null ,null ,null");
		 */
		/*
		 * ContentValues values = new ContentValues(); values.putNull(KEY_ID);
		 * values.put(TEST_KEY_GAWJAE_GAWMOK, Gawjae_gawmok);
		 * values.put(KEY_GAWJAE_INFO, Gawjae_info);
		 * db01.insert(TEST_TABLE_GAWJAE, null, values);
		 */

		// 아래 메서드를 실행하면 리스트가 갱신된다. 하지만 구글은 이 메서드를 deprecate한다. 고로 다른 방법으로 해보자.
		// cursor.requery();
		/*
		 * cursor = db01.rawQuery( querySelectAll, null );
		 * mAdapter.changeCursor( cursor );
		 */
		/*
		 * mAdapter.add(new mData(getApplicationContext(), "삭제", Gawjae_gawmok,
		 * Gawjae_info)); mAdapter.notifyDataSetChanged();
		 */
	}

}
