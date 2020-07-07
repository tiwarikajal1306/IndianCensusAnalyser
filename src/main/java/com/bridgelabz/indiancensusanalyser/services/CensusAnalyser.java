package com.bridgelabz.indiancensusanalyser.services;

import com.bridgelabz.indiancensusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.indiancensusanalyser.model.CensusDAO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.bridgelabz.indiancensusanalyser.services.CensusAdapter.censusList;

public class CensusAnalyser {
    // CensusAdapter loadData = new CensusAdapter();

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

    public String stateCensusData(String type, String filePath) throws CensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("empty file", CensusAnalyserException.ExceptionType.EMPTY_FILE);
        }
            ArrayList censusDTO;
            String sortedStateCensusJson;
            Comparator<CensusDAO> censusComparator;
            switch (type) {
                case "usPopulation":
                    censusComparator = Comparator.comparing(census -> census.usPopulation);
                    censusDTO = censusList.stream()
                            .sorted(censusComparator)
                            .map(censusDAO -> censusDAO.getCensusDTOS(country))
                            .collect(Collectors.toCollection(ArrayList::new));
                    sortedStateCensusJson = new Gson().toJson(censusDTO);
                    write( filePath, censusDTO);
                    return sortedStateCensusJson;
                case "state":
                    censusComparator = Comparator.comparing(census -> census.state);
                    censusDTO = censusList.stream()
                            .sorted(censusComparator)
                            .map(censusDAO -> censusDAO.getCensusDTOS(country))
                            .collect(Collectors.toCollection(ArrayList::new));
                    sortedStateCensusJson = new Gson().toJson(censusDTO);
                    write( filePath, censusDTO);
                    return sortedStateCensusJson;
                case "StateCode":
                    censusComparator = Comparator.comparing(census -> census.stateCode);
                    censusDTO = censusList.stream()
                            .sorted(censusComparator)
                            .map(censusDAO -> censusDAO.getCensusDTOS(country))
                            .collect(Collectors.toCollection(ArrayList::new));
                    sortedStateCensusJson = new Gson().toJson(censusDTO);
                    return sortedStateCensusJson;
                case "population":
                    censusComparator = Comparator.comparing(census -> census.population);
                    censusDTO = censusList.stream()
                            .sorted(censusComparator)
                            .map(censusDAO -> censusDAO.getCensusDTOS(country))
                            .collect(Collectors.toCollection(ArrayList::new));
                    sortedStateCensusJson = new Gson().toJson(censusDTO);
                    write( filePath, censusDTO);
                    return sortedStateCensusJson;
                case "densityPerSqKm":
                    censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
                    censusDTO = censusList.stream()
                            .sorted(censusComparator)
                            .map(censusDAO -> censusDAO.getCensusDTOS(country))
                            .collect(Collectors.toCollection(ArrayList::new));
                    sortedStateCensusJson = new Gson().toJson(censusDTO);
                    return sortedStateCensusJson;
                case "areaInSqKm":
                    censusComparator = Comparator.comparing(census -> census.areaInSqKm);
                    censusDTO = censusList.stream()
                            .sorted(censusComparator)
                            .map(censusDAO -> censusDAO.getCensusDTOS(country))
                            .collect(Collectors.toCollection(ArrayList::new));
                    sortedStateCensusJson = new Gson().toJson(censusDTO);
                    return sortedStateCensusJson;
                default:
                    throw new IllegalStateException("Unexpected value: " + type);
            }
        }

    }

