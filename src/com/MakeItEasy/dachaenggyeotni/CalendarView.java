package com.MakeItEasy.dachaenggyeotni;

import java.util.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import calender.Oneday;

public class CalendarView extends CalendarViewSetting {

	private Oneday basisDay;
	private int during;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initialize();

		basisDay = new Oneday(this);

		Intent intent = getIntent();
		int[] b = intent.getIntArrayExtra("basisDay");
		during = intent.getIntExtra("during", 0);
		if (b != null) {
			basisDay.setYear(b[0]);
			basisDay.setMonth(b[1]);
			basisDay.setDay(b[2]);
		} else {
			Calendar cal = Calendar.getInstance();
			basisDay.setYear(cal.get(Calendar.YEAR));
			basisDay.setMonth(cal.get(Calendar.MONTH));
			basisDay.setDay(cal.get(Calendar.DAY_OF_MONTH));
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		// ¿À´Ã
		case R.id.menuitem_calendar_0:
			gotoToday();
			return true;
		}

		return false;
	}

}