package com.survey.statistics.repository;

import java.util.List;

import com.survey.statistics.model.csvdata.Survey;

public interface CsvDataRepository {
	List<Survey> getAllSurveys();
}
