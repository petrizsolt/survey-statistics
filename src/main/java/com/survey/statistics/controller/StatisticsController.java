package com.survey.statistics.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.survey.statistics.model.MemberPointsResponse;
import com.survey.statistics.model.SurveyStatisticsResponse;
import com.survey.statistics.service.StatisticsService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/statistics")
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class StatisticsController {

	private final StatisticsService statisticsService;
	
	@Operation(summary = "Task 2/c.")
	@GetMapping("/get-points-by-member/{memberId}")
	public List<MemberPointsResponse> findAllPointsByMember(@PathVariable("memberId") Long memberId) {
		return statisticsService.findAllPointsByMember(memberId);
	}
	
	@Operation(summary = "Task 2/e.")
	@GetMapping("/calculate")
	public List<SurveyStatisticsResponse> calculateSurveyStatistics() {
		return statisticsService.calculateSurveyStatistics();
	}
}
