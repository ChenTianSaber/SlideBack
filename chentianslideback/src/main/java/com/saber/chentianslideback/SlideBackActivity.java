package com.saber.chentianslideback;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class SlideBackActivity extends AppCompatActivity {

    String TAG = "SlideBackActivity";

    boolean isEage = false;//判断是否从左边缘划过来

    float shouldFinishPix = 0;
    public static float screenWidth = 0;
    float screenHeight = 0;

    int CANSLIDE_LENGTH = 16;

    View backView;
    SlideBackView slideBackView;

    View containerView;
    FrameLayout slideContainerView;

    float x;
    float y;
    float downX;

    int offset;

    //下面是配置侧滑返回可以从屏幕什么位置召唤出来
    public static int LEFT = 0;
    public static int RIGHT = 1;
    public static int ALL = 2;

    public int SLIDEBACK_DIRECTION = ALL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels;
        screenHeight = outMetrics.heightPixels;
        shouldFinishPix = screenWidth / 3;

        backView = LayoutInflater.from(this).inflate(R.layout.chentian_view_slideback, null);
        slideBackView = backView.findViewById(R.id.slideBackView);


        FrameLayout container = (FrameLayout) getWindow().getDecorView();
        containerView = LayoutInflater.from(this).inflate(R.layout.chentian_view_slide_container, null);
        slideContainerView = containerView.findViewById(R.id.slide_container);
        slideContainerView.addView(backView);
        container.addView(slideContainerView);

        slideContainerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                x = Math.abs(screenWidth * offset - motionEvent.getRawX());
                y = motionEvent.getRawY();

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = motionEvent.getRawX();

                        //判断点击范围与设置的滑出区域是否符合
                        if (SLIDEBACK_DIRECTION == LEFT) {
                            if (downX > screenWidth / 2) {
                                //在右侧区域，直接return
                                return false;
                            } else {
                                offset = 0;
                            }
                        } else if (SLIDEBACK_DIRECTION == RIGHT) {
                            if (downX < screenWidth / 2) {
                                //在左侧区域，直接return
                                return false;
                            } else {
                                offset = 1;
                            }
                        } else if (SLIDEBACK_DIRECTION == ALL) {
                            if (downX > screenWidth / 2) {
                                //在右侧区域，设为RIGHT
                                offset = 1;
                            } else if (downX < screenWidth / 2) {
                                //在左侧区域，设为LEFT
                                offset = 0;
                            }
                        }

                        x = Math.abs(screenWidth * offset - motionEvent.getRawX());

                        if (x <= dp2px(CANSLIDE_LENGTH)) {
                            isEage = true;
                            slideBackView.updateControlPoint(Math.abs(x), offset);
                            setBackViewY(backView, (int) (motionEvent.getRawY()));
                        }
                        break;

                    case MotionEvent.ACTION_MOVE:
                        float moveX = Math.abs(screenWidth * offset - x) - downX;
                        if (isEage) {
                            if (Math.abs(moveX) <= shouldFinishPix) {
                                slideBackView.updateControlPoint(Math.abs(moveX) / 2, offset);
                            }
                            setBackViewY(backView, (int) (motionEvent.getRawY()));
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        //从左边缘划过来，并且最后在屏幕的三分之一外
                        if (isEage) {
                            if (x >= shouldFinishPix) {
                                slideBackSuccess();
                            }
                        }
                        isEage = false;
                        slideBackView.updateControlPoint(0, offset);
                        break;
                }
                if (isEage) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    public void setBackViewY(View view, int y) {
        //判断是否超出了边界
        int topMargin = y - dp2px(SlideBackView.height) / 2;
        if (topMargin < 0 || y > screenHeight - dp2px(SlideBackView.height) / 2) {
            return;
        }
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(view.getLayoutParams());
        layoutParams.topMargin = topMargin;
        view.setLayoutParams(layoutParams);
    }

    protected void slideBackSuccess() {
        Log.d(TAG, "slideSuccess");
    }

    public int dp2px(final float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setSlideBackDirection(int value){
        SLIDEBACK_DIRECTION = value;
    }
}
