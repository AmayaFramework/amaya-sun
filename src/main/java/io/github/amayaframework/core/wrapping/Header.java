package io.github.amayaframework.core.wrapping;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * An annotation that is a marker for injecting the header value into the marked argument.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Header {
    String value() default "";
}
