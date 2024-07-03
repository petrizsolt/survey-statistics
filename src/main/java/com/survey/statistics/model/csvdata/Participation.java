package com.survey.statistics.model.csvdata;

import com.opencsv.bean.CsvBindByName;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Participation {
	
	@CsvBindByName(column = "Member Id")
    private Long memberId;
    
	@CsvBindByName(column = "Survey Id")
    private Long surveyId;
    
	@CsvBindByName(column = "Status")
    private Long statusId;
    
	@CsvBindByName(column = "Length")
    private Integer length;

}
