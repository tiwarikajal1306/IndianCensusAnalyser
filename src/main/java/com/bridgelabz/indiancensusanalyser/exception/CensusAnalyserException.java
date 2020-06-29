package com.bridgelabz.indiancensusanalyser.exception;

public class CensusAnalyserException extends Exception {
   public enum ExceptionType {
        CENSUS_FILE_PROBLEM,NO_SUCH_TYPE,WRONG_DELIMITER_OR_HEADER
    }
    public ExceptionType type;
    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
