package com.bridgelabz.indiancensusanalyser.services;

import com.bridgelabz.indiancensusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.indiancensusanalyser.model.CensusDAO;
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

public class CensusLoader {
    Map<String, CensusDAO> censusMap = new HashMap<>();
    public static List<CensusDAO> censusList = new ArrayList<>();
    public  <E> int loadCensusData(Class<E> CSVClass, String... csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]));) {
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
            return censusMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
