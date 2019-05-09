package com.aaa.lsjdemo.demo;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.aaa.lsjdemo.R;

public class Main2Activity extends AppCompatActivity {
    private Button buttonPanel;
    private Boolean aBoolean=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        buttonPanel = (Button) findViewById(R.id.buttonPanel);
//        ValueAnimator valueAnimator = ValueAnimator.ofInt(buttonPanel.getLayoutParams().width,500);
//        //设置动画时长
//        valueAnimator.setDuration(500);
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                int kls = (Integer) valueAnimator.getAnimatedValue();
//                buttonPanel.getLayoutParams().width=kls;
//                //重新绘制
//                buttonPanel.requestLayout();
//            }
//        });
//        valueAnimator.start();
        buttonPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (aBoolean) {
                    // 设置在动画开始时,旋转角度为0度
                    Keyframe kf0 = Keyframe.ofFloat(0f, 0f);
                    // 设置在动画执行50%时,旋转角度为360度
                    Keyframe kf1 = Keyframe.ofFloat(.5f, 90f);
                    // 设置在动画结束时,旋转角度为0度
                    Keyframe kf2 = Keyframe.ofFloat(1f, 180f);
                    // 使用PropertyValuesHolder进行属性名称和值集合的封装
                    PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe(
                            "rotation", kf0, kf1, kf2);
                    // 通过ObjectAnimator进行执行
                    ObjectAnimator.ofPropertyValuesHolder(buttonPanel, pvhRotation)
                            // 设置执行时间(1000ms)
                            .setDuration(1000)
                            // 开始动画
                            .start();
                    aBoolean = false;
                } else {
                    // 设置在动画开始时,旋转角度为0度
                    Keyframe kf0 = Keyframe.ofFloat(0f, 180f);
                    // 设置在动画执行50%时,旋转角度为360度
                    Keyframe kf1 = Keyframe.ofFloat(0.5f, 90f);
                    // 设置在动画结束时,旋转角度为0度
                    Keyframe kf2 = Keyframe.ofFloat(1f, 0f);
                    // 使用PropertyValuesHolder进行属性名称和值集合的封装
                    PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe(
                            "rotation", kf0, kf1, kf2);
                    // 通过ObjectAnimator进行执行
                    ObjectAnimator.ofPropertyValuesHolder(buttonPanel, pvhRotation)
                            // 设置执行时间(1000ms)
                            .setDuration(1000)
                            // 开始动画
                            .start();
                    aBoolean = true;
                }

            }
        });





    }


}
