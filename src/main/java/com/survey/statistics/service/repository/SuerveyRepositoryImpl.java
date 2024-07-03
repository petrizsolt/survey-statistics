package com.survey.statistics.service.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.survey.statistics.model.csvdata.Survey;
import com.survey.statistics.repository.SurveyRepository;
import com.survey.statistics.service.LoadCsvDataService;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SuerveyRepositoryImpl implements SurveyRepository {
	private final LoadCsvDataService csvLoader;
	
	@Override
	public List<Survey> getAllSurveys() {
		return new ArrayList<Survey>(csvLoader.getSurveysMap().values());
	}

	@Override
	public List<Survey> findAllSurveyIdIn(List<Long> ids) {
		List<Survey> surveys = new ArrayList<>();
		
		ids.forEach(id -> surveys.add(csvLoader.getSurveysMap().get(id)));
		
		return surveys;
	}

}
