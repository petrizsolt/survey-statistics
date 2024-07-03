package com.survey.statistics.model.csvdata;

import com.opencsv.bean.CsvBindByName;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Survey {
	
	@CsvBindByName(column = "Survey Id")
    private Long id;
    
	@CsvBindByName(column = "Name")
    private String name;
    
	@CsvBindByName(column = "Expected completes")
    private Integer expectedComplets;
    
	@CsvBindByName(column = "Completion points")
    private Integer completionPoints;
    
	@CsvBindByName(column = "Filtered points")
    private Integer filteredPoints;

}
