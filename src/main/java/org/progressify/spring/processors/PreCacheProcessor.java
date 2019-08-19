package org.progressify.spring.processors;

import java.util.logging.Logger;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.*;
import javax.annotation.processing.*;
import javax.lang.model.element.*;
import static org.progressify.spring.util.Constants.*;

// Process the @PWA Annotation
public class PreCacheProcessor extends BaseProcessor {

    @Override
    public Result process(TypeElement annotation, Element element) {
        System.out.println("\n\n\nInside PreCacheProcessor");
        Map map = getMemberMap(annotation, element);
        List<String> versionedURL = (List<String>)map.get("versionedURL");
        
        List<String> urlSrtingsToBeVersioned = new ArrayList<String>();
        if(versionedURL != null){
            for(Object url :versionedURL){
                String urlString = (String)((AnnotationValue)url).getValue();
                urlSrtingsToBeVersioned.add(urlString);
            }
        }
        PreCacheResult result = new PreCacheResult();
        result.setVersionedURL(urlSrtingsToBeVersioned);
        return result;
    }

    
    
}