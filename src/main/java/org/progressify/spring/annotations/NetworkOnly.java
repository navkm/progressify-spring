package org.progressify.spring.annotations;

import java.lang.annotation.*;
import java.lang.annotation.Target;

/**
 * Always from the network. Never from the cache 
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention( RetentionPolicy.SOURCE )
@Documented
public @interface NetworkOnly {
    
    String cacheName() default "";
    
}