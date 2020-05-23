package com.yxe.ktarmor.ui.boot;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yxe.ktarmor.R;
import com.yxe.mvvm.base.BaseActivity;

import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    /**
     *
     * @param activity
     * @param
     */
    public static void setNavigationBar(Activity activity, int visible){
        View decorView = activity.getWindow().getDecorView();
        //显示NavigationBar
        if (View.GONE == visible){
            int option = SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            decorView.setSystemUiVisibility(option);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupActionBarWithNavController(this, navController);
        NavigationUI.setupWithNavController(navView, navController);
//        setNavigationBar(this,GONE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            Log.e(TAG, "MainActivity---->dispatchTouchEvent===ACTION_DOWN");
        }else if (event.getAction()==MotionEvent.ACTION_MOVE){
            Log.e(TAG, "MainActivity---->dispatchTouchEvent===ACTION_MOVE");
        }else if (event.getAction()==MotionEvent.ACTION_UP){
            Log.e(TAG, "MainActivity---->dispatchTouchEvent===ACTION_UP");
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            Log.e(TAG, "MainActivity---->onTouchEvent===ACTION_DOWN");
        }else if (event.getAction()==MotionEvent.ACTION_MOVE){
            Log.e(TAG, "MainActivity---->onTouchEvent===ACTION_MOVE");
        }else if (event.getAction()==MotionEvent.ACTION_UP){
            Log.e(TAG, "MainActivity---->onTouchEvent===ACTION_UP");
        }
        return super.onTouchEvent(event);
    }
}
