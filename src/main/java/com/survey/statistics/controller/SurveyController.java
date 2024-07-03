package com.survey.statistics.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.survey.statistics.model.csvdata.Survey;
import com.survey.statistics.service.SurveyService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/survey")
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class SurveyController {

	private final SurveyService surveyService;
	
	@GetMapping("/completed-by-member/{memberId}")
	public List<Survey> findAllSurveysCompletedByMember(@PathVariable("memberId") Long memberId) {
		return surveyService.findAllCompletedByMember(memberId);
	}
	
}
