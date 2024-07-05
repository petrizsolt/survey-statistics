package com.survey.statistics.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.survey.statistics.model.MemberPointsResponse;
import com.survey.statistics.model.StatusNames;
import com.survey.statistics.model.SurveyStatisticsResponse;
import com.survey.statistics.model.csvdata.Participation;
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
public class StatisticsService {
	
	private final ParticipationRepository participationRepo;
	private final SurveyRepository surveysRepo;
	private final StatusesRepository statusesRepo;
	
	public List<MemberPointsResponse> findAllPointsByMember(Long memberId) {
		Status statusCompleted = statusesRepo.findByName(StatusNames.COMPLETED.getValue())
				.orElseThrow(() -> new NoSuchElementException("Status not found!"));
		Status statusFiltered = statusesRepo.findByName(StatusNames.FILTERED.getValue())
				.orElseThrow(() -> new NoSuchElementException("Status not found!"));
		
		List<MemberPointsResponse> memberPoints = new ArrayList<>();
		
		memberPoints.addAll(calculateMemberPointsByStatus(memberId, statusCompleted));
		memberPoints.addAll(calculateMemberPointsByStatus(memberId, statusFiltered));
		
		return memberPoints;
	}
	
	public List<SurveyStatisticsResponse> calculateSurveyStatistics() {
		List<Survey> surveys = surveysRepo.getAllSurveys();
		List<SurveyStatisticsResponse> stats = new ArrayList<>();
		
		surveys.forEach(s -> stats.add(statsBySurveyId(s.getId(), s.getName())));
		
		return stats;
	}
	
	private SurveyStatisticsResponse statsBySurveyId(Long surveyId, String surveyName) {
		SurveyStatisticsResponse resp = new SurveyStatisticsResponse();
		resp.setSurveyId(surveyId);
		resp.setSurveyName(surveyName);
		List<Participation> surveyParticipations = participationRepo.findAllBySurveyId(surveyId);
		
		long completed = countParticipationByStatus(surveyParticipations, StatusNames.COMPLETED);
		long filtered = countParticipationByStatus(surveyParticipations, StatusNames.FILTERED);
		long rejected = countParticipationByStatus(surveyParticipations, StatusNames.REJECTED);
		long notAsked = countParticipationByStatus(surveyParticipations, StatusNames.NOT_ASKED);
		
		resp.setCompleted(completed);
		resp.setFiltered(filtered);
		resp.setRejected(rejected);
		resp.setNotAsked(notAsked);
		
		int totalSurveyTime = surveyParticipations.stream()
				.mapToInt(p -> p.getLength() == null ? 0 : p.getLength())
				.sum();
		
		log.info("Survey ({}) total survey time: {}", surveyId, totalSurveyTime);
		
		int totalSurveyParticipation = surveyParticipations
				.stream()
				.filter(s -> s.getLength() != null).toList().size();
		
		log.info("Survey ({}) total survey participations: {}", surveyId, totalSurveyParticipation);
		
		double averageTime = Double.valueOf(totalSurveyTime) / totalSurveyParticipation;
		
		resp.setAverageSurveyTime(averageTime);
		
		return resp;
	}

	private long countParticipationByStatus(List<Participation> surveyParticipations, StatusNames statusName) {
		Status status = statusesRepo.findByName(statusName.getValue())
				.orElseThrow(() -> new NoSuchElementException("Status not found!"));
		
		return surveyParticipations.stream()
				.filter(p -> p.getStatusId().equals(status.getId()))
				.count();
	}
	
	
	private List<MemberPointsResponse> calculateMemberPointsByStatus(Long memberId, Status status) {
		List<MemberPointsResponse> memberPoints = new ArrayList<>();
		
		List<Long> surveyIds = 
				participationRepo.findAllByMemberIdAndStatusId(memberId, status.getId())
				.stream()
				.map(p -> p.getSurveyId())
				.toList();
		
		List<Survey> surveys = surveysRepo.findAllSurveyIdIn(surveyIds);
		
		for(Survey s: surveys) {
			if(StatusNames.COMPLETED.getValue().equals(status.getName())) {
				memberPoints.add(new MemberPointsResponse(s.getId(), s.getCompletionPoints()));
			} else if(StatusNames.FILTERED.getValue().equals(status.getName())) {
				memberPoints.add(new MemberPointsResponse(s.getId(), s.getFilteredPoints()));
			}
		}
		
		return memberPoints;
	}
}
