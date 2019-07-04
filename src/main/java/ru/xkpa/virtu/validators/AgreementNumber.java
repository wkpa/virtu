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
@Constraint(validatedBy = AgreementNumberValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AgreementNumber {
    String message() default "Number of agreement should consist of six digits.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
