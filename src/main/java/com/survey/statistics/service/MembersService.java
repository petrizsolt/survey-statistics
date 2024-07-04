package com.survey.statistics.service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.survey.statistics.model.StatusNames;
import com.survey.statistics.model.csvdata.Member;
import com.survey.statistics.model.csvdata.Status;
import com.survey.statistics.repository.MembersRepository;
import com.survey.statistics.repository.ParticipationRepository;
import com.survey.statistics.repository.StatusesRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MembersService {
	
	private final MembersRepository membersRepo;
	private final StatusesRepository statusesRepo;
	private final ParticipationRepository participationRepo;
	
	public List<Member> findAllSurveysCompletedBySurveyId(Long surveyId) {
		Status status = statusesRepo.findByName(StatusNames.COMPLETED.getValue())
				.orElseThrow(() -> new NoSuchElementException("Status not found!"));
		
		Set<Long> memberIds =  
				participationRepo.findAllBySurveyIdAndStatusId(surveyId, status.getId())
				.stream().map(p -> p.getMemberId())
				.collect(Collectors.toCollection(HashSet::new));
		
		List<Member> membersFound = membersRepo.findAllMemberIdIn(memberIds);
		log.info("Members completed by surveyId found: {}", membersFound.size());
		return membersRepo.findAllMemberIdIn(memberIds);
		
	}
}
