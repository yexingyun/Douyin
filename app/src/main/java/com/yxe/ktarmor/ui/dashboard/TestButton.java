package com.yxe.ktarmor.ui.dashboard;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatButton;

/**
 * Created by 叶兴运 on
 * 2020/5/18 0018.11:09
 */
public class TestButton extends AppCompatButton {
    public static final String TAG = "TestButton";
    public TestButton(Context context) {
        this(context,null);
    }

    public TestButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TestButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            Log.e(TAG, "dispatchTouchEvent===ACTION_DOWN");
        }else if (event.getAction()==MotionEvent.ACTION_MOVE){
            Log.e(TAG, "dispatchTouchEvent===ACTION_MOVE");
        }else if (event.getAction()==MotionEvent.ACTION_UP){
            Log.e(TAG, "dispatchTouchEvent===ACTION_UP");
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            Log.e(TAG, "onTouchEvent===ACTION_DOWN");
        }else if (event.getAction()==MotionEvent.ACTION_MOVE){
            Log.e(TAG, "onTouchEvent===ACTION_MOVE");
        }else if (event.getAction()==MotionEvent.ACTION_UP){
            Log.e(TAG, "onTouchEvent===ACTION_UP");
        }
        return super.onTouchEvent(event);
//        return true;
    }


}
