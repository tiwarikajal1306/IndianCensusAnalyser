package com.bridgelabz.indiancensusanalyser.services;

import com.bridgelabz.indiancensusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.indiancensusanalyser.model.IndiaCensusCSV;
import com.bridgelabz.indiancensusanalyser.model.IndiaCensusDAO;
import com.bridgelabz.indiancensusanalyser.model.IndiaStateCSV;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    List<IndiaCensusDAO> censusList = null;
    List<IndiaStateCSV> stateCSVList = null;

    public CensusAnalyser() {
        this.censusList = new ArrayList<IndiaCensusDAO>();

        };


    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            while (csvFileIterator.hasNext()) {
               this.censusList.add(new IndiaCensusDAO(csvFileIterator.next()));
            }
            return this.censusList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }

    }

    public int loadIndiaStateCode(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            stateCSVList = csvBuilder.getCSVFileList(reader, IndiaStateCSV.class);
            return stateCSVList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    /**
     * Count the number of iteration
     *
     * @param integer
     * @param <E>
     * @return
     */
    private <E> int getCount(Iterator<E> integer) {
        Iterable<E> csvIterable = () -> integer;
        int numOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numOfEntries;
    }
//sort the state
    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("empty file",CensusAnalyserException.ExceptionType.EMPTY_FILE);
        }
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.state);
        this.sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.censusList);
        return sortedStateCensusJson;

    }

    private void sort(Comparator<IndiaCensusDAO> censusComparator) {
        for (int i = 0; i < censusList.size() - 1; i++) {
            for (int j = 0; j < censusList.size() - i - 1; j++) {
                IndiaCensusDAO census1 = censusList.get(j);
                IndiaCensusDAO census2 = censusList.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    censusList.set(j, census2);
                    censusList.set(j + 1, census1);
                }
            }
        }
    }

    //sort the state code
    public String getCodeWiseSortedStateCodeData() throws CensusAnalyserException {
        if (stateCSVList == null || stateCSVList.size() == 0) {
            throw new CensusAnalyserException("empty file",CensusAnalyserException.ExceptionType.EMPTY_FILE);
        }
        Comparator<IndiaStateCSV> censusComparator = Comparator.comparing(census -> census.stateCode);
        this.sortStateCode(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(stateCSVList);
        return sortedStateCensusJson;

    }
    private void sortStateCode(Comparator<IndiaStateCSV> stateCode) {
        for (int i = 0; i < stateCSVList.size() - 1; i++) {
            for (int j = 0; j < stateCSVList.size() - i - 1; j++) {
                IndiaStateCSV census1 = stateCSVList.get(j);
                IndiaStateCSV census2 = stateCSVList.get(j + 1);
                if (stateCode.compare(census1, census2) > 0) {
                    stateCSVList.set(j, census2);
                    stateCSVList.set(j + 1, census1);
                }
            }
        }
    }
    public String getPopulationWiseSortedCensusData() throws CensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("empty file",CensusAnalyserException.ExceptionType.EMPTY_FILE);
        }
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.population);
        this.sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        try (Writer writer = new FileWriter("./src/test/resources/IndiaStateCensusjsonData.json")) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(censusList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sortedStateCensusJson;
    }
    //sort by descending the density
    public String getDensityWiseSortedCensusData() throws CensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("empty file",CensusAnalyserException.ExceptionType.EMPTY_FILE);
        }
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
        this.sortDescending(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }
    private void sortDescending(Comparator<IndiaCensusDAO> censusComparator) {
        for (int i = 0; i < censusList.size() - 1; i++) {
            for (int j = 0; j < censusList.size() - i - 1; j++) {
                IndiaCensusDAO census1 = censusList.get(j);
                IndiaCensusDAO census2 = censusList.get(j + 1);
                if (censusComparator.compare(census1, census2) < 0) {
                    censusList.set(j, census2);
                    censusList.set(j + 1, census1);
                }
            }
        }
    }
    public String getAreaWiseSortedCensusData() throws CensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("empty file",CensusAnalyserException.ExceptionType.EMPTY_FILE);
        }
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.areaInSqKm);
        this.sortDescending(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }
}