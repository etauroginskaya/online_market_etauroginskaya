package com.gmail.etauroginskaya.online_market.service.validator.annotations;

import com.gmail.etauroginskaya.online_market.service.validator.UniqueEmailConstraintValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = UniqueEmailConstraintValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {

    String message() default "user with this email already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}