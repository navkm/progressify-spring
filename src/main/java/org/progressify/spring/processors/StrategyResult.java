package org.progressify.spring.processors;

import java.util.List;

public class StrategyResult implements Result{
    private String key;
    private List<String> values;
    private String comment;
    private String cacheName;

    public StrategyResult(String key,List<String> values,String comment,String cacheName){
        this.key=key;
        this.values=values;
        this.comment=comment;
        this.cacheName=cacheName;
    }
    public String getKey(){
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
