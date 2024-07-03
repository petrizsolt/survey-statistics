package com.survey.statistics.service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.MultiKeyMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.opencsv.bean.CsvToBeanBuilder;
import com.survey.statistics.model.csvdata.Member;
import com.survey.statistics.model.csvdata.Participation;
import com.survey.statistics.model.csvdata.Status;
import com.survey.statistics.model.csvdata.Survey;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LoadCsvDataService {
	private Map<Long, Survey> surveysMap;
	private Map<Long, Member> membersMap;
	private Map<Long, Status> statusesMap;
	/**
	 * firstKey: membersId
	 * secoundKey: surveyId
	 * thirdKey: statusId
	 */
	private MultiKeyMap participationMap;
	
    @Value("${csv.survey.file:static/Survey.csv}")
    private String surveyFile;
	
    @Value("${csv.members.file:static/Members.csv}")
    private String membersFile;
    
    @Value("${csv.participation.file:static/Participation.csv}")
    private String participationFile;
    
    @Value("${csv.statuses.file:static/Statuses.csv}")
    private String statusesFile;

    @PostConstruct
	public void readCsv() {
		log.info("CSV data loading started.");
		
		surveysMap = new HashMap<>();
		membersMap = new HashMap<>();
		statusesMap = new HashMap<>();
		participationMap = new MultiKeyMap();
		
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		
		readSuerveys(classloader);
		readMembers(classloader);
		readPartitipations(classloader);
		readStatuses(classloader);
		
		log.info("CSV data loading ended.");
	}

	private void readSuerveys(ClassLoader classloader) {
		List<Survey> surveys = loadCsvData(surveyFile, classloader, Survey.class);
		
		surveys.forEach(s -> surveysMap.put(s.getId(), s));
		log.info("Surveys successfully loaded. size: {}", surveys.size());
	}

	private void readMembers(ClassLoader classloader) {
		List<Member> members = loadCsvData(membersFile, classloader, Member.class);
		members.forEach(m -> membersMap.put(m.getId(), m));
		
		log.info("Members successfully loaded. size: {}", members.size());
	}
	
	private void readPartitipations(ClassLoader classloader) {
		List<Participation> partitipations = loadCsvData(participationFile, classloader, Participation.class);
		
		partitipations.forEach(p -> participationMap
				.put(p.getMemberId(), p.getSurveyId(), p.getStatusId(), p));
		
		log.info("Participations successfully loaded. size: {}", partitipations.size());
	}
	
	private void readStatuses(ClassLoader classloader) {
		List<Status> statuses = loadCsvData(statusesFile, classloader, Status.class);
		
		statuses.forEach(s -> statusesMap
				.put(s.getId(), s));

		log.info("Statuses successfully loaded. size: {}", statuses.size());
	}
	
	private <T> List<T> loadCsvData(String file, ClassLoader classloader, Class<T> type) {
		InputStream inputStream = classloader.getResourceAsStream(file);
		
		return new CsvToBeanBuilder<T>(new InputStreamReader(inputStream))
				.withType(type)
				.build().parse();
		
	}
	
	public Map<Long, Survey> getSurveysMap() {
		return surveysMap;
	}

	public Map<Long, Member> getMembersMap() {
		return membersMap;
	}

	public Map<Long, Status> getStatusesMap() {
		return statusesMap;
	}

	/**
	 * Keys: <br>
	 * 
	 * <b>firstKey:</b> membersId <br>
	 * <b>secoundKey:</b> surveyId <br>
	 * <b>thirdKey:</b> statusId <br>
	 */
	public MultiKeyMap getParticipationMap() {
		return participationMap;
	}

}
