package com.bridgelabz.indiancensusanalyser.services;

import com.bridgelabz.indiancensusanalyser.adapter.CensusAdapterFactory;
import com.bridgelabz.indiancensusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.indiancensusanalyser.dao.CensusDAO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.stream.Collectors;

import static com.bridgelabz.indiancensusanalyser.adapter.CensusAdapter.censusList;

public class CensusAnalyser {
    public enum Country {
        INDIA_CENSUS, INDIA_STATE_CODE, US_CENSUS
    }

    private Country country;

    public CensusAnalyser(Country country) {
        this.country = country;
    }

    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
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

    public String stateCensusData(String parameter, String filePath) throws CensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("empty file", CensusAnalyserException.ExceptionType.EMPTY_FILE);
        }
        ArrayList censusDTO;
       String sortedStateCensusJson;
        Comparator<CensusDAO> censusComparator = this.getComparator(parameter);
        censusDTO = censusList.stream()
                            .sorted(censusComparator)
                            .map(censusDAO -> censusDAO.getCensusDTOS(country))
                            .collect(Collectors.toCollection(ArrayList::new));
                    sortedStateCensusJson = new Gson().toJson(censusDTO);
                    write( filePath, censusDTO);
                    return sortedStateCensusJson;
    }

    public Comparator<CensusDAO> getComparator(String parameter) throws CensusAnalyserException {
        switch (parameter) {
            case "usPopulation":
                return Comparator.comparing(census -> census.usPopulation);
            case "state":
                return Comparator.comparing(census -> census.state);
            case "StateCode":
                return Comparator.comparing(census -> census.stateCode);
            case "densityPerSqKm":
                return Comparator.comparing(census -> census.densityPerSqKm);
            case "population":
                return Comparator.comparing(census -> census.population);
            case "areaInSqKm":
                 return Comparator.comparing(census -> census.areaInSqKm);
            default:
                throw new IllegalStateException("Unexpected value: " + parameter);
        }
    }

    }

