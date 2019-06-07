package com.gmail.etauroginskaya.online_market.service.validator.annotations;

import com.gmail.etauroginskaya.online_market.service.validator.MatchesXSDValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = MatchesXSDValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MatchesXSD {

    String message() default "does not match xsd";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
