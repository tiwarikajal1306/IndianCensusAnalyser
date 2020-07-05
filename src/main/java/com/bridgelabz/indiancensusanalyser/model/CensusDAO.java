package com.bridgelabz.indiancensusanalyser.model;

public class CensusDAO {
    public int densityPerSqKm;
    public int areaInSqKm;
    public String state;
    public int population;

    public int srNo;
    public String stateName;
    public int tin;
    public String stateCode;

    public int totalArea;
    public int usPopulation;
    public String usState;
    public int housingUnits;
    public double waterArea;
    public String stateId;

    public CensusDAO(IndiaCensusCSV indiaCensusCSV){
        state = indiaCensusCSV.state;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
        population = indiaCensusCSV.population;
}

    public CensusDAO(IndiaStateCSV indiaStateCSV) {
        srNo = indiaStateCSV.srNo;
        stateName = indiaStateCSV.stateName;
        tin = indiaStateCSV.tin;
        stateCode = indiaStateCSV.stateCode;

    }

    public CensusDAO(UsCensusCSV censusCSV) {
        totalArea = censusCSV.totalArea;
        usPopulation = censusCSV.usPopulation;
        usState = censusCSV.usState;
        waterArea = censusCSV.waterArea;
        housingUnits = censusCSV.housingUnits;
        stateId = censusCSV.stateId;
    }

}