package org.progressify.spring.processors;

import java.util.List;

public class StrategyResult implements Result{
    private String key;
    private List<String> values;
    private String comment;
    private String cacheName;
    private Integer networkTimeoutSeconds;
    private Boolean cacheQueryIgnoreSearch = false;
    private Boolean cacheQueryIgnoreMethod = false;
    private Boolean cacheQueryIgnoreVary = false;

    public StrategyResult(String key, List<String> values, String comment) {
        this.key = key;
        this.values = values;
        this.comment = comment;
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

    public Integer getNetworkTimeoutSeconds() {
        return networkTimeoutSeconds;
    }

    public void setNetworkTimeoutSeconds(Integer networkTimeoutSeconds) {
        this.networkTimeoutSeconds = networkTimeoutSeconds;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key){
        this.key=key;
    }

    public List<String> getValueList(){
        return values;
    }

    public void setValues(List<String> values){
        this.values=values;
    }
    public String getComment(){
        return comment;
    }

    public void setCacheName(String cacheName){
        this.cacheName=cacheName;
    }

    public String getCacheName(){
        return cacheName;
    }

    public void setComment(String comment){
        this.comment=comment;
    }

} 
