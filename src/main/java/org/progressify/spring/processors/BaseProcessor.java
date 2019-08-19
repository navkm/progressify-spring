package org.progressify.spring.processors;

import javax.lang.model.element.*;
import static org.progressify.spring.util.Constants.*;
import java.util.logging.Logger;
import java.util.*;

public abstract class BaseProcessor {

    public Logger log = Logger.getLogger(LOGGER_NAMESPACE);
    public abstract Result process(TypeElement annotation, Element element);

    protected Map<String,Object> getMemberMap(TypeElement annotation, Element element){

        Map<String,Object> map = new HashMap<String,Object>();
        String thisAnnotation = annotation.toString();
        log.info("Processing config element : " + element.getEnclosingElement() + " : " + element.getSimpleName()
                + " annotated by:" + thisAnnotation);
        List<AnnotationMirror> mirrors = (List<AnnotationMirror>) element.getAnnotationMirrors();
        for (AnnotationMirror mirror : mirrors) {
            if (thisAnnotation.equals(mirror.getAnnotationType().toString())) {
                Map valuesMap = mirror.getElementValues();
                Set<ExecutableElement> s = (Set<ExecutableElement>) valuesMap.keySet();
                for (ExecutableElement e : s) {
                    String key = e.getSimpleName().toString();
                    AnnotationValue val = (AnnotationValue) valuesMap.get(e);
                    Object valObject = val.getValue();
                    map.put(key, valObject);
                }

            }
        }
        return map;

    }

}

