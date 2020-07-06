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
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.bridgelabz.indiancensusanalyser.services.CensusAdapter.censusList;

public class CensusAnalyser {
   // CensusAdapter loadData = new CensusAdapter();
    public enum Country {
        INDIA_CENSUS, INDIA_STATE_CODE, US_CENSUS
    }

    public Map<String, CensusDAO> loadCensusData(Country country, String... csvFilePath) throws CensusAnalyserException{
        return new CensusAdapterFactory().getCensusData(country, csvFilePath);
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
    //method for sort
    public String stateCensusData(Comparator<CensusDAO> field, String jsonPath) throws CensusAnalyserException {
        System.out.println(censusList);
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("empty file", CensusAnalyserException.ExceptionType.EMPTY_FILE);
        }
        censusList.sort(field);
        this.write(jsonPath, censusList);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }
}
