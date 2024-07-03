package com.survey.statistics.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.survey.statistics.model.csvdata.Survey;
import com.survey.statistics.repository.CsvDataRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CsvDataRepositoryImpl implements CsvDataRepository {
	private final LoadCsvDataService csvLoader;
	
	@Override
	public List<Survey> getAllSurveys() {
		return new ArrayList<Survey>(csvLoader.getSurveysMap().values());
	}

}
