package com.example.auxiliar.database;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import android.content.Context;


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

	private int getTime() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormatDate = new SimpleDateFormat("HHmm");
		final String strDate = dateFormatDate.format(calendar.getTime());
		// transformar strDate a int
		return 0;
	}

	private String getWeekDay() {

		return "";
	}

	private String getMonthDay() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormatDate = new SimpleDateFormat("dd");		
		return dateFormatDate.format(calendar.getTime());
	}

	private String getLocation() {

		return "";
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
	}

	public List<Element> getNextElements(String id) {
		/*
		 * (BD) obtenego opening time de id for (JAVA) recorrer opening.time
		 * (BD) buscar en la siguientes N horas a opening.time + (BD) ordenar
		 * elementos (JAVA) quedarnos el elemento que se acerque a la hora
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
		return Collections.emptyList();
	}

	public List<Element> getElementsWeekDay() {
		return Collections.emptyList();
	}

	public List<Element> getElementsMonthDay() {
		return Collections.emptyList();
	}

	public List<Element> getElementsByLocation() {
		return Collections.emptyList();
	}

	public List<Element> getElementsWeekDayTime() {
		return Collections.emptyList();
	}

	public List<Element> getElementsWeekDayTimeLocation() {
		return Collections.emptyList();
	}

}
