package org.progressify.spring.annotations;

import java.lang.annotation.*;
import java.lang.annotation.Target;

/**
 * This annotation can be used to precache a set of resource files.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention( RetentionPolicy.SOURCE )
@Documented
public @interface PreCache {
    String[] versionedURL();
    /*
    String[] versionedPath() default {};
    String[] path() default {};
    String[] pathExpr() default {};
    */
}