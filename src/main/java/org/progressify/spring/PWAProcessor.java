package org.progressify.spring;

import java.lang.management.PlatformLoggingMXBean;
import java.util.*;
import java.util.logging.*;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;

import org.progressify.spring.jsbuilder.*;
import org.progressify.spring.processors.*;
import static org.progressify.spring.util.Constants.*;

/**
 * This is the annotation parser. This class should not be accessed programtically. Instead 
 * the annotaion parser needs to be configured in the maven compiler plugin
 * 
 * <pre> 
 * {@code
 *                   <plugin>
 *                       <artifactId>maven-compiler-plugin</artifactId>
 *                           <configuration>
 *                               <annotationProcessorPaths>
 *                                   <path>
 *                                       <groupId>org.progressify</groupId>
 *                                       <artifactId>progressify-spring</artifactId>
 *                                       <version>0.1.0</version>
 *                                   </path>
 *                               </annotationProcessorPaths>
 *                           </configuration>
 *                   </plugin>
 * }
 *</pre>
 *
 */
public class PWAProcessor extends AbstractProcessor {

    private Logger log;

    public PWAProcessor() {
        super();
    }

    @Override
    public synchronized void init(ProcessingEnvironment env) {

        log = Logger.getLogger(LOGGER_NAMESPACE);
        String logLevel = env.getOptions().get(COMPILER_ARG_LOG_LEVEL);
        if ("INFO".equals(logLevel)) {
            log.setLevel(Level.INFO);
        } else {
            log.setLevel(Level.SEVERE);
        }
    }

    /*
     * annotations return all the annotations found in this code base. To find the
     * exact occurence of each annotation, query the roundEnv
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        log.info("\n\n---- PWAProcessor start processing this round ----");
        if (annotations.isEmpty()) {
            log.info("---- PWAProcessor found no annotations to process. Exiting ! ----\n\n");
            return true;
        }

        List<Result> results = new ArrayList<Result>();

        // Enumerate all the supported annotations found in the source code
        // and process each annotation
        // One entry per annotation type not occurence ie. each annotation type can
        // have multiple occurences in the source code
        for (final TypeElement annotation : annotations) {
            // Process each elements annotated with this annotation
            
            for (final Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                BaseProcessor processor = ProcessorFactory.getProcessor(annotation.toString());
                Result result = processor.process(annotation, element);
                if(result != null){
                    results.add(result);
                }
                
            }
        }

        build(results);

        log.info("---- PWAProcessor end processing this round ----\n\n");
        return true;
    }

    private void build(List<Result> results) {

        Builder b = new Builder(getResourcesDirectory(CONFIG_DEFAULT_SW_DIR), CONFIG_DEFAULT_SW_FILE_NAME);
        b.addFirst(Comment.getDefaultComment());
        b.add(new ImportStatement());
        for (Result result : results) {
            if (result instanceof StrategyResult) {
                StrategyResult sr = (StrategyResult) result;
                List<String> values = sr.getValueList();
                for (String val : values) {
                    b.add(new Comment(sr.getComment(), true));
                    RegisterRouteStatement rStmt = new RegisterRouteStatement(sr.getKey(), val, sr.getCacheName(),sr.getNetworkTimeoutSeconds());
                    rStmt.setCacheQueryIgnoreMethod(sr.getCacheQueryIgnoreMethod());
                    rStmt.setCacheQueryIgnoreSearch(sr.getCacheQueryIgnoreSearch());
                    rStmt.setCacheQueryIgnoreVary(sr.getCacheQueryIgnoreVary());
                    b.add(rStmt);
                }
            } else if(result instanceof ConfigResult){
                ConfigResult cr = (ConfigResult)result;
                if(cr.getSwDirectory() != null){
                    b.setDirectory(getResourcesDirectory(cr.getSwDirectory()));
                }
                if(cr.getSwFileName() != null){
                    b.setFileName(cr.getSwFileName());
                }
            } else if(result instanceof PreCacheResult){
                PreCacheResult pr = (PreCacheResult)result;
                PreCacheStatement prStmt = new PreCacheStatement(pr);
                b.add(prStmt);
                
            }
        }
        try {
            
            b.flushToFile();
        } catch (Exception excp) {
            log.info("SEVERE : Exception caught");
            excp.printStackTrace();
        }

    }

    private String getResourcesDirectory(String dirLocation) {
        String multiModuleProjectDirectory = System.getProperty("maven.multiModuleProjectDirectory");
        log.info("Found maven.multiModuleProjectDirectory to be :" + multiModuleProjectDirectory);
        return multiModuleProjectDirectory + java.io.File.separator + dirLocation;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> s = new HashSet<String>();
        s.addAll(ProcessorFactory.strategies);
        s.addAll(ProcessorFactory.configs);
        s.addAll(ProcessorFactory.precache);
        return s;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

}
