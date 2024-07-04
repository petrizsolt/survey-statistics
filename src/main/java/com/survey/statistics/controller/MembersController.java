package com.survey.statistics.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.survey.statistics.model.csvdata.Member;
import com.survey.statistics.service.MembersService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/members")
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class MembersController {
	
	private final MembersService membersService;
	
	@Operation(summary = "Task 2/a.")
	@GetMapping("/completed-by-surveyId/{surveyId}")
	public List<Member> findAllSurveysCompletedBySurveyId(@PathVariable("surveyId") Long surveyId) {
		return membersService.findAllSurveysCompletedBySurveyId(surveyId);
	}
	
	@Operation(summary = "Task 2/d.")
	@GetMapping("/ready-to-survey")
	public List<Member> findAllMembersReadyToSurvey() {
		return membersService.findAllUsersActiveReadyToSurvey();
	}
}
