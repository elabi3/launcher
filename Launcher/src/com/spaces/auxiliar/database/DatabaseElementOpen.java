package com.spaces.auxiliar.database;

public class DatabaseElementOpen {
	private String id;
	private String type;
	private String category;
	private String description;
	private int time;
	private int week_day;
	private int month_day;
	private String latitude;
	private String longitude;

	public DatabaseElementOpen(String id, String type, String category,
			String description) {
		super();
		this.id = id;
		this.type = type;
		this.category = category;
		this.description = description;

		this.time = DataBaseAux.getInstance().getTime();
		this.week_day = DataBaseAux.getInstance().getWeekDay();
		this.month_day = DataBaseAux.getInstance().getMonthDay();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getWeek_day() {
		return week_day;
	}

	public void setWeek_day(int week_day) {
		this.week_day = week_day;
	}

	public int getMonth_day() {
		return month_day;
	}

	public void setMonth_day(int month_day) {
		this.month_day = month_day;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
}
