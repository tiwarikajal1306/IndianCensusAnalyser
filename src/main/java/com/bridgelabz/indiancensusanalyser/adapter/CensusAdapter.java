package com.bridgelabz.indiancensusanalyser.adapter;

import com.bridgelabz.indiancensusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.indiancensusanalyser.dao.CensusDAO;
import com.bridgelabz.indiancensusanalyser.model.IndiaCensusCSV;
import com.bridgelabz.indiancensusanalyser.model.IndiaStateCSV;
import com.bridgelabz.indiancensusanalyser.model.UsCensusCSV;
import com.opencsv.CSVBuilderException;
import com.opencsv.CSVBuilderFactory;
import com.opencsv.ICSVBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class CensusAdapter {
    public abstract Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException;
    public static List<CensusDAO> censusList = new ArrayList<>();

    public <E> Map<String, CensusDAO> loadCensusData(Class<E> CSVClass, String csvFilePath) throws CensusAnalyserException{
        Map<String, CensusDAO> censusMap = new HashMap<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> indiaCensusCodeIterator = csvBuilder.getCSVFileIterator(reader, CSVClass);
            Iterable<E> stateCensuses = () -> indiaCensusCodeIterator;
            switch (CSVClass.getSimpleName()) {
                case "IndiaCensusCSV":
                    StreamSupport.stream(stateCensuses.spliterator(), false)
                            .map(IndiaCensusCSV.class::cast)
                            .forEach(censusCSV -> censusMap.put(censusCSV.state, new CensusDAO(censusCSV)));
                    censusList = censusMap.values().stream().collect(Collectors.toList());
                    break;
                case "IndiaStateCSV":
                    StreamSupport.stream(stateCensuses.spliterator(), false)
                            .map(IndiaStateCSV.class::cast)
                            .forEach(censusCSV -> censusMap.put(censusCSV.stateCode, new CensusDAO(censusCSV)));
                    censusList = censusMap.values().stream().collect(Collectors.toList());
                    break;
                case "UsCensusCSV":
                    StreamSupport.stream(stateCensuses.spliterator(), false)
                            .map(UsCensusCSV.class::cast)
                            .forEach(censusCSV -> censusMap.put(censusCSV.usState, new CensusDAO(censusCSV)));
                    censusList = censusMap.values().stream().collect(Collectors.toList());
                    break;
            }
            return censusMap;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException("Invalid header or delimiter",
                    CensusAnalyserException.ExceptionType.INVALID_HEADER_AND_DELIMITER);
        }
        catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }
}
