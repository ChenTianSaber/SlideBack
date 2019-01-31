package com.saber.chentianslideback;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class SlideBackView extends View {
    String TAG = "SlideBackView";

    Path path, arrowPath;
    Paint paint, arrowPaint;

    float controlX = 0;//曲线的控制点

    String backViewColor = "#B3000000";

    float backViewHeight = 0;
    public static float width = 40;
    public static float height = 260;

    //下面是配置侧滑返回可以从屏幕什么位置召唤出来
    public static int LEFT = 0;
    public static int RIGHT = 1;
    public static int ALL = 2;

    public static int SLIDEBACK_DIRECTION = ALL;

    int offset;

    public SlideBackView(Context context) {
        this(context, null);
    }

    public SlideBackView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideBackView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        backViewHeight = dp2px(260);

        path = new Path();
        arrowPath = new Path();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.parseColor(backViewColor));
        paint.setStrokeWidth(1);

        arrowPaint = new Paint();
        arrowPaint.setAntiAlias(true);
        arrowPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        arrowPaint.setColor(Color.WHITE);
        arrowPaint.setStrokeWidth(5);
        arrowPaint.setStrokeCap(Paint.Cap.ROUND);

        setAlpha(0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画外面的东西
        float x1 = SlideBackActivity.screenWidth - SlideBackActivity.screenWidth * offset;

        path.reset();
        path.moveTo(x1, dp2px(40));
        path.quadTo(x1, backViewHeight / 4, Math.abs(x1 - controlX / 3), backViewHeight * 3 / 8);

        path.quadTo(Math.abs(x1 - controlX * 5 / 8), backViewHeight / 2, Math.abs(x1 - controlX / 3), backViewHeight * 5 / 8);

        path.quadTo(x1, backViewHeight * 6 / 8, x1, backViewHeight-dp2px(40));
        canvas.drawPath(path, paint);

        //画里面的箭头
        float x2 = Math.abs(SlideBackActivity.screenWidth - SlideBackActivity.screenWidth * offset - controlX / 6);

        arrowPath.reset();
        arrowPath.moveTo(x2 + (dp2px(5) * (controlX / (SlideBackActivity.screenWidth / 4f))), backViewHeight * 15.5f / 32);
        arrowPath.lineTo(x2, backViewHeight * 16f / 32);
        arrowPath.moveTo(x2, backViewHeight * 16f / 32);
        arrowPath.lineTo(x2 + (dp2px(5) * (controlX / (SlideBackActivity.screenWidth / 4f))), backViewHeight * 16.5f / 32);
        canvas.drawPath(arrowPath, arrowPaint);

        setAlpha(controlX / (SlideBackActivity.screenWidth / 6));
    }

    public void updateControlPoint(float controlX, int offset) {
        Log.d(TAG, "updateControlPoint: " + controlX);
        this.controlX = controlX;
        this.offset = 1 - offset;
        invalidate();
    }

    public float dp2px(final float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return dpValue * scale + 0.5f;
    }
}
