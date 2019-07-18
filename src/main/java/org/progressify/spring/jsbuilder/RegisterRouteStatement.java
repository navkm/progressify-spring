package org.progressify.spring.jsbuilder;

import java.util.*;

public class RegisterRouteStatement implements Statement{

    private String strategy;
    private String path;
    private String cacheName;

    //annotation - strategy mapper
    private static Map<String,String> annMapper = new HashMap<String,String>();

    static {
        annMapper.put("org.progressify.spring.annotations.NetworkOnly", "workbox.strategies.NetworkOnly");
        annMapper.put("org.progressify.spring.annotations.NetworkFirst", "workbox.strategies.NetworkFirst");
        annMapper.put("org.progressify.spring.annotations.CacheOnly", "workbox.strategies.CacheOnly");
        annMapper.put("org.progressify.spring.annotations.CacheFirst", "workbox.strategies.CacheFirst");
        annMapper.put("org.progressify.spring.annotations.StaleWhileRevalidate", "workbox.strategies.StaleWhileRevalidate");
    }
    public RegisterRouteStatement(){
        super();
    }

    public RegisterRouteStatement(String strategy, String path,String cacheName){
        this.strategy=strategy;
        this.path=path;
        this.cacheName=cacheName;
    }
    

    public String getJsString(){

        StringBuilder sb = new StringBuilder();
        sb.append("workbox.routing.registerRoute(");
        sb.append("\n\tnew RegExp('"+path+"'),");
        sb.append("\n\tnew "+annMapper.get(strategy)+"({");
        if(cacheName != null){
            sb.append("\n\t\tcacheName: '"+cacheName+"',");
        }
        sb.append("\n\t})");
        sb.append("\n);\n");

        return sb.toString();
    }



}