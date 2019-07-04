package ru.xkpa.virtu.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Pavel Kurakin
 */
public class AgreementNumberValidator implements ConstraintValidator<AgreementNumber, Integer> {
   public void initialize(AgreementNumber constraint) {
   }

   public boolean isValid(Integer value, ConstraintValidatorContext context) {
      if(value == null) {
         return true;
      }

      return String.valueOf(value).matches("\\d{6}");
   }
}
