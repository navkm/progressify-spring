package org.progressify.spring.processors;

import java.util.logging.Logger;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.*;
import javax.annotation.processing.*;
import javax.lang.model.element.*;
import static org.progressify.spring.util.Constants.*;

// Process the @PWA Annotation
public class ConfigProcessor extends BaseProcessor {

    public Result process(TypeElement annotation, Element element) {

        ConfigResult result = new ConfigResult();
        String thisAnnotation = annotation.toString();
        log.info("Processing config element : " + element.getEnclosingElement() + " : " + element.getSimpleName()
                + " annotated by:" + thisAnnotation);
        List<AnnotationMirror> mirrors = (List<AnnotationMirror>) element.getAnnotationMirrors();

        for (AnnotationMirror mirror : mirrors) {
            if (thisAnnotation.equals(mirror.getAnnotationType().toString())) {
 
                Map m = mirror.getElementValues();
                Set<ExecutableElement> s = (Set<ExecutableElement>) m.keySet();
                for (ExecutableElement e : s) {
                    if ("swDirectory".equals(e.getSimpleName().toString())) {
                        result.setSwDirectory(getValue(m, e));
                    } else if("swFileName".equals(e.getSimpleName().toString())){
                        result.setSwFileName(getValue(m, e));
                    }
                }

            }
        }
        
        return result;
    }

    private String getValue(Map m, ExecutableElement e){
        AnnotationValue val = (AnnotationValue) m.get(e);
        return (String) val.getValue();
    }
}