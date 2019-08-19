package org.progressify.spring.annotations;

import java.lang.annotation.*;
import java.lang.annotation.Target;


/**
 *  Check both the cache and the network in parallel. Reply immediately from the cache. 
 *  If the cached version is outdated, replace it with the network version.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention( RetentionPolicy.SOURCE )
@Documented
public @interface StaleWhileRevalidate {
    
    String cacheName() default "";
    boolean cacheQueryIgnoreSearch() default false;
    boolean cacheQueryIgnoreMethod() default false;
    boolean cacheQueryIgnoreVary() default false;
    
}



