
package org.progressify.spring.annotations;

import java.lang.annotation.*;
import java.lang.annotation.Target;

/**
 * Checks the cache first before going to the network. 
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention( RetentionPolicy.SOURCE )
@Documented
public @interface CacheFirst {
    
   String cacheName() default "";

}

