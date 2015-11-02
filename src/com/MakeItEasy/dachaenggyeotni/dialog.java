package com.MakeItEasy.dachaenggyeotni;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class dialog extends Activity {

	ImageView img1;

	Button next_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialoglayout);

		img1 = (ImageView) findViewById(R.id.img1);
		img1.setScaleType(ImageView.ScaleType.FIT_CENTER);

		next_btn = (Button) findViewById(R.id.next_btn1);

		next_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						dialog2.class);
				startActivity(intent);
				finish();
			}
		});
	}

}
