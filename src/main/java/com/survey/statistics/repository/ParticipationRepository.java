package com.survey.statistics.repository;

import java.util.List;

import com.survey.statistics.model.csvdata.Participation;

public interface ParticipationRepository {
	List<Participation> findAllByMemberIdAndStatusId(Long memberId, Long statusId);
	List<Participation> findAllBySurveyIdAndStatusId(Long surveyId, Long statusId);
	long countMemberParticipationByStatuses(Long memberId, List<Long> statusIds);
}
