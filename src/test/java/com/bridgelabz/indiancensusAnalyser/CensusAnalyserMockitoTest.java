package com.bridgelabz.indiancensusAnalyser;

import com.bridgelabz.indiancensusanalyser.dao.CensusDAO;
import com.bridgelabz.indiancensusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.indiancensusanalyser.model.IndiaCensusCSV;
import com.bridgelabz.indiancensusanalyser.services.CensusAnalyser;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CensusAnalyserMockitoTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    @Test
    public void givenIndianCensusCSVFile_mockitoTest_ReturnsCorrectRecords() throws CensusAnalyserException {
        Map<String, CensusDAO> censusMap = new HashMap<>();
        CensusAnalyser censusAnalyser =  mock(CensusAnalyser.class);
        when(censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH)).thenReturn(censusMap);
        try {
            Map numOfRecords = censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
