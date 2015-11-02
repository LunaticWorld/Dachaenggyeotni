package com.MakeItEasy.dachaenggyeotni;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

public class dialog2_2 extends Activity {

	Button next_btn, prv_btn;
	ImageView img1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.dialoglayout2_2);

		
		img1 = (ImageView) findViewById(R.id.img1);
		img1.setScaleType(ImageView.ScaleType.FIT_CENTER);
		
		next_btn = (Button) findViewById(R.id.new_next_btn1);
		next_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent nextintent = new Intent(getApplicationContext(),
						dialog2_3.class);
				startActivity(nextintent);

				finish();
			}
		});
		prv_btn = (Button) findViewById(R.id.new_prv_btn1);
		prv_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent backintent = new Intent(getApplicationContext(),
						dialog2.class);
				startActivity(backintent);

				finish();
			}
		});
	}


}