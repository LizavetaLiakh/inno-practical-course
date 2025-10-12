package com.innowise.minispring.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

/**
 * Determines lifecycle scope for a component.<p>
 * Example: {@code @Scope("prototype")} or {@code @Scope("singleton")}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(TYPE)
public @interface Scope {
    /**
     * Sets a lifecycle scope as a default value.
     *
     * @return A lifecycle scope.
     */
    String value() default "singleton";
}
