package com.survey.statistics.model.csvdata;

import com.opencsv.bean.CsvBindByName;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Member {
	
	@CsvBindByName(column = "Member Id")
    private Long id;
    
	@CsvBindByName(column = "Full name")
    private String fullName;
    
	@CsvBindByName(column = "E-mail address")
    private String emailAddress;
    
	@CsvBindByName(column = "Is Active")
    private Boolean isActive;

}
