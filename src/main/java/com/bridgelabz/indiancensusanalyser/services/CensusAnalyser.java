package com.bridgelabz.indiancensusanalyser.services;
import com.bridgelabz.indiancensusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.indiancensusanalyser.model.IndiaCensusCSV;
import com.bridgelabz.indiancensusanalyser.model.IndiaStateCSV;
import com.opencsv.CSVBuilderException;
import com.opencsv.CSVBuilderFactory;
import com.opencsv.ICSVBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
          List<IndiaCensusCSV> censusCSVList = csvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
            return censusCSVList.size();
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

    public int loadIndiaStateCode(String csvFilePath) throws CensusAnalyserException{
        try ( Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<IndiaStateCSV> stateCSVList = csvBuilder.getCSVFileList(reader, IndiaStateCSV.class);
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
     * @param integer
     * @param <E>
     * @return
     */
    private <E>int getCount(Iterator<E> integer){
        Iterable<E> csvIterable = () -> integer;
        int numOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numOfEntries;
    }
}