package com.survey.statistics.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.survey.statistics.model.csvdata.Status;
import com.survey.statistics.model.csvdata.Survey;
import com.survey.statistics.repository.ParticipationRepository;
import com.survey.statistics.repository.StatusesRepository;
import com.survey.statistics.repository.SurveyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SurveyService {
	
	private final ParticipationRepository participationRepo;
	private final StatusesRepository statusesRepo;
	private final SurveyRepository surveyRepo;
	
	private static final String STATUS_COMPLETED = "Completed";
	
	public List<Survey> findAllCompletedByMember(Long memberId) {
		Status status = statusesRepo.findByName(STATUS_COMPLETED)
				.orElseThrow(() -> new NoSuchElementException("Status not found!"));
		
		List<Long> surveyIds =  
				participationRepo.findAllByMemberIdAndStatusId(memberId, status.getId())
				.stream().map(p -> p.getSurveyId()).toList();
		List<Survey> surveysComplatedByMember = surveyRepo.findAllSurveyIdIn(surveyIds);
		log.info("Surveys complated by member {} found: {}", memberId, surveysComplatedByMember.size());
		return surveysComplatedByMember;
		
	}
}
