package org.progressify.spring.jsbuilder;

import java.util.*;

public class RegisterRouteStatement implements Statement{

    private String strategy;
    private String path;
    private String cacheName;
    private Integer networkTimeoutSeconds;
    private Boolean cacheQueryIgnoreSearch = false;
    private Boolean cacheQueryIgnoreMethod = false;
    private Boolean cacheQueryIgnoreVary = false;



    // annotation - strategy mapper
    private static Map<String, String> annMapper = new HashMap<String, String>();

    static {
        annMapper.put("org.progressify.spring.annotations.NetworkOnly", "workbox.strategies.NetworkOnly");
        annMapper.put("org.progressify.spring.annotations.NetworkFirst", "workbox.strategies.NetworkFirst");
        annMapper.put("org.progressify.spring.annotations.CacheOnly", "workbox.strategies.CacheOnly");
        annMapper.put("org.progressify.spring.annotations.CacheFirst", "workbox.strategies.CacheFirst");
        annMapper.put("org.progressify.spring.annotations.StaleWhileRevalidate",
                "workbox.strategies.StaleWhileRevalidate");
    }

    public RegisterRouteStatement() {
        super();
    }

    public Integer getNetworkTimeoutSeconds() {
        return networkTimeoutSeconds;
    }

    public void setNetworkTimeoutSeconds(Integer networkTimeoutSeconds) {
        this.networkTimeoutSeconds = networkTimeoutSeconds;
    }

    public Boolean getCacheQueryIgnoreVary() {
        return cacheQueryIgnoreVary;
    }

    public void setCacheQueryIgnoreVary(Boolean cacheQueryIgnoreVary) {
        this.cacheQueryIgnoreVary = cacheQueryIgnoreVary;
    }

    public Boolean getCacheQueryIgnoreMethod() {
        return cacheQueryIgnoreMethod;
    }

    public void setCacheQueryIgnoreMethod(Boolean cacheQueryIgnoreMethod) {
        this.cacheQueryIgnoreMethod = cacheQueryIgnoreMethod;
    }

    public Boolean getCacheQueryIgnoreSearch() {
        return cacheQueryIgnoreSearch;
    }

    public void setCacheQueryIgnoreSearch(Boolean cacheQueryIgnoreSearch) {
        this.cacheQueryIgnoreSearch = cacheQueryIgnoreSearch;
    }

    public RegisterRouteStatement(String strategy, String path, String cacheName, Integer networkTimeoutSeconds) {
        this.strategy=strategy;
        this.path=path;
        this.cacheName=cacheName;
        this.networkTimeoutSeconds = networkTimeoutSeconds;
    }
    

    public String getJsString(){

        StringBuilder stmtBuffer = new StringBuilder();
        stmtBuffer.append("workbox.routing.registerRoute(");
        stmtBuffer.append("\n\tnew RegExp('"+path+"'),");
        stmtBuffer.append("\n\tnew "+annMapper.get(strategy)+"({");
        StringBuilder routeConfig = new StringBuilder();
        if(cacheName != null){
            routeConfig.append("\n\t\tcacheName: '"+cacheName+"',");
        }
        if(networkTimeoutSeconds != null){
            routeConfig.append("\n\t\tnetworkTimeoutSeconds: "+networkTimeoutSeconds.toString()+",");
        }
        // matchOptions
        if(cacheQueryIgnoreSearch || cacheQueryIgnoreMethod || cacheQueryIgnoreVary){
            routeConfig.append("\n\t\tmatchOptions: {");
            StringBuilder igOptions= new StringBuilder();
            if(cacheQueryIgnoreSearch){
                igOptions.append("ignoreSearch:true,");
            }
            if(cacheQueryIgnoreMethod){
                igOptions.append("ignoreMethod:true,");
            }
            if(cacheQueryIgnoreVary){
                igOptions.append("ignoreVary:true,");
            }
            routeConfig.append(igOptions.toString().replaceAll(",$", ""));
            routeConfig.append("},");
        }
        stmtBuffer.append(routeConfig.toString().replaceAll(",$", ""));
        stmtBuffer.append("\n\t})");
        stmtBuffer.append("\n);\n");

        return stmtBuffer.toString();
    }



}