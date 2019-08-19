package org.progressify.spring.annotations;

import java.lang.annotation.*;
import java.lang.annotation.Target;


/**
 * Alyways check the network first 
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention( RetentionPolicy.SOURCE )
@Documented
public @interface NetworkFirst {
    
    String cacheName() default "";
    boolean cacheQueryIgnoreSearch() default false;
    boolean cacheQueryIgnoreMethod() default false;
    boolean cacheQueryIgnoreVary() default false;
    int networkTimeoutSeconds() default -1;

}