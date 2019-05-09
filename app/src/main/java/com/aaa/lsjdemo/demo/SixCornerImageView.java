package com.aaa.lsjdemo.demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2018/7/5 0005.
 */

public class SixCornerImageView extends AppCompatImageView {
    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private static final int COLORDRAWABLE_DIMENSION = 1;
    private static final Matrix mShaderMatrix = new Matrix();
    private static final Paint mBitmapPaint = new Paint();
    private final int outerMargin = 10;
    private BitmapShader mBitmapShader;
    private int mBitmapWidth;
    private int mBitmapHeight;
    private int mWidth = 0;
    private Bitmap mBitmap;
    private Path mPath = null;
    private Boolean DownOnEffectiveArea = false;

    public SixCornerImageView(Context context) {
        this(context, null);
    }

    public SixCornerImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SixCornerImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setClickable(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable == null || getWidth() * getHeight() == 0) {
            return;
        }
        mBitmap = getBitmapFromDrawable(drawable);
        if (mBitmap == null) {
            return;
        }

        setup();
        canvas.drawPath(getHexagonPath(), mBitmapPaint);

        Paint mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(Color.RED);
        mLinePaint.setStrokeWidth(2);
        canvas.drawPath(getOuterPath(), mLinePaint);

    }

    public Path getOuterPath() {
        if (mPath == null) {
            mPath = new Path();
        }

        int outR = mWidth / 2;

        float p0x = outR;
        float p0y = 0;

        float p1x = (float) (outR + Math.sqrt(outR * outR - (outR / 2) * (outR / 2)));
        float p1y = mWidth / 4;

        float p2x = (float) (outR + Math.sqrt(outR * outR - (outR / 2) * (outR / 2)));
        float p2y = (mWidth / 4 * 3);

        float p3x = outR;
        float p3y = 2 * outR;

        float p4x = (float) (outR - Math.sqrt(outR * outR - (outR / 2) * (outR / 2)));
        float p4y = (mWidth / 4 * 3);

        float p5x = (float) (outR - Math.sqrt(outR * outR - (outR / 2) * (outR / 2)));
        float p5y = mWidth / 4;


        mPath.reset();
        mPath.moveTo(p0x, p0y);
        mPath.lineTo(p1x, p1y);
        mPath.lineTo(p2x, p2y);
        mPath.lineTo(p3x, p3y);
        mPath.lineTo(p4x, p4y);
        mPath.lineTo(p5x, p5y);
        mPath.lineTo(p0x, p0y);

        return mPath;
    }

    public Path getHexagonPath() {
        if (mPath == null) {
            mPath = new Path();
        }

        int outR = mWidth / 2;

        float p0x = outR;
        float p0y = 0 + outerMargin;

        float p1x = (float) (outR + Math.sqrt(outR * outR - (outR / 2) * (outR / 2))) - outerMargin;
        float p1y = mWidth / 4 + outerMargin / 2;

        float p2x = (float) (outR + Math.sqrt(outR * outR - (outR / 2) * (outR / 2))) - outerMargin;
        float p2y = (mWidth / 4 * 3) - outerMargin / 2;

        float p3x = outR;
        float p3y = 2 * outR - outerMargin;

        float p4x = (float) (outR - Math.sqrt(outR * outR - (outR / 2) * (outR / 2))) + outerMargin;
        float p4y = (mWidth / 4 * 3) - outerMargin / 2;

        float p5x = (float) (outR - Math.sqrt(outR * outR - (outR / 2) * (outR / 2))) + outerMargin;
        float p5y = mWidth / 4 + outerMargin / 2;


        mPath.reset();
        mPath.moveTo(p0x, p0y);
        mPath.lineTo(p1x, p1y);
        mPath.lineTo(p2x, p2y);
        mPath.lineTo(p3x, p3y);
        mPath.lineTo(p4x, p4y);
        mPath.lineTo(p5x, p5y);
        mPath.lineTo(p0x, p0y);

        return mPath;
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        super.setOnTouchListener(l);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                CheckIfDownOnffectiveArea(event);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //if (event.getAction() == MotionEvent.ACTION_DOWN) {
        if (!CheckIfDownOnffectiveArea(event)) {
            return false;
        }
        // }
        return super.dispatchTouchEvent(event);
    }

    private Boolean CheckIfDownOnffectiveArea(MotionEvent event) {
        DownOnEffectiveArea = computeRegion(mPath).contains((int) event.getX(), (int) event.getY());
        return DownOnEffectiveArea;
    }

    private Region computeRegion(Path path) {
        Region region = new Region();
        RectF f = new RectF();
        path.computeBounds(f, true);
        region.setPath(path, new Region((int) f.left, (int) f.top, (int) f.right, (int) f.bottom));
        return region;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);//限制为正方形
        mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(mWidth, mWidth);
    }

    public int getmWidth() {
        return mWidth;
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        invalidate();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        invalidate();
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        invalidate();
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
//        java 中的instanceof 运算符是用来在运行时指出对象是否是特定类的一个实例。
//        instanceof通过返回一个布尔值来指出，这个对象是否是这个特定类或者是它的子类的一个实例。
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            Bitmap bitmap;
            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION,
                        COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);
            } else {
                //根据参数创建新位图
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(), BITMAP_CONFIG);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, mWidth, mWidth);
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }

    private void setup() {
        if (mBitmap != null) {
            mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mBitmapPaint.setAntiAlias(true);
            mBitmapHeight = mBitmap.getHeight();
            mBitmapWidth = mBitmap.getWidth();
            updateShaderMatrix();
            mBitmapPaint.setShader(mBitmapShader);
        }
    }


    private void updateShaderMatrix() {
        float scale;
        mShaderMatrix.set(null);
        if (mBitmapWidth != mBitmapHeight) {
            scale = Math.max((float) mWidth / mBitmapWidth, (float) mWidth / mBitmapHeight);
        } else {
            scale = (float) mWidth / mBitmapWidth;
        }

        mShaderMatrix.setScale(scale, scale);//放大铺满

        float dx = mWidth - mBitmapWidth * scale;
        float dy = mWidth - mBitmapHeight * scale;
        mShaderMatrix.postTranslate(dx / 2, dy / 2);//平移居中
        mBitmapShader.setLocalMatrix(mShaderMatrix);
    }
}
