package com.reworkplan.api.response;

import java.util.HashMap;

public class MetaMapperResponse<T> {

    private HashMap<String , Object> meta;
    private T data;

    public HashMap<String, Object> getMeta() {
        return meta;
    }

    public void setMeta(HashMap<String, Object> meta) {
        this.meta = meta;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        if (data != null) {
            this.data = data;
        }
    }

    public MetaMapperResponse(HashMap<String, Object> meta, T data) {
        this.meta = meta;
        if (data != null){
            this.data = data;
        } else{
            this.data = (T) new HashMap<>();
        }
    }

    public MetaMapperResponse() {
        this.meta = new HashMap<String, Object>();
        this.data = (T) new HashMap<>();
    }

    public void putMeta(String key, Object value){
        this.meta.put(key, value);
    }

}
