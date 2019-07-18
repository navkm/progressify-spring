package org.progressify.spring.annotations;

import java.lang.annotation.*;
import java.lang.annotation.Target;
import static org.progressify.spring.util.Constants.*;



/**
 * This is a global configuration for the tool. You should use this annotation only once in your source code.
 * It can currently used to set the service worker name and the directory it is generated to.
 * 
 * The defaults are
 * <p>
 * Service worker name : <b>sw.js</b>
 * Service worker dir  : <b>src/main/resources/static</b>
 * </p>
 *
 */
@Target({ElementType.TYPE})
@Retention( RetentionPolicy.SOURCE )
@Documented
public @interface PWA {
    //* <img src='https://developers.google.com/web/tools/workbox/images/modules/workbox-strategies/cache-first.png' width='300' alt='test'>
    String swDirectory() default CONFIG_DEFAULT_SW_DIR;
    String swFileName() default CONFIG_DEFAULT_SW_FILE_NAME;
}