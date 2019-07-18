package org.progressify.spring.processors;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.*;
import javax.lang.model.element.*;
import static org.progressify.spring.util.Constants.*;

public class StrategyProcessor extends BaseProcessor {

    @Override
    /*
     * This method is called once per annotation per occurence Process the values of
     * that annotation in conjunction with any spring annotations present on this
     * element
     */
    public Result process(TypeElement annotation, Element element) {

        Result result = null;
        // Should be set from either the GetMapping or the RequestMapping annotation
        // If both are set - might not be legal in Spring, last wins
        // If there is nothing set, this means only a Progressify annotation has been
        // set
        List<String> paths = null;
        String thisAnnotation = annotation.toString();
        String cacheName = null;
        log.info("Processing element : " + element.getEnclosingElement() 
                + " : " + element.getSimpleName()+ " annotated by:"+thisAnnotation);

        List<AnnotationMirror> mirrors = (List<AnnotationMirror>) element.getAnnotationMirrors();

        for (AnnotationMirror mirror : mirrors) {

            //System.out.println("\n\nCHECK THIS mirror.getAnnotationType().toString()=>"+mirror.getAnnotationType().toString());
            String mirrorAnnotationType = mirror.getAnnotationType().toString();
            switch (mirrorAnnotationType) {

            case SPR_GETMAPPING:
                paths = processSpringGetMappingAnnotation(mirror);
                break;
            case SPR_REQMAPPING:
                paths = processSpringRequestMappingAnnotation(mirror);
                break;
            default:
                if (thisAnnotation.equals(mirror.getAnnotationType().toString())) {
                    cacheName = processProgressifyAnnotation(thisAnnotation,mirror);
                }
                break;
            }

        }
        // Now check if any paths have been discovered. If none, then return null
        if (paths != null) {
            String comment = "Route added based on annotation found at " + element.getEnclosingElement() + " : "
                    + element.getSimpleName();
            result = new StrategyResult(annotation.toString(), paths, comment, cacheName);
        } else{
            log.info("No Spring annotation found for element : " + element.getEnclosingElement() 
                + " : " + element.getSimpleName()+ " annotated by: "+thisAnnotation+" .Hence ignoring");
        }
        return result;
    }

    /*
     * This method processes a Spring GetMapping and returns a list of strings
     * 
     */
    private List<String> processSpringGetMappingAnnotation(AnnotationMirror mirror) {
        List<String> paths = new ArrayList<String>();
        Map m = mirror.getElementValues();
        Set<ExecutableElement> s = (Set<ExecutableElement>) m.keySet();
        for (ExecutableElement e : s) {
            if (SPR_GETMAPPING_VALUE.equals(e.getSimpleName().toString())
                    || SPR_GETMAPPING_PATH.equals(e.getSimpleName().toString())) {
                AnnotationValue val = (AnnotationValue) m.get(e);
                List<AnnotationValue> listOfValues = (List<AnnotationValue>) val.getValue();
                for (AnnotationValue eachValue : listOfValues) {
                    String value = (String) eachValue.getValue();
                    paths.add(value);
                }
            }

        }
        return paths;
    }

    private List<String> processSpringRequestMappingAnnotation(AnnotationMirror mirror) {
        List<String> paths = new ArrayList<String>();
        log.info("\n\n\nINSIDE processSpringRequestMappingAnnotation");
        Map m = mirror.getElementValues();
        Set<ExecutableElement> s = (Set<ExecutableElement>) m.keySet();
        for (ExecutableElement e : s) {
            if (SPR_GETMAPPING_VALUE.equals(e.getSimpleName().toString())
                    || SPR_GETMAPPING_PATH.equals(e.getSimpleName().toString())) {
                AnnotationValue val = (AnnotationValue) m.get(e);
                List<AnnotationValue> listOfValues = (List<AnnotationValue>) val.getValue();
                for (AnnotationValue eachValue : listOfValues) {
                    String value = (String) eachValue.getValue();
                    paths.add(value);
                }
            }

        }

        return paths;
    }

    private String processProgressifyAnnotation(String thisAnnotation, AnnotationMirror mirror) {
        String cacheName = null;
        Map m = mirror.getElementValues();
        Set<ExecutableElement> s = (Set<ExecutableElement>) m.keySet();
        for (ExecutableElement e : s) {
            if ("cacheName".equals(e.getSimpleName().toString())) {
                AnnotationValue val = (AnnotationValue) m.get(e);
                cacheName = (String) val.getValue();
            }

        }
        return cacheName;
    }
}