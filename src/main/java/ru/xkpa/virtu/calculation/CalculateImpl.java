package ru.xkpa.virtu.calculation;

import org.apache.commons.math3.util.Precision;
import java.time.LocalDate;

/**
 * @author Pavel Kurakin
 */
public class CalculateImpl implements Calculate {

    @Override
    public CalculationResult calculate(Calculation data) {

        CalculationResult result = new CalculationResult();

        float insurance = (data.getInsurancePayment()/data.getDayCount()) * data.getAreaRatio()
                * data.getAssetKindRatio() * data.getYearOfBuildRatio();
        result.setCalculationDate(LocalDate.now());
        result.setInsurancePremium(Precision.round(insurance, 2));

        return result;
    }
}
