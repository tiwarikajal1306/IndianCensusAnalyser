package com.bridgelabz.indiancensusanalyser.exception;

public class CensusAnalyserException extends Exception {
    public CensusAnalyserException(String message, String name) {
        super(message);
        this.type = ExceptionType.valueOf(name);
    }
    public enum ExceptionType {
        CENSUS_FILE_PROBLEM, EMPTY_FILE, INVALID_COUNTRY, INVALID_HEADER_AND_DELIMITER
    }
    public ExceptionType type;
    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
