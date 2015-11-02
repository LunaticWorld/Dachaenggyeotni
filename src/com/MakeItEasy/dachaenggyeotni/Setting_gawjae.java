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
		dlg.setMessage("������ �߰��Ͻðڽ��ϱ�?")
				.setPositiveButton("��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

						EditText eName = (EditText) findViewById(R.id.gawmok_name02);
						EditText eAge = (EditText) findViewById(R.id.gawjae_Input);

						String Gawjae_gawmok = eName.getText().toString();

						String Gawjae_info = eAge.getText().toString();

						if (Gawjae_gawmok.length() == 0
								&& Gawjae_info.length() == 0) {
							// ������ �� ó���� ����
							Toast.makeText(getApplicationContext(),
									"������ �Է����ּ���!", Toast.LENGTH_SHORT).show();
							return;
						}

						// ���ڿ��� ''�� ���ξ� �Ѵ�.
						String query = String.format(
								"INSERT INTO %s VALUES ( null, '%s', '%s' );",
								TABLE_GAWJAE, Gawjae_gawmok, Gawjae_info);
						Test_Gawjae_item.pos_move = Test_Gawjae_item.pos_move + 1;
						db.execSQL(query);

						eName.setText("");
						eAge.setText("");

						// ���� ��ư ���� �� Ű���� �Ⱥ��̰� �ϱ�
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(eAge.getWindowToken(), 0);

						Toast.makeText(getApplicationContext(),
								"�غ��� �߰��Ǿ����ϴ�.", Toast.LENGTH_SHORT).show();
					}
				})
				.setNegativeButton("�ƴϿ�",
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

		// �Ʒ� �޼��带 �����ϸ� ����Ʈ�� ���ŵȴ�. ������ ������ �� �޼��带 deprecate�Ѵ�. ��� �ٸ� ������� �غ���.
		// cursor.requery();
		/*
		 * cursor = db01.rawQuery( querySelectAll, null );
		 * mAdapter.changeCursor( cursor );
		 */
		/*
		 * mAdapter.add(new mData(getApplicationContext(), "����", Gawjae_gawmok,
		 * Gawjae_info)); mAdapter.notifyDataSetChanged();
		 */
	}

}
