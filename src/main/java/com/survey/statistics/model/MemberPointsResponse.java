package com.survey.statistics.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberPointsResponse {
	private Long surveyId;
	private Integer pointsReceived;
}
