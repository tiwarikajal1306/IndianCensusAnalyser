package com.bridgelabz.indiancensusanalyser.model;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCSV {
    @CsvBindByName(column = "SrNo", required = true)
    public int srNo;
    @CsvBindByName(column = "State Name", required = true)
    public String stateName;
    @CsvBindByName(column = "TIN", required = true)
    public int tin;
    @CsvBindByName(column = "StateCode", required = true)
    public String stateCode;

}
