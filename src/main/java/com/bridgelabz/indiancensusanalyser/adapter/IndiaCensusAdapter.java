package com.bridgelabz.indiancensusanalyser.adapter;
import com.bridgelabz.indiancensusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.indiancensusanalyser.dao.CensusDAO;
import com.bridgelabz.indiancensusanalyser.model.IndiaCensusCSV;

import java.util.Map;

public class IndiaCensusAdapter extends CensusAdapter {

    @Override
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        Map<String, CensusDAO> indiaCensusMap = super.loadCensusData(IndiaCensusCSV.class, csvFilePath[0]);
        return indiaCensusMap;
    }
}