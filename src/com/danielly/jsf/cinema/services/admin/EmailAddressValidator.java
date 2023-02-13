package com.danielly.jsf.cinema.services.admin;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * The Class EmailAddressValidator.
 * 
 * @author Gil Danielly 
 * @date 05/10/20	
 * @version 1.0
 * 
 */


public class EmailAddressValidator implements ConstraintValidator<ValidEmailAddress, String> {

    /**
     * Initialize.
     *
     * @param constraintAnnotation the constraint annotation
     */
    @Override
    public void initialize(ValidEmailAddress constraintAnnotation) {
    }

    /**
     * Checks if is valid.
     *
     * @param value the value
     * @param context the context
     * @return true, if is valid
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || value.equals("") || value.contains("@");
    }
}
