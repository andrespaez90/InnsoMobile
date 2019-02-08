package com.innso.mobile.ui.itemViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;

import com.innso.mobile.R;
import com.innso.mobile.ui.interfaces.GenericItemView;

public class DefaultCategory extends androidx.appcompat.widget.AppCompatTextView implements GenericItemView<String> {

    private String category;

    public DefaultCategory(Context context) {
        super(context);
        this.init();
    }

    public DefaultCategory(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public DefaultCategory(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private void init() {
        LayoutParams params = new LayoutParams(-1, -2);
        int padding = Float.valueOf(this.getResources().getDimension(R.dimen.spacing_medium)).intValue();
        this.setGravity(1);
        this.setPadding(padding, padding, padding, padding);
        this.setAllCaps(true);
        this.setLayoutParams(params);
    }

    public void bind(String category) {
        this.category = category;
        this.setText(category);
    }

    public String getData() {
        return this.category;
    }

}
