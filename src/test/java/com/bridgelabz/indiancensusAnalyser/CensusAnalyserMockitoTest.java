package com.bridgelabz.indiancensusAnalyser;

import com.bridgelabz.indiancensusanalyser.adapter.CensusAdapter;
import com.bridgelabz.indiancensusanalyser.adapter.CensusAdapterFactory;
import com.bridgelabz.indiancensusanalyser.adapter.IndiaCensusAdapter;
import com.bridgelabz.indiancensusanalyser.dao.CensusDAO;
import com.bridgelabz.indiancensusanalyser.exception.CensusAnalyserException;
import com.bridgelabz.indiancensusanalyser.model.IndiaCensusCSV;
import com.bridgelabz.indiancensusanalyser.services.CensusAnalyser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CensusAnalyserMockitoTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";


    @Mock
    CensusAdapterFactory censusAdapterFactory;

    @InjectMocks
   CensusAnalyser censusAnalyser;

    Map <String, CensusDAO> censusDAOMap;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() {
      this.censusDAOMap = new HashMap<>();
        censusDAOMap.put("Maharashtra", new CensusDAO("Maharashtra",98746645,307713,365));
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getIndiaCensusCSVFile_ShouldReturn_MockedFile() throws CensusAnalyserException {
        when(censusAdapterFactory.getCensusData(CensusAnalyser.Country.INDIA_CENSUS, INDIA_CENSUS_CSV_FILE_PATH)).thenReturn(this.censusDAOMap);
        Map numOfRecords = censusAdapterFactory.getCensusData(CensusAnalyser.Country.INDIA_CENSUS, INDIA_CENSUS_CSV_FILE_PATH);
        Assert.assertEquals(1,numOfRecords.size());

    }
}