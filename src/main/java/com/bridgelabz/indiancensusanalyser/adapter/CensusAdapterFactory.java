package com.bridgelabz.indiancensusanalyser.adapter;

import com.bridgelabz.indiancensusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.indiancensusanalyser.dao.CensusDAO;
import com.bridgelabz.indiancensusanalyser.services.CensusAnalyser;

import java.util.Map;

public class CensusAdapterFactory {
    public Map<String, CensusDAO> getCensusData(CensusAnalyser.Country country, String... csvFilePath) throws CensusAnalyserException {
        if (country.equals(CensusAnalyser.Country.INDIA_CENSUS)) {
            return new IndiaCensusAdapter().loadCensusData(csvFilePath);
        } else if (country.equals(CensusAnalyser.Country.INDIA_STATE_CODE)) {
            return new IndiaStateAdapter().loadCensusData(csvFilePath);
        } else if (country.equals(CensusAnalyser.Country.US_CENSUS)) {
            return new USCensusAdapter().loadCensusData(csvFilePath);
        } else {
            throw new CensusAnalyserException("Invalid Country", CensusAnalyserException.ExceptionType.INVALID_COUNTRY);
        }
    }
}