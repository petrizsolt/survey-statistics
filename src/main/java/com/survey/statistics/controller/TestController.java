package com.survey.statistics.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.survey.statistics.model.csvdata.Survey;
import com.survey.statistics.repository.CsvDataRepository;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/test")
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class TestController {

	private final CsvDataRepository csvRepo;

	@GetMapping
	public List<Survey> findAllSurveys() {
		return csvRepo.getAllSurveys();
	}


}
