package org.progressify.spring.jsbuilder;

public class ImportStatement implements Statement{

    private String version;

    public ImportStatement(){
        super();
    }

    public ImportStatement(String version){
        this.version=version;
    }
    public String getJsString(){
        return (version == null)? "importScripts('https://storage.googleapis.com/workbox-cdn/releases/4.3.1/workbox-sw.js');\n":
                                  "importScripts('https://storage.googleapis.com/workbox-cdn/releases/"+version+"/workbox-sw.js');\n";
        
    }
}
