package com.bridgelabz.indiancensusanalyser.services;

public class CSVBuilderFactory {
    public static ICSVBuilder createCSVBuilder() {
        return new OpenCSVBuilder<>();
    }
}

