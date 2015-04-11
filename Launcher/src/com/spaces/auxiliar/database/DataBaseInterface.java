package com.spaces.auxiliar.database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import android.content.Context;

public class DataBaseInterface {
	private static DataBaseInterface instance = null;
	private static Context mContext;

	public static DataBaseInterface getInstance(Context context) {
		if (instance == null) {
			instance = new DataBaseInterface();
		}
		DataBaseInterface.mContext = context;
		return instance;
	}

	public static DataBaseInterface getInstance() {
		if (instance == null) {
			instance = new DataBaseInterface();
		}
		return instance;
	}

	public void newOpening(DatabaseElementOpen newElement) {
		DatabaseOps.getInstance(mContext).insertNewOpen(newElement);
	}

	public List<String> getNextElements(String id) {
		List<String> strings = DatabaseOps.getInstance(mContext).getNext(id,
				DataBaseAux.getInstance().getTime());
		strings.removeAll(Collections.singleton(id));

		// Count occurences
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (String s : strings) {
			if (map.containsKey(s)) {
				map.put(s, map.get(s) + 1);
			} else {
				map.put(s, 1);
			}
		}

		DataBaseAux.ValueComparatorNext<String, Integer> comparator = new DataBaseAux.ValueComparatorNext<String, Integer>(
				map);
		Map<String, Integer> sortedMap = new TreeMap<String, Integer>(
				comparator);
		sortedMap.putAll(map);

		// Si hay muchas de una sola apertura no nos las devuelve
		return new ArrayList<String>(sortedMap.keySet());
	}

	public int getOpeningsTimes(String id) {
		return DatabaseOps.getInstance(mContext).getOpeningTimes(id);
	}

	public List<String> getMostOpenings() {
		// Obtenemos las apps
		List<String> result = DatabaseOps.getInstance(mContext).getAll();
		return DataBaseAux.getInstance(mContext).sortElementsByMostOpen(result,
				true);
	}

	public List<String> getRecomended(int number) {
		// 1hora = 3600000 milisec
		int interval[] = DataBaseAux.getInstance().getInterval(3600000);
		int weekDay = DataBaseAux.getInstance().getWeekDay();

		List<String> result = DatabaseOps.getInstance(mContext).getRecomended(
				weekDay, interval);

		if (result.size() < number) {
			result = DatabaseOps.getInstance(mContext).getRecomended(-1,
					DataBaseAux.getInstance().getInterval(3600000*2));
		}
		return DataBaseAux.getInstance().sortElementsByMostOpen(result, true);
	}
}
