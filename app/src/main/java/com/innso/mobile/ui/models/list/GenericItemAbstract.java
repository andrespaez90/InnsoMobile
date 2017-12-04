package com.innso.mobile.ui.models.list;


import com.innso.mobile.ui.interfaces.GenericItem;

public class GenericItemAbstract implements GenericItem<Object> {

    private Object data;
    private int type;

    public GenericItemAbstract(Object data) {
        this.data = data;
    }

    public GenericItemAbstract(Object data, int type) {
        this.data = data;
        this.type = type;
    }

    public Object getData() {
        return this.data;
    }

    public int getType() {
        return this.type;
    }
}
