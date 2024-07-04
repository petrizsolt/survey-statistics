package com.survey.statistics.repository;

import java.util.List;
import java.util.Set;

import com.survey.statistics.model.csvdata.Member;

public interface MembersRepository {
	
	List<Member> findAllMemberIdIn(Set<Long> ids);

}
