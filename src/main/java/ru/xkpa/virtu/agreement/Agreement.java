package ru.xkpa.virtu.agreement;

import ru.xkpa.virtu.address.Address;
import ru.xkpa.virtu.calculation.CalculationData;
import ru.xkpa.virtu.calculation.CalculationResult;
import ru.xkpa.virtu.client.Client;
import ru.xkpa.virtu.common.BaseObjectId;
import ru.xkpa.virtu.validators.AgreementNumber;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * @author Pavel Kurakin
 */
@Entity
@Table(name = "agreement")
public class Agreement extends BaseObjectId {

    private int agreementNumber;
    private LocalDate agreementDate;
    private Client client;
    private Address address;
    private CalculationData calculationData;
    private CalculationResult calculationResult;

    @NotNull
    @AgreementNumber
    @Column(name = "agreement_number", nullable = false, unique = true)
    public int getAgreementNumber() {
        return agreementNumber;
    }

    public void setAgreementNumber(int agreementNumber) {
        this.agreementNumber = agreementNumber;
    }

    @NotNull
    @Column(name = "agreement_date", nullable = false)
    public LocalDate getAgreementDate() {
        return agreementDate;
    }

    public void setAgreementDate(LocalDate agreementDate) {
        this.agreementDate = agreementDate;
    }

    @NotNull
    @ManyToOne
    @JoinColumn(name = "client_id")
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Valid
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Valid
    public CalculationData getCalculationData() {
        return calculationData;
    }

    public void setCalculationData(CalculationData calculationData) {
        this.calculationData = calculationData;
    }

    @Valid
    public CalculationResult getCalculationResult() {
        return calculationResult;
    }

    public void setCalculationResult(CalculationResult calculationResult) {
        this.calculationResult = calculationResult;
    }
}
