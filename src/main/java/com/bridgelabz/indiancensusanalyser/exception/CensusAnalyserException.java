package com.bridgelabz.indiancensusanalyser.exception;

public class CensusAnalyserException extends Exception {
   public enum ExceptionType {
        CENSUS_FILE_PROBLEM,NO_SUCH_TYPE,UNABLE_TO_PARSE
    }
    public ExceptionType type;
    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
