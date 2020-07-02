package com.bridgelabz.indiancensusanalyser.model;

public class IndiaCensusDAO {

    public int tin;
    public String stateName;
    public String stateCode;
    public int srNo;
    public int population;
    public int densityPerSqKm;
    public int areaInSqKm;
    public String state;

    public IndiaCensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
        population = indiaCensusCSV.population;
    }
    public IndiaCensusDAO(IndiaStateCSV indiaStateCSV) {
        srNo = indiaStateCSV.srNo;
        stateCode = indiaStateCSV.stateCode;
        stateName = indiaStateCSV.stateName;
        tin = indiaStateCSV.tin;
    }
}
