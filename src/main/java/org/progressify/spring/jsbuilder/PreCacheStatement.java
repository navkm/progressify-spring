package org.progressify.spring.jsbuilder;

import org.progressify.spring.processors.*;

public class PreCacheStatement implements Statement{
 
    private PreCacheResult result;

    public PreCacheStatement(PreCacheResult result) {
        super();
        this.result=result;
    }

    public String getJsString(){
 
        StringBuilder stmtBuffer = new StringBuilder();
    
        stmtBuffer.append("workbox.precaching.precacheAndRoute([");

        StringBuilder vBuilder= new StringBuilder();
        for(String versionedURL : result.getVersionedURL()){
            vBuilder.append("\n\t'"+versionedURL+"',");
        }
        stmtBuffer.append(vBuilder.toString().replaceAll(",$", ""));
        stmtBuffer.append("\n]);\n");
        return stmtBuffer.toString();
    }

}