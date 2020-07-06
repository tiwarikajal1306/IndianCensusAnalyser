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
        //this.sortCSVData(field);
        censusList.sort(field);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }

}
