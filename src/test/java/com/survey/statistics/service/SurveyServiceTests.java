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
import com.survey.statistics.model.csvdata.Participation;
import com.survey.statistics.model.csvdata.Status;
import com.survey.statistics.model.csvdata.Survey;
import com.survey.statistics.repository.ParticipationRepository;
import com.survey.statistics.repository.StatusesRepository;
import com.survey.statistics.repository.SurveyRepository;

@ExtendWith(MockitoExtension.class)
public class SurveyServiceTests {
	
	@InjectMocks
	private SurveyService underTest;
	
	@Mock
	private ParticipationRepository participationRepo;
	
	@Mock
	private StatusesRepository statusesRepo;
	
	@Mock
	private SurveyRepository surveyRepo;
	
	private static final Long MEMBER_ID = 1L;
	private static final Long STATUS_COMPLETED_ID = 4L;
	
	@Test
	void findAllCompletedByMember_ReturnsWellResponse() { 
		Status statusCompleted = new Status();
		statusCompleted.setName(StatusNames.COMPLETED.getValue());
		statusCompleted.setId(STATUS_COMPLETED_ID);
		
		List<Survey> expected = getSurveys();
		
		Mockito.when(statusesRepo.findByName(StatusNames.COMPLETED.getValue()))
			.thenReturn(Optional.of(statusCompleted));
		Mockito.when(participationRepo
				.findAllByMemberIdAndStatusId(MEMBER_ID, STATUS_COMPLETED_ID))
			.thenReturn(getParticipations());
		Mockito.when(surveyRepo.findAllSurveyIdIn(Mockito.any())).thenReturn(getSurveys());
		
		List<Survey> actual = underTest.findAllCompletedByMember(MEMBER_ID);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void findAllCompletedByMember_StatusNotFoundThrowProperlyException() {
		Long memberId = 1L;

		Mockito.when(statusesRepo.findByName(Mockito.any())).thenReturn(Optional.empty());
		
		assertThrows(NoSuchElementException.class, () -> underTest.findAllCompletedByMember(memberId));
	}
	
	List<Participation> getParticipations() {
		List<Participation> participations = new ArrayList<>();
		Participation p = new Participation();
		p.setLength(5);
		p.setMemberId(MEMBER_ID);
		p.setStatusId(STATUS_COMPLETED_ID);
		p.setSurveyId(1L);
		participations.add(p);
		
		Participation p2 = new Participation();
		p2.setLength(5);
		p2.setMemberId(MEMBER_ID);
		p2.setStatusId(3L);
		p2.setSurveyId(2L);
		participations.add(p2);
		
		return participations;
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
}
