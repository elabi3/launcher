package com.example.auxiliar.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.content.Context;

public class DataBaseAux {
	public static DataBaseAux instance;
	private static Context mContext;

	public static DataBaseAux getInstance(Context context) {
		DataBaseAux.mContext = context;
		return DataBaseAux.getInstance();
	}

	public static DataBaseAux getInstance() {
		if (instance == null) {
			instance = new DataBaseAux();
		}
		return instance;
	}

	public int getTime() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HHmm", Locale.ENGLISH);
		return Integer.parseInt(sdf.format(cal.getTimeInMillis()));
	}

	public int getWeekDay() {
		Calendar c = Calendar.getInstance();
		c.setTime(c.getTime());
		return c.get(Calendar.DAY_OF_WEEK);
	}

	public int getMonthDay() {
		Calendar c = Calendar.getInstance();
		c.setTime(c.getTime());
		return c.get(Calendar.DAY_OF_MONTH);
	}

	public String getLocation() {
		return "";
	}

	public int[] getInterval(int interval) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HHmm", Locale.ENGLISH);
		int result[] = new int[2];

		result[0] = Integer.parseInt(sdf.format(cal.getTimeInMillis()
				+ interval));
		result[1] = Integer.parseInt(sdf.format(cal.getTimeInMillis()
				- interval));
		return result;
	}

	public List<String> sortElementsByMostOpen(List<String> elements,
			boolean bool) {
		// Creamos un hashMap
		Map<String, Integer> mappedData = new HashMap<String, Integer>();
		for (String string : elements) {
			if (mappedData.get(string) == null) {
				mappedData.put(string, DatabaseOps.getInstance(mContext)
						.getOpeningTimes(string));
			}
		}

		// Ordenamos de mayor a menor
		List<Map.Entry> sortedList = new ArrayList<Map.Entry>(
				mappedData.entrySet());
		if (bool) {
			Collections.sort(sortedList, new Comparator() {
				public int compare(Object o1, Object o2) {
					Map.Entry e1 = (Map.Entry) o1;
					Map.Entry e2 = (Map.Entry) o2;
					return ((Comparable) e2.getValue()).compareTo(e1.getValue());
				}
			});
		} else {
			Collections.sort(sortedList, new Comparator() {
				public int compare(Object o1, Object o2) {
					Map.Entry e1 = (Map.Entry) o1;
					Map.Entry e2 = (Map.Entry) o2;
					return ((Comparable) e1.getValue()).compareTo(e2.getValue());
				}
			});
		}

		List<String> resultList = new ArrayList<String>();
		for (Map.Entry e : sortedList) {
			resultList.add(e.getKey().toString());
		}

		return resultList;
	}

	private class ValueComparator implements Comparator<String> {

		Map<String, Integer> base;

		public ValueComparator(Map<String, Integer> base) {
			this.base = base;
		}

		// Note: this comparator imposes orderings that are inconsistent with
		// equals.
		public int compare(String a, String b) {
			if (base.get(a) <= base.get(b)) {
				return -1;
			} else {
				return 1;
			}
		}
	}

	public static class ValueComparatorNext<K, V extends Comparable<V>> implements
			Comparator<K> {
		private Map<K, V> map;

		public ValueComparatorNext(Map<K, V> base) {
			this.map = base;
		}

		@Override
		public int compare(K o1, K o2) {
			return map.get(o2).compareTo(map.get(o1));
		}
	}
}
