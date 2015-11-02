package com.MakeItEasy.dachaenggyeotni;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import calender.Oneday;

/*
 Button n = (Button) findViewById(R.id.tl_calendar_monthly);

 n.setOnClickListener(new OnClickListener() {

 public void onClick(View v) {

 Intent intent = new Intent(getApplicationContext(),
 Setting_j_g.class);
 startActivity(intent);
 }
 });
 */
public class CalendarViewSetting extends Activity implements OnClickListener {

	private Calendar rightNow;
	private GregorianCalendar gCal;
	private int iYear = 0;
	private int iMonth = 0;

	private int startDayOfweek = 0;
	private int maxDay = 0;
	private int oneday_width = 0;
	private int oneday_height = 0;

	ArrayList<String> daylist; // ���� ����� ������ �ִ´�. 1,2,3,4,.... 28?30?31?
	ArrayList<String> actlist; // ���ڿ� �ش��ϴ� Ȱ�������� ������ �ִ´�.

	TextView aDateTxt,txt1;

	private int dayCnt;
	private int mSelect = -1;

	static int deviceWidth, deviceHeight;

	protected void GetDeviceScale() {

		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		deviceWidth = displayMetrics.widthPixels;
		deviceHeight = displayMetrics.heightPixels;

	}

	protected void SetViewScale() {

		aDateTxt.setTextSize(TypedValue.COMPLEX_UNIT_PX, deviceHeight * 5 / 100);
		txt1.setTextSize(TypedValue.COMPLEX_UNIT_PX, deviceWidth * 5 / 100);

	}

	protected void initialize() {
		setContentView(R.layout.calendarview);
		GetDeviceScale();

		rightNow = Calendar.getInstance();
		gCal = new GregorianCalendar();
		iYear = rightNow.get(Calendar.YEAR);
		iMonth = rightNow.get(Calendar.MONTH);

		Button btnMPrev = (Button) findViewById(R.id.btn_calendar_prevmonth);
		btnMPrev.setOnClickListener(this);
		Button btnMNext = (Button) findViewById(R.id.btn_calendar_nextmonth);
		btnMNext.setOnClickListener(this);

		btnMPrev.setText("");
		btnMNext.setText("");

		aDateTxt = (TextView) findViewById(R.id.CalendarMonthTxt);
		txt1 = (TextView) findViewById(R.id.txt1);

		makeCalendardata(iYear, iMonth);

		// ImageView img = (ImageView) findViewById(R.id.imageView103);

		// img.setScaleType(ImageView.ScaleType.FIT_XY);
		// img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		SetViewScale();
	}

	// �޷��� ���ڸ� ǥ���Ѵ�.
	private void printDate(String thisYear, String thisMonth) {

		if (thisMonth.length() == 1) {
			aDateTxt.setText(String.valueOf(thisYear) + "." + "0" + thisMonth);
		} else {
			aDateTxt.setText(String.valueOf(thisYear) + "." + thisMonth);
		}
	}

	// �޷¿� ǥ���� ���ڸ� �迭�� �־� �����Ѵ�.
	private void makeCalendardata(int thisYear, int thisMonth) {
		printDate(String.valueOf(thisYear), String.valueOf(thisMonth + 1));

		rightNow.set(thisYear, thisMonth, 1);
		gCal.set(thisYear, thisMonth, 1);
		startDayOfweek = rightNow.get(Calendar.DAY_OF_WEEK);
		maxDay = gCal.getActualMaximum((Calendar.DAY_OF_MONTH));
		if (daylist == null)
			daylist = new ArrayList<String>();
		daylist.clear();

		if (actlist == null)
			actlist = new ArrayList<String>();
		actlist.clear();

		daylist.add("");
		actlist.add("");
		daylist.add("");
		actlist.add("");
		daylist.add("");
		actlist.add("");
		daylist.add("");
		actlist.add("");
		daylist.add("");
		actlist.add("");
		daylist.add("");
		actlist.add("");
		daylist.add("");
		actlist.add("");

		if (startDayOfweek != 1) {
			gCal.set(thisYear, thisMonth - 1, 1);
			int prevMonthMaximumDay = (gCal
					.getActualMaximum((Calendar.DAY_OF_MONTH)) + 2);
			for (int i = startDayOfweek; i > 1; i--) {
				daylist.add(Integer.toString(prevMonthMaximumDay - i));
				actlist.add("p");
			}
		}

		for (int i = 1; i <= maxDay; i++) // ���ڸ� �ִ´�.
		{
			daylist.add(Integer.toString(i));
			actlist.add("");
		}

		int dayDummy = (startDayOfweek - 1) + maxDay;
		if (dayDummy > 35) {
			dayDummy = 42 - dayDummy;
		} else {
			dayDummy = 35 - dayDummy;
		}

		if (dayDummy != 0) {
			for (int i = 1; i <= dayDummy; i++) {
				daylist.add(Integer.toString(i));
				actlist.add("n");
			}
		}

		makeCalendar();
	}

