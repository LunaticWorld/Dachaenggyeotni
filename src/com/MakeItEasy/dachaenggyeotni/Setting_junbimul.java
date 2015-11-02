package com.MakeItEasy.dachaenggyeotni;

import java.sql.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Setting_junbimul extends Activity {

	Junbimul_DBHelper mHelper;
	SQLiteDatabase db;
	Button btn, btn1, junmok;
	Intent intent;
	Cursor cursor;
	Junbimul_CursorAdapter myAdapter;

	int Year, Month, Day;

	final static String KEY_ID = "_id";
	final static String KEY_JUNBIMUL_GAWMOK = "junbimul_gawmok";
	final static String KEY_JUNBIMUL_INFO = "junbimul_info";
	// final static String KEY_GAWJAE_GAWMOK = "gawjae_gawmok";
	// final static String KEY_GAWJAE_INFO = "gawjae_info";
	final static String TABLE_NAME = "mytable";
	final static String KEY_YEAR = "junbimul_year";

	final static String querySelectAll = String.format(
			"SELECT * FROM '%s' Order by junbimul_year asc", TABLE_NAME);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_junbimul);

		mHelper = new Junbimul_DBHelper(this);
		db = mHelper.getWritableDatabase();

		cursor = db.rawQuery(querySelectAll, null);
		myAdapter = new Junbimul_CursorAdapter(this, cursor);

		btn = (Button) findViewById(R.id.Cancel);
		junmok = (Button) findViewById(R.id.junmok);

		TextView junset = (TextView) findViewById(R.id.junset);

		Intent intent = getIntent();

		final String value = intent.getStringExtra("Junset");
		final int year = intent.getIntExtra("year_set", 0);
		final int month = intent.getIntExtra("month_set", 0);
		final int day = intent.getIntExtra("day_set", 0);

		Year = year;
		Month = month;
		Day = day;

		junset.setText(value);

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		junmok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						Junbimul_moklok.class);
				startActivity(intent);
				finish();
			}
		});

	}

	public void mOnClick(View v) {
		// TODO Auto-generated method stub

		Builder dlg = new AlertDialog.Builder(Setting_junbimul.this);
		dlg.setMessage("�غ��� �߰��Ͻðڽ��ϱ�?")
				.setPositiveButton("��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

						EditText eName = (EditText) findViewById(R.id.gawmok_name01);

						EditText eAge = (EditText) findViewById(R.id.junbimul_Input);

						TextView junset = (TextView) findViewById(R.id.junset);

						String Junbimul_gawmok = eName.getText().toString();

						String Junbimul_info = eAge.getText().toString();

						String Junbimul_year = junset.getText().toString();

						if (Junbimul_gawmok.length() == 0
								&& Junbimul_info.length() == 0) {
							// ������ �� ó���� ����
							Toast.makeText(getApplicationContext(),
									"������ �Է����ּ���!", Toast.LENGTH_SHORT).show();
							return;
						}
						// ���ڿ��� ''�� ���ξ� �Ѵ�.
						String query = String
								.format("INSERT INTO %s VALUES ( null, '%s', '%s', '%s', '%s', '%s', '%s' );",
										TABLE_NAME, Junbimul_gawmok,
										Junbimul_info, Junbimul_year, Year,
										Month, Day);
						db.execSQL(query);

						// �Ʒ� �޼��带 �����ϸ� ����Ʈ�� ���ŵȴ�. ������ ������ ��
						// �޼��带 deprecate�Ѵ�. ��� �ٸ�
						// ������� �غ���.
						// cursor.requery();
						cursor = db.rawQuery(querySelectAll, null);
						myAdapter.changeCursor(cursor);

						eName.setText("");
						eAge.setText("");
						Log.i("Ȯ��", "" + Year);
						Log.i("Ȯ��", "" + Month);
						Log.i("Ȯ��", "" + Day);

						// ���� ��ư ���� �� Ű���� �Ⱥ��̰� �ϱ�
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(eAge.getWindowToken(), 0);

						Toast.makeText(Setting_junbimul.this, "�غ��� �߰��Ǿ����ϴ�.",
								Toast.LENGTH_SHORT).show();
					}
				})
				.setNegativeButton("�ƴϿ�",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

							}
						}).show();

	}

	void DB_DELETE(long ID) {
		// TODO Auto-generated method stub
		String[] id = { String.valueOf(ID) };

		db.delete("mytable", "_id=?", id);

	}

	void DB_LOAD() {
		// �ҷ���
		db = mHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery(querySelectAll, null);
		startManagingCursor(cursor);
		// ����
		myAdapter.changeCursor(cursor);
	}

}