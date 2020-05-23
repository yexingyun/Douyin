package com.yxe.ktarmor.ui.camera;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yxe.ktarmor.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

public class PhotoViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);

    }

    @Override
    protected void onResume() {
        super.onResume();
        String path = getIntent().getStringExtra("path");
        try {
//          String filename= Objects.requireNonNull(getExternalFilesDir(Environment.DIRECTORY_PICTURES)).getAbsolutePath()+path;
          String filename=  getApplicationContext().getFilesDir().getAbsolutePath()+path;
            InputStream is =new FileInputStream(filename);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            ImageView image = findViewById(R.id.imageView);
            Glide.with(this).load(bitmap).into(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
