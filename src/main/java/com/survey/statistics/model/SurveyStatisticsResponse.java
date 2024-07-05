package com.survey.statistics.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyStatisticsResponse {
	private Long surveyId;
	private String surveyName;
	private Long completed;
	private Long filtered;
	private Long rejected;
	private Long notAsked;
	private Double averageSurveyTime;
}
