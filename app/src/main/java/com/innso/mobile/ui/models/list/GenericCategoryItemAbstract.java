package com.innso.mobile.ui.models.list;

import com.innso.mobile.ui.interfaces.GenericCategoryItem;

public class GenericCategoryItemAbstract implements GenericCategoryItem<Object> {

    private Object data;
    private int type;
    private String category;

    public GenericCategoryItemAbstract(Object data, String category) {
        this.data = data;
        this.category = category;
    }

    public GenericCategoryItemAbstract(Object data, int type, String category) {
        this.data = data;
        this.type = type;
        this.category = category;
    }

    public Object getData() {
        return this.data;
    }

    public int getType() {
        return this.type;
    }

    public String getCategoryName() {
        return this.category;
    }

    public int compareTo(GenericCategoryItem categoryItem) {
        return this.category.compareTo(categoryItem.getCategoryName());
    }
}
