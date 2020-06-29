package com.bridgelabz.indiancensusAnalyser;

import com.bridgelabz.indiancensusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.indiancensusanalyser.services.CensusAnalyser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_INDIAN_STATE_CENSUS_DATA_PATH = "./src/test/resources/IndiaStateCensusData.sh";
    CensusAnalyser censusAnalyser;
    //tc1.1
    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
          censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,numOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
//tc1.2
    @Test
    public void givenIndiaCensusData_WithWrongFilePath_ShouldThrowException() {
        try {
             censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }
    //tc1.3
    @Test
    public void givenIndiaCensusData_WithWrongFileState_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.wrongFileExtension(WRONG_INDIAN_STATE_CENSUS_DATA_PATH);
        } catch (CensusAnalyserException e) {
            System.out.println(e.getMessage());
            //Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }
    //tc1.4

}
