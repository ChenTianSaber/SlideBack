package com.example.saber.slideback;

import android.content.Context;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class SlideBackActivity extends AppCompatActivity{

    String TAG = "SlideBackActivity";

    boolean isEage = false;//判断是否从左边缘划过来

    float shouldFinishPix = 0;
    public static float screenWidth = 0;
    float screenHeight = 0;

    int CANSLIDE_LENGTH = 16;

    View backView;
    SlideBackView slideBackView;
    WindowManager windowManager;
    WindowManager.LayoutParams layoutParams;

    float x;
    float y;
    float downX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels;
        screenHeight = outMetrics.heightPixels;
        shouldFinishPix = screenWidth/3;

        //添加返回的View
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = dp2px(SlideBackView.width);
        layoutParams.height = 0;
        layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.x = (int) (-screenWidth/2);

        backView = LayoutInflater.from(this).inflate(R.layout.view_slideback,null);
        slideBackView = backView.findViewById(R.id.slideBackView);

        View view = getWindow().getDecorView().findViewById(android.R.id.content);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                x = motionEvent.getRawX();
                y = motionEvent.getRawY();
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        downX = motionEvent.getRawX();
                        if(x<=dp2px(CANSLIDE_LENGTH)){
                            isEage = true;
                            layoutParams.height = dp2px(SlideBackView.height);
                            layoutParams.y = (int) (motionEvent.getRawY()-screenHeight/2);
                            slideBackView.updateControlPoint(Math.abs(x));
                            windowManager.addView(backView, layoutParams);
                        }
                        break;

                    case MotionEvent.ACTION_MOVE:
                        float moveX = x - downX;
                        if(isEage){
                            if(Math.abs(moveX)<=shouldFinishPix){
                                slideBackView.updateControlPoint(Math.abs(moveX)/2);
                            }
                            layoutParams.y = (int) (motionEvent.getRawY()-screenHeight/2);
                            windowManager.updateViewLayout(backView, layoutParams);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        //从左边缘划过来，并且最后在屏幕的三分之一外
                        if(isEage){
                            if(x>=shouldFinishPix){
                                slideBackSuccess();
                            }
                            windowManager.removeViewImmediate(backView);
                        }
                        isEage = false;
                        slideBackView.updateControlPoint(0);
                        break;
                }
                return true;
            }
        });
    }

    protected void slideBackSuccess(){
        Log.d(TAG, "slideSuccess");
    }

    public int dp2px(final float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
