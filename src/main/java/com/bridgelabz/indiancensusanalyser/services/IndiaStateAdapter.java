package com.bridgelabz.indiancensusanalyser.services;

import com.bridgelabz.indiancensusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.indiancensusanalyser.model.CensusDAO;
import com.bridgelabz.indiancensusanalyser.model.IndiaCensusCSV;
import com.bridgelabz.indiancensusanalyser.model.IndiaStateCSV;

import java.util.Map;

public class IndiaStateAdapter extends CensusAdapter{
    @Override
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        Map<String, CensusDAO> indiaCensusStateMap = super.loadCensusData(IndiaStateCSV.class, csvFilePath[0]);
        return indiaCensusStateMap;
    }
}
