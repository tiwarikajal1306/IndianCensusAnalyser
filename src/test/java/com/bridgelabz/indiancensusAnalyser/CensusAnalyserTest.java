package com.bridgelabz.indiancensusAnalyser;

import com.bridgelabz.indiancensusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.indiancensusanalyser.model.CensusDAO;
import com.bridgelabz.indiancensusanalyser.model.IndiaCensusCSV;
import com.bridgelabz.indiancensusanalyser.model.IndiaStateCSV;
import com.bridgelabz.indiancensusanalyser.model.UsCensusCSV;
import com.bridgelabz.indiancensusanalyser.services.CensusAnalyser;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;

import static com.bridgelabz.indiancensusanalyser.services.CensusAnalyser.Country.*;

public class CensusAnalyserTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_INDIAN_STATE_CENSUS_DATA_PATH = "./src/test/resources/IndiaStateCensusData.sh";
    private static final String WRONG_DELIMITER_STATE_CENSUS_DATA_PATH="./src/test/resources/WrongDelimiter.csv";
    private static final String WRONG_HEADER_STATE_CENSUS_DATA_PATH = "./src/test/resources/WrongDelimiter.csv";
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_INDIAN_STATE_DATA_PATH = "./src/test/resources/IndiaStateCode.sh";
    private static final String Us_CENSUS_CSV_FILE_PATH = "./src/test/resources/US_STATE_CENSUS.csv";
            CensusAnalyser censusAnalyser;
    //tc1.1
    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
          censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadCensusData(INDIA_CENSUS, INDIA_CENSUS_CSV_FILE_PATH);
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
            censusAnalyser.loadCensusData(INDIA_CENSUS, WRONG_CSV_FILE_PATH);
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
            censusAnalyser.loadCensusData(INDIA_CENSUS, WRONG_INDIAN_STATE_CENSUS_DATA_PATH);
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
            censusAnalyser.loadCensusData(INDIA_CENSUS, WRONG_DELIMITER_STATE_CENSUS_DATA_PATH);
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
            censusAnalyser.loadCensusData(INDIA_CENSUS, WRONG_HEADER_STATE_CENSUS_DATA_PATH);
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
            int numOfRecords = censusAnalyser.loadCensusData(INDIA_STATE_CODE, INDIA_STATE_CODE_CSV_FILE_PATH);
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
            censusAnalyser.loadCensusData(INDIA_STATE_CODE, WRONG_CSV_FILE_PATH);
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
            censusAnalyser.loadCensusData(INDIA_STATE_CODE, WRONG_INDIAN_STATE_DATA_PATH);
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
            censusAnalyser.loadCensusData(INDIA_STATE_CODE, WRONG_DELIMITER_STATE_CENSUS_DATA_PATH);
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
          censusAnalyser.loadCensusData(INDIA_STATE_CODE, WRONG_HEADER_STATE_CENSUS_DATA_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
            System.out.println(e.getMessage());
        }
    }
    //uc3
    @Test
    public void giveIndianCensusDatatry_WhenSortOnState_ShouldReturnSortedResult() {
        try {
            censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(INDIA_CENSUS, INDIA_CENSUS_CSV_FILE_PATH);
            String sortCensusData = censusAnalyser.stateCensusData(Comparator.comparing(CensusDAO -> CensusDAO.state));
            IndiaCensusCSV[] indiaCensusCSV = new Gson().fromJson(sortCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", indiaCensusCSV[0].state);
            Assert.assertEquals("West Bengal", indiaCensusCSV[28].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
    //uc4
    @Test
    public void giveIndiaStateCode_WhenSortOnStateCode_ShouldReturnSortedResult() {
        try {
            censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(INDIA_STATE_CODE, INDIA_STATE_CODE_CSV_FILE_PATH );
            String sortStateData = censusAnalyser.stateCensusData(Comparator.comparing(CensusDAO -> CensusDAO.stateCode));
            IndiaStateCSV[] indiaStateCSV = new Gson().fromJson(sortStateData, IndiaStateCSV[].class);
            Assert.assertEquals("AD", indiaStateCSV[0].stateCode);
            Assert.assertEquals("WB", indiaStateCSV[36].stateCode);
        } catch (CensusAnalyserException e) {

        }
    }
    //uc5
    @Test
    public void giveIndianCensusData_WhenSortOnPopulation_ShouldReturnSortedResult() {
        try {
            censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(INDIA_CENSUS, INDIA_CENSUS_CSV_FILE_PATH);
            String sortCensusData = censusAnalyser.stateCensusData(Comparator.comparing(CensusDAO -> CensusDAO.population));
            IndiaCensusCSV[] indiaCensusCSV = new Gson().fromJson(sortCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals(199812341, indiaCensusCSV[indiaCensusCSV.length-1].population);
        } catch (CensusAnalyserException e) {
        }
    }
   // uc6
    @Test
    public void giveIndianCensusData_WhenSortOnDensity_ShouldReturnSortedResult() {
        try {
            censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(INDIA_CENSUS, INDIA_CENSUS_CSV_FILE_PATH);
            String sortCensusData = censusAnalyser.stateCensusData(Comparator.comparing(CensusDAO -> CensusDAO.densityPerSqKm));
            IndiaCensusCSV[] indiaCensusCSV = new Gson().fromJson(sortCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals(1102, indiaCensusCSV[indiaCensusCSV.length-1].densityPerSqKm);
        } catch (CensusAnalyserException e) {
        }
    }
    //uc7
    @Test
    public void giveIndianCensusData_WhenSortOnArea_ShouldReturnSortedResult() {
        try {
            censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(INDIA_CENSUS, INDIA_CENSUS_CSV_FILE_PATH);
            String sortCensusData = censusAnalyser.stateCensusData(Comparator.comparing(CensusDAO -> CensusDAO.areaInSqKm));
            IndiaCensusCSV[] indiaCensusCSV = new Gson().fromJson(sortCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals(3702, indiaCensusCSV[0].areaInSqKm);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
    //check the records of usCsv file
    @Test
    public void givenUsCensusCSVFile_ReturnsCorrectRecords() {
        try {
            censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadCensusData(US_CENSUS, Us_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(45, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }
    @Test
    public void giveUsCensusData_WhenSortOnArea_ShouldReturnSortedResult() {
        try {
            censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(US_CENSUS, Us_CENSUS_CSV_FILE_PATH);
            String sortCensusData = censusAnalyser.stateCensusData(Comparator.comparing(CensusDAO -> CensusDAO.totalArea));
            UsCensusCSV[] usCensusCSV = new Gson().fromJson(sortCensusData, UsCensusCSV[].class);
            Assert.assertEquals(1723338, usCensusCSV[usCensusCSV.length-1].totalArea);
        } catch (CensusAnalyserException e) {
        }
    }
    @Test
    public void givenUSCensusData_WhenSortedOnHousingUnitWise_ShouldReturnSortedResult(){
        try {
            censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(US_CENSUS, Us_CENSUS_CSV_FILE_PATH);
            String sortCensusData = censusAnalyser.stateCensusData(Comparator.comparing(CensusDAO -> CensusDAO.housingUnits));
            UsCensusCSV[] usCensusCSV = new Gson().fromJson(sortCensusData, UsCensusCSV[].class);
            Assert.assertEquals(13680081, usCensusCSV[usCensusCSV.length-1].housingUnits);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void givenUSCensusCSVFile_Return_MaximumPopulationState(){
        try {
            censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(US_CENSUS, Us_CENSUS_CSV_FILE_PATH);
            String sortCensusData = censusAnalyser.stateCensusData(Comparator.comparing(CensusDAO -> CensusDAO.usPopulation));
            UsCensusCSV[] usCensusCSV = new Gson().fromJson(sortCensusData, UsCensusCSV[].class);
            Assert.assertEquals("California", usCensusCSV[usCensusCSV.length-1].usState);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

}