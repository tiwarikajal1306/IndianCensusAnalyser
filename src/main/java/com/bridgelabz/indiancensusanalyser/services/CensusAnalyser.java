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
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

Map<String, CensusDAO> censusStateMap ;
public CensusAnalyser() {
    censusStateMap = new HashMap<>();
}
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> csvIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            Iterable<IndiaCensusCSV> csvIterable = () -> csvIterator;
            StreamSupport.stream(csvIterable.spliterator(),false).
                    forEach(censusCSV -> censusStateMap.put(censusCSV.state, new CensusDAO(censusCSV)));
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

    public int loadIndiaStateCode(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCSV> csvIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCSV.class);
            Iterable<IndiaStateCSV> csvIterable = () -> csvIterator;
            StreamSupport.stream(csvIterable.spliterator(),false).
                     filter(csvState -> censusStateMap.get(csvState.stateName) != null)
                    .forEach(censusCSV -> censusStateMap.get(censusCSV.stateName).state = censusCSV.stateCode);
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

    public int loadUsCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<UsCensusCSV> csvIterator = csvBuilder.getCSVFileIterator(reader, UsCensusCSV.class);
            Iterable<UsCensusCSV> csvIterable = () -> csvIterator;
            StreamSupport.stream(csvIterable.spliterator(),false).
                    forEach(censusCSV -> censusStateMap.put(censusCSV.usState, new CensusDAO(censusCSV)));
            return censusStateMap.size();
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

//    //sort the state
//    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
//        if (censusHashMap == null || censusHashMap.size() == 0){
//            throw new CensusAnalyserException("empty file",CensusAnalyserException.ExceptionType.EMPTY_FILE);
//        }
//        Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.state);
//        this.asscendingSort(censusComparator, censusHashMap);
//        censusRecords = censusHashMap.values();
//        String sortedStateCensusJson = new Gson().toJson(censusRecords);
//        return sortedStateCensusJson;
//    }
//    //sort the state in ascending order
//    private <E> void asscendingSort(Comparator<E> censusComparator, Map<Object, Object> censusRecords) {
//        IntStream.range(0, censusRecords.size()).flatMap(rowCounter -> IntStream
//                .range(0, censusRecords.size() - rowCounter - 1)).forEach(columnCounter -> {
//            E census1 = (E) censusRecords.get(columnCounter);
//            E census2 = (E) censusRecords.get(columnCounter + 1);
//            if (censusComparator.compare(census1, census2) > 0) {
//                censusRecords.put(columnCounter, census2);
//                censusRecords.put(columnCounter + 1, census1);
//            }
//        });
//    }
//
//    //sort the state code in ascending order
//    public String getCodeWiseSortedStateCodeData() throws CensusAnalyserException {
//        if (censusHashMap == null || censusHashMap.size() == 0)
//            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.  CENSUS_FILE_PROBLEM);
//        Comparator<IndiaStateCSV> stateCodeComparator = Comparator.comparing(census -> census.stateCode);
//        this.asscendingSort(stateCodeComparator, censusHashMap);
//        censusRecords = censusHashMap.values();
//        String sortedStateCodeJson = new Gson().toJson(censusRecords);
//        return sortedStateCodeJson;
//    }
//    //sort the population of indian Census data in ascending order
//    public String getPopulationWiseSortedCensusData() throws CensusAnalyserException {
//        if (censusHashMap == null || censusHashMap.size() == 0)
//            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
//        Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.population);
//        this.asscendingSort(censusComparator, censusHashMap);
//        censusRecords = censusHashMap.values();
//        String sortedPopulationCensusJson = new Gson().toJson(censusRecords);
//        String fileName = "./src/test/resources/IndiaStateCensusjson.json";
//        try (Writer writer = new FileWriter(fileName)) {
//            Gson gson = new GsonBuilder().create();
//            gson.toJson(censusHashMap, writer);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return sortedPopulationCensusJson;
//    }
////sort the population of uc in ascending order
//    public String getPopulationWiseSortedUsCensusData() throws CensusAnalyserException {
//        if (censusHashMap == null || censusHashMap.size() == 0)
//            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
//        Comparator<UsCensusCSV> censusComparator = Comparator.comparing(census -> census.usPopulation);
//        this.asscendingSort(censusComparator, censusHashMap);
//        censusRecords = censusHashMap.values();
//        String sortedPopulationCensusJson = new Gson().toJson(censusRecords);
//        String fileName = "./src/test/resources/UsStateCensusjson.json";
//        try (Writer writer = new FileWriter(fileName)) {
//            Gson gson = new GsonBuilder().create();
//            gson.toJson(censusHashMap, writer);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return sortedPopulationCensusJson;
//    }
//
//    //sort by descending the density of indian census data
//    public String getDensityWiseSortedCensusData() throws CensusAnalyserException {
//        if (censusHashMap == null || censusHashMap.size() == 0)
//            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
//        Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
//        this.descendingSort(censusComparator, censusHashMap);
//        censusRecords = censusHashMap.values();
//        String sortedPopulationDensityCensusJson = new Gson().toJson(censusRecords);
//        String fileName = "./src/test/resources/PopulationDensityWiseSortedIndiaCensus.json";
//        try (Writer writer = new FileWriter(fileName)) {
//            Gson gson = new GsonBuilder().create();
//            gson.toJson(censusHashMap, writer);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return sortedPopulationDensityCensusJson;
//    }
////use generic to sort by descending
//    private <E> void descendingSort(Comparator censusComparator, Map<Object, Object> records) {
//        for (int i = 0; i < records.size() - 1; i++) {
//            for (int j = 0; j < records.size() - i - 1; j++) {
//                E census1 = (E) records.get(j);
//                E census2 = (E) records.get(j + 1);
//                if (censusComparator.compare(census1, census2) < 0) {
//                    records.put(j, census2);
//                    records.put(j + 1, census1);
//                }
//            }
//        }
//    }
//    //sort the area in decending order of indian census data
//    public String getAreaWiseSortedCensusData() throws CensusAnalyserException {
//        if (censusHashMap == null || censusHashMap.size() == 0)
//            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
//        Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.areaInSqKm);
//        this.descendingSort(censusComparator, censusHashMap);
//        censusRecords = censusHashMap.values();
//        String sortedAreaCensusJson = new Gson().toJson(censusRecords);
//        String fileName = "./src/test/resources/AreaWiseSortedIndiaCensus.json";
//        try (Writer writer = new FileWriter(fileName)) {
//            Gson gson = new GsonBuilder().create();
//            gson.toJson(censusHashMap, writer);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return sortedAreaCensusJson;
//    }
////sort us data total area in descending order
//    public String getAreaWiseSortedUsCensusData() throws CensusAnalyserException {
//        if (censusHashMap == null || censusHashMap.size() == 0)
//            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
//        Comparator<UsCensusCSV> censusComparator = Comparator.comparing(census -> census.totalArea);
//        this.descendingSort(censusComparator, censusHashMap);
//        censusRecords = censusHashMap.values();
//        String sortedAreaCensusJson = new Gson().toJson(censusRecords);
//        String fileName = "./src/test/resources/TotalAreaWiseSortedUsCensus.json";
//        try (Writer writer = new FileWriter(fileName)) {
//            Gson gson = new GsonBuilder().create();
//            gson.toJson(censusHashMap, writer);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return sortedAreaCensusJson;
//    }
}
