package com.innso.mobile.ui.models.list;

import com.innso.mobile.ui.interfaces.GenericCategoryItem;

public class CategoryItem implements GenericCategoryItem<String> {
    public String category;

    public CategoryItem(String category) {
        this.category = category;
    }

    public String getData() {
        return this.category;
    }

    public int getType() {
        return 1004;
    }

    public String getCategoryName() {
        return this.category;
    }

    public int compareTo(GenericCategoryItem categoryItem) {
        return this.category.compareTo(categoryItem.getCategoryName());
    }
}