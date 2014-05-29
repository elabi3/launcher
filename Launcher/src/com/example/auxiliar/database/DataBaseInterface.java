package com.example.auxiliar.database;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.util.Log;


public class DataBaseInterface {
	private static DataBaseInterface instance = null;
	private Context mContext;

	public static DataBaseInterface getInstance(Context context) {
		if (instance == null) {
			instance = new DataBaseInterface(context);
		}
		return instance;
	}

	private DataBaseInterface(Context context) {
		mContext = context;
	}
	
	/********************************************
	 * Private Methods
	 ********************************************/

	// Check integer 4 digits
	// Check time 24h
	private int getTime() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormatDate = new SimpleDateFormat("HHmm");
		String strDate = dateFormatDate.format(calendar.getTime());
		return Integer.parseInt(strDate);
	}

	private int getWeekDay() {
		Calendar c = Calendar.getInstance();
		c.setTime(c.getTime()); 
		return c.get(Calendar.DAY_OF_WEEK);
	}

	private int getMonthDay() {
		Calendar c = Calendar.getInstance();
		c.setTime(c.getTime()); 
		return c.get(Calendar.DAY_OF_MONTH);
	}

	private String getLocation() {
		return "";
	}

	private int[] getInterval(int interval) {
		int currentTime = getTime();
		int result[] = new int[2];
		
		if ((currentTime % 100) + interval >= 60) {
			int time = (currentTime / 100) == 23 ? 0 : (currentTime / 100) + 1;
			int hour = ((currentTime % 100) + interval) - 60;
			result[0] = time*100 + hour;
		} else {
			result[0] = currentTime + interval;
		}
		
		if ((currentTime % 100) - interval < 0) {
			int time = (currentTime / 100) == 0 ? 23 : (currentTime / 100) - 1;
			int hour = ((currentTime % 100) - interval) + 60;
			result[1] = time*100 + hour;
		} else {
			result[1] = currentTime - interval;
		}		
		
		return result;
	}
	
	/********************************************
	 * Public Interface
	 ********************************************/

	public void newOpening(Element newElement) {
		// Check id exist - YES -> add open info | NO -> add all data
		// Element data
		newElement.getId();
		newElement.getType();
		newElement.getCategory();
		newElement.getDescription();
		
		// Info open
		getTime();
		getWeekDay();
		getMonthDay();
		getLocation();
		
		// This is not here, just for check
		Log.v("", getInterval(45)[0] + " " + getInterval(45)[1]);
	}

	public List<Element> getNextElements(String id) {
		/*
		 * (BD) obtenego opening time de id 
		 * for (JAVA) recorrer opening.time
		 * (BD) buscar en la siguientes N horas a opening.time
		 * (BD) ordenar elementos 
		 * (JAVA) quedarnos el elemento que se acerque a la hora
		 * (JAVA) a–adir a lista resultado
		 */
		return Collections.emptyList();
	}

	public int getOpeningsTimes(String id) {
		return 0;
	}

	public List<Element> getMostOpenings() {
		return Collections.emptyList();
	}

	public List<Element> getElementsTime() {
		// Info open
		int interval[] = getInterval(30);
		return Collections.emptyList();
	}

	public List<Element> getElementsWeekDay() {
		int weekDay = getWeekDay();
		return Collections.emptyList();
	}

	public List<Element> getElementsMonthDay() {
		int monthDay = getMonthDay();
		return Collections.emptyList();
	}

	public List<Element> getElementsByLocation() {
		return Collections.emptyList();
	}

	public List<Element> getElementsWeekDayTime() {
		int interval[] = getInterval(30);
		int weekDay = getWeekDay();
		return Collections.emptyList();
	}

	public List<Element> getElementsWeekDayTimeLocation() {
		int interval[] = getInterval(30);
		int weekDay = getWeekDay();
		return Collections.emptyList();
	}

}
