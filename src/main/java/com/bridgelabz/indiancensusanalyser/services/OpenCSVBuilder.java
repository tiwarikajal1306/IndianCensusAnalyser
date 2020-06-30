package com.bridgelabz.indiancensusanalyser.services;

import com.bridgelabz.indiancensusanalyser.exception.CensusAnalyserException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;

public class OpenCSVBuilder {
    /**
     * Single responsibility principle
     * @param reader
     * @param csvClass
     * @param <E>
     * @return
     * @throws CensusAnalyserException
     */
    public  <E> Iterator<E> getCSVFileIterator(Reader reader, Class<E> csvClass) throws CensusAnalyserException {
        try{
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
            return csvToBean.iterator();
        }
        catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }
        catch (RuntimeException e){
            throw new CensusAnalyserException("Improper Header OR Delimiter",
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
}
