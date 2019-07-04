package ru.xkpa.virtu.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

/**
 * @author Pavel Kurakin
 */
public class YearOfBuildConstraintValidator implements ConstraintValidator<YearOfBuild, Integer> {
   public void initialize(YearOfBuild constraint) {
   }

   public boolean isValid(Integer value, ConstraintValidatorContext context) {
      if(value == null) {
         return true;
      }

      return (value <= LocalDate.now().getYear()) && (String.valueOf(value).matches("\\d{4}"));
   }
}
