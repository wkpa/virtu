package ru.xkpa.virtu.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Pavel Kurakin
 */
@Constraint(validatedBy = AgreementPeriodValidator.class)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface AgreementPeriod {

    String fromDateField();
    String toDateField();
    String message() default "The first date of the period must be less than the second date of the period" +
            " and the difference must be no more than a year.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {

        AgreementPeriod[] value();
    }
}
