package com.survey.statistics.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.survey.statistics.model.MemberPointsResponse;
import com.survey.statistics.model.StatusNames;
import com.survey.statistics.model.csvdata.Status;
import com.survey.statistics.model.csvdata.Survey;
import com.survey.statistics.repository.ParticipationRepository;
import com.survey.statistics.repository.StatusesRepository;
import com.survey.statistics.repository.SurveyRepository;

import lombok.RequiredArgsConstructor;

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
