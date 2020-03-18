package com.reworkplan.api.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MetaListResponse<T> {

    public HashMap<String, Object> getMeta() {
        return meta;
    }

    public void setMeta(HashMap<String, Object> meta) {
        this.meta = meta;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    private HashMap<String , Object> meta;
    private List<T> data;

    public MetaListResponse(HashMap<String, Object> meta, List<T> data) {
        this.meta = meta;
        this.data = data;
    }

    public MetaListResponse() {
        this.meta = new HashMap<String, Object>();
        this.data = new ArrayList<>();
    }

    public void putMeta(String key, Object value){
        this.meta.put(key, value);
    }
}
