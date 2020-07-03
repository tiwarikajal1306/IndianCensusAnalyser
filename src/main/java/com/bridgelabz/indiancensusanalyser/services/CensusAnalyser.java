package com.bridgelabz.indiancensusanalyser.services;

import com.bridgelabz.indiancensusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.indiancensusanalyser.model.IndiaCensusCSV;
import com.bridgelabz.indiancensusanalyser.model.IndiaStateCSV;
import com.bridgelabz.indiancensusanalyser.model.UsCensusCSV;
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

    Collection<Object> censusRecords = null;
    HashMap<Object, Object> censusHashMap = null;

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            censusHashMap = csvBuilder.getCSVFileMap(reader, IndiaCensusCSV.class);
            return censusHashMap.size();
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
            censusHashMap = csvBuilder.getCSVFileMap(reader, IndiaStateCSV.class);
            return censusHashMap.size();
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
            censusHashMap = csvBuilder.getCSVFileMap(reader, UsCensusCSV.class);
            return censusHashMap.size();
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

    private <E> int getCount(Iterator<E> integer) {
        Iterable<E> csvIterable = () -> integer;
        int numOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numOfEntries;
    }
    //sort the state
    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        if (censusHashMap == null || censusHashMap.size() == 0){
            throw new CensusAnalyserException("empty file",CensusAnalyserException.ExceptionType.EMPTY_FILE);
        }
        Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.state);
        this.asscendingSort(censusComparator, censusHashMap);
        censusRecords = censusHashMap.values();
        String sortedStateCensusJson = new Gson().toJson(censusRecords);
        return sortedStateCensusJson;
    }
    //sort the state in ascending order
    private <E> void asscendingSort(Comparator<E> censusComparator, Map<Object, Object> censusRecords) {
        IntStream.range(0, censusRecords.size()).flatMap(rowCounter -> IntStream
                .range(0, censusRecords.size() - rowCounter - 1)).forEach(columnCounter -> {
            E census1 = (E) censusRecords.get(columnCounter);
            E census2 = (E) censusRecords.get(columnCounter + 1);
            if (censusComparator.compare(census1, census2) > 0) {
                censusRecords.put(columnCounter, census2);
                censusRecords.put(columnCounter + 1, census1);
            }
        });
    }

    //sort the state code in ascending order
    public String getCodeWiseSortedStateCodeData() throws CensusAnalyserException {
        if (censusHashMap == null || censusHashMap.size() == 0)
            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.  CENSUS_FILE_PROBLEM);
        Comparator<IndiaStateCSV> stateCodeComparator = Comparator.comparing(census -> census.stateCode);
        this.asscendingSort(stateCodeComparator, censusHashMap);
        censusRecords = censusHashMap.values();
        String sortedStateCodeJson = new Gson().toJson(censusRecords);
        return sortedStateCodeJson;
    }
    public String getPopulationWiseSortedCensusData() throws CensusAnalyserException {
        if (censusHashMap == null || censusHashMap.size() == 0)
            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.population);
        this.asscendingSort(censusComparator, censusHashMap);
        censusRecords = censusHashMap.values();
        String sortedPopulationCensusJson = new Gson().toJson(censusRecords);
        String fileName = "./src/test/resources/IndiaStateCensusjson.json";
        try (Writer writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(censusHashMap, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sortedPopulationCensusJson;
    }
//population for us
    public String getPopulationWiseSortedUsCensusData() throws CensusAnalyserException {
        if (censusHashMap == null || censusHashMap.size() == 0)
            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        Comparator<UsCensusCSV> censusComparator = Comparator.comparing(census -> census.usPopulation);
        this.asscendingSort(censusComparator, censusHashMap);
        censusRecords = censusHashMap.values();
        String sortedPopulationCensusJson = new Gson().toJson(censusRecords);
        String fileName = "./src/test/resources/UsStateCensusjson.json";
        try (Writer writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(censusHashMap, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sortedPopulationCensusJson;
    }

    //sort by descending the density
    public String getDensityWiseSortedCensusData() throws CensusAnalyserException {
        if (censusHashMap == null || censusHashMap.size() == 0)
            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
        this.descendingSort(censusComparator, censusHashMap);
        censusRecords = censusHashMap.values();
        String sortedPopulationDensityCensusJson = new Gson().toJson(censusRecords);
        String fileName = "./src/test/resources/PopulationDensityWiseSortedIndiaCensus.json";
        try (Writer writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(censusHashMap, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sortedPopulationDensityCensusJson;
    }

    private <E> void descendingSort(Comparator censusComparator, Map<Object, Object> records) {
        for (int i = 0; i < records.size() - 1; i++) {
            for (int j = 0; j < records.size() - i - 1; j++) {
                E census1 = (E) records.get(j);
                E census2 = (E) records.get(j + 1);
                if (censusComparator.compare(census1, census2) < 0) {
                    records.put(j, census2);
                    records.put(j + 1, census1);
                }
            }
        }
    }
    public String getAreaWiseSortedCensusData() throws CensusAnalyserException {
        if (censusHashMap == null || censusHashMap.size() == 0)
            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.areaInSqKm);
        this.descendingSort(censusComparator, censusHashMap);
        censusRecords = censusHashMap.values();
        String sortedAreaCensusJson = new Gson().toJson(censusRecords);
        String fileName = "./src/test/resources/AreaWiseSortedIndiaCensus.json";
        try (Writer writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(censusHashMap, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sortedAreaCensusJson;
    }
}
