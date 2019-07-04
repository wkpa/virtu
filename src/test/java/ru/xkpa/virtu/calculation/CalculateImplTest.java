package ru.xkpa.virtu.calculation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculateImplTest {

    private CalculateImpl calculate = new CalculateImpl();
    private Calculation calculation;

    @BeforeEach
    void setUp() {
        calculation = new Calculation();
        calculation.setInsurancePayment(1_220_000);
        calculation.setDayCount(100);
        calculation.setAreaRatio(1.2F);
        calculation.setYearOfBuildRatio(1.2F);
        calculation.setAssetKindRatio(1.2F);
    }

    // (InsurancePayment / DayCount) *  AreaRatio *  YearOfBuildRatio * AssetKindRatio
    // ( 1220000  / 100 ) * 1.2 * 1.6 * 1.2 =  28108,8

    @Test
    void calculateDate() {
        assertEquals(calculate.calculate(calculation).getCalculationDate(), LocalDate.now());
    }

    @ParameterizedTest
    @CsvSource({ "1.2, 21081.6", "1.5, 26352.0", "2.0, 35136.01" })
    void calculateWithVariantAreaRatio(float areaRatio, float insurancePremium) {
        calculation.setAreaRatio(areaRatio);
        assertEquals(calculate.calculate(calculation).getInsurancePremium(), insurancePremium);
    }

    @ParameterizedTest
    @CsvSource({ "1.3, 22838.4", "1.6, 28108.8", "2.0, 35136.01" })
    void calculateWithVariantYearOfBuildRatio(float yearOfBuildRatio, float insurancePremium) {
        calculation.setYearOfBuildRatio(yearOfBuildRatio);
        assertEquals(calculate.calculate(calculation).getInsurancePremium(), insurancePremium);
    }

    @ParameterizedTest
    @CsvSource({ "1.3, 22838.4", "1.5, 26352.01", "1.7, 29865.6" })
    void calculateWithVariantAssetKindRatio(float assetKindRatio, float insurancePremium) {
        calculation.setAssetKindRatio(assetKindRatio);
        assertEquals(calculate.calculate(calculation).getInsurancePremium(), insurancePremium);
    }

}