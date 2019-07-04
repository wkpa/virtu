package ru.xkpa.virtu.calculation;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

/**
 * @author Pavel Kurakin
 */
@Embeddable
public class CalculationResult {

    private LocalDate calculationDate;
    private float insurancePremium;

    @NotNull
    @Column(name = "calculation_date", nullable = false)
    public LocalDate getCalculationDate() {
        return calculationDate;
    }

    public void setCalculationDate(LocalDate calculationDate) {
        this.calculationDate = calculationDate;
    }

    @NotNull
    @Positive
    @Digits(integer = 10, fraction = 2)
    @Column(name = "insurance_premium", nullable = false)
    public float getInsurancePremium() {
        return insurancePremium;
    }

    public void setInsurancePremium(float insurancePremium) {
        this.insurancePremium = insurancePremium;
    }
}
