package org.progressify.spring.processors;

import java.util.*;


public class ProcessorFactory {
    public static final String NETWORK_ONLY="org.progressify.spring.annotations.NetworkOnly";
    public static final String NETWORK_FIRST="org.progressify.spring.annotations.NetworkFirst";
    public static final String CACHE_ONLY="org.progressify.spring.annotations.CacheOnly";
    public static final String CACHE_FIRST="org.progressify.spring.annotations.CacheFirst";
    public static final String STALE_WHILE_REVALIDATE="org.progressify.spring.annotations.StaleWhileRevalidate";

    public static final String PRECACHE="org.progressify.spring.annotations.PreCache";

    public static final String PWA="org.progressify.spring.annotations.PWA";

    public static Set<String> strategies = new HashSet<String>();
    public static Set<String> configs = new HashSet<String>();
    public static Set<String> precache = new HashSet<String>();

    static {
        strategies.add(NETWORK_ONLY);
        strategies.add(NETWORK_FIRST);
        strategies.add(CACHE_ONLY);
        strategies.add(CACHE_FIRST);
        strategies.add(STALE_WHILE_REVALIDATE);

        configs.add(PWA);

        precache.add(PRECACHE);
    }

    private static StrategyProcessor sp = new StrategyProcessor();
    private static ConfigProcessor   cp = new ConfigProcessor();
    private static PreCacheProcessor pp = new PreCacheProcessor();

    

    public static BaseProcessor getProcessor(String name){

        return (strategies.contains(name))?sp:(precache.contains(name)?pp:cp);
    }
    
    
}