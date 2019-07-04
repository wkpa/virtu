package ru.xkpa.virtu.client;

import ru.xkpa.virtu.common.BaseObjectId;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Pavel Kurakin
 */
@Entity
@Table(name = "client")
public class Client extends BaseObjectId implements Serializable {

    private String name;
    private String surname;
    private String patronymic;
    private LocalDate birthday;
    private String passportSeries;
    private String passportNumber;

    @NotNull
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Column(name = "surname", nullable = false)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Column(name = "patronymic")
    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @NotNull
    @Column(name = "birthday")
    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @NotNull
    @Pattern(regexp = "\\d{4}", message = "The serial of passport should consist of four digits.")
    @Column(name = "passport_series")
    public String getPassportSeries() {
        return passportSeries;
    }

    public void setPassportSeries(String passportSeries) {
        this.passportSeries = passportSeries;
    }

    @NotNull
    @Pattern(regexp = "\\d{6}", message = "The number of passport should consist of six digits.")
    @Column(name = "passport_number")
    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    @Transient
    public String getFullName() {
        StringBuilder sb = new StringBuilder();
        sb.append(getName() != null ? getName() : "");
        sb.append(" ");
        sb.append(getSurname() != null ? getSurname() : "");
        sb.append(" ");
        sb.append(getPatronymic() != null ? getPatronymic() : "");
        return sb.toString().trim();
    }
}
