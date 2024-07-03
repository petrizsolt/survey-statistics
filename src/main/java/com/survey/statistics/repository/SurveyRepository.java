package com.survey.statistics.repository;

import java.util.List;

import com.survey.statistics.model.csvdata.Survey;

public interface SurveyRepository {
	List<Survey> getAllSurveys();
	List<Survey> findAllSurveyIdIn(List<Long> ids);
}
