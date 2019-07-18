package org.progressify.spring.annotations;

import java.lang.annotation.*;
import java.lang.annotation.Target;

/**
 * Only retrieve from the cache 
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention( RetentionPolicy.SOURCE )
@Documented
public @interface CacheOnly {
    
    String cacheName() default "";
    
}



