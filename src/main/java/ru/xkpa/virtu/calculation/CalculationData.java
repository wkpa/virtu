package ru.xkpa.virtu.calculation;

import ru.xkpa.virtu.asset.AssetKind;
import ru.xkpa.virtu.validators.AgreementPeriod;
import ru.xkpa.virtu.validators.YearOfBuild;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

/**
 * @author Pavel Kurakin
 */
@Embeddable
@AgreementPeriod(fromDateField = "periodFrom", toDateField = "periodTo")
public class CalculationData {

    private float insurancePayment;
    private LocalDate periodFrom;
    private LocalDate periodTo;
    private int yearOfBuild;
    private float area;
    private AssetKind assetKind;

    @NotNull
    @Positive
    @Column(name = "insurance_payment", nullable = false)
    public float getInsurancePayment() {
        return insurancePayment;
    }

    public void setInsurancePayment(float insurancePayment) {
        this.insurancePayment = insurancePayment;
    }

    @NotNull
    @Column(name = "period_from", nullable = false)
    public LocalDate getPeriodFrom() {
        return periodFrom;
    }

    public void setPeriodFrom(LocalDate periodFrom) {
        this.periodFrom = periodFrom;
    }

    @NotNull
    @Column(name = "period_to", nullable = false)
    public LocalDate getPeriodTo() {
        return periodTo;
    }

    public void setPeriodTo(LocalDate periodTo) {
        this.periodTo = periodTo;
    }

    @NotNull
    @Positive
    @YearOfBuild
    @Column(name = "year_of_build", nullable = false)
    public int getYearOfBuild() {
        return yearOfBuild;
    }

    public void setYearOfBuild(int yearOfBuild) {
        this.yearOfBuild = yearOfBuild;
    }

    @NotNull
    @Positive
    @Digits(integer = 4, fraction = 1)
    @Column(name = "area", nullable = false)
    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    @NotNull
    @ManyToOne
    @JoinColumn(name = "asset_kind_id", nullable = false)
    public AssetKind getAssetKind() {
        return assetKind;
    }

    public void setAssetKind(AssetKind assetKind) {
        this.assetKind = assetKind;
    }
}
