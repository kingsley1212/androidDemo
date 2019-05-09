package com.aaa.lsjdemo.demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Administrator on 2018/7/9 0009.
 */

@SuppressLint("AppCompatCustomView")
public class ShiView extends ImageView {
    private Paint paint;
    private Path path;

    public ShiView(Context context) {
        this(context, null);
    }

    public ShiView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public ShiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        path = new Path();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        float w = getWidth();
        float h = getHeight();
        float x1 = getWidth() / 2;
        path.moveTo(0, -200);
        path.lineTo(-(float) Math.sqrt((double) 30000), -100);
        path.lineTo(-(float) Math.sqrt((double) 30000), 100);
        path.lineTo(0, 200);
        path.lineTo((float) Math.sqrt((double) 30000), 100);
        path.lineTo((float) Math.sqrt((double) 30000), -100);
        path.close();
        //        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawPath(path, paint);
//        Drawable drawable = getDrawable();
//        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
    }
}
