package com.survey.statistics.service.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.survey.statistics.model.csvdata.Status;
import com.survey.statistics.repository.StatusesRepository;
import com.survey.statistics.service.LoadCsvDataService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class StatusesRepositoryImpl implements StatusesRepository {
	
	private final LoadCsvDataService csvLoader;
	
	@Override
	public Optional<Status> findByName(String name) {
		return csvLoader.getStatusesMap().values()
				.stream()
				.filter(s -> s.getName().equals(name))
				.findFirst();
	}
}
