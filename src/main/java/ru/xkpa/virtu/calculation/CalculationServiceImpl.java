package ru.xkpa.virtu.calculation;

import org.springframework.stereotype.Service;
import static java.time.temporal.ChronoUnit.DAYS;

/**
 * @author Pavel Kurakin
 */
@Service
public class CalculationServiceImpl implements CalculationService {

    private AreaRatio areaRatio = new AreaRatioImpl();
    private Calculate calculate = new CalculateImpl();
    private YearOfBuildRatio yearOfBuildRatio = new YearOfBuildRatioImpl();

    @Override
    public CalculationResult calculate(CalculationData calculationData) {
        Calculation calculation = new Calculation();
        calculation.setAreaRatio(areaRatio.getRatio(calculationData.getArea()));
        calculation.setAssetKindRatio(calculationData.getAssetKind().getRatio());
        calculation.setYearOfBuildRatio(yearOfBuildRatio.getRatio(calculationData.getYearOfBuild()));
        calculation.setInsurancePayment(calculationData.getInsurancePayment());
        calculation.setDayCount(DAYS.between(calculationData.getPeriodFrom(), calculationData.getPeriodTo()));
        return calculate.calculate(calculation);
    }
}
