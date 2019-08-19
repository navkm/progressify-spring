package org.progressify.spring.processors;

import java.util.*;

public class PreCacheResult implements Result{

    private List<String> versionedURL;

    public List<String> getVersionedURL() {
        return versionedURL;
    }

    public void setVersionedURL(List<String> versionedURL) {
        this.versionedURL = versionedURL;
    }

}