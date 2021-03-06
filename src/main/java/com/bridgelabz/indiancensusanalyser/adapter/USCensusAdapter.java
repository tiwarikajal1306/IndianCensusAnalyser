package com.bridgelabz.indiancensusanalyser.adapter;
import com.bridgelabz.indiancensusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.indiancensusanalyser.dao.CensusDAO;
import com.bridgelabz.indiancensusanalyser.model.UsCensusCSV;

import java.util.Map;

public class USCensusAdapter extends CensusAdapter {
    @Override
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        Map<String, CensusDAO> usCensusMap = super.loadCensusData(UsCensusCSV.class, csvFilePath[0]);
        return usCensusMap;
    }
}
