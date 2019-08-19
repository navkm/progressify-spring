package org.progressify.spring.jsbuilder;

import java.io.*;
import java.util.*;

public class Builder {

    private String directory;
    private String fileName;

    private LinkedList<Statement> statements = new LinkedList<Statement>();

    public Builder(String directory, String fileName) {
        this.directory = directory;
        this.fileName = fileName;
    }

    public Builder add(Statement stmt) {
        this.statements.add(stmt);
        return this;
    }

    public Builder addFirst(Statement stmt) {
        this.statements.addFirst(stmt);
        return this;
    }

    public void setDirectory(String directory){
        this.directory=directory;
    }

    public void setFileName(String fileName){
        this.fileName=fileName;
    }

    
    public void flushToFile() throws IOException {

        //System.out.println("\n\n\n Flushing out to "+directory+" file:"+fileName);
        File parent = new File(directory);
        if (!parent.exists()) {
            parent.mkdir();
        }
        File sw = new File(parent, fileName);
        FileWriter fileWriter = new FileWriter(sw);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for (Statement s : statements) {
            printWriter.print(s.getJsString());
            printWriter.print("\n");
        }
        printWriter.close();
    }
}