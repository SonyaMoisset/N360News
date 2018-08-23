package com.sonyamoisset.android.n360news.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SourcesResponse {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("sources")
    @Expose
    private List<Source> sources = null;

    public String getStatus() {
        return status;
    }

    public List<Source> getSources() {
        return sources;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }
}
