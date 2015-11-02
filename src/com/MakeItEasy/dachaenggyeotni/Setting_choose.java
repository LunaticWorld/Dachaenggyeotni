package com.MakeItEasy.dachaenggyeotni;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Setting_choose extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_choose);

		Button junbimul_input = (Button) findViewById(R.id.junbimul_input);
		TextView txt = (TextView) findViewById(R.id.asdf);

		Intent intent = getIntent();

		final String value = intent.getStringExtra("juntext");
		final int year = intent.getIntExtra("intent_year", 0);
		final int month = intent.getIntExtra("intent_month", 0);
		final int day = intent.getIntExtra("intent_day", 0);

		junbimul_input.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Intent intent = new Intent(getApplicationContext(),
						Setting_junbimul.class);

				intent.putExtra("Junset", value);
				intent.putExtra("year_set", year);
				intent.putExtra("month_set", month);
				intent.putExtra("day_set", day);
				
				
				startActivity(intent);
			}
		});

		Button gawjae_input = (Button) findViewById(R.id.gawjae_input);

		gawjae_input.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Intent intent = new Intent(getApplicationContext(),
						Setting_gawjae.class);
				startActivity(intent);
			}
		});

	}

}
