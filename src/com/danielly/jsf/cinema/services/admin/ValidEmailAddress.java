package com.danielly.jsf.cinema.services.admin;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * The Interface ValidEmailAddress provides the methods in order to validate the Email Address.
 *
 * @author Gil Danielly
 * @version 1.0
 * @date 05/10/20
 * 
 */


@Constraint(validatedBy = EmailAddressValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmailAddress {

    /**
     * Message.
     *
     * @return the string
     */
    String message() default "Please enter a valid e-mail address.";

    /**
     * Groups.
     *
     * @return the class[]
     */
    Class<?>[] groups() default {};

    /**
     * Payload.
     *
     * @return the array of the class type which extends payload
     */
    Class<? extends Payload>[] payload() default {};
}
