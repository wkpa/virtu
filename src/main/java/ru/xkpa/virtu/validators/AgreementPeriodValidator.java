package ru.xkpa.virtu.validators;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.util.ReflectionUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * @author Pavel Kurakin
 */
public class AgreementPeriodValidator implements ConstraintValidator<AgreementPeriod, Object> {

    private String fromDateField;
    private String toDateField;
    private String message;

    @Override
    public void initialize(AgreementPeriod constraintAnnotation) {
        fromDateField = constraintAnnotation.fromDateField();
        toDateField = constraintAnnotation.toDateField();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value,
                           ConstraintValidatorContext context) {

        Field fromDate = FieldUtils.getField(value.getClass(), this.fromDateField, true);
        Field toDate = FieldUtils.getField(value.getClass(), this.toDateField, true);

        LocalDate fromLocalDate = (LocalDate) ReflectionUtils.getField(fromDate, value);
        LocalDate toLocalDate = (LocalDate) ReflectionUtils.getField(toDate, value);

        
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(fromDateField)
                .addConstraintViolation().disableDefaultConstraintViolation();

        if (fromLocalDate == null || toLocalDate == null) {
            return false;
        }


        return fromLocalDate.isBefore(toLocalDate) && (ChronoUnit.YEARS.between(fromLocalDate,toLocalDate) <= 0);
    }
}
