package com.example.mediademo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //暴力方式获取用户权限，随便指定一个请求Code.
        //使用默认的requestPermissions方法会报错，因为这个是在API23 以上才有，所以要用ActivitCompat兼容包下的requestPermissions方法才能正常调用。
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO},100);

        //在activity_main 布局下写三个按钮，录制视频，播放视频以及播放音效

    }

    public void recordVideo(View view) {
        //点击视频录制按钮后，跳转到MediaRecordActivity 去执行录制。
        Intent intent = new Intent(this,MediaRecordActivity.class);
        startActivity(intent);
    }

    public void playVideo(View view) {
    }

    public void playAudio(View view) {
    }
}