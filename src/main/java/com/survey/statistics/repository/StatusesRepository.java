package com.survey.statistics.repository;

import java.util.Optional;

import com.survey.statistics.model.csvdata.Status;

public interface StatusesRepository {
	
	Optional<Status> findByName(String name);

}
