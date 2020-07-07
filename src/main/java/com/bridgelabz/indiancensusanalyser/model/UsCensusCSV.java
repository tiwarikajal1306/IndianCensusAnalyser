package com.bridgelabz.indiancensusanalyser.model;

import com.opencsv.bean.CsvBindByName;

public class UsCensusCSV {
    @CsvBindByName(column = "State Id", required = true)
    public String stateId;

    @CsvBindByName(column = "State", required = true)
    public String usState;

    @CsvBindByName(column = "Population", required = true)
    public int usPopulation;

    @CsvBindByName(column = "Housing units", required = true)
    public double housingUnits;

    @CsvBindByName(column = "Total area", required = true)
    public double totalArea;

    @CsvBindByName(column = "Water area", required = true)
    public double waterArea;

    public UsCensusCSV() {
    }

    public UsCensusCSV(double housingUnits, String stateId, double totalArea, double waterArea, String usState) {
        this.housingUnits = housingUnits;
        this.waterArea = waterArea;
        this.stateId = stateId;
        this.totalArea = totalArea;
        this.usState = usState;
    }
}