	private void makeCalendar() {
		final Oneday[] oneday = new Oneday[daylist.size()];
		final Calendar today = Calendar.getInstance();
		TableLayout tl = (TableLayout) findViewById(R.id.tl_calendar_monthly);
		tl.removeAllViews();

		dayCnt = 0;
		int maxRow = ((daylist.size() > 42) ? 7 : 6);
		int maxColumn = 7;

		oneday_width = getWindow().getWindowManager().getDefaultDisplay()
				.getWidth();
		oneday_height = getWindow().getWindowManager().getDefaultDisplay()
				.getHeight();

		oneday_height = ((((oneday_height >= oneday_width) ? oneday_height
				: oneday_width) - tl.getTop()) / (maxRow + 1)) - 10;
		oneday_width = (oneday_width / maxColumn) + 1;

		int daylistsize = daylist.size() - 1;
		for (int i = 1; i <= maxRow; i++) {
			TableRow tr = new TableRow(this);
			tr.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			for (int j = 1; j <= maxColumn; j++) {
				// calender_oneday�� ������ ������ �ִ´�.
				oneday[dayCnt] = new Oneday(getApplicationContext());

				// ���Ϻ� ���� ���ϱ�
				if ((dayCnt % 7) == 0) {
					oneday[dayCnt].setTextDayColor(Color.RED);
				} else if ((dayCnt % 7) == 6) {
					oneday[dayCnt].setTextDayColor(Color.BLUE);
				} else {
					oneday[dayCnt].setTextDayColor(Color.BLACK);
				}

				// ���� ǥ���� ����
				if (dayCnt >= 0 && dayCnt < 7) {
					// oneday[dayCnt].setBgDayPaint(Color.BLACK); // ������
					// --------------------------------------
					oneday[dayCnt].setTextDayTopPadding(12); // ����ǥ�� �Ҷ� top
																// padding
					// oneday[dayCnt].setTextDayColor(Color.WHITE); // ������ �۾� ����
					// --------------------------------
					oneday[dayCnt].setTextDaySize(50); // ������ �۾�ũ��
					oneday[dayCnt].setLayoutParams(new LayoutParams(
							oneday_width, 100)); // ���� ��Ʈ�� ũ��
					oneday[dayCnt].isToday = false;

				} else {

					oneday[dayCnt].isToday = false;
					oneday[dayCnt].setDayOfWeek(dayCnt % 7 + 1);
					oneday[dayCnt].setDay(Integer.valueOf(daylist.get(dayCnt))
							.intValue());
					oneday[dayCnt].setTextActcntSize(60);
					oneday[dayCnt].setTextActcntColor(Color.rgb(0, 204, 204));
					oneday[dayCnt].setTextActcntTopPadding(80);
					oneday[dayCnt]
							.setBgSelectedDayPaint(Color.rgb(0, 162, 232));
					oneday[dayCnt].setBgTodayPaint(Color.TRANSPARENT);// --------------------------------------
					oneday[dayCnt].setBgActcntPaint(Color.rgb(251, 247, 176));
					oneday[dayCnt].setLayoutParams(new LayoutParams(
							oneday_width, oneday_height));

					// ���� �� �� ǥ��
					if (actlist.get(dayCnt).equals("p")) {
						oneday[dayCnt].setTextDaySize(0);
						actlist.set(dayCnt, "");
						oneday[dayCnt].setTextDayTopPadding(-4);

						if (iMonth - 1 < Calendar.JANUARY) {
							oneday[dayCnt].setMonth(Calendar.DECEMBER);
							oneday[dayCnt].setYear(iYear - 1);
						} else {
							oneday[dayCnt].setMonth(iMonth - 1);
							oneday[dayCnt].setYear(iYear);
						}

						// ���� �� �� ǥ��
					} else if (actlist.get(dayCnt).equals("n")) {
						oneday[dayCnt].setTextDaySize(0);
						actlist.set(dayCnt, "");
						oneday[dayCnt].setTextDayTopPadding(-4);
						if (iMonth + 1 > Calendar.DECEMBER) {
							oneday[dayCnt].setMonth(Calendar.JANUARY);
							oneday[dayCnt].setYear(iYear + 1);
						} else {
							oneday[dayCnt].setMonth(iMonth + 1);
							oneday[dayCnt].setYear(iYear);
						}
						// ���� �� ��� ǥ��
					} else {
						oneday[dayCnt].setTextDaySize(70);
						oneday[dayCnt].setYear(iYear);
						oneday[dayCnt].setMonth(iMonth);

						// ���� ǥ��
						if (oneday[dayCnt].getDay() == today
								.get(Calendar.DAY_OF_MONTH)
								&& oneday[dayCnt].getMonth() == today
										.get(Calendar.MONTH)
								&& oneday[dayCnt].getYear() == today
										.get(Calendar.YEAR)) {

							oneday[dayCnt].isToday = true;
							actlist.set(dayCnt, "����");
							oneday[dayCnt].setTextActcntSize(60);
							oneday[dayCnt].invalidate();
							mSelect = dayCnt;
						}
					}

					oneday[dayCnt].setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							TableLayout n = (TableLayout) findViewById(R.id.tl_calendar_monthly);
							Intent intent = new Intent(getApplicationContext(),
									Setting_choose.class);

							intent.putExtra(
									"juntext",
									iYear + "-" + (iMonth + 1) + "-"
											+ oneday[v.getId()].getTextDay());
							int iDay = Integer.valueOf(oneday[v.getId()].getTextDay());
							intent.putExtra("intent_year", iYear);
							intent.putExtra("intent_month", iMonth + 1);
							intent.putExtra("intent_day", iDay);
							
							Toast.makeText(
									getApplicationContext(),
									iYear + "-" + (iMonth + 1) + "-"
											+ oneday[v.getId()].getTextDay(),
									Toast.LENGTH_SHORT).show();
							startActivity(intent);
							return;
						}
					});

					oneday[dayCnt].setOnTouchListener(new OnTouchListener() {

						@Override
						public boolean onTouch(View v, MotionEvent event) {

							if (oneday[v.getId()].getTextDay() != ""
									&& event.getAction() == MotionEvent.ACTION_UP) {
								if (mSelect != -1) {
									oneday[mSelect].setSelected(false);
									oneday[mSelect].invalidate();
								}
								oneday[v.getId()].setSelected(true);
								oneday[v.getId()].invalidate();
								mSelect = v.getId();

								// Log.d("hahaha",
								// oneday[mSelect].getMonth()+"-"+
								// oneday[mSelect].getDay());

								onTouched(oneday[mSelect]);
							}
							return false;
						}
					});
				}

