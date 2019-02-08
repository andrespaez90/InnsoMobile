package com.innso.mobile.ui.itemViews;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.innso.mobile.R;
import com.innso.mobile.api.models.users.UserRequest;
import com.innso.mobile.databinding.ItemDetailUserBinding;
import com.innso.mobile.ui.interfaces.GenericItemView;
import com.innso.mobile.utils.StringUtils;

public class ItemDetailUser extends FrameLayout implements GenericItemView<UserRequest> {

    private ItemDetailUserBinding binding;

    private UserRequest userRequest;

    public ItemDetailUser(@NonNull Context context) {
        super(context);
        init();
    }

    public ItemDetailUser(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ItemDetailUser(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.item_detail_user, this, true);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void bind(UserRequest data) {
        this.userRequest = data;
        binding.textViewName.setText(StringUtils.setSpannablesFromRegex(userRequest.getName()+"\n"+userRequest.getEmail(),
                "^(.*?)\\n", new StyleSpan(Typeface.BOLD), new RelativeSizeSpan(1.2f)));
        binding.textViewPhone.setText(userRequest.getPhone());

    }

    @Override
    public UserRequest getData() {
        return userRequest;
    }
}
