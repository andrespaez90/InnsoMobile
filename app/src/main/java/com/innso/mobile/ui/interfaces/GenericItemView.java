package com.innso.mobile.ui.interfaces;

public interface GenericItemView<T> {

    void bind(T var1);

    T getData();

    void setSelected(boolean var1);
}
