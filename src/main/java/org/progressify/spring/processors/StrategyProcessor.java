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

        StrategyResult result = null;
        // Should be set from either the GetMapping or the RequestMapping annotation
        // If both are set - might not be legal in Spring, last wins
        // If there is nothing set, this means only a Progressify annotation has been
        // set
        List<String> paths = null;
        String thisAnnotation = annotation.toString();
        int networkTimeoutSeconds =-1;
        Map pOptions = null;
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
                    pOptions = processProgressifyAnnotation(thisAnnotation,mirror);
                }
                break;
            }

        }
        // Now check if any paths have been discovered. If none, then return null
        if (paths != null) {
            String comment = "Route added based on annotation found at " + element.getEnclosingElement() + " : "
                    + element.getSimpleName();
            result = new StrategyResult(annotation.toString(), paths, comment);
            if(pOptions.containsKey("cacheName")){
                result.setCacheName((String)pOptions.get("cacheName"));
            }
            if(pOptions.containsKey("networkTimeoutSeconds")){
                result.setNetworkTimeoutSeconds((Integer)pOptions.get("networkTimeoutSeconds"));
            }

            // Not only do we check if the attribute exists, it should also be set to true 
            if(pOptions.containsKey("cacheQueryIgnoreSearch") && (Boolean)pOptions.get("cacheQueryIgnoreSearch")){
                result.setCacheQueryIgnoreSearch(true);
            }
            // Not only do we check if the attribute exists, it should also be set to true 
            if(pOptions.containsKey("cacheQueryIgnoreMethod") && (Boolean)pOptions.get("cacheQueryIgnoreMethod")){
                result.setCacheQueryIgnoreMethod(true);
            }
            // Not only do we check if the attribute exists, it should also be set to true 
            if(pOptions.containsKey("cacheQueryIgnoreVary") && (Boolean)pOptions.get("cacheQueryIgnoreVary")){
                result.setCacheQueryIgnoreVary(true);
            }


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

    private Map processProgressifyAnnotation(String thisAnnotation, AnnotationMirror mirror) {
    
        Map<String,Object> pOptions = new HashMap<String,Object>();
        Map m = mirror.getElementValues();
        Set<ExecutableElement> s = (Set<ExecutableElement>) m.keySet();
        for (ExecutableElement e : s) {
            //System.out.println(e.getSimpleName().toString());
            /*if ("cacheName".equals(e.getSimpleName().toString())) {
                AnnotationValue val = (AnnotationValue) m.get(e);
                String cacheName = (String) val.getValue();
                pOptions.put("cacheName",cacheName);
            }
            if ("networkTimeoutSeconds".equals(e.getSimpleName().toString())) {
                AnnotationValue val = (AnnotationValue) m.get(e);
                Integer networkTimeoutSeconds = (Integer) val.getValue();
                pOptions.put("networkTimeoutSeconds",networkTimeoutSeconds);
            }*/
            extractMemberAndPutInMap("cacheName", e, m, pOptions);
            extractMemberAndPutInMap("networkTimeoutSeconds", e, m, pOptions);
            extractMemberAndPutInMap("cacheQueryIgnoreSearch", e, m, pOptions);
            extractMemberAndPutInMap("cacheQueryIgnoreMethod", e, m, pOptions);
            extractMemberAndPutInMap("cacheQueryIgnoreVary", e, m, pOptions);
        }
        return pOptions;
    }

    private void extractMemberAndPutInMap(String memberName,ExecutableElement e, Map elementVals,Map map){
        if (memberName.equals(e.getSimpleName().toString())) {
            AnnotationValue val = (AnnotationValue) elementVals.get(e);
            map.put(memberName,(Object) val.getValue());
        }
    }
   
}