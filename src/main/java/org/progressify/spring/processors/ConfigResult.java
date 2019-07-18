package org.progressify.spring.processors;



public class ConfigResult implements Result{

    private String swDirectory;
    private String swFileName;

    public ConfigResult(){
    }
       
    public String getSwDirectory(){
        return swDirectory;
    }

    public void setSwDirectory(String swDirectory){
        this.swDirectory = swDirectory;
    }

    public void setSwFileName(String swFileName){
        this.swFileName = swFileName;
    }

    public String getSwFileName(){
        return swFileName;
    }


}

