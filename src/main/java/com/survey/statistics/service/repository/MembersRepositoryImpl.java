package com.survey.statistics.service.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.survey.statistics.model.csvdata.Member;
import com.survey.statistics.repository.MembersRepository;
import com.survey.statistics.service.LoadCsvDataService;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MembersRepositoryImpl implements MembersRepository {
	
	private final LoadCsvDataService csvService;
	
	@Override
	public List<Member> findAllMemberIdIn(Set<Long> ids) {
		List<Member> members = new ArrayList<>();
		
		ids.forEach(id -> members.add(csvService.getMembersMap().get(id)));
		
		return members;
	}

}
