package com.survey.statistics.model;

public enum StatusNames {
	NOT_ASKED("Not asked"),
	REJECTED("Rejected"),
	FILTERED("Filtered"),
	COMPLETED("Completed");
	
	private String value;
	
	StatusNames(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
