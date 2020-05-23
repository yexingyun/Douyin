package com.yxe.ktarmor.ui.dashboard;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * Created by 叶兴运 on
 * 2020/5/18 0018.11:55
 */
public class TestLinerlayout extends LinearLayout {
    public static final String TAG = "TestLinerlayout";
    public TestLinerlayout(Context context) {
        this(context,null);
    }

    public TestLinerlayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TestLinerlayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            Log.e(TAG, "TestLinerlayout---->dispatchTouchEvent===ACTION_DOWN");
        }else if (event.getAction()== MotionEvent.ACTION_MOVE){
            Log.e(TAG, "TestLinerlayout---->dispatchTouchEvent===ACTION_MOVE");
        }else if (event.getAction()==MotionEvent.ACTION_UP){
            Log.e(TAG, "TestLinerlayout---->dispatchTouchEvent===ACTION_UP");
        }
//        return true;
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            Log.e(TAG, "TestLinerlayout---->onInterceptTouchEvent===ACTION_DOWN");
        }else if (event.getAction()==MotionEvent.ACTION_MOVE){
            Log.e(TAG, "TestLinerlayout---->onInterceptTouchEvent===ACTION_MOVE");
        }else if (event.getAction()==MotionEvent.ACTION_UP){
            Log.e(TAG, "TestLinerlayout---->onInterceptTouchEvent===ACTION_UP");
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            Log.e(TAG, "TestLinerlayout---->onTouchEvent===ACTION_DOWN");
        }else if (event.getAction()==MotionEvent.ACTION_MOVE){
            Log.e(TAG, "TestLinerlayout---->onTouchEvent===ACTION_MOVE");
        }else if (event.getAction()==MotionEvent.ACTION_UP){
            Log.e(TAG, "TestLinerlayout---->onTouchEvent===ACTION_UP");
        }
        return super.onTouchEvent(event);
    }


}
