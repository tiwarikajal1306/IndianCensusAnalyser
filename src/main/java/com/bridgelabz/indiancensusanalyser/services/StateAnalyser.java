package com.bridgelabz.indiancensusanalyser.services;

import com.bridgelabz.indiancensusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.indiancensusanalyser.model.IndiaStateCSV;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class StateAnalyser {
    public int loadIndiaStateData(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            CsvToBeanBuilder<IndiaStateCSV> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(IndiaStateCSV.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<IndiaStateCSV> csvToBean = csvToBeanBuilder.build();
            Iterator<IndiaStateCSV> stateCSVIterator = csvToBean.iterator();
            int numOfEntries = 0;
            while (stateCSVIterator.hasNext()) {
                numOfEntries++;
                IndiaStateCSV censusData = stateCSVIterator.next();
            }

            return numOfEntries;
        }catch(RuntimeException e){
            throw new CensusAnalyserException("Wrong delimiter OR Header", CensusAnalyserException.ExceptionType.WRONG_DELIMITER_OR_HEADER);

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
    public void getFileExtension(String path) throws CensusAnalyserException {
        boolean extension=path.endsWith(".csv");
        if(!extension)
            throw new CensusAnalyserException("Wrong Extension",CensusAnalyserException.ExceptionType.NO_SUCH_TYPE);
    }
}
