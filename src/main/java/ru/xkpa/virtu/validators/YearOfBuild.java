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
@Constraint(validatedBy = YearOfBuildConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface YearOfBuild {
    String message() default "Year of build must be equal or less than current year.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
