package com.bridgelabz.indiancensusanalyser.model;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCSV {
    @CsvBindByName(column = "SrNo", required = true)
    public int SrNo;

    @CsvBindByName(column = "State Name", required = true)
    public String StateName;

    @CsvBindByName(column = "TIN", required = true)
    public int TIN;

    @CsvBindByName(column = "StateCode", required = true)
    public int StateCode;

    @Override
    public String toString() {
        return "IndiaStateCSV{" +
                "SrNo='" + SrNo + '\'' +
                ", State Name='" + StateName + '\'' +
                ", TIN='" + TIN + '\'' +
                ", StateCode='" + StateCode + '\'' +
                '}';
    }
}
