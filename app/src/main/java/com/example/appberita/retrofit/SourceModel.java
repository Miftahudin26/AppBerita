package com.example.appberita.retrofit;

import com.google.gson.annotations.SerializedName;

public class SourceModel {
    @SerializedName("id")
    private Object id;
    @SerializedName("name")
    private String name;

    public SourceModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Object getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
