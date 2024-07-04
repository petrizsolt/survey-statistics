package com.survey.statistics.service.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.survey.statistics.model.csvdata.Participation;
import com.survey.statistics.repository.ParticipationRepository;
import com.survey.statistics.service.LoadCsvDataService;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PartitipationRepositoryImpl implements ParticipationRepository {

	private final LoadCsvDataService csvService;
	
	@Override
	public List<Participation> findAllByMemberIdAndStatusId(Long memberId, Long statusId) {
		List<Participation> participations = csvService.getParticipations()
				.stream()
				.filter(p -> p.getMemberId().equals(memberId))
				.filter(p -> p.getStatusId().equals(statusId))
				.toList();
		return participations;
	}

	@Override
	public List<Participation> findAllBySurveyIdAndStatusId(Long surveyId, Long statusId) {
		List<Participation> participations = csvService.getParticipations()
				.stream()
				.filter(p -> p.getSurveyId().equals(surveyId))
				.filter(p -> p.getStatusId().equals(statusId))
				.toList();
		return participations;
	}

	@Override
	public long countMemberParticipationByStatuses(Long memberId, List<Long> statusIds) {
		return csvService.getParticipations().stream()
			.filter(p-> p.getMemberId().equals(memberId))
			.filter(p -> statusIds.contains(p.getStatusId()))
			.count();
	}

}
