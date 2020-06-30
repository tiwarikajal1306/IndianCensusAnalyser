package com.bridgelabz.indiancensusanalyser.model;

import com.opencsv.bean.CsvBindByPosition;

public class IndiaStateCSV {
    @CsvBindByPosition(position = 0, required = true)
    public int SrNo;

    @CsvBindByPosition(position = 1, required = true)
    public String StateName;

    @CsvBindByPosition(position = 2, required = true)
    public int TIN;

    @CsvBindByPosition(position = 3, required = true)
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
