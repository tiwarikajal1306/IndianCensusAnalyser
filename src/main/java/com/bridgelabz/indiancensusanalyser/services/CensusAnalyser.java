package com.bridgelabz.indiancensusanalyser.services;

import com.bridgelabz.indiancensusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.indiancensusanalyser.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVBuilderException;
import com.opencsv.CSVBuilderFactory;
import com.opencsv.ICSVBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    List<CensusDAO> censusList;
    Map<String, CensusDAO> censusMap;

    public CensusAnalyser() {
        this.censusMap = new HashMap<>();
        this.censusList = new ArrayList<>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        return new CensusLoader().loadCensusData(csvFilePath, IndiaCensusCSV.class);
    }

    public int loadIndiaStateCode(String csvFilePath) throws CensusAnalyserException {
        return new CensusLoader().loadCensusData(csvFilePath, IndiaStateCSV.class);
    }

    public int loadUSCensusData(String csvFilePath) throws CensusAnalyserException {
        return new CensusLoader().loadCensusData(csvFilePath, UsCensusCSV.class);
    }


//    private <E> int loadCensusData(String csvFilePath, Class <E> CSVClass) throws CensusAnalyserException {
//        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
//            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
//            Iterator<E> indiaCensusCodeIterator = csvBuilder.getCSVFileIterator(reader, CSVClass);
//            Iterable<E> stateCensuses = () -> indiaCensusCodeIterator;
//            switch (CSVClass.getSimpleName()) {
//                case "IndiaCensusCSV":
//                StreamSupport.stream(stateCensuses.spliterator(), false)
//                        .map(IndiaCensusCSV.class::cast)
//                        .forEach(censusCSV -> censusMap.put(censusCSV.state, new CensusDAO(censusCSV)));
//                    censusList = censusMap.values().stream().collect(Collectors.toList());
//                    break;
//                case "IndiaStateCSV":
//                    StreamSupport.stream(stateCensuses.spliterator(), false)
//                            .map(IndiaStateCSV.class::cast)
//                            .forEach(censusCSV -> censusMap.put(censusCSV.stateCode, new CensusDAO(censusCSV)));
//                    censusList = censusMap.values().stream().collect(Collectors.toList());
//                    break;
//                case "UsCensusCSV":
//                    StreamSupport.stream(stateCensuses.spliterator(), false)
//                            .map(UsCensusCSV.class::cast)
//                            .forEach(censusCSV -> censusMap.put(censusCSV.usState, new CensusDAO(censusCSV)));
//                    censusList = censusMap.values().stream().collect(Collectors.toList());
//                    break;
//            }
//            return censusMap.size();
//        } catch (IOException e) {
//            throw new CensusAnalyserException(e.getMessage(),
//                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
//        } catch (CSVBuilderException e) {
//            throw new CensusAnalyserException(e.getMessage(), e.type.name());
//        } catch (RuntimeException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }

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

    //sort the state
    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("empty file", CensusAnalyserException.ExceptionType.EMPTY_FILE);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(censusDAO -> censusDAO.state);
        this.sortCSVData(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }

    public String getCodeWiseSortedStateCodeData() throws CensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("empty file", CensusAnalyserException.ExceptionType.EMPTY_FILE);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(censusDAO -> censusDAO.stateCode);
        this.sortCSVData(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }

    //sort the population of indian Census data in ascending order
    public String getPopulationWiseSortedCensusData() throws CensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("empty file", CensusAnalyserException.ExceptionType.EMPTY_FILE);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(censusDAO -> censusDAO.population);
        this.sortCSVData(censusComparator);
        Collections.reverse(censusList);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        String fileName = "./src/test/resources/UsStateCensusjson.json";
        this.write(fileName, censusList);
        return sortedStateCensusJson;
    }
    // sort the area of indian census data
    public String getAreaWiseSortedCensusData() throws CensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("empty file", CensusAnalyserException.ExceptionType.EMPTY_FILE);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(censusDAO -> censusDAO.areaInSqKm);
        this.sortCSVData(censusComparator);
        Collections.reverse(censusList);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }

    //sort by descending the density of indian census data
    public String getDensityWiseSortedCensusData() throws CensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("empty file", CensusAnalyserException.ExceptionType.EMPTY_FILE);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(censusDAO -> censusDAO.densityPerSqKm);
        this.sortCSVData(censusComparator);
        Collections.reverse(censusList);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }

    public void write(String fileName,List listToWrite) {
        try (Writer writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(listToWrite, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}