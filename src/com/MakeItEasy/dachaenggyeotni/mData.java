package com.MakeItEasy.dachaenggyeotni;

import android.content.Context;
import android.widget.Button;
import android.widget.ToggleButton;

public class mData {
	/*
	 * FrameLayout frame; TableLayout table1, table2, table3; RelativeLayout
	 * rLayout; LinearLayout table1_linear, table2_linear, table2_linear2,
	 * table3_linear, table3_linear2;
	 */
	Button frame_push_alarm_off, frame_push_alarm_on, timesetting_1, timesetting_2, timesetting_3, timesetting_4;
	String delete_btn;
	ToggleButton setting_save, setting_save2;
	String gawjae_name, gawjae_story;

	/*
	 * mData(Button _frame_push_alarm_off, Button _frame_push_alarm_on, Button
	 * _delete_btn, Button _timesetting_1, Button _timesetting_2, Button
	 * _timesetting_3, Button _timesetting_4, ToggleButton _setting_save,
	 * ToggleButton _setting_save2, String _gawjae_name, String _gawjae_story) {
	 * frame_push_alarm_off = _frame_push_alarm_off; frame_push_alarm_on =
	 * _frame_push_alarm_on; delete_btn = _delete_btn; timesetting_1 =
	 * _timesetting_1; timesetting_2 = _timesetting_2; timesetting_3 =
	 * _timesetting_3; timesetting_4 = _timesetting_4; setting_save =
	 * _setting_save; setting_save2 = _setting_save2; gawjae_name =
	 * _gawjae_name; gawjae_story = _gawjae_story; }
	 */
	public mData(Context context,String _delete_btn, String _gawjae_name, String _gawjae_story) {
		// TODO Auto-generated constructor stub
		this.delete_btn = _delete_btn;
		this.gawjae_name = _gawjae_name;
		this.gawjae_story = _gawjae_story;
	}

	public mData(Context context, String _gawjae_name, String _gawjae_story) {
		// TODO Auto-generated constructor stub

		this.gawjae_name = _gawjae_name;
		this.gawjae_story = _gawjae_story;
	}

	public String get_name() {
		// TODO Auto-generated method stub
		return gawjae_name;
	}

	public String get_story() {
		// TODO Auto-generated method stub
		return gawjae_story;
	}
}
