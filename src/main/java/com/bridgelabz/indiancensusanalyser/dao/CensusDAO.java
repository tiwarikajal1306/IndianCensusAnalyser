package com.bridgelabz.indiancensusanalyser.dao;

import com.bridgelabz.indiancensusanalyser.model.IndiaCensusCSV;
import com.bridgelabz.indiancensusanalyser.model.IndiaStateCSV;
import com.bridgelabz.indiancensusanalyser.model.UsCensusCSV;
import com.bridgelabz.indiancensusanalyser.services.CensusAnalyser;

public class CensusDAO {
    public int densityPerSqKm;
    public int areaInSqKm;
    public String state;
    public int population;

    //public int srNo;
    public String stateName;
   // public int tin;
    public String stateCode;

    public double totalArea;
    public int usPopulation;
    public String usState;
    public double housingUnits;
    public double waterArea;
    public String stateId;

    public CensusDAO(IndiaCensusCSV indiaCensusCSV){
        state = indiaCensusCSV.state;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
        population = indiaCensusCSV.population;
}

    public CensusDAO(IndiaStateCSV indiaStateCSV) {
       // srNo = indiaStateCSV.srNo;
        stateName = indiaStateCSV.stateName;
        //tin = indiaStateCSV.tin;
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

    public CensusDAO(String state, int population, int areaInSqKm, int densityPerSqKm) {
        this.state = state;
        this.population = population;
        this.areaInSqKm = areaInSqKm;
        this.densityPerSqKm = densityPerSqKm;
    }


    public Object getCensusDTOS(CensusAnalyser.Country country) {
        if (country.equals(CensusAnalyser.Country.US_CENSUS))
            return new UsCensusCSV(housingUnits, stateId, totalArea, waterArea, usState);
        else if (country.equals(CensusAnalyser.Country.INDIA_CENSUS))
            return new IndiaCensusCSV(state, areaInSqKm, (int) densityPerSqKm, (int) totalArea, (int) population);
        return new IndiaStateCSV(stateName, stateCode);
    }
}