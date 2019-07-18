package org.progressify.spring.processors;

import javax.lang.model.element.*;
import static org.progressify.spring.util.Constants.*;
import java.util.logging.Logger;


public abstract class BaseProcessor {

    public Logger log = Logger.getLogger(LOGGER_NAMESPACE);
    public abstract Result process(TypeElement annotation, Element element);

}

