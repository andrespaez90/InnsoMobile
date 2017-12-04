package com.innso.mobile.ui.interfaces;


public interface GenericCategoryItem<T> extends GenericItem<T> {
    String getCategoryName();

    int compareTo(GenericCategoryItem var1);
}
