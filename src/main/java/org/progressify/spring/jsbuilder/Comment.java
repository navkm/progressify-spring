package org.progressify.spring.jsbuilder;

public class Comment implements Statement{

    String comment;
    boolean simple;

    public Comment(String comment, boolean simple){
        this.comment=comment;
        this.simple = simple;
    }

    
    public String getJsString(){

        if(simple){
            return "//"+comment;
        } else{
            return "/*\n"+comment+"\n*/\n";
        }
    }

    public static Comment getDefaultComment(){
        String msg = "\nThis Service Worker javascript has been autogenerated by the progressify-spring"+
         " : "+Comment.class.getPackage().getImplementationVersion()+" \n"+
          "This file will be regenerated every build. Hence editing this directly is not recommended.\n";
        Comment comment = new Comment(msg,false);
        return comment;
    }

}