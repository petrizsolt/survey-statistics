package com.survey.statistics.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.survey.statistics.model.StatusNames;
import com.survey.statistics.model.SurveyStatisticsResponse;
import com.survey.statistics.model.csvdata.Participation;
import com.survey.statistics.model.csvdata.Status;
import com.survey.statistics.model.csvdata.Survey;
import com.survey.statistics.repository.ParticipationRepository;
import com.survey.statistics.repository.StatusesRepository;
import com.survey.statistics.repository.SurveyRepository;

@ExtendWith(MockitoExtension.class)
public class StatisticsServiceTests {
	
	@InjectMocks
	private StatisticsService underTest;
	
	@Mock
	private ParticipationRepository participationRepo;
	
	@Mock
	private SurveyRepository surveysRepo;
	
	@Mock
	private StatusesRepository statusesRepo;
	
	@Test
	void findAllPointsByMember_StatusNotFoundThrowProperlyException() {
		Long memberId = 1L;

		Mockito.when(statusesRepo.findByName(Mockito.any())).thenReturn(Optional.empty());
		
		assertThrows(NoSuchElementException.class, () -> underTest.findAllPointsByMember(memberId));
	}
	
	@Test
	void calculateSurveyStatistics_calculatesCorrectStats() {
		Long surveyId = 1L;
		Status statusCompleted = new Status();
		statusCompleted.setName(StatusNames.COMPLETED.getValue());
		statusCompleted.setId(4L);
		
		Status statusFiltered = new Status();
		statusFiltered.setName(StatusNames.FILTERED.getValue());
		statusFiltered.setId(3L);
		
		Status statusRejected = new Status();
		statusRejected.setName(StatusNames.REJECTED.getValue());
		statusRejected.setId(1L);
		
		Status statusNotAsked = new Status();
		statusNotAsked.setName(StatusNames.NOT_ASKED.getValue());
		statusNotAsked.setId(1L);
		
		List<SurveyStatisticsResponse> expected = new ArrayList<>();
		SurveyStatisticsResponse expectedValue = new SurveyStatisticsResponse();
		expectedValue.setAvarageSurveyTime(5D);
		expectedValue.setCompleted(1L);
		expectedValue.setFiltered(1L);
		expectedValue.setNotAsked(0L);
		expectedValue.setRejected(0L);
		expectedValue.setSurveyId(surveyId);
		expectedValue.setSurveyName("Survey1");
		expected.add(expectedValue);
		
		Mockito.when(surveysRepo.getAllSurveys()).thenReturn(getSurveys());
		Mockito.when(participationRepo.findAllBySurveyId(surveyId)).thenReturn(getParticipations());
		Mockito.when(statusesRepo.findByName(StatusNames.COMPLETED.getValue()))
			.thenReturn(Optional.of(statusCompleted));
		Mockito.when(statusesRepo.findByName(StatusNames.FILTERED.getValue()))
			.thenReturn(Optional.of(statusFiltered));
		Mockito.when(statusesRepo.findByName(StatusNames.REJECTED.getValue()))
			.thenReturn(Optional.of(statusRejected));
		Mockito.when(statusesRepo.findByName(StatusNames.NOT_ASKED.getValue()))
			.thenReturn(Optional.of(statusNotAsked));
		
		List<SurveyStatisticsResponse> actual = underTest.calculateSurveyStatistics();
		
		assertEquals(expected, actual);
	}
	
	
	List<Survey> getSurveys() {
		List<Survey> surveys = new ArrayList<>();
		Survey s = new Survey();
		s.setCompletionPoints(5);
		s.setExpectedComplets(5);
		s.setFilteredPoints(2);
		s.setId(1L);
		s.setName("Survey1");
		surveys.add(s);
		return surveys;
	}
	
	List<Participation> getParticipations() {
		List<Participation> participations = new ArrayList<>();
		Participation p = new Participation();
		p.setLength(5);
		p.setMemberId(1L);
		p.setStatusId(4L);
		p.setSurveyId(1L);
		participations.add(p);
		
		Participation p2 = new Participation();
		p2.setLength(5);
		p2.setMemberId(2L);
		p2.setStatusId(3L);
		p2.setSurveyId(1L);
		participations.add(p2);
		
		return participations;
	}
}
