package ru.xkpa.virtu.calculation;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class AreaRatioImplTest {

    private AreaRatio areaRatio = new AreaRatioImpl();

    @ParameterizedTest
    @CsvSource({ "14.5, 1.2", "49.99, 1.2", "50.0, 1.5", "75.5, 1.5", "100.0, 1.5", "240.1, 2.0", "100.01, 2.0" })
    void getRatio(float area, float ratio) {
        assertEquals(areaRatio.getRatio(area), ratio);
    }


}