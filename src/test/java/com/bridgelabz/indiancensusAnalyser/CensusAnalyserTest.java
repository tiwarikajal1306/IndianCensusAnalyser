package com.bridgelabz.indiancensusAnalyser;

import com.bridgelabz.indiancensusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.indiancensusanalyser.model.IndiaCensusCSV;
import com.bridgelabz.indiancensusanalyser.model.IndiaStateCSV;
import com.bridgelabz.indiancensusanalyser.services.CensusAnalyser;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

public class CensusAnalyserTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_INDIAN_STATE_CENSUS_DATA_PATH = "./src/test/resources/IndiaStateCensusData.sh";
    private static final String WRONG_DELIMITER_STATE_CENSUS_DATA_PATH="./src/test/resources/WrongDelimiter.csv";
    private static final String WRONG_HEADER_STATE_CENSUS_DATA_PATH = "./src/test/resources/WrongDelimiter.csv";
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_INDIAN_STATE_DATA_PATH = "./src/test/resources/IndiaStateCode.sh";
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
            System.out.println(e.getMessage());
        }
    }
    //tc1.3
    @Test
    public void givenIndiaCensusData_WithWrongFileState_ShouldThrowException() {
        try {
            censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(WRONG_INDIAN_STATE_CENSUS_DATA_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
            System.out.println(e.getMessage());
        }
    }
    //tc1.4
    @Test
    public void givenIndianCensusCsvFile_WhenImproperDelimiter_ShouldThrowException() {
        try {
            censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(WRONG_DELIMITER_STATE_CENSUS_DATA_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
            System.out.println(e.getMessage());
        }
    }
    //tc1.5
    @Test
    public void givenIndianCensusCsvFile_WhenImproperHeader_ShouldThrowException()  {
        try {
            censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(WRONG_HEADER_STATE_CENSUS_DATA_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
            System.out.println(e.getMessage());
        }
    }
    //tc2.1
    @Test
    public void givenIndiaStateCodeCSVFileReturnsCorrectRecords() {
        try {
            censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaStateCode(INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(37,numOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
    //tc 2.2
    @Test
    public void givenIndiaStateData_WithWrongFilePath_ShouldThrowException() {
        try {
            censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaStateCode(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
            System.out.println(e.getMessage());
        }
    }
    //tc 2.3
    @Test
    public void givenIndiaStateData_WithWrongFileState_ShouldThrowException() {
        try {
            censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaStateCode(WRONG_INDIAN_STATE_DATA_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
            System.out.println(e.getMessage());
        }
    }
    //tc2.4
    @Test
    public void givenIndianStateCsvFile_WhenImproperDelimiter_ShouldThrowException() {
        try {
            censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaStateCode(WRONG_DELIMITER_STATE_CENSUS_DATA_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
            System.out.println(e.getMessage());
        }
    }
    //tc2.5
    @Test
    public void givenIndianStateCsvFile_WhenImproperHeader_ShouldThrowException() {
        try {
            censusAnalyser = new CensusAnalyser();
          censusAnalyser.loadIndiaStateCode(WRONG_HEADER_STATE_CENSUS_DATA_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
            System.out.println(e.getMessage());
        }
    }
    //uc3
    @Test
    public void giveIndianCensusData_WhenSortOnState_ShouldReturnSortedResult() {
        try {
            censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortCensusData = censusAnalyser.getStateWiseSortedCensusData();
            IndiaCensusCSV[] indiaCensusCSV = new Gson().fromJson(sortCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", indiaCensusCSV[0].state);
        } catch (CensusAnalyserException e) {

        }
    }
    //uc4
    @Test
    public void giveIndiaStateCode_WhenSortOnStateCode_ShouldReturnSortedResult() {
        try {
            censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaStateCode(INDIA_STATE_CODE_CSV_FILE_PATH );
            String sortStateData = censusAnalyser.getCodeWiseSortedStateCodeData();
            IndiaStateCSV[] indiaStateCSV = new Gson().fromJson(sortStateData, IndiaStateCSV[].class);
            Assert.assertEquals("AD", indiaStateCSV[0].stateCode);
        } catch (CensusAnalyserException e) {

        }
    }
    //uc5
    @Test
    public void giveIndianCensusData_WhenSortOnPopulation_ShouldReturnSortedResult() {
        try {
            censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortCensusData = censusAnalyser.getPopulationWiseSortedCensusData();
            IndiaCensusCSV[] indiaCensusCSV = new Gson().fromJson(sortCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals(199812341, indiaCensusCSV[indiaCensusCSV.length-1].population);
        } catch (CensusAnalyserException e) {

        }
    }
}