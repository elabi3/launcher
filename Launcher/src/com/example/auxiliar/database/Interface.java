package com.example.auxiliar.database;

import java.util.List;

import android.content.Context;

public class Interface {
	private static Interface instance = null;
	private static Context mContext;

	public static Interface getInstance(Context context) {
		if (instance == null) {
			instance = new Interface();
		}
		Interface.mContext = context;
		return instance;
	}

	public static Interface getInstance() {
		if (instance == null) {
			instance = new Interface();
		}
		return instance;
	}

	public void newOpening(DatabaseElementOpen newElement) {
		DatabaseOps.getInstance(mContext).insertNewOpen(newElement);
	}

	public List<String> getNextElements(String id) {
		return DatabaseOps.getInstance(mContext).getNext(id,
				DataBaseAux.getInstance().getTime());
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
					interval);
		}
		return DataBaseAux.getInstance().sortElementsByMostOpen(result, true);
	}
}
