package com.aaa.lsjdemo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018/7/14 0014.
 */

public class ValidationCode extends View {
    public ValidationCode(Context context) {
        this(context,null);
    }

    public ValidationCode(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ValidationCode(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
    }
}
