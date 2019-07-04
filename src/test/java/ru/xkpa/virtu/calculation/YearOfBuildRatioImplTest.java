package ru.xkpa.virtu.calculation;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class YearOfBuildRatioImplTest {

    private YearOfBuildRatio yearOfBuildRatio = new YearOfBuildRatioImpl();

    @ParameterizedTest
    @CsvSource({ "1917, 1.3", "1999, 1.3", "2000, 1.6", "2003, 1.6", "2014, 1.6", "2015, 2.0", "2018, 2.0" })
    void getRatio(int year, float ratio) {
        assertEquals(yearOfBuildRatio.getRatio(year), ratio);
    }

}