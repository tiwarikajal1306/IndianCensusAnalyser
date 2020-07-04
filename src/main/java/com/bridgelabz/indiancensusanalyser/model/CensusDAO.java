package com.bridgelabz.indiancensusanalyser.model;

public class CensusDAO  {
    public int densityPerSqKm;
    public int areaInSqKm;
    public String state;
    public int population;

    public int srNo;
    public String stateName;
    public int tin;
    public String stateCode;

//    public double totalArea;
//    public int usPopulation;
//    public String usState;
//    public int housingUnits;
//    public double waterArea;
//    public String stateId;

    public CensusDAO(IndiaCensusCSV indiaCensusCSV){
        this.state = indiaCensusCSV.state;
        this.areaInSqKm = indiaCensusCSV.areaInSqKm;
        this.densityPerSqKm = indiaCensusCSV.densityPerSqKm;
        this.population = indiaCensusCSV.population;
}

    public CensusDAO(IndiaStateCSV indiaStateCSV) {
        srNo = indiaStateCSV.srNo;
        stateName = indiaStateCSV.stateName;
        tin = indiaStateCSV.tin;
        stateCode = indiaStateCSV.stateCode;

    }
//
//    public CensusDAO(UsCensusCSV censusCSV) {
//        totalArea = censusCSV.totalArea;
//        usPopulation = censusCSV.usPopulation;
//        usState = censusCSV.usState;
//        waterArea = censusCSV.waterArea;
//        housingUnits = censusCSV.housingUnits;
//        stateId = censusCSV.stateId;
//    }
//    public String state;
//    public String stateCode;
//    public int population;
//    public double populationDensity;
//    public double totalArea;
//
//    public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
//        state = indiaCensusCSV.state;
//        totalArea = indiaCensusCSV.areaInSqKm;
//        populationDensity = indiaCensusCSV.densityPerSqKm;
//        population = indiaCensusCSV.population;
//    }
//
//    public CensusDAO(UsCensusCSV usCensusCSV) {
//        state = usCensusCSV.usState;
//        stateCode = usCensusCSV.stateId;
//        population = usCensusCSV.usPopulation;
//        totalArea
//    }
}