				oneday[dayCnt].setTextDay(daylist.get(dayCnt).toString()); // ����,����
																			// �ֱ�
				oneday[dayCnt].setTextActCnt(actlist.get(dayCnt).toString());// Ȱ������
																				// �ֱ�
				oneday[dayCnt].setId(dayCnt); // ������ ��ü�� �����Ҽ� �ִ� id�ֱ�
				oneday[dayCnt].invalidate();
				tr.addView(oneday[dayCnt]);

				if (daylistsize != dayCnt) {
					dayCnt++;
				} else {
					break;
				}
			}
			tl.addView(tr, new TableLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		}
	}

	/**
	 * ���ڸ� 2�ڸ� ���ڷ� ��ȯ, 2 -> 02
	 * 
	 * @param value
	 * @return
	 */
	protected String doubleString(int value) {
		String temp;

		if (value < 10) {
			temp = "0" + String.valueOf(value);

		} else {
			temp = String.valueOf(value);
		}
		return temp;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_calendar_nextmonth:
			if (iMonth == 11) {
				iYear = iYear + 1;
				iMonth = 0;
			} else {
				iMonth = iMonth + 1;
			}
			makeCalendardata(iYear, iMonth);
			break;
		case R.id.btn_calendar_prevmonth:
			if (iMonth == 0) {
				iYear = iYear - 1;
				iMonth = 11;
			} else {
				iMonth = iMonth - 1;
			}
			makeCalendardata(iYear, iMonth);
			break;
		}
	}

	/**
	 * ���� Ŭ�������� �������̵� �ؼ� ��ġ�� ��¥ �Է� �ޱ�
	 * 
	 * @param oneday
	 */
	protected void onTouched(Oneday oneday) {

		/**
		 * �ش� ���� ������ ���� �ȿ� �ִ��� �˻�
		 * 
		 * @param test
		 *            �˻��� ��¥
		 * @param basis
		 *            ���� ��¥
		 * @param during
		 *            �Ⱓ(��)
		 * @return
		 */

	}

	protected boolean isInside(Oneday test, Oneday basis, int during) {
		Calendar calbasis = Calendar.getInstance();
		calbasis.set(basis.getYear(), basis.getMonth(), basis.getDay());
		calbasis.add(Calendar.DAY_OF_MONTH, during);

		Calendar caltest = Calendar.getInstance();
		caltest.set(test.getYear(), test.getMonth(), test.getDay());

		if (caltest.getTimeInMillis() < calbasis.getTimeInMillis()) {
			return true;
		}
		return false;
	}

	/**
	 * ���� �޷����� �̵�
	 */
	public void gotoToday() {
		final Calendar today = Calendar.getInstance();
		iYear = today.get(Calendar.YEAR);
		iMonth = today.get(Calendar.MONTH);
		makeCalendardata(today.get(Calendar.YEAR), today.get(Calendar.MONTH));
	}

}
