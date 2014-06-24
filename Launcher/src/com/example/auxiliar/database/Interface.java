package com.example.auxiliar.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import android.content.Context;
import android.util.Log;

public class Interface {
	private static Interface instance = null;
	private Context mContext;

	public static Interface getInstance(Context context) {
		if (instance == null) {
			instance = new Interface(context);
		}
		return instance;
	}

	private Interface(Context context) {
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
			result[0] = time * 100 + hour;
		} else {
			result[0] = currentTime + interval;
		}

		if ((currentTime % 100) - interval < 0) {
			int time = (currentTime / 100) == 0 ? 23 : (currentTime / 100) - 1;
			int hour = ((currentTime % 100) - interval) + 60;
			result[1] = time * 100 + hour;
		} else {
			result[1] = currentTime - interval;
		}

		return result;
	}

	/********************************************
	 * Public Interface
	 ********************************************/

	public void newOpening(DatabaseElementOpen newElement) {
		newElement.setTime(getTime());
		newElement.setWeek_day(getWeekDay());
		newElement.setMonth_day(getMonthDay());
		newElement.setLatitude("");
		newElement.setLatitude("");

		DatabaseOps.getInstance(mContext).insertNewOpen(newElement);
	}

	public List<DatabaseElementOpen> getNextElements(String id) {
		int interval[] = getInterval(120);
		int weekDay = getWeekDay();		
		/*
		 * (BD) obtenego opening time de id for (JAVA) recorrer opening.time
		 * (BD) buscar en la siguientes N horas a opening.time (BD) ordenar
		 * elementos (JAVA) quedarnos el elemento que se acerque a la hora
		 * (JAVA) a–adir a lista resultado
		 */
		return Collections.emptyList();
	}

	public int getOpeningsTimes(String id) {
		return DatabaseOps.getInstance(mContext).getOpeningTimes(id);
	}

	public List<String> getMostOpenings() {
		// Obtenemos las apps
		List<String> result = DatabaseOps.getInstance(mContext).getAll();
		
		// Creamos un hashMap
		Map<String, Integer> mappedData = new HashMap<String, Integer>();
		for (String string : result) {
			if (mappedData.get(string) == null) {
				mappedData.put(string, 1);
			} else {
				int value = mappedData.get(string) + 1;
				mappedData.put(string, value);
			}
		}
		
		// Ordenamos de mayor a menor
		List<Map.Entry> sortedList = new ArrayList<Map.Entry>(mappedData.entrySet());
		Collections.sort(sortedList,
		         new Comparator() {
		             public int compare(Object o1, Object o2) {
		                 Map.Entry e1 = (Map.Entry) o1;
		                 Map.Entry e2 = (Map.Entry) o2;
		                 return ((Comparable) e2.getValue()).compareTo(e1.getValue());
		             }
		         });

		
		List<String> resultList = new ArrayList<String>();
		for (Map.Entry e : sortedList) {
		        resultList.add(e.getKey().toString());
		}
   
		return resultList;
	}
	
	public List<String> getLessOpenings() {
		// Obtenemos las apps
		List<String> result = DatabaseOps.getInstance(mContext).getAll();
		
		// Creamos un hashMap
		Map<String, Integer> mappedData = new HashMap<String, Integer>();
		for (String string : result) {
			if (mappedData.get(string) == null) {
				mappedData.put(string, 1);
			} else {
				int value = mappedData.get(string) + 1;
				mappedData.put(string, value);
			}
		}
		
		// Ordenamos de mayor a menor
		List<Map.Entry> sortedList = new ArrayList<Map.Entry>(mappedData.entrySet());
		Collections.sort(sortedList,
		         new Comparator() {
		             public int compare(Object o1, Object o2) {
		                 Map.Entry e1 = (Map.Entry) o1;
		                 Map.Entry e2 = (Map.Entry) o2;
		                 return ((Comparable) e1.getValue()).compareTo(e2.getValue());
		             }
		         });

		
		List<String> resultList = new ArrayList<String>();
		for (Map.Entry e : sortedList) {
		        resultList.add(e.getKey().toString());
		}
   
		return resultList;
	}
	
	class ValueComparator implements Comparator<String> {

	    Map<String, Integer> base;
	    public ValueComparator(Map<String, Integer> base) {
	        this.base = base;
	    }

	    // Note: this comparator imposes orderings that are inconsistent with equals.    
	    public int compare(String a, String b) {
	        if (base.get(a) <= base.get(b)) {
	            return -1;
	        } else {
	            return 1;
	        } // returning 0 would merge keys
	    }
	}

	public List<String> getElementsWeekDayTime() {
		int interval[] = getInterval(120);
		int weekDay = getWeekDay();

		return DatabaseOps.getInstance(mContext).getElementsWeekDayTime(
				weekDay, interval);
	}

	public List<String> getElementsTime() {
		int interval[] = getInterval(120);
		int weekDay = -1;

		return DatabaseOps.getInstance(mContext).getElementsWeekDayTime(
				weekDay, interval);
	}
	
	public List<DatabaseElementOpen> getElementsWeekDayTimeLocation() {
		int interval[] = getInterval(120);
		int weekDay = getWeekDay();
		return Collections.emptyList();
	}

}
