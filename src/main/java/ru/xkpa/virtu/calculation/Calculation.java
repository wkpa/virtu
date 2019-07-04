package ru.xkpa.virtu.calculation;

/**
 * @author Pavel Kurakin
 */
public class Calculation {
    private float insurancePayment;
    private long dayCount;
    private float yearOfBuildRatio;
    private float assetKindRatio;
    private float areaRatio;

    public float getInsurancePayment() {
        return insurancePayment;
    }

    public void setInsurancePayment(float insurancePayment) {
        this.insurancePayment = insurancePayment;
    }

    public long getDayCount() {
        return dayCount;
    }

    public void setDayCount(long dayCount) {
        this.dayCount = dayCount;
    }

    public float getYearOfBuildRatio() {
        return yearOfBuildRatio;
    }

    public void setYearOfBuildRatio(float yearOfBuildRatio) {
        this.yearOfBuildRatio = yearOfBuildRatio;
    }

    public float getAssetKindRatio() {
        return assetKindRatio;
    }

    public void setAssetKindRatio(float assetKindRatio) {
        this.assetKindRatio = assetKindRatio;
    }

    public float getAreaRatio() {
        return areaRatio;
    }

    public void setAreaRatio(float areaRatio) {
        this.areaRatio = areaRatio;
    }
}
