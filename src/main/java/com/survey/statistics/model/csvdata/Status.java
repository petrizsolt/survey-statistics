package com.survey.statistics.model.csvdata;

import com.opencsv.bean.CsvBindByName;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Status {
	
	@CsvBindByName(column = "Status Id")
    private Long id;
    
	@CsvBindByName(column = "Name")
    private String name;

}
