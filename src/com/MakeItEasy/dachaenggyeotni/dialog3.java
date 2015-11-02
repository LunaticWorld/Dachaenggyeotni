package com.MakeItEasy.dachaenggyeotni;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;

public class dialog3 extends Activity {

	Button ok_btn, prv_btn2;
	CheckBox check_btn;
	Variable isCheck;
	ImageView img1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialoglayout3);

		ok_btn = (Button) findViewById(R.id.ok_btn1);
		check_btn = (CheckBox) findViewById(R.id.end_check);

		isCheck = (Variable) getApplicationContext();
		SharedPreferences pref = getSharedPreferences("checkbox",
				Activity.MODE_PRIVATE);
		check_btn.setChecked(pref.getBoolean("check_btn", false));

		img1 = (ImageView) findViewById(R.id.img1);
		img1.setScaleType(ImageView.ScaleType.FIT_CENTER);

		ok_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				finish();
			}
		});
		prv_btn2 = (Button) findViewById(R.id.prv_btn2);
		prv_btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent prvintent = new Intent(getApplicationContext(),
						dialog2_4.class);
				startActivity(prvintent);

				finish();
			}
		});
		check_btn.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					isCheck.isChecked = true;
				} else {
					isCheck.isChecked = false;
				}
			}
		});
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		SharedPreferences pref = getSharedPreferences("checkbox",
				Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putBoolean("check_btn", check_btn.isChecked());
		editor.commit();
		super.onStop();
	}

}