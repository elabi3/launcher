package com.example.auxiliar.database;

public class Element {
	private String id;
	private String type;
	private String category;
	private String description;
	
	public Element(String id, String type, String category, String description) {
		super();
		this.id = id;
		this.type = type;
		this.category = category;
		this.description = description;
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

}
