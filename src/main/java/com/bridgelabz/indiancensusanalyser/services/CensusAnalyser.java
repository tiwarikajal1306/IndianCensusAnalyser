package com.bridgelabz.indiancensusanalyser.services;

import com.bridgelabz.indiancensusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.indiancensusanalyser.model.CensusDAO;
import com.bridgelabz.indiancensusanalyser.model.IndiaCensusCSV;
import com.bridgelabz.indiancensusanalyser.model.IndiaStateCSV;
import com.bridgelabz.indiancensusanalyser.model.UsCensusCSV;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import static com.bridgelabz.indiancensusanalyser.services.CensusLoader.censusList;

public class CensusAnalyser {

    CensusLoader loadData = new CensusLoader();

    public enum Country {
        INDIA_CENSUS, INDIA_STATE_CODE, US_CENSUS
    }

    public int loadCensusData(Country country, String... csvFilePath) throws CensusAnalyserException {

        if (country.equals(Country.INDIA_CENSUS)) {
            return loadData.loadCensusData(IndiaCensusCSV.class, csvFilePath);
        } else if (country.equals(Country.INDIA_STATE_CODE)) {
            return loadData.loadCensusData(IndiaStateCSV.class, csvFilePath);
        } else if (country.equals(Country.US_CENSUS)) {
           return loadData.loadCensusData(UsCensusCSV.class, csvFilePath);
        } else {
            throw new CensusAnalyserException("Invalid Country", CensusAnalyserException.ExceptionType.INVALID_COUNTRY);
        }
    }

    //Method for sorting
    private void sortCSVData(Comparator<CensusDAO> csvComparator) {
        for (int i = 0; i < censusList.size() - 1; i++) {
            for (int j = 0; j < censusList.size() - i - 1; j++) {
                CensusDAO census1 = censusList.get(j);
                CensusDAO census2 = censusList.get(j + 1);
                if (csvComparator.compare(census1, census2) > 0) {
                    censusList.set(j, census2);
                    censusList.set(j + 1, census1);
                }
            }
        }
    }

    // method to write into json
    public void write(String fileName, List listToWrite) {
        try (Writer writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(listToWrite, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String stateCensusData(Comparator<CensusDAO> field) throws CensusAnalyserException {
        System.out.println(censusList);
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("empty file", CensusAnalyserException.ExceptionType.EMPTY_FILE);
        }
        this.sortCSVData(field);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }

}
